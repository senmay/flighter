package basic_travel_agency.scheduler;

import basic_travel_agency.config.AdminConfig;
import basic_travel_agency.domain.*;
import basic_travel_agency.service.MailSentRecordService;
import basic_travel_agency.service.SimpleEmailService;
import basic_travel_agency.service.WeekendFlightOffersCreator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class NotificationSchedulerTest {
    @InjectMocks
    private NotificationScheduler notificationScheduler;
    @Mock
    private MailSentRecordService mailSentRecordService;
    @Mock
    private AdminConfig adminConfig;
    @Mock
    private SimpleEmailService simpleEmailService;
    @Mock
    private WeekendFlightOffersCreator offersCreator;

    @Test
    public void testSendNotificationEmail() {
        //Given
        User testUser = User.builder()
                .email("test@test.com")
                .build();
        NotificationPreference testPreference = NotificationPreference.builder()
                .user(testUser)
                .build();
        TripOffer testOffer = new TripOffer();
        testOffer.setPrice( BigDecimal.ONE );
        testOffer.setReturnConnection( new FlightConnectionOfferWithWeather() );
        testOffer.setThereConnection(new FlightConnectionOfferWithWeather() );
        Map<NotificationPreference, TripOffer> testMap = new HashMap<>();
        testMap.put(testPreference, testOffer);

        when(adminConfig.getApplicationEmail()).thenReturn("test@test.com");
        when(offersCreator.getPreferencesAndOffers()).thenReturn( testMap );

        //When
        notificationScheduler.notifyAboutOffers();
        //Then
        verify(simpleEmailService, times(1)).send(anyString(), any(Mail.class));
    }
}
