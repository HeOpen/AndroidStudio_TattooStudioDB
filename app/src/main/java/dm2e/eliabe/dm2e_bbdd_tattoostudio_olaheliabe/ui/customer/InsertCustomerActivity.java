package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.customer;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// Import your specific DAO and Model
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.R;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.dao.DAO_Customer;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Customer;

public class InsertCustomerActivity extends AppCompatActivity {

    // 1. Declare UI variables
    private EditText etFirstName, etLastName, etEmail, etPhone;
    private Button btSave;

    // 2. Declare DAO
    private DAO_Customer dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_customer);

        // 3. Initialize Views
        etFirstName = findViewById(R.id.et_firstName);
        etLastName = findViewById(R.id.et_lastName);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        btSave = findViewById(R.id.bt_save_customer);

        // 4. Initialize DAO
        dao = new DAO_Customer(this);

        // 5. Set Click Listener
        btSave.setOnClickListener(v -> {
            // Get text from fields
            String firstName = etFirstName.getText().toString().trim();
            String lastName = etLastName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            // Simple Validation: Don't save if name is empty
            if (firstName.isEmpty() || lastName.isEmpty()) {
                Toast.makeText(this, "Please enter First and Last name", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create Model Object (ID is 0 because it's new)
            Customer newCustomer = new Customer(firstName, lastName, email, phone);

            // Save to DB
            long result = dao.insert(newCustomer);

            if (result != -1) {
                Toast.makeText(this, "Customer Saved!", Toast.LENGTH_SHORT).show();
                finish(); // Closes this activity and goes back
            } else {
                Toast.makeText(this, "Error: Email might be duplicate", Toast.LENGTH_SHORT).show();
            }
        });
    }
}