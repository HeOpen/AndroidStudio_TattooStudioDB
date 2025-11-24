package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.customer;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.R;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.dao.DAO_Customer;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Customer;

public class EditCustomerActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etEmail, etPhone;
    private Button btUpdate;
    private RecyclerView recyclerView; // Added RecyclerView
    private CustomerAdapter adapter;   // Added Adapter

    private DAO_Customer dao;
    private int selectedCustomerId = -1; // Use this to track which ID we are editing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_customer); // Ensure XML name matches

        dao = new DAO_Customer(this);

        // Initialize Views
        etFirstName = findViewById(R.id.et_firstName);
        etLastName = findViewById(R.id.et_lastName);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        btUpdate = findViewById(R.id.bt_save_customer);
        recyclerView = findViewById(R.id.recyclerViewCustomers); // Bind RecyclerView

        btUpdate.setText("Select a Customer to Edit");
        btUpdate.setEnabled(false); // Disable until selection

        // Setup List
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadCustomersList();

        // Save/Update Logic
        btUpdate.setOnClickListener(v -> {
            if (selectedCustomerId == -1) return;

            String fName = etFirstName.getText().toString().trim();
            String lName = etLastName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            // Create object using the SELECTED ID
            Customer customerToUpdate = new Customer(selectedCustomerId, fName, lName, email, phone);

            int rows = dao.update(customerToUpdate);

            if (rows > 0) {
                Toast.makeText(this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                // Refresh list to see changes immediately
                loadCustomersList();
                clearForm();
            } else {
                Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method moved OUTSIDE of onCreate
    private void loadCustomersList() {
        List<Customer> allCustomers = dao.getAllCustomers();
        if (allCustomers == null) allCustomers = new ArrayList<>();

        // Initialize adapter with Click Listener
        adapter = new CustomerAdapter(this, allCustomers, customer -> {
            // When a row is clicked, fill the form
            populateForm(customer);
        });

        recyclerView.setAdapter(adapter);
    }

    private void populateForm(Customer customer) {
        selectedCustomerId = customer.getCustomerID();
        etFirstName.setText(customer.getFirstName());
        etLastName.setText(customer.getLastName());
        etEmail.setText(customer.getEmail());
        etPhone.setText(customer.getPhoneNumber());

        btUpdate.setText("Update Customer (ID: " + selectedCustomerId + ")");
        btUpdate.setEnabled(true);
    }

    private void clearForm() {
        selectedCustomerId = -1;
        etFirstName.setText("");
        etLastName.setText("");
        etEmail.setText("");
        etPhone.setText("");
        btUpdate.setText("Select a Customer to Edit");
        btUpdate.setEnabled(false);
    }
}