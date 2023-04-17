# Flighter

Flighter is a backend application that provides information about flight offers based on user preferences. Users can specify departure and destination cities, maximum total price for the trip, and minimum temperature expected at the destination city during the weekend. The application uses Skyscanner, Weatherbit, and RestCountries APIs to fetch flight and weather information.

**Please note that this project uses RAPIDAPI, which has a very limited number of requests allowed. Also, there is no frontend for this application.**

## Features

- Users can create and manage their preferences for flight notifications.
- Users can register and manage their accounts.
- Users can view flight offers based on their preferences.
- Users can view and manage payments for their reservations.
- Users can view and manage their reservations.

## REST Controllers

The project contains the following REST controllers:

- `NotificationPreferenceController`: Handles CRUD operations for user preferences.
- `PaymentController`: Handles CRUD operations for payments.
- `UserController`: Handles CRUD operations for user accounts.
- `ReservationController`: Handles CRUD operations for reservations.

## How to Use

Please note that there is no frontend for this application. To use the backend, you can make API calls directly to the respective endpoints provided by the controllers.

For example, to create a new user preference, you can make a `POST` request to the `preferences` endpoint with the required JSON data. Similarly, you can use the other endpoints to perform the desired operations on user accounts, payments, and reservations.

## Dependencies

The project uses the following dependencies:

- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Lombok
- PostgreSQL
- RapidAPI (Skyscanner, Weatherbit, RestCountries)

## Disclaimer

This project is for educational purposes only. Please make sure to adhere to the API usage guidelines and limitations set by the respective API providers.
