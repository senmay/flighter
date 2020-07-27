package basic_travel_agency.api.restcountries;

import basic_travel_agency.api.countries.domain.Country;
import basic_travel_agency.api.countries.facade.RestcountriesFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestcountriesFacadeTest {
    @Autowired
    private RestcountriesFacade restcountriesFacade;

    @Test
    public void getCountries() {
        //Given
        List<Country> testCountries = restcountriesFacade.getCountries();

        //Then
        assertNotNull(testCountries);
        assertTrue(testCountries.size() > 5);
    }
}