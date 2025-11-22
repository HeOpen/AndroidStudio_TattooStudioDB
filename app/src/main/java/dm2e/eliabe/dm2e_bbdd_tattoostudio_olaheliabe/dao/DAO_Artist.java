package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Artist;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.services.DataBaseHelper;

public class DAO_Artist {

    private DataBaseHelper dbHelper;
    private SQLiteDatabase db;

    public DAO_Artist(Context context) {
        dbHelper = new DataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    // =========================================================================
    // 1. INSERT (Create)
    // =========================================================================
    public int insert(Artist artist) {
        ContentValues values = new ContentValues();
        values.put("firstName", artist.getFirstName());
        values.put("lastName", artist.getLastName());
        values.put("email", artist.getEmail());
        values.put("phoneNumber", artist.getPhoneNumber());

        return (int) db.insert("Artist", null, values);
    }
    // =========================================================================
    // 2. READ ALL (Select *)
    // =========================================================================
    public List<Artist> getAllArtists() {
        List<Artist> list = new ArrayList<>();
        Cursor cursor = db.query("Artist", null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Artist artist = new Artist(
                        cursor.getInt(cursor.getColumnIndexOrThrow("artistID_PK")),
                        cursor.getString(cursor.getColumnIndexOrThrow("firstName")),
                        cursor.getString(cursor.getColumnIndexOrThrow("lastName")),
                        cursor.getString(cursor.getColumnIndexOrThrow("email")),
                        cursor.getString(cursor.getColumnIndexOrThrow("phoneNumber"))
                );
                list.add(artist);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }
    // =========================================================================
    // 3. READ ONE (Select by ID)
    // =========================================================================
    public Artist getArtistById(int id) {
        Cursor cursor = db.query("Artist", null, "artistID_PK = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Artist artist = new Artist(
                    cursor.getInt(cursor.getColumnIndexOrThrow("artistID_PK")),
                    cursor.getString(cursor.getColumnIndexOrThrow("firstName")),
                    cursor.getString(cursor.getColumnIndexOrThrow("lastName")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("phoneNumber"))
            );
            cursor.close();
            return artist;
        }
        return null;
    }
    // =========================================================================
    // 4. UPDATE
    // =========================================================================
    public int update(Artist artist) {
        ContentValues values = new ContentValues();
        values.put("firstName", artist.getFirstName());
        values.put("lastName", artist.getLastName());
        values.put("email", artist.getEmail());
        values.put("phoneNumber", artist.getPhoneNumber());

        return db.update("Artist", values, "artistID_PK = ?",
                new String[]{String.valueOf(artist.getArtistID_PK())});
    }
    // =========================================================================
    // 5. DELETE
    // =========================================================================
    public int delete(int id) {
        return db.delete("Artist", "artistID_PK = ?", new String[]{String.valueOf(id)});
    }
}