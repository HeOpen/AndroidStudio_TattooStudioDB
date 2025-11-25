package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.R;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.customer.DeleteCustomerActivity;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.customer.EditCustomerActivity;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.customer.InsertCustomerActivity;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.customer.ReadCustomerActivity;

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
}