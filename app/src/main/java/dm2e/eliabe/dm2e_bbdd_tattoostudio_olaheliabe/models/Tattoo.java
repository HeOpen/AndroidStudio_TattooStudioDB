package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models;

public class Tattoo {
    private int tattooID_PK;
    private String bodyPart;
    private double price;
    private String description;
    private int appointmentID_FK;

    // 1. Empty Constructor
    public Tattoo() {
    }

    // 2. Constructor for INSERT (No ID)
    public Tattoo(String bodyPart, double price, String description, int appointmentID_FK) {
        this.bodyPart = bodyPart;
        this.price = price;
        this.description = description;
        this.appointmentID_FK = appointmentID_FK;
    }

    // 3. Constructor for READ (With ID)
    public Tattoo(int tattooID_PK, String bodyPart, double price, String description, int appointmentID_FK) {
        this.tattooID_PK = tattooID_PK;
        this.bodyPart = bodyPart;
        this.price = price;
        this.description = description;
        this.appointmentID_FK = appointmentID_FK;
    }

    // Getters and Setters
    public int getTattooID_PK() { return tattooID_PK; }
    public void setTattooID_PK(int tattooID_PK) { this.tattooID_PK = tattooID_PK; }

    public String getBodyPart() { return bodyPart; }
    public void setBodyPart(String bodyPart) { this.bodyPart = bodyPart; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getAppointmentID_FK() { return appointmentID_FK; }
    public void setAppointmentID_FK(int appointmentID_FK) { this.appointmentID_FK = appointmentID_FK; }

    @Override
    public String toString() {
        return description + " (" + bodyPart + ")";
    }
}