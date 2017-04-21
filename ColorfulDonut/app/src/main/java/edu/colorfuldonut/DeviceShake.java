package edu.colorfuldonut;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by DavidKramer on 4/21/17.
 */

public class DeviceShake {
    private static final float SHAKE_THRESHOLD = 12f;

    protected Context m_context;
    protected SensorManager m_sensorManager;
    protected float m_accel;
    protected float m_accelCurrent;
    protected float m_accelLast;

    protected ShakeCallback m_shakeCallback;

    public DeviceShake(Context context, ShakeCallback shakeCallback) {
        m_context = context;
        m_sensorManager = (SensorManager)m_context.getSystemService(Context.SENSOR_SERVICE);
        m_shakeCallback = shakeCallback;
    }

    public void register() {
        m_sensorManager.registerListener(m_sensorListener,
                m_sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregister() {
        m_sensorManager.unregisterListener(m_sensorListener);
    }

    private final SensorEventListener m_sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            m_accelLast = m_accelCurrent;
            m_accelCurrent = (float)Math.sqrt(((x * x) + (y * y) + (z * z)));

            float delta = (m_accelCurrent - m_accelLast);
            m_accel = (m_accel * 0.9f + delta);

            if (m_accel > SHAKE_THRESHOLD) {
                m_shakeCallback.onShake();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
            // unused
        }
    };

    interface ShakeCallback {
        void onShake();
    }
}
