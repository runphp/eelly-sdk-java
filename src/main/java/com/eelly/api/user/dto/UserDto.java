package com.eelly.api.user.dto;

public class UserDto {
    public String username;
    public String mobile;
    public String avatar;
    public Integer uid;

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", mobile='" + mobile + '\'' +
                ", avatar='" + avatar + '\'' +
                ", uid=" + uid +
                '}';
    }
}
