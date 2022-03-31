package com.payment.entity;

public class StripeResponse {
    private String sessionId;
    private String url;

    public StripeResponse(String url,String sessionId) {
        this.sessionId = sessionId;
        this.url = url;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
