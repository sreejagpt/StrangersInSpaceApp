package strangers.hackmathon.org.strangersinspace;

import java.util.List;

public class MotionDetector {
    public boolean isKnock(List<SensorValue> sensorValues) {
        for (SensorValue sensorValue: sensorValues) {
            if (sensorValue.sensorType.equals("android.sensor.gyroscope")) {
                return false;
            }
        }
        return true;
    }

    public boolean isButtTap(List<SensorValue> sensorValues) {
        for (SensorValue sensorValue: sensorValues) {
            if (sensorValue.sensorType.equals("android.sensor.gyroscope")) {
                return true;
            }
        }
        return false;
    }
}
