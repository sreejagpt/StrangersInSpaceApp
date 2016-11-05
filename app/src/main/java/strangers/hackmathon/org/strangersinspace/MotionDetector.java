package strangers.hackmathon.org.strangersinspace;

import java.util.List;

public class MotionDetector {
    public static boolean isKnock(List<SensorValue> sensorValues) {
        for (SensorValue sensorValue: sensorValues) {
            if (sensorValue.sensorType.equals("android.sensor.gyroscope")) {
                return false;
            }
        }
        return true;
    }

    public static boolean isButtTap(List<SensorValue> sensorValues) {
        int gyroCount = 0;
        for (SensorValue sensorValue: sensorValues) {
            if (sensorValue.sensorType.equals("android.sensor.gyroscope")) {
                gyroCount++;
            }
        }
        if (gyroCount > (sensorValues.size() / 2)) {
            return true;
        }
        return false;
    }
}
