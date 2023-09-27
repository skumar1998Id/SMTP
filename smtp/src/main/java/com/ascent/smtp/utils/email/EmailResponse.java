package com.ascent.smtp.utils.email;

public class EmailResponse {

    private Integer status;
    private String message;

    public EmailResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public EmailResponse() {}

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
