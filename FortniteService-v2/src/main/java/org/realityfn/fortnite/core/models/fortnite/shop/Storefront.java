package org.realityfn.fortnite.core.models.fortnite.shop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Storefront {

    @Field("refreshIntervalHrs")
    private int refreshIntervalHrs;

    @Field("dailyPurchaseHrs")
    private int dailyPurchaseHrs;

    @Field("expiration")
    private String expiration;

    @Field("storefronts")
    private List<StorefrontEntry> storefronts;

    public Storefront() {
        this.refreshIntervalHrs = 24;
        this.dailyPurchaseHrs = 24;
        this.expiration = calculateExpiration();
        this.storefronts = List.of();
    }

    public Storefront(List<StorefrontEntry> storefronts) {
        this.refreshIntervalHrs = 24;
        this.dailyPurchaseHrs = 24;
        this.expiration = calculateExpiration();
        this.storefronts = storefronts;
    }

    public int getRefreshIntervalHrs() {
        return refreshIntervalHrs;
    }

    public void setRefreshIntervalHrs(int refreshIntervalHrs) {
        this.refreshIntervalHrs = refreshIntervalHrs;
    }

    public int getDailyPurchaseHrs() {
        return dailyPurchaseHrs;
    }

    public void setDailyPurchaseHrs(int dailyPurchaseHrs) {
        this.dailyPurchaseHrs = dailyPurchaseHrs;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public List<StorefrontEntry> getStorefronts() {
        return storefronts;
    }

    public void setStorefronts(List<StorefrontEntry> storefronts) {
        this.storefronts = storefronts;
    }

    private String calculateExpiration() {
        LocalDate date = LocalDate.now(ZoneId.of("UTC"));

        // if 6 pm today is before the time at the moment, add a day to the date, so the expiration becomes 6pm tomorrow
        if (LocalTime.MIDNIGHT.plusHours(2).isBefore(LocalTime.now(ZoneId.of("UTC")))) {
            date = date.plusDays(1);
        }

        Instant expirationInstant = LocalDateTime.of(date, LocalTime.MIDNIGHT.plusHours(2)).toInstant(ZoneOffset.UTC);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("UTC"));

        return dtf.format(expirationInstant);
    }

    public void initializeCalculatedFields() {
        if (this.refreshIntervalHrs == 0) {
            this.refreshIntervalHrs = 24;
        }
        if (this.dailyPurchaseHrs == 0) {
            this.dailyPurchaseHrs = 24;
        }

        this.expiration = calculateExpiration();
    }
}