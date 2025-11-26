package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models;

public class Artist {
    private int artistID_PK;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public Artist() {}


    public Artist(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Artist(int artistID_PK, String firstName, String lastName, String email, String phoneNumber) {
        this.artistID_PK = artistID_PK;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public int getArtistID_PK() { return artistID_PK; }
    public void setArtistID_PK(int artistID_PK) { this.artistID_PK = artistID_PK; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    @Override
    public String toString() {
        return "Artist ID: " + artistID_PK + "\n" +
                "Name: " + firstName + " " + lastName + "\n" +
                "Email: " + email + "\n" +
                "Phone: " + phoneNumber;
    }
}