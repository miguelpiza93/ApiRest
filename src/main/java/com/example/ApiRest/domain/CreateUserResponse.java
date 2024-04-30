package com.example.ApiRest.domain;

import com.example.ApiRest.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.UUID;

public class CreateUserResponse {
    private UUID id;
    private Date created;
    private Date modified;
    @JsonProperty("last_login")
    private Date lastLogin;
    private UUID token;
    @JsonProperty("isactive")
    private boolean isActive;

    public CreateUserResponse(User user){
        this.id = user.getId();
        this.created = user.getCreated();
        this.modified = user.getModified();
        this.lastLogin = user.getLastLogin();
        this.token = user.getToken();
        this.isActive = user.isActive();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
