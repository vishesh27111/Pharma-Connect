package com.pharmaconnect.pharma.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.mail.Address;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import com.pharmaconnect.pharma.model.EmailDetails;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.FileCopyUtils;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;


    @Spy
    @InjectMocks
    private EmailService emailService;

    @Value("${mail.username}")
    private String sender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendHtmlMailSuccess() throws Exception {

        String firstName = "Test";
        String lastName = "Test";
        String email = "testuser@gmail.com";
        String bloodGroup = "O+";
        String age = "20";
        String phone = "1234567890";
        String address = "south Street";
        Boolean donate = true;
        String recipient = "testrecipient@gmail.com";
        String subject = "test subject";
        String expectedOutput = "Mail Sent Successfully...";

        // Arrange
        EmailDetails details = new EmailDetails();
        details.setFirstName(firstName);
        details.setLastName(lastName);
        details.setAddress(address);
        details.setEmail(email);
        details.setBloodGroup(bloodGroup);
        details.setPhone(phone);
        details.setAge(age);
        details.setDonatedPreviously(donate);
        details.setRecipient(recipient);
        details.setSubject(subject);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = mock(MimeMessageHelper.class);

        // Mock the behavior of MimeMessageHelper method
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        doNothing().when(emailService).setMimeMessageContent(any(), any(), any(), any());

         // Act
        String result = emailService.sendBloodRegistrationDetailsMail(details);

        // Assert
        assertEquals(expectedOutput, result);

        verify(javaMailSender).send(eq(mimeMessage));

    }


}
