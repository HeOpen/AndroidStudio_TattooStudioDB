package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="TattooStudioDB.db";
    private static final int DB_VERSION=1;


    public DataBaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_CUSTOMER_TABLE = """
        CREATE TABLE Customer (
            customerID_PK INTEGER PRIMARY KEY AUTOINCREMENT,
            firstName TEXT NOT NULL,
            lastName TEXT NOT NULL,
            email TEXT UNIQUE,
            phoneNumber TEXT
        );
    """;

        String SQL_CREATE_ARTIST_TABLE = """
        CREATE TABLE Artist (
            artistID_PK INTEGER PRIMARY KEY AUTOINCREMENT,
            firstName TEXT NOT NULL,
            lastName TEXT NOT NULL,
            email TEXT UNIQUE,
            phoneNumber TEXT
        );
    """;

        //Foreign key de las 2 tablas anteriores
        String SQL_CREATE_APPOINTMENT_TABLE = """
        CREATE TABLE Appointment (
            appointmentID_PK INTEGER PRIMARY KEY AUTOINCREMENT,
            dateTime TEXT NOT NULL,
            status TEXT NOT NULL,
            customerID_FK INTEGER NOT NULL,
            artistID_FK INTEGER NOT NULL,
            FOREIGN KEY (customerID_FK) REFERENCES Customer(customerID_PK),
            FOREIGN KEY (artistID_FK) REFERENCES Artist(artistID_PK)
        );
    """;

        // 4. Tabla Tattoo (Depende de Appointment)

        String SQL_CREATE_TATTOO_TABLE = """
        CREATE TABLE Tattoo (
            tattooID_PK INTEGER PRIMARY KEY AUTOINCREMENT,
            bodyPart TEXT NOT NULL,
            price REAL NOT NULL,
            description TEXT,
            appointmentID_FK INTEGER NOT NULL UNIQUE,
            FOREIGN KEY (appointmentID_FK) REFERENCES Appointment(appointmentID_PK)
        );
    """;

        //Orden de ejecución, el mismo de la creación
        db.execSQL(SQL_CREATE_CUSTOMER_TABLE);
        db.execSQL(SQL_CREATE_ARTIST_TABLE);
        db.execSQL(SQL_CREATE_APPOINTMENT_TABLE);
        db.execSQL(SQL_CREATE_TATTOO_TABLE);

        // Default Customers
        db.execSQL("INSERT INTO Customer (firstName, lastName, email, phoneNumber) VALUES ('Eliabe', 'Hello', 'eliabe@email.com', '600123456')");
        db.execSQL("INSERT INTO Customer (firstName, lastName, email, phoneNumber) VALUES ('Joseph', 'Swords', 'swordsjoseph@email.com', '600987654')");
        db.execSQL("INSERT INTO Customer (firstName, lastName, email, phoneNumber) VALUES ('Elvis', 'Dances', 'delegate@email.com', '600555111')");
        db.execSQL("INSERT INTO Customer (firstName, lastName, email, phoneNumber) VALUES ('Jiateng', 'Ming', 'bruceliofficial@email.com', '600555111')");
        db.execSQL("INSERT INTO Customer (firstName, lastName, email, phoneNumber) VALUES ('Corey', 'Taylor', 'corey@slipknot.com', '515000001')");
        db.execSQL("INSERT INTO Customer (firstName, lastName, email, phoneNumber) VALUES ('Shawn', 'Crahan', 'clown@slipknot.com', '515000006')");
        db.execSQL("INSERT INTO Customer (firstName, lastName, email, phoneNumber) VALUES ('Mick', 'Thomson', 'mick@slipknot.com', '515000007')");
        db.execSQL("INSERT INTO Customer (firstName, lastName, email, phoneNumber) VALUES ('Joey', 'Jordison', 'jjordison@slipknot.com', '515000007')");
        db.execSQL("INSERT INTO Customer (firstName, lastName, email, phoneNumber) VALUES ('Jim', 'Root', 'jim@slipknot.com', '515000004')");
        db.execSQL("INSERT INTO Customer (firstName, lastName, email, phoneNumber) VALUES ('Sid', 'Wilson', 'sid@slipknot.com', '515000000')");
        db.execSQL("INSERT INTO Customer (firstName, lastName, email, phoneNumber) VALUES ('Alessandro', 'Venturella', 'vman@slipknot.com', '515000009')");
        db.execSQL("INSERT INTO Customer (firstName, lastName, email, phoneNumber) VALUES ('Michael', 'Pfaff', 'tortilla@slipknot.com', '515000010')");
        db.execSQL("INSERT INTO Customer (firstName, lastName, email, phoneNumber) VALUES ('Eloy', 'Casagrande', 'eloy@slipknot.com', '515000011')");
        db.execSQL("INSERT INTO Customer (firstName, lastName, email, phoneNumber) VALUES ('Aristos', 'Petrou', 'ruby@g59records.com', '504000001')");
        db.execSQL("INSERT INTO Customer (firstName, lastName, email, phoneNumber) VALUES ('Scott', 'Arceneaux', 'scrim@g59records.com', '504000002')");


        // Default Artists
        db.execSQL("INSERT INTO Artist (firstName, lastName, email, phoneNumber) VALUES ('James', 'Hetfield', 'james@metallica.com', '415000001')");
        db.execSQL("INSERT INTO Artist (firstName, lastName, email, phoneNumber) VALUES ('Lars', 'Ulrich', 'lars@metallica.com', '415000002')");
        db.execSQL("INSERT INTO Artist (firstName, lastName, email, phoneNumber) VALUES ('Kirk', 'Hammett', 'kirk@metallica.com', '415000003')");
        db.execSQL("INSERT INTO Artist (firstName, lastName, email, phoneNumber) VALUES ('Robert', 'Trujillo', 'rob@metallica.com', '415000004')");
        db.execSQL("INSERT INTO Customer (firstName, lastName, email, phoneNumber) VALUES ('M. Shadows', 'Sanders', 'shadows@a7x.com', '714000001')");
        db.execSQL("INSERT INTO Customer (firstName, lastName, email, phoneNumber) VALUES ('Synyster', 'Gates', 'syn@a7x.com', '714000002')");

        // Appointment samples
        db.execSQL("INSERT INTO Appointment (dateTime, status, customerID_FK, artistID_FK) VALUES ('2023-11-01 10:00', 'Completed', 1, 1)");
        db.execSQL("INSERT INTO Appointment (dateTime, status, customerID_FK, artistID_FK) VALUES ('2023-11-02 14:00', 'Completed', 4, 3)");
        db.execSQL("INSERT INTO Appointment (dateTime, status, customerID_FK, artistID_FK) VALUES ('2023-11-03 16:30', 'Pending', 12, 2)");
        db.execSQL("INSERT INTO Appointment (dateTime, status, customerID_FK, artistID_FK) VALUES ('2023-11-05 09:00', 'Confirmed', 14, 4)");
        db.execSQL("INSERT INTO Appointment (dateTime, status, customerID_FK, artistID_FK) VALUES ('2023-11-06 11:00', 'Completed', 8, 5)");

        db.execSQL("INSERT INTO Appointment (dateTime, status, customerID_FK, artistID_FK) VALUES ('2023-11-07 13:00', 'Pending', 2, 1)");
        db.execSQL("INSERT INTO Appointment (dateTime, status, customerID_FK, artistID_FK) VALUES ('2023-11-08 15:00', 'Confirmed', 5, 3)");
        db.execSQL("INSERT INTO Appointment (dateTime, status, customerID_FK, artistID_FK) VALUES ('2023-11-09 10:00', 'Cancelled', 13, 6)");
        db.execSQL("INSERT INTO Appointment (dateTime, status, customerID_FK, artistID_FK) VALUES ('2023-11-10 12:00', 'Completed', 15, 2)");
        db.execSQL("INSERT INTO Appointment (dateTime, status, customerID_FK, artistID_FK) VALUES ('2023-11-11 14:00', 'Pending', 3, 1)");

        // Default tattoos
        db.execSQL("INSERT INTO Tattoo (bodyPart, price, description, appointmentID_FK) VALUES ('Chest', 600.00, 'PRAY FOR ME script in gothic font', 1)");
        db.execSQL("INSERT INTO Tattoo (bodyPart, price, description, appointmentID_FK) VALUES ('Neck', 450.00, 'Fear No One text vertical placement', 2)");
        db.execSQL("INSERT INTO Tattoo (bodyPart, price, description, appointmentID_FK) VALUES ('Hand', 200.00, 'G59 Skull logo with grey shading', 3)");
        db.execSQL("INSERT INTO Tattoo (bodyPart, price, description, appointmentID_FK) VALUES ('Forearm', 350.00, 'Skeleton bones anatomy overlay', 4)");
        db.execSQL("INSERT INTO Tattoo (bodyPart, price, description, appointmentID_FK) VALUES ('Arm', 500.00, 'Global Epidemic AK-47 silhouette', 5)");
        db.execSQL("INSERT INTO Tattoo (bodyPart, price, description, appointmentID_FK) VALUES ('Back', 900.00, 'Champion of Death grim reaper full piece', 6)");
        db.execSQL("INSERT INTO Tattoo (bodyPart, price, description, appointmentID_FK) VALUES ('Knuckles', 150.00, 'FTP text block letters', 7)");
        db.execSQL("INSERT INTO Tattoo (bodyPart, price, description, appointmentID_FK) VALUES ('Heel', 250.00, 'Razor guarding the heel design', 8)");
        db.execSQL("INSERT INTO Tattoo (bodyPart, price, description, appointmentID_FK) VALUES ('Thigh', 400.00, 'Grey Sheep with glowing eyes portrait', 9)");
        db.execSQL("INSERT INTO Tattoo (bodyPart, price, description, appointmentID_FK) VALUES ('Cheek', 100.00, 'Small inverted cross and teardrops', 10)");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Vacio
    }
}
