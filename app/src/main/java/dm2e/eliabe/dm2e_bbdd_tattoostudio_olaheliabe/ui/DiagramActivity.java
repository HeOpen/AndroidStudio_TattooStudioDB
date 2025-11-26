package dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import dm2e.eliabe.dm2e_bbdd_tattoostudio_olaheliabe.R;

public class DiagramActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagram);

        // No complex logic needed here, just displaying the static layout!
        setTitle("Documentation");
    }
}