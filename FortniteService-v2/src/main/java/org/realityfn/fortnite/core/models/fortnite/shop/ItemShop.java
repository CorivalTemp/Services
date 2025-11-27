package org.realityfn.fortnite.core.models.fortnite.shop;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class ItemShop {

    @JsonProperty("expiration")
    public String Expiration;

    @JsonProperty("cacheExpire")
    public String CacheExpire;

    @JsonProperty("catalogItems")
    public HashMap<String, ArrayList<CatalogItem>> CatalogItems;

    public ItemShop(HashMap<String, ArrayList<CatalogItem>> catalogItems) {
        LocalDate date = LocalDate.now(ZoneId.of("UTC"));

        // if 6 pm today is before the time at the moment, add a day to the date, so the expiration becomes 6pm tomorrow
        if (LocalTime.MIDNIGHT.plusHours(2).isBefore(LocalTime.now(ZoneId.of("UTC")))) {
            date = date.plusDays(1);
        }

        Instant expirationInstant = LocalDateTime.of(date, LocalTime.MIDNIGHT.plusHours(2)).toInstant(ZoneOffset.UTC);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("UTC"));

        Expiration = dtf.format(expirationInstant);
        CacheExpire = dtf.format(expirationInstant);
        CatalogItems = catalogItems;
    }
}
