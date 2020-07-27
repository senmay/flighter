package basic_travel_agency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalTime;

@Service
public class EmailCreatorService {
    public static final String MAIL_TYPE_USER_CUSTOM_NOTIFICATIONS = "CUSTOM_NOTIFICATIONS";

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    private boolean isAfterNoon() {
        return LocalTime.now().isAfter(LocalTime.NOON);
    }

    private String buildUserCustomNotificationEmail(String message) {

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("portal_url", "https://floating-lake-32865.herokuapp.com/");
        context.setVariable("button", "Visit website");
        context.setVariable("goodbye_message", "Have a nice day");
        context.setVariable("show_button", true);
        context.setVariable("is_after_12", isAfterNoon());
        context.setVariable("company_details", "Company: weekend flights");
        context.setVariable("customer_name", "TEST NAME HERE");

        return templateEngine.process("mail/custom-user-notification-mail", context);
    }


    public String createMail(final String mailType, final String message) {
        switch (mailType) {
            case MAIL_TYPE_USER_CUSTOM_NOTIFICATIONS:
                return buildUserCustomNotificationEmail(message);
            default:
                return null;
        }
    }
}
