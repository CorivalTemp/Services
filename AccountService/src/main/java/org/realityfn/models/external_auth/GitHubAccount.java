package org.realityfn.models.external_auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitHubAccount {
    @JsonProperty("login")
    public String Login;

    @JsonProperty("id")
    public long Id;

    public GitHubAccount() {}
    public GitHubAccount(String login, long id) {
        Login = login;
        Id = id;
    }
}
