package basic_travel_agency.enumeration;

public enum PaymentStatus {

    AWAITING("AWAITING"),
    PAID("PAID"),
    REJECTED("REJECTED");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.getDescription();
    }
}
