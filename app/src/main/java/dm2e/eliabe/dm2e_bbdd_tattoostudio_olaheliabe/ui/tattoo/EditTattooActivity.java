package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.tattoo;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.R;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.dao.DAO_Appointment;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.dao.DAO_Tattoo;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Appointment;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Tattoo;

public class EditTattooActivity extends AppCompatActivity {

    private EditText etBodyPart, etPrice, etDescription;
    private Spinner spAppointment;
    private Button btUpdate;
    private RecyclerView recyclerView;
    private TattooAdapter adapter;

    private DAO_Tattoo daoTattoo;
    private DAO_Appointment daoAppointment;

    private List<Appointment> appointmentList;
    private int selectedTattooId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tattoo);

        daoTattoo = new DAO_Tattoo(this);
        daoAppointment = new DAO_Appointment(this);

        etBodyPart = findViewById(R.id.et_edit_tattoo_bodypart);
        etPrice = findViewById(R.id.et_edit_tattoo_price);
        etDescription = findViewById(R.id.et_edit_tattoo_description);
        spAppointment = findViewById(R.id.sp_edit_tattoo_appointment);
        btUpdate = findViewById(R.id.bt_update_tattoo);
        recyclerView = findViewById(R.id.rv_edit_tattoos);

        // Setup List
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 1. Load Appointments first (needed for Spinner)
        loadAppointments();

        // 2. Load Tattoos List
        loadTattoosList();

        btUpdate.setOnClickListener(v -> updateTattoo());
    }

    private void loadAppointments() {
        appointmentList = daoAppointment.getAllAppointments();
        if (appointmentList == null) appointmentList = new ArrayList<>();

        ArrayAdapter<Appointment> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, appointmentList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAppointment.setAdapter(adapter);
    }

    private void loadTattoosList() {
        List<Tattoo> allTattoos = daoTattoo.getAllTattoos();
        if (allTattoos == null) allTattoos = new ArrayList<>();

        adapter = new TattooAdapter(this, allTattoos, tattoo -> {
            populateForm(tattoo);
        });
        recyclerView.setAdapter(adapter);
    }

    private void populateForm(Tattoo tattoo) {
        selectedTattooId = tattoo.getTattooID_PK();

        etBodyPart.setText(tattoo.getBodyPart());
        etPrice.setText(String.valueOf(tattoo.getPrice()));
        etDescription.setText(tattoo.getDescription());

        // Pre-select the correct appointment in Spinner
        int apptId = tattoo.getAppointmentID_FK();
        if (appointmentList != null) {
            for (int i = 0; i < appointmentList.size(); i++) {
                if (appointmentList.get(i).getAppointmentID_PK() == apptId) {
                    spAppointment.setSelection(i);
                    break;
                }
            }
        }

        btUpdate.setText("Update Tattoo (ID: " + selectedTattooId + ")");
        btUpdate.setEnabled(true);
    }

    private void updateTattoo() {
        if (selectedTattooId == -1) return;

        String bodyPart = etBodyPart.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (bodyPart.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, "Please fill required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);
        Appointment selectedAppt = (Appointment) spAppointment.getSelectedItem();

        Tattoo updatedTattoo = new Tattoo(
                selectedTattooId,
                bodyPart,
                price,
                description,
                selectedAppt.getAppointmentID_PK()
        );

        int rows = daoTattoo.update(updatedTattoo);

        if (rows > 0) {
            Toast.makeText(this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
            clearForm();
            loadTattoosList(); // Refresh list to show changes
        } else {
            Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearForm() {
        selectedTattooId = -1;
        etBodyPart.setText("");
        etPrice.setText("");
        etDescription.setText("");
        btUpdate.setText("Select Tattoo from List");
        btUpdate.setEnabled(false);
    }
}