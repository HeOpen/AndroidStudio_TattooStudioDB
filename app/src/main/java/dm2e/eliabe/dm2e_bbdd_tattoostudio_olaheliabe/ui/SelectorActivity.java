package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.R;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.appointment.DeleteAppointmentActivity;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.appointment.EditAppointmentActivity;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.appointment.InsertAppointmentActivity;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.appointment.ReadAppointmentActivity;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.artist.DeleteArtistActivity;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.artist.EditArtistActivity;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.artist.InsertArtistActivity;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.artist.ReadArtistActivity;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.customer.DeleteCustomerActivity;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.customer.EditCustomerActivity;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.customer.InsertCustomerActivity;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.customer.ReadCustomerActivity;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.tattoo.DeleteTattooActivity;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.tattoo.EditTattooActivity;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.tattoo.InsertTattooActivity;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.tattoo.ReadTattooActivity;

public class SelectorActivity extends AppCompatActivity {

    private Button btCustomer, btArtist, btAppointment, btTattoo;
    private TextView tvTitle;
    private String currentMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);

        currentMode = getIntent().getStringExtra("MODE");
        if (currentMode == null) currentMode = "INSERT";

        tvTitle = findViewById(R.id.tv_selector_title);
        tvTitle.setText("Select Table to\n" + currentMode);

        btCustomer = findViewById(R.id.bt_sel_customer);
        btArtist = findViewById(R.id.bt_sel_artist);
        btAppointment = findViewById(R.id.bt_sel_appointment);
        btTattoo = findViewById(R.id.bt_sel_tattoo);


        btCustomer.setOnClickListener(v -> navigateToCustomer());
        btArtist.setOnClickListener(v -> navigateToArtist());
        btAppointment.setOnClickListener(v -> navigateToAppointment());
        btTattoo.setOnClickListener(v-> navigateToTattoo());

    }

    private void navigateToCustomer() {
        Intent intent = null;

        switch (currentMode) {
            case "INSERT":
                intent = new Intent(this, InsertCustomerActivity.class);
                break;
            case "READ":
                intent = new Intent(this, ReadCustomerActivity.class);
                break;
            case "UPDATE":
                intent = new Intent(this, EditCustomerActivity.class);
                break;
            case "DELETE":
                intent = new Intent(this, DeleteCustomerActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Feature under construction", Toast.LENGTH_SHORT).show();
        }
    }
    private void navigateToArtist() {
        Intent intent = null;

        switch (currentMode) {
            case "INSERT":
                intent = new Intent(this, InsertArtistActivity.class);
                break;
            case "READ":
                intent = new Intent(this, ReadArtistActivity.class);
                break;
            case "UPDATE":
                intent = new Intent(this, EditArtistActivity.class);
                break;
            case "DELETE":
                intent = new Intent(this, DeleteArtistActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Feature under construction", Toast.LENGTH_SHORT).show();
        }
    }
    private void navigateToAppointment() {
        Intent intent = null;

        switch (currentMode) {
            case "INSERT":
                intent = new Intent(this, InsertAppointmentActivity.class);
                break;
            case "READ":
                intent = new Intent(this, ReadAppointmentActivity.class);
                break;
            case "UPDATE":
                intent = new Intent(this, EditAppointmentActivity.class); //Blehhhhhhhh
                break;
            case "DELETE":
                intent = new Intent(this, DeleteAppointmentActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Feature under construction", Toast.LENGTH_SHORT).show();
        }
    }
    //todo FIX ME
    private void navigateToTattoo() {
        Intent intent = null;

        switch (currentMode) {
            case "INSERT":
               intent = new Intent(this, InsertTattooActivity.class);//todo FIX ME
                break;
            case "READ":
                intent = new Intent(this, ReadTattooActivity.class);
                break;
            case "UPDATE":
                intent = new Intent(this, EditTattooActivity.class);
                break;
            case "DELETE":
                intent = new Intent(this, DeleteTattooActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Feature under construction", Toast.LENGTH_SHORT).show();
        }
    }
}