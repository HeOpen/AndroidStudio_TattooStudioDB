package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Customer;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.services.DataBaseHelper;

public class DAO_Customer {

    private DataBaseHelper dbHelper;
    private SQLiteDatabase db;

    public DAO_Customer(Context context) {
        dbHelper = new DataBaseHelper(context);
        // We open the database immediately when the DAO is created
        db = dbHelper.getWritableDatabase();
    }

    // =========================================================================
    // 1. INSERT (Create)
    // =========================================================================
    public int insert(Customer customer) {
        ContentValues values = new ContentValues();

        values.put("firstName", customer.getFirstName());
        values.put("lastName", customer.getLastName());
        values.put("email", customer.getEmail());
        values.put("phoneNumber", customer.getPhoneNumber());

        return (int) db.insert("Customer", null, values);
    }

    // =========================================================================
    // 2. READ ALL (Select *)
    // =========================================================================
    public List<Customer> getAllCustomers() {
        List<Customer> allCustomers = new ArrayList<>();

        // SELECT * FROM Customer
        Cursor cursor = db.query("Customer", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Customer customer = new Customer(
                        cursor.getInt(cursor.getColumnIndexOrThrow("customerID_PK")),
                        cursor.getString(cursor.getColumnIndexOrThrow("firstName")),
                        cursor.getString(cursor.getColumnIndexOrThrow("lastName")),
                        cursor.getString(cursor.getColumnIndexOrThrow("email")),
                        cursor.getString(cursor.getColumnIndexOrThrow("phoneNumber"))
                );
                allCustomers.add(customer);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return allCustomers;
    }

    // =========================================================================
    // 3. READ ONE (Select by ID)
    // =========================================================================
    public Customer getCustomerById(int id) {
        Cursor cursor = db.query(
                "Customer",
                null,
                "customerID_PK = ?",
                new String[]{String.valueOf(id)},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            Customer customer = new Customer(
                    cursor.getInt(cursor.getColumnIndexOrThrow("customerID_PK")),
                    cursor.getString(cursor.getColumnIndexOrThrow("firstName")),
                    cursor.getString(cursor.getColumnIndexOrThrow("lastName")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("phoneNumber"))
            );
            cursor.close();
            return customer;
        }
        return null;
    }

    // =========================================================================
    // 4. UPDATE
    // =========================================================================
    public int update(Customer customer) {
        ContentValues values = new ContentValues();
        values.put("firstName", customer.getFirstName());
        values.put("lastName", customer.getLastName());
        values.put("email", customer.getEmail());
        values.put("phoneNumber", customer.getPhoneNumber());

        // UPDATE Customer SET ... WHERE customerID_PK = ?
        return db.update("Customer", values, "customerID_PK = ?", new String[]{String.valueOf(customer.getCustomerID())});
    }

    // =========================================================================
    // 5. DELETE
    // =========================================================================
    public int delete(int id) {
        // DELETE FROM Customer WHERE customerID_PK = ?
        return db.delete("Customer", "customerID_PK = ?", new String[]{String.valueOf(id)});
    }

    // =========================================================================
    // READ ALL (SELECT * FROM Customer)
    // =========================================================================
    public List<Customer> readAll() {

        List<Customer> allCustomerList = new ArrayList<>();

        Cursor cursor = db.query("Customer", null, null, null, null, null, null);

        // 3. Loop through the cursor results
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("customerID_PK"));
                String firstName = cursor.getString(cursor.getColumnIndexOrThrow("firstName"));
                String lastName = cursor.getString(cursor.getColumnIndexOrThrow("lastName"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow("phoneNumber"));

                // B. Create a new Customer object
                Customer customer = new Customer(id, firstName, lastName, email, phone);

                // C. Add it to the list
                allCustomerList.add(customer);

            } while (cursor.moveToNext());

            cursor.close();
        }
        return allCustomerList;
    }
}