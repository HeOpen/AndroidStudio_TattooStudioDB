package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.R;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui.customer.InsertCustomerActivity;

public class MainActivity extends AppCompatActivity {

    //Main menu buttons
    private Button btCreate, brRead, btUpdate, btDelete,btER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btCreate = this.findViewById(R.id.bt_create);
        brRead = this.findViewById(R.id.bt_read);
        btUpdate = this.findViewById(R.id.bt_update);
        btDelete = this.findViewById(R.id.bt_delete);
        btER = this.findViewById(R.id.bt_erDiagram);

        btCreate.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, InsertCustomerActivity.class);
            startActivity(intent);
        });
        btCreate.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SelectorActivity.class);
            intent.putExtra("MODE", "INSERT VALUES");
            startActivity(intent);
        });
        brRead.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SelectorActivity.class);
            intent.putExtra("MODE", "READ"); 
            startActivity(intent);
        });
        btUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SelectorActivity.class);
            intent.putExtra("MODE", "UPDATE"); 
            startActivity(intent);
        });
        btDelete.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SelectorActivity.class);
            intent.putExtra("MODE", "DELETE"); 
            startActivity(intent);
        });
        /*
        btER.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, );

            startActivity(intent);
        });
         */

    }
}