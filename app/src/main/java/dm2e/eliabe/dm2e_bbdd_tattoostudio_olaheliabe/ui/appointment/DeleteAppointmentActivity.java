package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.appointment;

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
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.dao.DAO_Artist;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Artist;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.artist.ArtistAdapter;

public class DeleteAppointmentActivity extends AppCompatActivity {

    private EditText editTextSearchID;
    private RecyclerView recyclerView;
    private ArtistAdapter adapter;

    private LinearLayout layoutSelectedInfo;
    private TextView tvId, tvName, tvEmail, tvPhone;
    private Button btDelete;

    private List<Artist> allArtists;
    private DAO_Artist daoArtist;

    private int selectedArtistId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_artist);

        daoArtist = new DAO_Artist(this);

        editTextSearchID = findViewById(R.id.et_search_delete);
        // CHANGED: ID from xml
        recyclerView = findViewById(R.id.rv_delete_Artists);

        layoutSelectedInfo = findViewById(R.id.artist_ll_selected_info_container);
        tvId = findViewById(R.id.artist_tv_delete_id);
        tvName = findViewById(R.id.artist_tv_delete_name);
        tvEmail = findViewById(R.id.artist_tv_delete_email);
        tvPhone = findViewById(R.id.artist_tv_delete_phone);

        // CHANGED: ID from xml
        btDelete = findViewById(R.id.artist_bt_delete_Artist);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadInitialData();

        setupSearchListener();

        btDelete.setOnClickListener(v -> deleteSelectedArtist());
    }

    private void loadInitialData() {
        allArtists = daoArtist.getAllArtists();
        if (allArtists == null) {
            allArtists = new ArrayList<>();
        }

        // CHANGED: Using ArtistAdapter
        adapter = new ArtistAdapter(this, allArtists, artist -> {
            showArtistDetails(artist);
        });

        recyclerView.setAdapter(adapter);
    }

    private void showArtistDetails(Artist artist) {
        selectedArtistId = artist.getArtistID_PK();

        tvId.setText("ID: " + artist.getArtistID_PK());
        tvName.setText("Name: " + artist.getFirstName() + " " + artist.getLastName());
        tvEmail.setText("Email: " + artist.getEmail());
        tvPhone.setText("Phone: " + artist.getPhoneNumber());

        layoutSelectedInfo.setVisibility(View.VISIBLE);
        btDelete.setEnabled(true);
        btDelete.setText("DELETE " + artist.getFirstName().toUpperCase());
    }

    private void deleteSelectedArtist() {
        if (selectedArtistId == -1) return;

        int rowsDeleted = daoArtist.delete(selectedArtistId);

        if (rowsDeleted > 0) {
            Toast.makeText(this, "Artist Deleted Successfully!", Toast.LENGTH_SHORT).show();

            selectedArtistId = -1;
            btDelete.setEnabled(false);
            btDelete.setText("DELETE PERMANENTLY");
            layoutSelectedInfo.setVisibility(View.INVISIBLE);

            loadInitialData();

        } else {
            Toast.makeText(this, "Error: Could not delete artist.", Toast.LENGTH_SHORT).show();
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