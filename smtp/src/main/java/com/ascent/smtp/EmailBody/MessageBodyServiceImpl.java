package com.ascent.smtp.EmailBody;

import com.ascent.smtp.utils.email.BaseEmailService;
import com.ascent.smtp.utils.email.EmailDetailsDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageBodyServiceImpl {

    @Value("${TO_EMAIL}")
    private String toMail;

    @Value("${MESSAGE}")
    private String message;


    private final BaseEmailService baseEmailService;

    public MessageBodyServiceImpl(BaseEmailService baseEmailService) {
        this.baseEmailService = baseEmailService;
    }


    public void sendTestMail() throws MessagingException {
        List<EmailDetailsDTO> emailDetailsDtos = new ArrayList<>();

        EmailDetailsDTO emailDetailsDto = new EmailDetailsDTO();

        emailDetailsDto.setSubject("AutoBCM 5.0 Notification");

        emailDetailsDto.setEmail(toMail);
        emailDetailsDto.setLanguage("en_US");
        emailDetailsDto.setMsgBody(message);
        emailDetailsDtos.add(emailDetailsDto);
        try{
            baseEmailService.sendEmail(emailDetailsDtos);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
