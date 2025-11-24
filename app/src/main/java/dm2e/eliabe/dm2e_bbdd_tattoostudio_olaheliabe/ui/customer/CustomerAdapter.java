package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.R;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.models.Customer;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Customer customer);
    }

    private List<Customer> customerList;
    private final Context context;

    private final OnItemClickListener listener;

    public CustomerAdapter(Context context, List<Customer> list, OnItemClickListener listener) {
        this.context = context;
        this.customerList = list;
        this.listener = listener;
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder {
        final TextView idTextView;
        final TextView nameTextView;
        final TextView phoneTextView;

        public CustomerViewHolder(View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.textViewCustomerId);
            nameTextView = itemView.findViewById(R.id.textViewCustomerName);
            phoneTextView = itemView.findViewById(R.id.textViewCustomerPhone);
        }
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_customer, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer customer = customerList.get(position);

        holder.idTextView.setText("ID: " + customer.getCustomerID());
        holder.nameTextView.setText(customer.getFirstName());
        holder.phoneTextView.setText(customer.getPhoneNumber());

        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(customer);
        });
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public void setCustomers(List<Customer> newCustomers) {
        this.customerList = newCustomers;
        notifyDataSetChanged();
    }
}