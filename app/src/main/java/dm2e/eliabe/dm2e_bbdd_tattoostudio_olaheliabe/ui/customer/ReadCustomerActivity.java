package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.R;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.dao.DAO_Customer;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Customer;

public class ReadCustomerActivity extends AppCompatActivity {

    private EditText editTextSearchID;
    private RecyclerView recyclerView;
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

        loadInitialData();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomerAdapter(this, allCustomers);
        recyclerView.setAdapter(adapter);

        setupSearchListener();
    }

    private void loadInitialData() {
        allCustomers = daoCustomer.readAll();
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