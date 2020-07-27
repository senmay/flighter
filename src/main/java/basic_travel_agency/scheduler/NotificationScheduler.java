package basic_travel_agency.scheduler;

import basic_travel_agency.config.AdminConfig;
import basic_travel_agency.domain.Mail;
import basic_travel_agency.domain.MailSentRecord;
import basic_travel_agency.domain.NotificationPreference;
import basic_travel_agency.domain.TripOffer;
import basic_travel_agency.service.EmailCreatorService;
import basic_travel_agency.service.MailSentRecordService;
import basic_travel_agency.service.SimpleEmailService;
import basic_travel_agency.service.WeekendFlightOffersCreator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
@Slf4j
public class NotificationScheduler {
    @Autowired
    private SimpleEmailService simpleEmailService;
    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    private WeekendFlightOffersCreator offersCreator;
    @Autowired
    private MailSentRecordService mailRecordService;

    private static final String SUBJECT = "Flights matching your preferences";

    private Map<String, List<String>> groupEmailAndOffers(Map<NotificationPreference, TripOffer> preferencesAndOffers) {
        Map<String, List<String>> result = new HashMap<>();

        for (Map.Entry<NotificationPreference, TripOffer> entry : preferencesAndOffers.entrySet()) {
            String email = entry.getKey().getUser().getEmail();
            String offer = entry.getValue().toString();

            if (result.containsKey(email)) {
                result.get(email).add(offer);
            } else {
                result.put(email, Arrays.asList(offer));
            }
        }
        return result;
    }

    private Map<String, String> getEmailAndOfferMessage(Map<NotificationPreference, TripOffer> preferencesAndOffers) {
        Map<String, List<String>> emailAndOffers = groupEmailAndOffers(preferencesAndOffers);
        Map<String, String> result = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : emailAndOffers.entrySet()) {
            StringBuilder builder = new StringBuilder();
            builder.append("Offers that match your requested trip preferences: ");
            entry.getValue().forEach(e -> builder.append(e + "\r\n"));
            result.put(entry.getKey(), builder.toString());
        }
        return result;
    }

    //@Scheduled(fixedDelay = 14400000)
    public void notifyAboutOffers() {
        Map<String, String> emailAndOfferMessage = getEmailAndOfferMessage(offersCreator.getPreferencesAndOffers());

        for (Map.Entry<String, String> entry : emailAndOfferMessage.entrySet()) {
            simpleEmailService.send(EmailCreatorService.MAIL_TYPE_USER_CUSTOM_NOTIFICATIONS, Mail.builder()
                    .mailTo(entry.getKey())
                    .toCc(adminConfig.getApplicationEmail())
                    .subject(SUBJECT)
                    .message(entry.getValue())
                    .build()
            );

            MailSentRecord record = new MailSentRecord(LocalDateTime.now(), entry.getKey());
            mailRecordService.addRecord(record);
        }
    }
}
