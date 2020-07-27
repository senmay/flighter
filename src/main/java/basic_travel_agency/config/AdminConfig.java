package basic_travel_agency.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AdminConfig {
    @Value("${spring.mail.username}")
    private String applicationEmail;

    @Value("${weather.api.key}")
    private String weatherApiKey;

    @Value("${weather.api.baseurl}")
    private String weatherApiBaseUrl;

    @Value("${skyscanner.api.header.host}")
    private String skyScannerApiHeaderHost;

    @Value("${skyscanner.api.header.key}")
    private String skyScannerApiHeaderKey;

    @Value("${skyscanner.api.baseurl}")
    private String skyScannerApiBaseUrl;
}
