package com.ascent.smtp.utils.email;

import java.io.Serializable;

public class EmailDetailsDTO implements Serializable {

    private String email; // mandatory mail id
    private String phoneNumber; // mandatory SA number with country code. ex: 9666345679632
    private String msgBody; // mandatory
    private String subject;
    private String attachment; // optional
    private String language; // mandatory en_US/ar_SA
    private Long templateId; // for cm message templates (optional)

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
