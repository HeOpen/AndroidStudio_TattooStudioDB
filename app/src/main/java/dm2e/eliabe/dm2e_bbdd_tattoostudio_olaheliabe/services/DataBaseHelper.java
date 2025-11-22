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

        //Orden de ejecución el mismo de la creación
        db.execSQL(SQL_CREATE_CUSTOMER_TABLE);
        db.execSQL(SQL_CREATE_ARTIST_TABLE);
        db.execSQL(SQL_CREATE_APPOINTMENT_TABLE);
        db.execSQL(SQL_CREATE_TATTOO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
