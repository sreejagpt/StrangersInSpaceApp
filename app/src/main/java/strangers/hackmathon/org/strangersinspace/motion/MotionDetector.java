package strangers.hackmathon.org.strangersinspace.motion;

import android.util.Log;

import java.util.List;

public class MotionDetector {
    public static String deriveMotion(List<Delta> deltas) {
        int sumX = 0;
        int sumY = 0;
        int sumZ = 0;
        for (Delta delta : deltas) {
            sumX += delta.deltaX;
            sumY += delta.deltaY;
            sumZ += delta.deltaZ;
        }

        if (sumX != 0 && Math.abs(sumY) <= 2 && sumZ != 0) {
            Log.d("DEBUG", "CIRCLE");
            return "CIRCLE";
        }

        return null;
    }
//    public static boolean isKnock(List<SensorValue> sensorValues) {
//        for (SensorValue sensorValue: sensorValues) {
//            if (sensorValue.sensorType.equals("android.sensor.gyroscope")) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public static boolean isButtTap(List<SensorValue> sensorValues) {
//        int gyroCount = 0;
//        for (SensorValue sensorValue: sensorValues) {
//            if (sensorValue.sensorType.equals("android.sensor.gyroscope")) {
//                gyroCount++;
//            }
//        }
//        if (gyroCount > (sensorValues.size() / 2)) {
//            return true;
//        }
//        return false;
//    }
}
