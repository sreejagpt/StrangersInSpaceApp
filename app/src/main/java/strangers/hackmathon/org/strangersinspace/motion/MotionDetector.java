package strangers.hackmathon.org.strangersinspace.motion;

import android.util.Log;

import java.util.List;

public class MotionDetector {
    public static String deriveMotion(List<Delta> deltas) {
        int sumX = 0;
        int sumY = 0;
        int sumZ = 0;
        for (Delta delta : deltas) {
//            Log.d("DEBUG", String.format("%s %s %s", delta.deltaX, delta.deltaY, delta.deltaZ));
            sumX += delta.deltaX;
            sumY += delta.deltaY;
            sumZ += delta.deltaZ;
        }
        Log.d("DEBUG", String.format("%s %s %s", sumX, sumY, sumZ));

        if (Math.abs(sumZ) > 6) {
            Log.d("DEBUG", "BUTTTAP");
            return "BUTTTAP";
        }

        if (Math.abs(sumX) > 5 && Math.abs(sumY) > 0 && Math.abs(sumZ) < 2) {
            Log.d("DEBUG", "KNOCK");
            return "KNOCK";
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
