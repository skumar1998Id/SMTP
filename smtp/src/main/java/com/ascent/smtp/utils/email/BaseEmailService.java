package com.ascent.smtp.utils.email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.*;

@Service
public class BaseEmailService {

    @Value("${HOST}")
    private String host;

    @Value("${USER_NAME}")
    private String userName;

    @Value("${PASSWORD}")
    private String password;

    @Value("${PORT}")
    private String port;

    @Value("${FROM_EMAIL}")
    private String fromMail;

    @Value("${AUTH}")
    private String auth;

    @Value("${DEBUGS}")
    private String debug;

    @Value("${START_TLS}")
    private String startTls;

    @Value("${PROTOCOL}")
    private String protocol;

    @Value("${SSL_PROTOCOL}")
    private String ssl;

    private final Logger log = LoggerFactory.getLogger(BaseEmailService.class);


    public List<Map<String, String>> sendEmail(List<EmailDetailsDTO> details) throws MessagingException {
        List<Map<String, String>> status = new ArrayList<>();
        Map<String, String> map = new LinkedHashMap<>();
        Set<EmailDetailsDTO> set = new HashSet<>(details);
        List<EmailDetailsDTO> newList = new ArrayList<>(set);

        try {
            trustAllCerts();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Prepare Sender
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(Integer.parseInt(port));
        mailSender.setUsername(userName);
        mailSender.setPassword(password);

        // Set mailProperties
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.debug", debug);
        javaMailProperties.put("mail.smtp.auth", auth);
        javaMailProperties.put("mail.smtp.starttls.enable", startTls);
        javaMailProperties.put("mail.transport.protocol ", protocol);
        javaMailProperties.put("mail.smtp.ssl.protocols", ssl); // Without SSL Protocol, will get ssl handshake error

        mailSender.setJavaMailProperties(javaMailProperties);
        mailSender.getSession().setDebug(true);

        // Send Email
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name()
        );

        int contactedCount = 0;

        for (EmailDetailsDTO emailDetailsDto : newList) {
            if (emailDetailsDto.getEmail() != null) {
                helper.setTo(emailDetailsDto.getEmail());
                helper.setText(emailDetailsDto.getMsgBody(), true);
                helper.setSubject("AutoBCM");
                helper.setFrom(fromMail);
                if (emailDetailsDto.getAttachment() != null && !emailDetailsDto.getAttachment().isEmpty()) {
                    FileSystemResource file = new FileSystemResource(new File(emailDetailsDto.getAttachment()));
                    helper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
                }
                mailSender.send(message);
                contactedCount++;

                log.info("Email message =====>" + message);

                log.info("Email sent to =====>" + emailDetailsDto.getEmail());
            }
        }
        map.put("email", "success");
        map.put("contacted Count", String.valueOf(contactedCount));
        status.add(map);


        return status;
    }

    public static void trustAllCerts() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {}

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {}
            },
        };

        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = (hostname, session) -> true;

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }
}
