package com.wondersgroup.qdaio.gett.dto;

public class AccessTokenDto {
    private String token;

    public AccessTokenDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
