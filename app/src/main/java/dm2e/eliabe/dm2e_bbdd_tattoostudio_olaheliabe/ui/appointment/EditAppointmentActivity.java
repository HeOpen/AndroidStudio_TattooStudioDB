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

public class EditAppointmentActivity extends AppCompatActivity {

    private EditText etDateTime;
    // CHANGED: etStatus replaced with spStatus
    private Spinner spCustomer, spArtist, spStatus;
    private Button btUpdate;

    private DAO_Appointment daoAppointment;
    private DAO_Customer daoCustomer;
    private DAO_Artist daoArtist;

    private int appointmentId;
    private String currentStatus; // Store the string passed from Intent
    private int currentCustomerId;
    private int currentArtistId;

    private List<Customer> customerList;
    private List<Artist> artistList;

    // Define Status Options
    private final String[] statusOptions = {"Pending", "Confirmed", "Completed", "Cancelled"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_appointment);

        daoAppointment = new DAO_Appointment(this);
        daoCustomer = new DAO_Customer(this);
        daoArtist = new DAO_Artist(this);

        etDateTime = findViewById(R.id.et_edit_appointment_date);
        spStatus = findViewById(R.id.sp_edit_status); // Bind new spinner
        spCustomer = findViewById(R.id.sp_edit_customer);
        spArtist = findViewById(R.id.sp_edit_artist);
        btUpdate = findViewById(R.id.bt_update_appointment);

        // 1. Get Data passed from List
        appointmentId = getIntent().getIntExtra("EXTRA_ID", -1);
        String dateTime = getIntent().getStringExtra("EXTRA_DATE");
        currentStatus = getIntent().getStringExtra("EXTRA_STATUS");
        currentCustomerId = getIntent().getIntExtra("EXTRA_CUST_ID", -1);
        currentArtistId = getIntent().getIntExtra("EXTRA_ART_ID", -1);

        // 2. Populate Fields
        etDateTime.setText(dateTime);

        // 3. Load Spinners and Select correct items
        loadSpinners();

        btUpdate.setOnClickListener(v -> updateAppointment());
    }

    private void loadSpinners() {
        customerList = daoCustomer.getAllCustomers();
        artistList = daoArtist.getAllArtists();

        // --- Setup STATUS Spinner ---
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusOptions);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStatus.setAdapter(statusAdapter);

        // Pre-select Status
        if (currentStatus != null) {
            for (int i = 0; i < statusOptions.length; i++) {
                if (statusOptions[i].equalsIgnoreCase(currentStatus)) {
                    spStatus.setSelection(i);
                    break;
                }
            }
        }

        // --- Setup Customer Spinner ---
        ArrayAdapter<Customer> custAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, customerList);
        custAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCustomer.setAdapter(custAdapter);

        if (currentCustomerId != -1) {
            for (int i = 0; i < customerList.size(); i++) {
                if (customerList.get(i).getCustomerID() == currentCustomerId) {
                    spCustomer.setSelection(i);
                    break;
                }
            }
        }

        // --- Setup Artist Spinner ---
        ArrayAdapter<Artist> artAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, artistList);
        artAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spArtist.setAdapter(artAdapter);

        if (currentArtistId != -1) {
            for (int i = 0; i < artistList.size(); i++) {
                if (artistList.get(i).getArtistID_PK() == currentArtistId) {
                    spArtist.setSelection(i);
                    break;
                }
            }
        }
    }

    private void updateAppointment() {
        String dateTime = etDateTime.getText().toString().trim();

        // Get selected status from Spinner
        String status = spStatus.getSelectedItem().toString();

        Customer selectedCustomer = (Customer) spCustomer.getSelectedItem();
        Artist selectedArtist = (Artist) spArtist.getSelectedItem();

        Appointment updatedAppointment = new Appointment(
                appointmentId,
                dateTime,
                status,
                selectedCustomer.getCustomerID(),
                selectedArtist.getArtistID_PK()
        );

        int rows = daoAppointment.update(updatedAppointment);

        if (rows > 0) {
            Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
        }
    }
}