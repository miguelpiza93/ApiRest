package com.example.ApiRest.domain;

import com.example.ApiRest.entities.User;

import java.util.ArrayList;
import java.util.List;

public class GetUsersResponse {
    private List<UserInfo> users;

    public GetUsersResponse(Iterable<User> userList) {
        this.users = new ArrayList<>();
        for (User user : userList) {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(user.getId());
            userInfo.setCreated(user.getCreated());
            userInfo.setModified(user.getModified());
            userInfo.setLastLogin(user.getLastLogin());
            userInfo.setActive(user.isActive());
            this.users.add(userInfo);
        }
    }

    public List<UserInfo> getUsers() {
        return users;
    }

    public void setUsers(List<UserInfo> users) {
        this.users = users;
    }


}
