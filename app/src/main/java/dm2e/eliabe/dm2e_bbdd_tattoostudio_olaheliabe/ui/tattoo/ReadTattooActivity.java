package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.tattoo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.R;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.dao.DAO_Tattoo;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Tattoo;

public class ReadTattooActivity extends AppCompatActivity {

    private EditText editTextSearchID;
    private RecyclerView recyclerView;
    private TextView tvInfo;

    private TattooAdapter adapter;
    private List<Tattoo> allTattoos;
    private DAO_Tattoo daoTattoo;

    private String currentMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_customer); // Reuse generic layout

        currentMode = getIntent().getStringExtra("MODE");
        if (currentMode == null) currentMode = "READ";

        setTitle("Select Tattoo to " + currentMode);

        daoTattoo = new DAO_Tattoo(this);

        editTextSearchID = findViewById(R.id.editTextSearchID);
        recyclerView = findViewById(R.id.recyclerViewCustomers);
        tvInfo = findViewById(R.id.tv_selected_customer_info);

        loadInitialData();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TattooAdapter(this, allTattoos, tattoo -> {

            if (currentMode.equals("READ")) {
                tvInfo.setText(tattoo.toString());

            } else if (currentMode.equals("UPDATE")) {
                // TODO: You will create EditTattooActivity next
                Toast.makeText(this, "Edit Tattoo: Coming Next", Toast.LENGTH_SHORT).show();
                /* Intent intent = new Intent(this, EditTattooActivity.class);
                intent.putExtra("EXTRA_ID", tattoo.getTattooID_PK());
                intent.putExtra("EXTRA_BODY", tattoo.getBodyPart());
                intent.putExtra("EXTRA_PRICE", tattoo.getPrice());
                intent.putExtra("EXTRA_DESC", tattoo.getDescription());
                intent.putExtra("EXTRA_APPT_ID", tattoo.getAppointmentID_FK());
                startActivity(intent);
                */

            } else if (currentMode.equals("DELETE")) {
                new AlertDialog.Builder(this)
                        .setTitle("Delete Tattoo")
                        .setMessage("Delete tattoo '" + tattoo.getDescription() + "'?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            daoTattoo.delete(tattoo.getTattooID_PK());
                            Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT).show();

                            loadInitialData();
                            adapter.setTattoos(allTattoos);
                            tvInfo.setText("");
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        recyclerView.setAdapter(adapter);
        setupSearchListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentMode.equals("UPDATE")) {
            loadInitialData();
            if (adapter != null) adapter.setTattoos(allTattoos);
        }
    }

    private void loadInitialData() {
        allTattoos = daoTattoo.getAllTattoos();
        if (allTattoos == null) {
            allTattoos = new ArrayList<>();
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
            adapter.setTattoos(allTattoos);
            return;
        }
        try {
            int searchID = Integer.parseInt(searchText);
            Tattoo tattoo = daoTattoo.getTattooById(searchID);
            List<Tattoo> filteredList = new ArrayList<>();
            if (tattoo != null) {
                filteredList.add(tattoo);
            }
            adapter.setTattoos(filteredList);
        } catch (NumberFormatException e) {
            adapter.setTattoos(new ArrayList<>());
        }
    }
}