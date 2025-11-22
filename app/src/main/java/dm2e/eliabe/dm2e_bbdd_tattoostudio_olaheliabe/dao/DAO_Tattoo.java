package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Tattoo;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.services.DataBaseHelper;

public class DAO_Tattoo {

    private DataBaseHelper dbHelper;
    private SQLiteDatabase db;

    public DAO_Tattoo(Context context) {
        dbHelper = new DataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    // =========================================================================
    // 1. INSERT (Create)
    // =========================================================================
    public int insert(Tattoo tattoo) {
        ContentValues values = new ContentValues();
        values.put("bodyPart", tattoo.getBodyPart());
        values.put("price", tattoo.getPrice());
        values.put("description", tattoo.getDescription());
        values.put("appointmentID_FK", tattoo.getAppointmentID_FK());

        return (int) db.insert("Tattoo", null, values);
    }
    // =========================================================================
    // 2. READ ALL (Select *)
    // =========================================================================
    public List<Tattoo> getAllTattoos() {
        List<Tattoo> list = new ArrayList<>();
        Cursor cursor = db.query("Tattoo", null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Tattoo tattoo = new Tattoo(
                        cursor.getInt(cursor.getColumnIndexOrThrow("tattooID_PK")),
                        cursor.getString(cursor.getColumnIndexOrThrow("bodyPart")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("price")),
                        cursor.getString(cursor.getColumnIndexOrThrow("description")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("appointmentID_FK"))
                );
                list.add(tattoo);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }
    // =========================================================================
    // 3. READ ONE (Select by ID)
    // =========================================================================
    public Tattoo getTattooById(int id) {
        Cursor cursor = db.query("Tattoo", null, "tattooID_PK = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Tattoo tattoo = new Tattoo(
                    cursor.getInt(cursor.getColumnIndexOrThrow("tattooID_PK")),
                    cursor.getString(cursor.getColumnIndexOrThrow("bodyPart")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("price")),
                    cursor.getString(cursor.getColumnIndexOrThrow("description")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("appointmentID_FK"))
            );
            cursor.close();
            return tattoo;
        }
        return null;
    }
    // =========================================================================
    // 4. UPDATE
    // =========================================================================
    public int update(Tattoo tattoo) {
        ContentValues values = new ContentValues();
        values.put("bodyPart", tattoo.getBodyPart());
        values.put("price", tattoo.getPrice());
        values.put("description", tattoo.getDescription());
        values.put("appointmentID_FK", tattoo.getAppointmentID_FK());

        return db.update("Tattoo", values, "tattooID_PK = ?",
                new String[]{String.valueOf(tattoo.getTattooID_PK())});
    }
    // =========================================================================
    // 5. DELETE
    // =========================================================================
    public int delete(int id) {
        return db.delete("Tattoo", "tattooID_PK = ?", new String[]{String.valueOf(id)});
    }
}