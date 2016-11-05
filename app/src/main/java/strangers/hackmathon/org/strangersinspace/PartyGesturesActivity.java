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

import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;

public class PartyGesturesActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor gyroscope;
    private Sensor accelerometer;
    private float[] mGravity;

    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private final String DEBUG_TAG = "[DEBUG]";
    private static boolean isPhoneSensing = false;
    private StopWatch stopWatch = new StopWatch();
    List<SensorValue> gestureValues = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        setContentView(R.layout.content_party_gestures);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    protected void onResume() {
        super.onResume();
        gyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onSensorChanged(SensorEvent event) {
        if (isPhoneSensing && stopWatch.getTime() > 1000) {
            stopWatch.reset();
            isPhoneSensing = false;
            if (MotionDetector.isKnock(gestureValues)) {
                Log.d(DEBUG_TAG, "~~~~~~~~~KNOCK~~~~~~~~~");
                voteForGenre("hiphop");
            } else if (MotionDetector.isButtTap(gestureValues)) {
                Log.d(DEBUG_TAG, "****************BUTT TAP*************");
                voteForGenre("funk");
            }
            gestureValues = new ArrayList<>();
            Log.d(DEBUG_TAG, "Stopped watch, now examining array of values");
        }
        mGravity = event.values.clone();
        float x = mGravity[0];
        float y = mGravity[1];
        float z = mGravity[2];
        mAccelLast = mAccelCurrent;
        mAccelCurrent = (float) sqrt(x * x + y * y + z * z);
        float delta = mAccelCurrent - mAccelLast;
        mAccel = mAccel * 0.9f + delta;
        // Make this higher or lower according to how much
        // motion you want to detect
        if (mAccel > 25 || mAccel < 0) {
            if (!isPhoneSensing) {
                isPhoneSensing = true;
                stopWatch.start();
                gestureValues.add(new SensorValue(event.sensor.getStringType(), mAccel));
            }
        }
    }

    private void voteForGenre(String genre) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://strangers-in-space.herokuapp.com/" + genre;

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
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
