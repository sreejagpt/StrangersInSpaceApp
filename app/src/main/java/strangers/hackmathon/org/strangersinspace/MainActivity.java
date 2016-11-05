package strangers.hackmathon.org.strangersinspace;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final float EPSILON = 0.1f;
    private SensorManager mSensorManager;
    private Sensor gyroscope;
    private Sensor accelerometer;
    private float[] mGravity;


    // Create a constant to convert nanoseconds to seconds.
    private static final float NS2S = 1.0f / 1000000000.0f;
    private final float[] deltaRotationVector = new float[4];
    private float timestamp;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private String debug = "[DEBUG]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupButton();
//        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

//        mAccel = 0.00f;
//        mAccelCurrent = SensorManager.GRAVITY_EARTH;
//        mAccelLast = SensorManager.GRAVITY_EARTH;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupButton() {
        final Button button = (Button) findViewById(R.id.partyButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String partyCode = ((EditText) findViewById(R.id.editText)).getText().toString();
                if (partyCode.equals("5429T") || true) {
                    Intent myIntent = new Intent(getApplicationContext(), PartyGesturesActivity.class);
                    startActivity(myIntent);
                }
            }
        });
    }

//    protected void onResume() {
//        super.onResume();
////        gyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
////        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
////        mSensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_FASTEST);
////        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
//
//    }
//
//    protected void onPause() {
//        super.onPause();
//        mSensorManager.unregisterListener(this);
//    }
//
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//    }
//
//
//    public void onSensorChanged(SensorEvent event) {
//
//            mGravity = event.values.clone();
//            // Shake detection
//            float x = mGravity[0];
//            float y = mGravity[1];
//            float z = mGravity[2];
//            mAccelLast = mAccelCurrent;
//            mAccelCurrent = (float) sqrt(x*x + y*y + z*z);
//            float delta = mAccelCurrent - mAccelLast;
//            mAccel = mAccel * 0.9f + delta;
//            // Make this higher or lower according to how much
//            // motion you want to detect
//            if(mAccel > 2) {
//                Log.d(debug, "========== " + event.sensor.getStringType() + " ============");
//                Log.d(debug, "x = " + x + "y = " + y + " z= " + z);
//            }
//        }


}
