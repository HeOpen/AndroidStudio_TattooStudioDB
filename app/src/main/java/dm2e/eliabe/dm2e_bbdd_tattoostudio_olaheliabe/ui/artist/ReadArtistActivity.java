package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.artist;

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
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.dao.DAO_Artist; // DAO Diferente
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Artist; // Modelo Diferente

public class ReadArtistActivity extends AppCompatActivity {

    private EditText editTextSearchID;
    private RecyclerView recyclerView;
    private TextView tvArtistInfo;
    private ArtistAdapter adapter;
    private List<Artist> allArtists;
    private DAO_Artist daoArtist;

    private String currentMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_artist);

        currentMode = getIntent().getStringExtra("MODE");
        if (currentMode == null) currentMode = "READ";

        setTitle("Select Artist to " + currentMode);

        daoArtist = new DAO_Artist(this);
        editTextSearchID = findViewById(R.id.editTextSearchID);
        recyclerView = findViewById(R.id.recyclerViewCustomers);
        tvArtistInfo = findViewById(R.id.tv_selected_customer_info);

        loadInitialData();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ArtistAdapter(this, allArtists, artist -> {

            if (currentMode.equals("READ")) {
                tvArtistInfo.setText(artist.toString());

            } else if (currentMode.equals("UPDATE")) {
                Toast.makeText(this, "Edit Artist: Coming Soon", Toast.LENGTH_SHORT).show();

            } else if (currentMode.equals("DELETE")) {
                new AlertDialog.Builder(this)
                        .setTitle("Delete Artist")
                        .setMessage("Are you sure you want to delete " + artist.getFirstName() + "?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            daoArtist.delete(artist.getArtistID_PK()); // Asegúrate que DAO_Artist tenga delete()
                            Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT).show();
                            loadInitialData();
                            adapter.setArtists(allArtists);
                            tvArtistInfo.setText("");
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        recyclerView.setAdapter(adapter);
        setupSearchListener();
    }

    private void loadInitialData() {
        allArtists = daoArtist.getAllArtists();
        if (allArtists == null) {
            allArtists = new ArrayList<>();
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
            adapter.setArtists(allArtists);
            return;
        }
        try {
            int searchID = Integer.parseInt(searchText);
            Artist artist = daoArtist.getArtistById(searchID);
            List<Artist> filteredList = new ArrayList<>();
            if (artist != null) {
                filteredList.add(artist);
            }
            adapter.setArtists(filteredList);
        } catch (NumberFormatException e) {
            adapter.setArtists(new ArrayList<>());
        }
    }
}