package com.pharmaconnect.pharma.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import com.pharmaconnect.pharma.model.EmailDetails;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

     protected Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${mail.username}")
    private String sender;

    // To send a simple email
    public String sendBloodRegistrationDetailsMail(EmailDetails details) {
        try {

            log.info("-----sendBloodRegistrationDetailsMail called---");
            String template = readHtmlTemplate("email-template.html");
        
            Map<String, String> emailValues = new HashMap<>();
            emailValues.put("[FirstName]", details.getFirstName());
            emailValues.put("[LastName]", details.getLastName());
            emailValues.put("[Address]", details.getAddress());
            emailValues.put("[Email]", details.getEmail());
            emailValues.put("[BloodGroup]", details.getBloodGroup());
            emailValues.put("[Phone]", details.getPhone());
            emailValues.put("[Age]", details.getAge());
            emailValues.put("[PreviouslyDonated]", details.getDonatedPreviously().toString());

            String emailBody = template;
            for (Map.Entry<String, String> entry : emailValues.entrySet()) {
                emailBody = emailBody.replace(entry.getKey(), entry.getValue());
            }

            // Add more replacements as needed
            MimeMessage mimeMessage = createMimeMessage(details);
            setMimeMessageContent(mimeMessage, details.getRecipient(), details.getSubject(), emailBody);
            javaMailSender.send(mimeMessage);

            return "Mail Sent Successfully...";
        } catch (MessagingException | IOException e) {
            return "Error while Sending Mail";
        }
    }

    private MimeMessage createMimeMessage(EmailDetails details) {
        return javaMailSender.createMimeMessage();
    }

    void setMimeMessageContent(MimeMessage mimeMessage, String recipient, String subject, String emailBody)
            throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setFrom(sender);
        helper.setTo(recipient);
        helper.setSubject(subject);
        helper.setText(emailBody, true); // true indicates HTML content
    }

    private String readHtmlTemplate(String templatePath) throws IOException {
        ClassPathResource resource = new ClassPathResource(templatePath);
        byte[] templateBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
        return new String(templateBytes, StandardCharsets.UTF_8);
    }
}
