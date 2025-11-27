package org.realityfn.fortnite.core.models.timeline;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedHashMap;

public class State {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("activeStorefronts")
    public String[] ActiveStorefronts = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("eventNamedWeights")
    public LinkedHashMap<String, Double> EventNamedWeights = null;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("seasonNumber")
    public int SeasonNumber;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("seasonTemplateId")
    public String SeasonTemplateId = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("matchXpBonusPoints")
    public float MatchXpBonusPoints;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("eventPunchCardTemplateId")
    public String EventPunchCardTemplateId = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("seasonBegin")
    public String SeasonBegin = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("seasonEnd")
    public String SeasonEnd = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("seasonDisplayedEnd")
    public String SeasonDisplayedEnd = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("weeklyStoreEnd")
    public String WeeklyStoreEnd = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("dailyStoreEnd")
    public String DailyStoreEnd = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("stwDailyStoreEnd")
    public String StwDailyStoreEnd = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("stwEventStoreEnd")
    public String StwEventStoreEnd = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("stwWeeklyStoreEnd")
    public String StwWeeklyStoreEnd = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("sectionStoreEnds")
    public LinkedHashMap<String, String> SectionStoreEnds = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("activePurchaseLimitingEventIds")
    public String[] ActivePurchaseLimitingEventIds = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("storefront")
    public LinkedHashMap<String, String> Storefront = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("rmtPromotionConfig")
    public String[] RMTPromotionConfig = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("storeEnd")
    public String StoreEnd = null;

    public State(int seasonNumber) {
        SeasonNumber = seasonNumber;
        SeasonTemplateId = "AthenaSeason:athenaseason" + seasonNumber;

        LocalDate today = LocalDate.now(ZoneId.of("UTC"));
        LocalDate nextSunday = today.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        LocalTime targetTime = LocalTime.MIDNIGHT.plusHours(2);

        LocalDate nextDay = LocalDateTime.now(ZoneId.of("UTC")).toLocalTime().isAfter(targetTime) ? today.plusDays(1) : today;

        Instant dailyExpirationInstant = LocalDateTime.of(nextDay, targetTime).toInstant(ZoneOffset.UTC);
        Instant featuredExpirationInstant = LocalDateTime.of(nextSunday, targetTime).toInstant(ZoneOffset.UTC);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("UTC"));

        DailyStoreEnd = dtf.format(dailyExpirationInstant);
        StwDailyStoreEnd = dtf.format(dailyExpirationInstant);

        WeeklyStoreEnd = dtf.format(featuredExpirationInstant);
        StwWeeklyStoreEnd = dtf.format(featuredExpirationInstant);

        SectionStoreEnds = new LinkedHashMap<>();
        SectionStoreEnds.put("Featured", WeeklyStoreEnd);
        SectionStoreEnds.put("Daily", DailyStoreEnd);

        SeasonBegin = dtf.format(Instant.now().minusSeconds(60 * 60 * 24 * 7 * 4));

        ActiveStorefronts = new String[0];
        EventNamedWeights = new LinkedHashMap<>();
        MatchXpBonusPoints = 0.0f;
        EventPunchCardTemplateId = "";
        SeasonEnd = "9999-01-01T00:00:00.000Z";
        SeasonDisplayedEnd = "9999-01-01T00:00:00.000Z";
        StwEventStoreEnd = "9999-01-01T00:00:00.000Z";
    }

    public State(boolean isStandaloneStore) {
        LocalDate today = LocalDate.now(ZoneId.of("UTC"));
        LocalDate nextSunday = today.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        LocalTime targetTime = LocalTime.MIDNIGHT.plusHours(2);

        LocalDate nextDay = LocalDateTime.now(ZoneId.of("UTC")).toLocalTime().isAfter(targetTime) ? today.plusDays(1) : today;

        Instant dailyExpirationInstant = LocalDateTime.of(nextDay, targetTime).toInstant(ZoneOffset.UTC);
        Instant featuredExpirationInstant = LocalDateTime.of(nextSunday, targetTime).toInstant(ZoneOffset.UTC);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("UTC"));

        DailyStoreEnd = dtf.format(dailyExpirationInstant);
        StwDailyStoreEnd = dtf.format(dailyExpirationInstant);

        WeeklyStoreEnd = dtf.format(featuredExpirationInstant);
        StwWeeklyStoreEnd = dtf.format(featuredExpirationInstant);

        SectionStoreEnds = new LinkedHashMap<>();
        SectionStoreEnds.put("Featured", WeeklyStoreEnd);
        SectionStoreEnds.put("Daily", DailyStoreEnd);

        ActivePurchaseLimitingEventIds = new String[0];
        Storefront = new LinkedHashMap<>();
        RMTPromotionConfig = new String[0];
        StoreEnd = "0001-01-01T00:00:00.000Z";
    }

    public State() {
    }
}
