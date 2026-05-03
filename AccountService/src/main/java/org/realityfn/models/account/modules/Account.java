package org.realityfn.models.account.modules;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "accounts")
public class Account {
    @Id
    private String id; // This will double as account ids and mongo ids

    @JsonProperty("user")
    @Indexed(unique = true)
    private String user; // Primary username

    @JsonProperty("pwd")
    private String pwd;

    @JsonProperty("roles")
    private List<String> roles = new ArrayList<>(); // Such as read/readWrite

    public Account() {}

    public Account(String id, String user, String pwd, List<String> roles) {
        this.id = id;
        this.user = user;
        this.pwd = pwd;
        this.roles = roles != null ? roles : new ArrayList<>();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public String getPwd() { return pwd; }
    public void setPwd(String pwd) { this.pwd = pwd; }

    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }

    // Role Utilities
    public void addRole(String role) {
        if (!this.roles.contains(role)) {
            this.roles.add(role);
        }
    }

    public boolean hasRole(String role) {
        return this.roles.contains(role);
    }
}
