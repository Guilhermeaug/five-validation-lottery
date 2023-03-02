# Lotterry Draw

This is a simple lottery draw API written in Java using the Spring framework. It is a REST API that allows users
to sign up and buy tickets for a lottery draw. The draw is then run and the winners are announced.

There is also a simple frontend that shows all tickets already sold. It is written in React and can be found on the
following [GitHub repo](https://github.com/Guilhermeaug/lottery-frontend). It has tests written in JUnit5.

## Business Rules

### About the tickets
* The entry-level ticket has five numbers between 1 and 10, inclusive. It costs R$ 5.00.
  * for every additional number, the ticket costs 10% over the previous price.
  * a ticket can have at most 10 numbers.
  * a ticket is taxed 15% of the price.

### About the draw
* The draw can be runned only once. When it happens, 5 random numbers between 1 and 10 are generated.
  * the prize money is the sum of all ticket prices (before taxes). 
  * a customer can only win if he manages to get all five numbers right.
  * the jackpot will be shared equally among all winners.

## Technical Requirements

The following dependencies are being used:
* Spring Web MVC
* Spring Validation
* Spring Data JPA
* H2 Database
* Lombok

## About the API

### Customer Endpoints
* `POST /customers` - Creates a new customer.
  * Request body:
    * `name` - Customer's name.
* `GET /customers` - Retrieves all customers.

### Ticket Endpoints
* `POST /tickets` - Creates a new ticket.
  * Request body:
    * `numbers` - An array of numbers.
    * `customerId` - The customer's ID.
* `POST /tickets/random` - Creates a new ticket with random numbers.
  * Request body:
    * `customerId` - The customer's ID.
  * Query parameters:
    * `amount` - The amount of numbers to be generated.
* `GET /tickets` - Retrieves all tickets.
  * Query parameters:
    * `customerName` - The customer's name. (optional)

### Draw Endpoint
* `GET /draws` - Runs the draw.
  * Returns a list consisting of all winners and their share of the prize money.