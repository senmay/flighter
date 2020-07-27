package basic_travel_agency.service;

import basic_travel_agency.api.countries.domain.Country;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest

public class CountryServiceTest {
    @Autowired
    private CountryService countryService;

    @Before
    public void cleanupDatabase() {
        countryService.deleteAllCountries();
    }

    @Test
    public void testGetCountryByName() {
        //Given
        Country testCountryOne = new Country();
        testCountryOne.setName("Myanmar");
        testCountryOne.setCapital("Rangun");
        countryService.addCountry(testCountryOne);

        //When
        countryService.addCountry(testCountryOne);
        Country result = countryService.getCountryByName("Myanmar");

        //Then
        assertEquals(testCountryOne, result);
    }

    @Test
    public void testUpdateDatabase() {
        //Given
        Country testCountryOne = new Country(1L, "Poland", "Warsaw");
        Country testCountryTwo = new Country(2L, "Germany", "Berlin");
        Country testCountryThree = new Country(3L, "Russia", "Moscow");
        countryService.addCountry(testCountryOne);
        countryService.addCountry(testCountryTwo);
        countryService.addCountry(testCountryThree);

        List<Country> updatingList = Arrays.asList(
                new Country(1L, "Poland", "Warsaw"),
                new Country(2L, "Germany", "Berlin"),
                new Country(3L, "Russia", "Moscow"),
                new Country(4L, "France", "Paris"),
                new Country(5L, "Spain", "Madrid")
        );

        //When
        countryService.updateDatabase(updatingList);
        List<Country> result = countryService.getAllCountries();

        //Then
        assertEquals(5, result.size());
    }
}