package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.tattoo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.R;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Tattoo;

public class TattooAdapter extends RecyclerView.Adapter<TattooAdapter.TattooViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Tattoo tattoo);
    }

    private List<Tattoo> tattooList;
    private final Context context;
    private final OnItemClickListener listener;

    public TattooAdapter(Context context, List<Tattoo> list, OnItemClickListener listener) {
        this.context = context;
        this.tattooList = list;
        this.listener = listener;
    }

    public static class TattooViewHolder extends RecyclerView.ViewHolder {
        final TextView tvMain;
        final TextView tvSub;
        final TextView tvDetail;

        public TattooViewHolder(View itemView) {
            super(itemView);
            // Reusing the same item layout (assuming generic IDs)
            tvMain = itemView.findViewById(R.id.textViewCustomerName); // Description
            tvSub = itemView.findViewById(R.id.textViewCustomerPhone); // Body Part / Price
            tvDetail = itemView.findViewById(R.id.textViewCustomerId); // ID
        }
    }

    @NonNull
    @Override
    public TattooViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_customer, parent, false);
        return new TattooViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TattooViewHolder holder, int position) {
        Tattoo tattoo = tattooList.get(position);

        holder.tvDetail.setText("ID: " + tattoo.getTattooID_PK());
        holder.tvMain.setText(tattoo.getDescription());
        holder.tvSub.setText(tattoo.getBodyPart() + " - $" + tattoo.getPrice());

        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(tattoo);
        });
    }

    @Override
    public int getItemCount() {
        return tattooList.size();
    }

    public void setTattoos(List<Tattoo> newTattoos) {
        this.tattooList = newTattoos;
        notifyDataSetChanged();
    }
}