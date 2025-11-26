package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.tattoo;

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
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.dao.DAO_Tattoo;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Appointment;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Tattoo;

public class InsertTattooActivity extends AppCompatActivity {

    private EditText etBodyPart, etPrice, etDescription;
    private Spinner spAppointment;
    private Button btSave;

    private DAO_Tattoo daoTattoo;
    private DAO_Appointment daoAppointment;
    private List<Appointment> appointmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_tattoo);

        daoTattoo = new DAO_Tattoo(this);
        daoAppointment = new DAO_Appointment(this);

        etBodyPart = findViewById(R.id.et_tattoo_bodypart);
        etPrice = findViewById(R.id.et_tattoo_price);
        etDescription = findViewById(R.id.et_tattoo_description);
        spAppointment = findViewById(R.id.sp_tattoo_appointment);
        btSave = findViewById(R.id.bt_save_tattoo);

        loadAppointments();

        btSave.setOnClickListener(v -> saveTattoo());
    }

    private void loadAppointments() {
        appointmentList = daoAppointment.getAllAppointments();

        if (appointmentList == null || appointmentList.isEmpty()) {
            Toast.makeText(this, "No Appointments found! Create one first.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // This uses Appointment.toString() to display the date/status in the list
        ArrayAdapter<Appointment> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, appointmentList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAppointment.setAdapter(adapter);
    }

    private void saveTattoo() {
        String bodyPart = etBodyPart.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (bodyPart.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, "Please fill in Body Part and Price", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);
        Appointment selectedAppointment = (Appointment) spAppointment.getSelectedItem();

        // Create new Tattoo
        // ID is 0, Appointment ID comes from the spinner selection
        Tattoo newTattoo = new Tattoo(
                bodyPart,
                price,
                description,
                selectedAppointment.getAppointmentID_PK()
        );

        long result = daoTattoo.insert(newTattoo);

        if (result != -1) {
            Toast.makeText(this, "Tattoo Saved!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            // If result is -1, it usually means the UNIQUE constraint failed
            Toast.makeText(this, "Error: This appointment already has a tattoo!", Toast.LENGTH_LONG).show();
        }
    }
}