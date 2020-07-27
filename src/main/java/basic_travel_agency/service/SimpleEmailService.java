package basic_travel_agency.service;

import basic_travel_agency.domain.Mail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class SimpleEmailService {
    private final EmailCreatorService mailCreatorService;
    private final JavaMailSender javaMailSender;

    public void send(final String mailType, final Mail mail) {
        log.info("Preparing mail to send");
        try {
            javaMailSender.send(createMimeMessage(mailType, mail));
            log.info("Mail sent properly");
        } catch (MailException e) {
            log.error("Failed to send mail: ", e.getMessage(), e);
        }
    }

    private MimeMessagePreparator createMimeMessage(final String mailType, final Mail mail) { //MIME to standard służący do wysyłania wiadomości e-mail. Dzięki temu standardowi, jesteśmy w stanie wysyłać obrazy, dźwięk i video.
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setCc(mail.getToCc());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.createMail(mailType, mail.getMessage()), true);
        };
    }
}
