package cz.pikadorama.led;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ToggleButton flashToggle = (ToggleButton) findViewById(R.id.flashToggle);
        flashToggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                if (isChecked) {
                    flashOn();
                } else {
                    flashOff();
                }
            }
        });
    }

    private void flashOn() {
        try {
            camera = Camera.open();
            Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
        } catch (RuntimeException ex) {
            Log.e(TAG, "Error when connecting to camera", ex);
            Toast.makeText(this, R.string.error_connect_to_camera, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void flashOff() {
        camera.release();
        camera = null;
    }

    @Override
    protected void onDestroy() {
        if (camera != null) {
            camera.release();
        }
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
