package strangers.hackmathon.org.strangersinspace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupButton();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupButton() {
        final Button button = (Button) findViewById(R.id.partyButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String partyCode = ((EditText) findViewById(R.id.editText)).getText().toString();
                if (partyCode.equals("5429T")) {
                    Intent myIntent = new Intent(getApplicationContext(), PartyGesturesActivity.class);
                    startActivity(myIntent);
                }
            }
        });
    }

}
