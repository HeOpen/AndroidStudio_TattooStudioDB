package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.customer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.R;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.dao.DAO_Customer;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Customer;

public class DeleteCustomerActivity extends AppCompatActivity {

    private EditText editTextSearchID;
    private RecyclerView recyclerView;
    private CustomerAdapter adapter;

    private LinearLayout layoutSelectedInfo;
    private TextView tvId, tvName, tvEmail, tvPhone;
    private Button btDelete;

    private List<Customer> allCustomers;
    private DAO_Customer daoCustomer;

    private int selectedCustomerId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_customer);

        daoCustomer = new DAO_Customer(this);

        editTextSearchID = findViewById(R.id.et_search_delete);
        recyclerView = findViewById(R.id.rv_delete_customers);
        layoutSelectedInfo = findViewById(R.id.ll_selected_info_container);

        tvId = findViewById(R.id.tv_delete_id);
        tvName = findViewById(R.id.tv_delete_name);
        tvEmail = findViewById(R.id.tv_delete_email);
        tvPhone = findViewById(R.id.tv_delete_phone);

        btDelete = findViewById(R.id.bt_delete_customer);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadInitialData();

        setupSearchListener();

        btDelete.setOnClickListener(v -> deleteSelectedCustomer());
    }

    private void loadInitialData() {
        allCustomers = daoCustomer.getAllCustomers();
        if (allCustomers == null) {
            allCustomers = new ArrayList<>();
        }

        adapter = new CustomerAdapter(this, allCustomers, customer -> {
            showCustomerDetails(customer);
        });

        recyclerView.setAdapter(adapter);
    }

    private void showCustomerDetails(Customer customer) {
        selectedCustomerId = customer.getCustomerID(); // Ensure using int getter

        tvId.setText("ID: " + customer.getCustomerID());
        tvName.setText("Name: " + customer.getFirstName() + " " + customer.getLastName());
        tvEmail.setText("Email: " + customer.getEmail());
        tvPhone.setText("Phone: " + customer.getPhoneNumber());

        layoutSelectedInfo.setVisibility(View.VISIBLE);
        btDelete.setEnabled(true);
        btDelete.setText("DELETE " + customer.getFirstName().toUpperCase());
    }

    private void deleteSelectedCustomer() {
        if (selectedCustomerId == -1) return;

        int rowsDeleted = daoCustomer.delete(selectedCustomerId);

        if (rowsDeleted > 0) {
            Toast.makeText(this, "Customer Deleted Successfully!", Toast.LENGTH_SHORT).show();

            selectedCustomerId = -1;
            btDelete.setEnabled(false);
            btDelete.setText("DELETE PERMANENTLY");
            layoutSelectedInfo.setVisibility(View.INVISIBLE); // Hide info after delete

            loadInitialData();

        } else {
            Toast.makeText(this, "Error: Could not delete customer.", Toast.LENGTH_SHORT).show();
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
            adapter.setCustomers(allCustomers);
            return;
        }
        try {
            int searchID = Integer.parseInt(searchText);
            Customer customer = daoCustomer.getCustomerById(searchID);
            List<Customer> filteredList = new ArrayList<>();
            if (customer != null) {
                filteredList.add(customer);
            }
            adapter.setCustomers(filteredList);
        } catch (NumberFormatException e) {
            adapter.setCustomers(new ArrayList<>());
        }
    }
}