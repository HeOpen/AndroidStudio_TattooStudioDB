package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Appointment;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.services.DataBaseHelper;

public class DAO_Appointment {

    private DataBaseHelper dbHelper;
    private SQLiteDatabase db;

    public DAO_Appointment(Context context) {
        dbHelper = new DataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    // =========================================================================
    // 1. INSERT (Create)
    // =========================================================================
    public int insert(Appointment appointment) {
        ContentValues values = new ContentValues();
        values.put("dateTime", appointment.getDateTime());
        values.put("status", appointment.getStatus());
        values.put("customerID_FK", appointment.getCustomerID_FK());
        values.put("artistID_FK", appointment.getArtistID_FK());

        return (int) db.insert("Appointment", null, values);
    }
    // =========================================================================
    // 2. READ ALL (Select *)
    // =========================================================================
    public List<Appointment> getAllAppointments() {
        List<Appointment> list = new ArrayList<>();
        Cursor cursor = db.query("Appointment", null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Appointment appointment = new Appointment(
                        cursor.getInt(cursor.getColumnIndexOrThrow("appointmentID_PK")),
                        cursor.getString(cursor.getColumnIndexOrThrow("dateTime")),
                        cursor.getString(cursor.getColumnIndexOrThrow("status")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("customerID_FK")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("artistID_FK"))
                );
                list.add(appointment);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }
    // =========================================================================
    // 3. READ ONE (Select by ID)
    // =========================================================================
    public Appointment getAppointmentById(int id) {
        Cursor cursor = db.query("Appointment", null, "appointmentID_PK = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Appointment appointment = new Appointment(
                    cursor.getInt(cursor.getColumnIndexOrThrow("appointmentID_PK")),
                    cursor.getString(cursor.getColumnIndexOrThrow("dateTime")),
                    cursor.getString(cursor.getColumnIndexOrThrow("status")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("customerID_FK")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("artistID_FK"))
            );
            cursor.close();
            return appointment;
        }
        return null;
    }
    // =========================================================================
    // 4. UPDATE
    // =========================================================================
    public int update(Appointment appointment) {
        ContentValues values = new ContentValues();
        values.put("dateTime", appointment.getDateTime());
        values.put("status", appointment.getStatus());
        values.put("customerID_FK", appointment.getCustomerID_FK());
        values.put("artistID_FK", appointment.getArtistID_FK());

        return db.update("Appointment", values, "appointmentID_PK = ?",
                new String[]{String.valueOf(appointment.getAppointmentID_PK())});
    }
    // =========================================================================
    // 5. DELETE
    // =========================================================================
    public int delete(int id) {
        return db.delete("Appointment", "appointmentID_PK = ?", new String[]{String.valueOf(id)});
    }
}