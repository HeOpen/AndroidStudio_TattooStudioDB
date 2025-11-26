package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.artist;

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
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.dao.DAO_Artist;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.dao.DAO_Customer;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Artist;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Customer;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.customer.CustomerAdapter;

public class EditArtistActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etEmail, etPhone;
    private Button btUpdate;
    private RecyclerView recyclerView;
    private ArtistAdapter adapter;

    private DAO_Artist dao;
    private int selectedCustomerId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_artist);

        dao = new DAO_Artist(this);

        // Initialize Views
        etFirstName = findViewById(R.id.artist_et_firstName);
        etLastName = findViewById(R.id.artist_et_lastName);
        etEmail = findViewById(R.id.artist_et_email);
        etPhone = findViewById(R.id.artist_et_phone);
        btUpdate = findViewById(R.id.artist_bt_save_customer);
        recyclerView = findViewById(R.id.artist_recyclerViewCustomers);

        btUpdate.setText("Select an Artist to Edit");
        btUpdate.setEnabled(false);

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

            Artist artistToUpdate = new Artist(selectedCustomerId, fName, lName, email, phone);

            int rows = dao.update(artistToUpdate);

            if (rows > 0) {
                Toast.makeText(this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                loadCustomersList();
                clearForm();
            } else {
                Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadCustomersList() {
        List<Artist> allArtists = dao.getAllArtists();
        if (allArtists == null) allArtists = new ArrayList<>();

        adapter = new ArtistAdapter(this, allArtists, artist -> {
            populateForm(artist);
        });

        recyclerView.setAdapter(adapter);
    }

    private void populateForm(Artist artist) {
        selectedCustomerId = artist.getArtistID_PK();
        etFirstName.setText(artist.getFirstName());
        etLastName.setText(artist.getLastName());
        etEmail.setText(artist.getEmail());
        etPhone.setText(artist.getPhoneNumber());

        btUpdate.setText("Update Artist (ID: " + selectedCustomerId + ")");
        btUpdate.setEnabled(true);
    }

    private void clearForm() {
        selectedCustomerId = -1;
        etFirstName.setText("");
        etLastName.setText("");
        etEmail.setText("");
        etPhone.setText("");
        btUpdate.setText("Select a Artist to Edit");
        btUpdate.setEnabled(false);
    }
}