package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.R;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.customer.InsertCustomerActivity;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.customer.ReadCustomerActivity;


public class SelectorActivity extends AppCompatActivity {

    private Button btCustomer, btArtist, btAppointment, btTattoo;
    private TextView tvTitle;
    private String currentMode; // "INSERT", "READ", "UPDATE", o "DELETE"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);

        // 1. Receive the "MODE" from MainActivity
        currentMode = getIntent().getStringExtra("MODE");
        if (currentMode == null) currentMode = "INSERT"; // Default safety

        // 2. Update the Title so the user knows what they are doing
        tvTitle = findViewById(R.id.tv_selector_title);
        tvTitle.setText("Select Table to\n" + currentMode);

        // 3. Initialize Buttons
        btCustomer = findViewById(R.id.bt_sel_customer);
        btArtist = findViewById(R.id.bt_sel_artist);
        btAppointment = findViewById(R.id.bt_sel_appointment);
        btTattoo = findViewById(R.id.bt_sel_tattoo);

        // 4. Set Listeners
        btCustomer.setOnClickListener(v -> navigateToCustomer());
    }
    private void navigateToCustomer() {
        Intent intent = null;

        // Decide which Customer screen to open based on the Mode
        switch (currentMode) {
            case "INSERT":
                intent = new Intent(this, InsertCustomerActivity.class);
                break;
            case "READ":
                intent = new Intent(this, ReadCustomerActivity.class);
                break;
            case "UPDATE":
                // intent = new Intent(this, UpdateCustomerSelectorActivity.class);
                break;
            case "DELETE":
                // intent = new Intent(this, DeleteCustomerSelectorActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Feature under construction", Toast.LENGTH_SHORT).show();
        }
    }
}