package basic_travel_agency.scheduler;

import basic_travel_agency.api.countries.facade.RestcountriesFacade;
import basic_travel_agency.service.CountryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
@EnableAspectJAutoProxy
public class UpdateCountryDatabaseScheduler {
    @Autowired
    private CountryService countryService;
    @Autowired
    private RestcountriesFacade restcountriesFacade;

    //@Scheduled(fixedDelay = 86400000)
    public void updateCountries() {
        log.info("Updating country database");
        countryService.updateDatabase(restcountriesFacade.getCountries());
    }
}
