package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.appointment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.R;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Appointment;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Appointment appointment);
    }

    private List<Appointment> appointmentList;
    private final Context context;
    private final OnItemClickListener listener;

    public AppointmentAdapter(Context context, List<Appointment> list, OnItemClickListener listener) {
        this.context = context;
        this.appointmentList = list;
        this.listener = listener;
    }

    public static class AppointmentViewHolder extends RecyclerView.ViewHolder {
        // Reusing IDs from item_customer.xml or item_artist.xml if you haven't created item_appointment.xml
        // Ideally, create a generic item layout or specific ones.
        final TextView tvMainInfo; // Date
        final TextView tvSubInfo;  // Status
        final TextView tvDetailInfo; // IDs

        public AppointmentViewHolder(View itemView) {
            super(itemView);
            // Make sure these IDs match your XML layout for the row item
            tvMainInfo = itemView.findViewById(R.id.textViewCustomerName);
            tvSubInfo = itemView.findViewById(R.id.textViewCustomerPhone);
            tvDetailInfo = itemView.findViewById(R.id.textViewCustomerId);
        }
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // You can reuse item_customer.xml if the TextView IDs match
        View view = LayoutInflater.from(context).inflate(R.layout.item_customer, parent, false);
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        Appointment appointment = appointmentList.get(position);

        // Display Appointment specific data
        holder.tvDetailInfo.setText("ID: " + appointment.getAppointmentID_PK());
        holder.tvMainInfo.setText("Date: " + appointment.getDateTime());
        holder.tvSubInfo.setText("Status: " + appointment.getStatus());

        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(appointment);
        });
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public void setAppointments(List<Appointment> newAppointments) {
        this.appointmentList = newAppointments;
        notifyDataSetChanged();
    }
}