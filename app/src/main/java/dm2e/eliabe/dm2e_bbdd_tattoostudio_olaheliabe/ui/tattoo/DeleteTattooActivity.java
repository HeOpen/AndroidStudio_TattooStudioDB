package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.tattoo;

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
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.dao.DAO_Tattoo;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Tattoo;

public class DeleteTattooActivity extends AppCompatActivity {

    private EditText editTextSearchID;
    private RecyclerView recyclerView;
    private TattooAdapter adapter;

    private LinearLayout layoutSelectedInfo;
    private TextView tvId, tvDesc, tvBody, tvPrice;
    private Button btDelete;

    private List<Tattoo> allTattoos;
    private DAO_Tattoo daoTattoo;

    private int selectedTattooId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_tattoo);

        daoTattoo = new DAO_Tattoo(this);

        editTextSearchID = findViewById(R.id.et_search_delete_tattoo);
        recyclerView = findViewById(R.id.rv_delete_tattoos);

        layoutSelectedInfo = findViewById(R.id.ll_selected_info_container);
        tvId = findViewById(R.id.tv_delete_id);
        tvDesc = findViewById(R.id.tv_delete_desc);
        tvBody = findViewById(R.id.tv_delete_body);
        tvPrice = findViewById(R.id.tv_delete_price);

        btDelete = findViewById(R.id.bt_delete_tattoo);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadInitialData();

        setupSearchListener();

        btDelete.setOnClickListener(v -> deleteSelectedTattoo());
    }

    private void loadInitialData() {
        allTattoos = daoTattoo.getAllTattoos();
        if (allTattoos == null) {
            allTattoos = new ArrayList<>();
        }

        adapter = new TattooAdapter(this, allTattoos, tattoo -> {
            showTattooDetails(tattoo);
        });

        recyclerView.setAdapter(adapter);
    }

    private void showTattooDetails(Tattoo tattoo) {
        selectedTattooId = tattoo.getTattooID_PK();

        tvId.setText("ID: " + tattoo.getTattooID_PK());
        tvDesc.setText("Desc: " + tattoo.getDescription());
        tvBody.setText("Body: " + tattoo.getBodyPart());
        tvPrice.setText("Price: $" + tattoo.getPrice());

        layoutSelectedInfo.setVisibility(View.VISIBLE);
        btDelete.setEnabled(true);
        btDelete.setText("DELETE TATTOO ID " + tattoo.getTattooID_PK());
    }

    private void deleteSelectedTattoo() {
        if (selectedTattooId == -1) return;

        int rowsDeleted = daoTattoo.delete(selectedTattooId);

        if (rowsDeleted > 0) {
            Toast.makeText(this, "Tattoo Deleted Successfully!", Toast.LENGTH_SHORT).show();

            selectedTattooId = -1;
            btDelete.setEnabled(false);
            btDelete.setText("DELETE PERMANENTLY");
            layoutSelectedInfo.setVisibility(View.INVISIBLE);

            loadInitialData();

        } else {
            Toast.makeText(this, "Error: Could not delete tattoo.", Toast.LENGTH_SHORT).show();
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