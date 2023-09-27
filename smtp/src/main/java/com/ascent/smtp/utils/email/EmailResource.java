package com.ascent.smtp.utils.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class EmailResource {

    @Autowired
    private BaseEmailService baseEmailService;

    @PostMapping("/send-mail")
    public List<Map<String, String>> sendMail(@RequestBody List<EmailDetailsDTO> details) throws MessagingException {
        return baseEmailService.sendEmail(details);
    }

    // Sending email with attachment
    @PostMapping("/send-mail-with-attachment")
    public void sendMailWithAttachment(@RequestBody List<EmailDetailsDTO> details) throws MessagingException {
        baseEmailService.sendEmail(details);
    }
}
