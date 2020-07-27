package basic_travel_agency.service;

import basic_travel_agency.api.skyscanner.domain.Airport;
import basic_travel_agency.api.skyscanner.domain.Flight;
import basic_travel_agency.api.skyscanner.facade.SkyScannerFacade;
import basic_travel_agency.api.weather.domain.DailyWeatherForecast;
import basic_travel_agency.api.weather.facade.WeatherClientFacade;
import basic_travel_agency.domain.FlightConnectionOfferWithWeather;
import basic_travel_agency.domain.FlightSearchRequest;
import basic_travel_agency.domain.NotificationPreference;
import basic_travel_agency.domain.TripOffer;
import basic_travel_agency.exceptions.UnableToGetWeatherForecastException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class WeekendFlightOffersCreator {
    private final FlightSearchRequestService requestService;
    private final NotificationPreferenceService notificationPreferenceService;
    private final SkyScannerFacade skyScannerFacade;
    private final WeatherClientFacade weatherClientFacade;

    private LocalDate getNextFriday() {
        return LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
    }

    private LocalDate getSundayAfterDeparture() {
        return getNextFriday().plusDays(2);
    }

    private Set<String> getAllCitiesFromPreferences(List<NotificationPreference> allPreferences) {
        Set<String> cities = new HashSet<>();
        allPreferences.forEach(e -> {
            cities.add(e.getDepartureCity().toLowerCase());
            cities.add(e.getDestinationCity().toLowerCase());
        });
        return cities;
    }

    private Map<String, List<Airport>> getAvailableAirportsForPreferredCities(Set<String> cities) {
        Map<String, List<Airport>> citiesAndAirports = new HashMap<>();
        cities.forEach(
                e -> citiesAndAirports.put(e, skyScannerFacade.getAirportsInCity(e))
        );
        return citiesAndAirports;
    }

    private Map<String, String> createReverseMappingForCitiesAndAirports(Map<String, List<Airport>> citiesAndAirports) {
        Map<String, String> result = new HashMap<>();
        citiesAndAirports.entrySet().stream()
                .forEach(entry -> {
                    for (Airport airport : entry.getValue()) {
                        result.put(airport.getAirportCode(), entry.getKey());
                    }
                });

        return result;
    }

    private Set<FlightSearchRequest> getSearchRequestsForPreference(NotificationPreference preference, Map<String, List<Airport>> citiesAndAirports) {
        Set<FlightSearchRequest> requestsForPreference = new HashSet<>();
        List<Airport> departureAirports = citiesAndAirports.get(preference.getDepartureCity().toLowerCase());
        List<Airport> destinationAirports = citiesAndAirports.get(preference.getDestinationCity().toLowerCase());
        Map<String, String> reversedCitiesAndAirports = createReverseMappingForCitiesAndAirports(citiesAndAirports);

        for (Airport departure : departureAirports) {
            for (Airport destination : destinationAirports) {

                FlightSearchRequest thereRequest = FlightSearchRequest.builder()
                        .departureCity(reversedCitiesAndAirports.get(departure.getAirportCode()))
                        .departureAirport(departure.getAirportCode())
                        .destinationCity(reversedCitiesAndAirports.get(destination.getAirportCode()))
                        .destinationAirport(destination.getAirportCode())
                        .departureDay(getNextFriday())
                        .build();
                requestsForPreference.add(thereRequest);
                requestService.addSearchRequest(thereRequest);

                FlightSearchRequest returnRequest = FlightSearchRequest.builder()
                        .departureCity(reversedCitiesAndAirports.get(destination.getAirportCode()))
                        .departureAirport(destination.getAirportCode())
                        .destinationCity(reversedCitiesAndAirports.get(departure.getAirportCode()))
                        .destinationAirport(departure.getAirportCode())
                        .departureDay(getSundayAfterDeparture())
                        .build();
                requestsForPreference.add(returnRequest);
                requestService.addSearchRequest(returnRequest);
            }
        }

        return requestsForPreference;
    }

    public Set<FlightSearchRequest> getAllSearchRequests() {
        Set<FlightSearchRequest> uniqueSearchRequestsForPreferenfces = new HashSet<>();

        List<NotificationPreference> allPreferences = notificationPreferenceService.getAllPreferences();
        Set<String> cities = getAllCitiesFromPreferences(allPreferences);
        Map<String, List<Airport>> citiesAndAirports = getAvailableAirportsForPreferredCities(cities);

        allPreferences.forEach(e -> {
            uniqueSearchRequestsForPreferenfces.addAll(getSearchRequestsForPreference(e, citiesAndAirports));
        });

        return uniqueSearchRequestsForPreferenfces;
    }

    private boolean isNextWeekendDay(LocalDate date) {
        return (date.isAfter(getNextFriday().minusDays(1)) && date.isBefore(getSundayAfterDeparture().plusDays(1)));
    }

    private Double getWeekendAverageTemperature(String city) {
        return weatherClientFacade.getWeatherForecast(city).getDailyForecasts().stream()
                .filter(e -> isNextWeekendDay(e.getDate()))
                .mapToDouble(DailyWeatherForecast::getMaxTemperature)
                .average().orElseThrow(UnableToGetWeatherForecastException::new);
    }

    public Map<String, Double> getWeatherForDestinationCities() {
        Map<String, Double> averageTemperaturesForDestinationCities = new HashMap<>();
        getAllCitiesFromPreferences(notificationPreferenceService.getAllPreferences()).forEach(
                e -> averageTemperaturesForDestinationCities.put(e, getWeekendAverageTemperature(e))
        );

        return averageTemperaturesForDestinationCities;
    }

    private List<Flight> getConnectionsForRequest(FlightSearchRequest request) {
        List<Flight> result = skyScannerFacade.getFlightConnections(request.getDepartureAirport(), request.getDestinationAirport(), request.getDepartureDay());
        for (Flight flight : result) {
            flight.setDepartureCity(request.getDepartureCity());
            flight.setDestinationCity(request.getDestinationCity());
            flight.setDepartureAirport(request.getDepartureAirport());
            flight.setDestinationAirport(request.getDestinationAirport());
        }

        return result;
    }

    public List<Flight> getConnectionsForAllPreferences() {
        List<Flight> result = new ArrayList<>();
        Set<FlightSearchRequest> requests = this.getAllSearchRequests();
        log.info("Flight search requests number: " + requests.size());

        for (FlightSearchRequest request : requests) {
            log.info("Processing request: " + request);
            List<Flight> searchResult = this.getConnectionsForRequest(request);
            log.info("Found " + searchResult.size() + " flight(s) for the request");

            result.addAll(this.getConnectionsForRequest(request));
            log.info("Total result size: " + result.size());
        }

        return result;
    }

    public List<FlightConnectionOfferWithWeather> getAllFlightOffersWithExpectedWeather() {
        List<FlightConnectionOfferWithWeather> result = new ArrayList<>();

        List<Flight> flights = this.getConnectionsForAllPreferences();
        Map<String, Double> weather = this.getWeatherForDestinationCities();

        for (Flight flight : flights) {
            BigDecimal temperature = BigDecimal.valueOf(weather.get(flight.getDestinationCity())).setScale(2, RoundingMode.HALF_EVEN);
            result.add(
                    new FlightConnectionOfferWithWeather(flight, temperature)
            );
        }

        return result;
    }

    public Map<NotificationPreference, TripOffer> getPreferencesAndOffers() {
        log.info("Matching preferences with available connections...");
        Map<NotificationPreference, TripOffer> preferencesAndOffers = new HashMap<>();
        List<NotificationPreference> preferences = notificationPreferenceService.getAllPreferences();
        List<FlightConnectionOfferWithWeather> offers = getAllFlightOffersWithExpectedWeather();

        for (NotificationPreference preference : preferences) {
            log.info("Processing preference: " + preference);
            Optional<FlightConnectionOfferWithWeather> cheapestThereFlight = offers.stream()
                    .filter(offer -> offer.getFlight().getDestinationCity().equals(preference.getDestinationCity().toLowerCase()))
                    .filter(offer -> offer.getFlight().getDepartureCity().equals(preference.getDepartureCity().toLowerCase()))
                    .filter(offer -> offer.getExpectedTemperature().intValue() >= preference.getMinTemperature())
                    .min(FlightConnectionOfferWithWeather::compareTo);

            log.info("Cheapest there connection is: " + cheapestThereFlight);

            Optional<FlightConnectionOfferWithWeather> cheapestReturnFlight = offers.stream()
                    .filter(offer -> offer.getFlight().getDestinationCity().equals(preference.getDepartureCity().toLowerCase()))
                    .filter(offer -> offer.getFlight().getDepartureCity().equals(preference.getDestinationCity().toLowerCase()))
                    .min(FlightConnectionOfferWithWeather::compareTo);
            log.info("Cheapest return connection is: " + cheapestReturnFlight);

            if (cheapestReturnFlight.isPresent() && cheapestThereFlight.isPresent()) {
                FlightConnectionOfferWithWeather thereFlight = cheapestThereFlight.get();
                FlightConnectionOfferWithWeather returnFlight = cheapestReturnFlight.get();
                BigDecimal totalPrice = thereFlight.getFlight().getMinPrice().add(returnFlight.getFlight().getMinPrice());

                if (totalPrice.compareTo(preference.getMaxPrice()) < 1) {
                    log.info("Total price is acceptable, adding to offers");
                    TripOffer offer = new TripOffer(thereFlight, returnFlight, totalPrice);
                    preferencesAndOffers.put(preference, offer);
                }
            }
        }
        return preferencesAndOffers;
    }
}
