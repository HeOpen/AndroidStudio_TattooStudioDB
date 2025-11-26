package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.appointment;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.R;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.dao.DAO_Appointment;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.dao.DAO_Artist;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.dao.DAO_Customer;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Appointment;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Artist;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Customer;

public class InsertAppointmentActivity extends AppCompatActivity {

    private EditText etDateTime;
    private Spinner spCustomer, spArtist;
    private Button btSave;

    private DAO_Appointment daoAppointment;
    private DAO_Customer daoCustomer;
    private DAO_Artist daoArtist;

    private List<Customer> customerList;
    private List<Artist> artistList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_appointment);

        // Initialize DAOs
        daoAppointment = new DAO_Appointment(this);
        daoCustomer = new DAO_Customer(this);
        daoArtist = new DAO_Artist(this);

        // Initialize Views
        etDateTime = findViewById(R.id.et_appointment_date);
        spCustomer = findViewById(R.id.spinner_customer);
        spArtist = findViewById(R.id.spinner_artist);
        btSave = findViewById(R.id.bt_save_appointment);

        // Load Spinners
        loadSpinners();

        btSave.setOnClickListener(v -> saveAppointment());
    }

    private void loadSpinners() {
        // 1. Get Data from DB
        customerList = daoCustomer.getAllCustomers();
        artistList = daoArtist.getAllArtists();

        // Check if lists are empty to prevent crashes
        if (customerList == null || customerList.isEmpty()) {
            Toast.makeText(this, "No Customers found! Add one first.", Toast.LENGTH_LONG).show();
            finish(); // Cannot create appointment without customer
            return;
        }
        if (artistList == null || artistList.isEmpty()) {
            Toast.makeText(this, "No Artists found! Add one first.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // 2. Create Adapters for Spinners
        // Android's default toString() will be used for display
        ArrayAdapter<Customer> customerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, customerList);
        customerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCustomer.setAdapter(customerAdapter);

        ArrayAdapter<Artist> artistAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, artistList);
        artistAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spArtist.setAdapter(artistAdapter);
    }

    private void saveAppointment() {
        String dateTime = etDateTime.getText().toString().trim();

        if (dateTime.isEmpty()) {
            Toast.makeText(this, "Please enter a date and time", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get selected objects from Spinners
        Customer selectedCustomer = (Customer) spCustomer.getSelectedItem();
        Artist selectedArtist = (Artist) spArtist.getSelectedItem();

        // Create new Appointment
        // ID is 0 (auto-generated), Status default to "Pending"
        Appointment newAppointment = new Appointment(
                dateTime,
                "Pending",
                selectedCustomer.getCustomerID(),
                selectedArtist.getArtistID_PK()
        );

        long result = daoAppointment.insert(newAppointment);

        if (result != -1) {
            Toast.makeText(this, "Appointment Saved!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error saving appointment", Toast.LENGTH_SHORT).show();
        }
    }
}