package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.artist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.R;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Artist;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Artist artist);
    }

    private List<Artist> artistList;
    private final Context context;
    private final OnItemClickListener listener;

    public ArtistAdapter(Context context, List<Artist> list, OnItemClickListener listener) {
        this.context = context;
        this.artistList = list;
        this.listener = listener;
    }

    public static class ArtistViewHolder extends RecyclerView.ViewHolder {
        final TextView idTextView;
        final TextView nameTextView;
        final TextView infoTextView;

        public ArtistViewHolder(View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.artistTextViewCustomerId); // O textViewArtistId
            nameTextView = itemView.findViewById(R.id.artistTextViewCustomerName);
            infoTextView = itemView.findViewById(R.id.artistTextViewCustomerPhone);
        }
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_artist, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        Artist artist = artistList.get(position);

        holder.idTextView.setText("ID: " + artist.getArtistID_PK());
        holder.nameTextView.setText(artist.getFirstName() + " " + artist.getLastName());
        holder.infoTextView.setText(artist.getEmail()); // Mostramos Email para variar

        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(artist);
        });
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    public void setArtists(List<Artist> newArtists) {
        this.artistList = newArtists;
        notifyDataSetChanged();
    }
}