package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.appointment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.R;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.dao.DAO_Appointment;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Appointment;

public class ReadAppointmentActivity extends AppCompatActivity {

    private EditText editTextSearchID;
    private RecyclerView recyclerView;
    private TextView tvInfo; // Changed name to be generic
    private AppointmentAdapter adapter; // CHANGED: Use AppointmentAdapter
    private List<Appointment> allAppointments; // CHANGED: List of Appointments
    private DAO_Appointment daoAppointment; // CHANGED: DAO Appointment

    private String currentMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Reusing the generic list layout (ensure IDs match!)
        setContentView(R.layout.activity_read_customer);

        currentMode = getIntent().getStringExtra("MODE");
        if (currentMode == null) currentMode = "READ";

        setTitle("Select Appointment to " + currentMode);

        daoAppointment = new DAO_Appointment(this);

        // Views
        editTextSearchID = findViewById(R.id.editTextSearchID);
        recyclerView = findViewById(R.id.recyclerViewCustomers);
        tvInfo = findViewById(R.id.tv_selected_customer_info);

        loadInitialData();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Appointment Adapter
        adapter = new AppointmentAdapter(this, allAppointments, appointment -> {

            if (currentMode.equals("READ")) {
                // Shows the toString() at the bottom
                tvInfo.setText(appointment.toString());

            } else if (currentMode.equals("UPDATE")) {
                // --- THE CRITICAL PART ---
                // Open EditActivity and PASS ALL DATA
                Intent intent = new Intent(this, EditAppointmentActivity.class);
                intent.putExtra("EXTRA_ID", appointment.getAppointmentID_PK());
                intent.putExtra("EXTRA_DATE", appointment.getDateTime());
                intent.putExtra("EXTRA_STATUS", appointment.getStatus());
                // Pass Foreign Keys so Spinners can select correct people
                intent.putExtra("EXTRA_CUST_ID", appointment.getCustomerID_FK());
                intent.putExtra("EXTRA_ART_ID", appointment.getArtistID_FK());

                startActivity(intent);

            } else if (currentMode.equals("DELETE")) {
                new AlertDialog.Builder(this)
                        .setTitle("Delete Appointment")
                        .setMessage("Delete appointment on " + appointment.getDateTime() + "?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            daoAppointment.delete(appointment.getAppointmentID_PK());
                            Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT).show();
                            loadInitialData();
                            adapter.setAppointments(allAppointments);
                            tvInfo.setText("");
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        recyclerView.setAdapter(adapter);
        setupSearchListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh list when coming back from Edit screen
        if (currentMode.equals("UPDATE")) {
            loadInitialData();
            adapter.setAppointments(allAppointments);
        }
    }

    private void loadInitialData() {
        allAppointments = daoAppointment.getAllAppointments();
        if (allAppointments == null) {
            allAppointments = new ArrayList<>();
        }
    }

    private void setupSearchListener() {
        editTextSearchID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                filterList(s.toString());
            }
        });
    }

    private void filterList(String searchText) {
        if (searchText.isEmpty()) {
            adapter.setAppointments(allAppointments);
            return;
        }
        try {
            int searchID = Integer.parseInt(searchText);
            Appointment appointment = daoAppointment.getAppointmentById(searchID);
            List<Appointment> filteredList = new ArrayList<>();
            if (appointment != null) {
                filteredList.add(appointment);
            }
            adapter.setAppointments(filteredList);
        } catch (NumberFormatException e) {
            adapter.setAppointments(new ArrayList<>());
        }
    }
}