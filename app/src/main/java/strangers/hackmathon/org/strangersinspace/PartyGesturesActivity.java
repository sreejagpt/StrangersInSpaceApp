package strangers.hackmathon.org.strangersinspace;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class PartyGesturesActivity extends AppCompatActivity implements SensorEventListener {
    public static final String KNOCK = "KNOCK";
    public static final String BUTTTAP = "BUTTTAP";
    private SensorManager mSensorManager;
    private Sensor gyroscope;
    private Sensor accelerometer;
    private float[] mGravity;

    private final String DEBUG_TAG = "[DEBUG]";
    Map<String, Integer> gestureCount = new HashMap<>();
    long lastDate = 0l;
    private double lastAccelerationX = 0;
    private double lastAccelerationY = 0;
    private double lastAccelerationZ = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        setContentView(R.layout.content_party_gestures);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gestureCount.put(KNOCK, 0);
        gestureCount.put(BUTTTAP, 0);
    }

    protected void onResume() {
        super.onResume();
        gyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        mSensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        long newDate = event.timestamp;
        if (newDate - lastDate > 2000000000) {
            Log.d(DEBUG_TAG, "2 seconds later");
            lastDate = newDate;
            if (gestureCount.get(KNOCK) > gestureCount.get(BUTTTAP)) {
                Log.d("GESTURE", "KNOCK! ==================" + gestureCount.get(KNOCK) + " " + gestureCount.get(BUTTTAP));
                voteForGenre("hiphop");
            } else if (gestureCount.get(KNOCK) < gestureCount.get(BUTTTAP)) {
                Log.d("GESTURE", "BUTT TAP! *****************" + gestureCount.get(KNOCK) + " " + gestureCount.get(BUTTTAP));
                voteForGenre("funk");
            }
            gestureCount.put(KNOCK, 0);
            gestureCount.put(BUTTTAP, 0);
        } else {
            final float alpha = 0.8f;

            double[] gravity = new double[3];
            double[] linear_acceleration = new double[3];

            gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
            gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
            gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

            linear_acceleration[0] = event.values[0] - gravity[0];
            linear_acceleration[1] = event.values[1] - gravity[1];
            linear_acceleration[2] = event.values[2] - gravity[2];

            double deltaX = linear_acceleration[0] - lastAccelerationX;

            double deltaY = linear_acceleration[1] - lastAccelerationY;
            double deltaZ = linear_acceleration[2] - lastAccelerationZ;

            lastAccelerationX = linear_acceleration[0];
            lastAccelerationY = linear_acceleration[1];
            lastAccelerationZ = linear_acceleration[2];



            if (deltaX > 1 && deltaY > 1 && deltaZ < 5) {
                incrementGestureOfType(KNOCK);
            } else if (deltaZ > 6) {
                incrementGestureOfType(BUTTTAP);
            }
        }
    }

    private void incrementGestureOfType(String gestureType) {
        int vote = 1;
        if (gestureType.equals(BUTTTAP)) {
            vote *= 2;
        }
        gestureCount.put(gestureType, gestureCount.get(gestureType) + vote);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
//
//    public void onSensorChanged(SensorEvent event) {
//        if (isPhoneSensing && stopWatch.getTime() > 1000) {
//            stopWatch.reset();
//            isPhoneSensing = false;
//            if (MotionDetector.isKnock(gestureValues)) {
//                Log.d(DEBUG_TAG, "~~~~~~~~~KNOCK~~~~~~~~~");
//                voteForGenre("hiphop");
//                Button hipHopButton = (Button) findViewById(R.id.hiphopButton);
//                hipHopButton.setBackgroundColor(Color.CYAN);
//                Button funkButton = (Button) findViewById(R.id.funkButton);
//                funkButton.setBackgroundColor(Color.LTGRAY);
//            } else if (MotionDetector.isButtTap(gestureValues)) {
//                Log.d(DEBUG_TAG, "****************BUTT TAP*************");
//                Button funkButton = (Button) findViewById(R.id.funkButton);
//                funkButton.setBackgroundColor(Color.CYAN);
//                Button hipHopButton = (Button) findViewById(R.id.hiphopButton);
//                hipHopButton.setBackgroundColor(Color.LTGRAY);
//                voteForGenre("funk");
//            }
//            gestureValues = new ArrayList<>();
//            Log.d(DEBUG_TAG, "Stopped watch, now examining array of values");
//        }
//        mGravity = event.values.clone();
//        float x = mGravity[0];
//        float y = mGravity[1];
//        float z = mGravity[2];
//        mAccelLast = mAccelCurrent;
//        mAccelCurrent = (float) sqrt(x * x + y * y + z * z);
//        float delta = mAccelCurrent - mAccelLast;
//        mAccel = mAccel * 0.9f + delta;
//        // Make this higher or lower according to how much
//        // motion you want to detect
//        if (mAccel > 25 || mAccel < 0) {
//            if (!isPhoneSensing) {
//                isPhoneSensing = true;
//                stopWatch.start();
//                gestureValues.add(new SensorValue(event.sensor.getStringType(), mAccel));
//            }
//        }
//    }

    private void voteForGenre(String genre) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://strangers-in-space.herokuapp.com/vote/" + genre;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }
}
