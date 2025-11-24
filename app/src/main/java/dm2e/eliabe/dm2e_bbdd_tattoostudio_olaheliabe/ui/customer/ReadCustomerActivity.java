package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView; // Import TextView
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.R;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.dao.DAO_Customer;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Customer;

public class ReadCustomerActivity extends AppCompatActivity {

    private EditText editTextSearchID;
    private RecyclerView recyclerView;
    private TextView tvCustomerInfo; // 1. Declare the new TextView
    private CustomerAdapter adapter;

    private List<Customer> allCustomers;
    private DAO_Customer daoCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_customer);

        daoCustomer = new DAO_Customer(this);
        editTextSearchID = findViewById(R.id.editTextSearchID);
        recyclerView = findViewById(R.id.recyclerViewCustomers);

        // 2. Initialize the new TextView
        tvCustomerInfo = findViewById(R.id.tv_selected_customer_info);

        loadInitialData();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 3. Initialize Adapter with the Click Listener (Lambda)
        adapter = new CustomerAdapter(this, allCustomers, customer -> {
            // This code runs when an item is clicked
            tvCustomerInfo.setText(customer.toString());
        });

        recyclerView.setAdapter(adapter);

        setupSearchListener();
    }

    private void loadInitialData() {
        allCustomers = daoCustomer.getAllCustomers(); // Corrected method name
        if (allCustomers == null) {
            allCustomers = new ArrayList<>();
            Toast.makeText(this, "No se pudieron cargar los clientes.", Toast.LENGTH_LONG).show();
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
            Customer customer = daoCustomer.getCustomerById(searchID); // Corrected method name

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