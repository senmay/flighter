package basic_travel_agency.service;

import basic_travel_agency.domain.Mail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTest {
    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void testSendEmail() {
        //Given
        Mail mail = Mail.builder()
                .mailTo("test@test.com")
                .subject("Test")
                .message("Test message")
                .build();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());

        //When
        simpleEmailService.send(EmailCreatorService.MAIL_TYPE_USER_CUSTOM_NOTIFICATIONS, mail);

        //Then
        verify( javaMailSender, times(1) ).send(any(MimeMessagePreparator.class));
    }

    @Test
    public void testSendEmailAndCc() {
        //Given
        Mail mail = Mail.builder()
                .mailTo("test@test.com")
                .toCc("test@test.com")
                .subject("Test")
                .message("Test message")
                .build();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        mailMessage.setCc(mail.getToCc());

        //When
        simpleEmailService.send(EmailCreatorService.MAIL_TYPE_USER_CUSTOM_NOTIFICATIONS, mail);

        //Then
        verify( javaMailSender, times(1) ).send(any(MimeMessagePreparator.class));
    }

}
