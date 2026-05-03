package org.realityfn.models.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountRegistrationResponse {
    @JsonProperty("accountInfo")
    private AccountResponse accountInfo;

    // Constructors
    public AccountRegistrationResponse() {}

    // Getters and Setters
    public AccountResponse getAccountInfo() { return accountInfo; }
    public void setAccountInfo(AccountResponse accountInfo) { this.accountInfo = accountInfo; }

}
