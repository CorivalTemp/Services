package org.realityfn.fortnite.core.models.profiles.attributes;

import org.realityfn.fortnite.core.enums.PlayerBanReasons;
import org.realityfn.fortnite.core.enums.PlayerCompetitiveBanReasons;

public class BanStatus {
    private boolean bBanHasStarted;
    private PlayerBanReasons[] banReasons;
    private double banDurationDays;
    private PlayerCompetitiveBanReasons competitiveBanReason;
    private String additionalInfo;
    private String exploitProgramName;
    private boolean bRequiresUserAck;
    private String banStartTimeUtc;

    public BanStatus() {
    }

    public BanStatus(boolean bBanHasStarted, PlayerBanReasons[] banReasons, double banDurationDays, PlayerCompetitiveBanReasons competitiveBanReason, String additionalInfo, String exploitProgramName, boolean bRequiresUserAck, String banStartTimeUtc) {
        this.bBanHasStarted = bBanHasStarted;
        this.banReasons = banReasons;
        this.banDurationDays = banDurationDays;
        this.competitiveBanReason = competitiveBanReason;
        this.additionalInfo = additionalInfo;
        this.exploitProgramName = exploitProgramName;
        this.bRequiresUserAck = bRequiresUserAck;
        this.banStartTimeUtc = banStartTimeUtc;
    }

    public boolean isbBanHasStarted() {
        return bBanHasStarted;
    }

    public void setbBanHasStarted(boolean bBanHasStarted) {
        this.bBanHasStarted = bBanHasStarted;
    }

    public PlayerBanReasons[] getBanReasons() {
        return banReasons;
    }

    public void setBanReasons(PlayerBanReasons[] banReasons) {
        this.banReasons = banReasons;
    }

    public double getBanDurationDays() {
        return banDurationDays;
    }

    public void setBanDurationDays(double banDurationDays) {
        this.banDurationDays = banDurationDays;
    }

    public PlayerCompetitiveBanReasons getCompetitiveBanReason() {
        return competitiveBanReason;
    }

    public void setCompetitiveBanReason(PlayerCompetitiveBanReasons competitiveBanReason) {
        this.competitiveBanReason = competitiveBanReason;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getExploitProgramName() {
        return exploitProgramName;
    }

    public void setExploitProgramName(String exploitProgramName) {
        this.exploitProgramName = exploitProgramName;
    }

    public boolean isbRequiresUserAck() {
        return bRequiresUserAck;
    }

    public void setbRequiresUserAck(boolean bRequiresUserAck) {
        this.bRequiresUserAck = bRequiresUserAck;
    }

    public String getBanStartTimeUtc() {
        return banStartTimeUtc;
    }

    public void setBanStartTimeUtc(String banStartTimeUtc) {
        this.banStartTimeUtc = banStartTimeUtc;
    }
}
