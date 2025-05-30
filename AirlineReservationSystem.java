import java.util.*;

// Aircraft class
class Aircraft {
    private String model;
    private int capacity;

    public Aircraft(String model, int capacity) {
        this.model = model;
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}

// Seat class
class Seat {
    private String seatNumber;
    private String classType;
    private boolean isBooked;

    public Seat(String seatNumber, String classType) {
        this.seatNumber = seatNumber;
        this.classType = classType;
        this.isBooked = false;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void book() {
        this.isBooked = true;
    }

    public String getSeatNumber() {
        return seatNumber;
    }
}

// Passenger class
class Passenger {
    private String name;
    private String passportNo;
    private String contactInfo;
    private List<Ticket> tickets;

    public Passenger(String name, String passportNo, String contactInfo) {
        this.name = name;
        this.passportNo = passportNo;
        this.contactInfo = contactInfo;
        this.tickets = new ArrayList<>();
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public List<Ticket> viewTickets() {
        return tickets;
    }

    public String getName() {
        return name;
    }
}

// Ticket class
class Ticket {
    private String ticketNumber;
    private Passenger passenger;
    private Flight flight;
    private Seat seat;
    private Date bookingDate;

    public Ticket(String ticketNumber, Passenger passenger, Flight flight, Seat seat, Date bookingDate) {
        this.ticketNumber = ticketNumber;
        this.passenger = passenger;
        this.flight = flight;
        this.seat = seat;
        this.bookingDate = bookingDate;
        passenger.addTicket(this);
    }

    public void cancel() {
        System.out.println("Ticket " + ticketNumber + " has been canceled.");
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public Seat getSeat() {
        return seat;
    }
}

// Flight class
class Flight {
    private String flightNumber;
    private String origin;
    private String destination;
    private Date departureTime;
    private Date arrivalTime;
    private Aircraft aircraft;
    private List<Seat> seatMap;

    public Flight(String flightNumber, String origin, String destination, Date departureTime, Date arrivalTime, Aircraft aircraft) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.aircraft = aircraft;
        this.seatMap = new ArrayList<>();

        for (int i = 1; i <= aircraft.getCapacity(); i++) {
            seatMap.add(new Seat("S" + i, "Economy"));
        }
    }

    public int checkAvailability() {
        int available = 0;
        for (Seat seat : seatMap) {
            if (!seat.isBooked()) {
                available++;
            }
        }
        return available;
    }

    public Ticket bookSeat(Passenger passenger) {
        for (Seat seat : seatMap) {
            if (!seat.isBooked()) {
                seat.book();
                return new Ticket(UUID.randomUUID().toString(), passenger, this, seat, new Date());
            }
        }
        return null;
    }
}

// Airline class
class Airline {
    private String name;
    private String code;
    private List<Flight> flights;

    public Airline(String name, String code) {
        this.name = name;
        this.code = code;
        this.flights = new ArrayList<>();
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public List<Flight> getFlights() {
        return flights;
    }
}

// Main class for testing
public class AirlineReservationSystem {
    public static void main(String[] args) {
        // Create airline
        Airline airline = new Airline("Sky Airlines", "SA");

        // Create aircraft
        Aircraft aircraft = new Aircraft("Boeing 737", 5);

        // Create flight
        Flight flight = new Flight("SA101", "New York", "Los Angeles", new Date(), new Date(), aircraft);
        airline.addFlight(flight);

        // Create passenger
        Passenger passenger = new Passenger("Alice Smith", "P1234567", "alice@example.com");

        // Book a ticket
        Ticket ticket = flight.bookSeat(passenger);

        if (ticket != null) {
            System.out.println("Booking successful. Ticket Number: " + ticket.getTicketNumber());
            System.out.println("Seat Assigned: " + ticket.getSeat().getSeatNumber());
        } else {
            System.out.println("No available seats.");
        }
    }
}
