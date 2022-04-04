package com.payment.entity;

public class StripeResponse {
    private String sessionId;
    private String url;
    private String paymentId;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public StripeResponse(String url, String sessionId,String paymentId) {
        this.sessionId = sessionId;
        this.url = url;
        this.paymentId = paymentId;
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
