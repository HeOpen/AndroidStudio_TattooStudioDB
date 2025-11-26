package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models;

public class Appointment {
    private int appointmentID_PK;
    private String dateTime;
    private String status;
    private int customerID_FK;
    private int artistID_FK;

    // 1. Constructor Vacío
    public Appointment() {
    }

    // 2. Constructor para INSERTAR (Coincide con tu Activity)
    // Recibe: Fecha, Estado, ID Cliente, ID Artista
    public Appointment(String dateTime, String status, int customerID_FK, int artistID_FK) {
        this.dateTime = dateTime;
        this.status = status;
        this.customerID_FK = customerID_FK;
        this.artistID_FK = artistID_FK;
    }

    // 3. Constructor para LEER (Incluye el ID propio)
    public Appointment(int appointmentID_PK, String dateTime, String status, int customerID_FK, int artistID_FK) {
        this.appointmentID_PK = appointmentID_PK;
        this.dateTime = dateTime;
        this.status = status;
        this.customerID_FK = customerID_FK;
        this.artistID_FK = artistID_FK;
    }

    // --- Getters y Setters ---
    public int getAppointmentID_PK() { return appointmentID_PK; }
    public void setAppointmentID_PK(int appointmentID_PK) { this.appointmentID_PK = appointmentID_PK; }

    public String getDateTime() { return dateTime; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getCustomerID_FK() { return customerID_FK; }
    public void setCustomerID_FK(int customerID_FK) { this.customerID_FK = customerID_FK; }

    public int getArtistID_FK() { return artistID_FK; }
    public void setArtistID_FK(int artistID_FK) { this.artistID_FK = artistID_FK; }

    @Override
    public String toString() {
        return "Appointment ID: " + appointmentID_PK + "\n" +
                "Date & Time: " + dateTime + "\n" +
                "Status: " + status + "\n" +
                "Customer ID: " + customerID_FK + "\n" +
                "Artist ID: " + artistID_FK;
    }
}