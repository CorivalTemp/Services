package org.realityfn.models.otp;

import org.realityfn.enums.OTPType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "one_time_passwords")
public class AccountOTPModel {
    public String accountId;
    public String OTP;
    public long counterUsed;
    public Date createdAt;
    public Date expiresAt;
    public OTPType type;
}
