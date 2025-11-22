package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Customer;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.services.DataBaseHelper;

public class DAO_Customer {
    private DataBaseHelper dbHelper;
    private SQLiteDatabase db;

    public DAO_Customer(Context context){
        dbHelper = new DataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    //Create
    public void insert(Customer customer) {

        ContentValues values = new ContentValues();

        values.put("firstName", customer.getFirstName());
        values.put("lastName", customer.getLastName());
        values.put("email", customer.getEmail());
        values.put("phoneNumber", customer.getPhoneNumber());

        db.insert("Customer", null, values);
    }
    public void getAllCostumers()


}
