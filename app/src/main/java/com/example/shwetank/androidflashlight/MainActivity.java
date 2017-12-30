package com.example.shwetank.androidflashlight;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Button mSwitch;
    private boolean mFlashLightStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findId();

        final boolean isCapable = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (isCapable)
            flashLightOn();

        mSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCapable) {
                    if (mFlashLightStatus) {
                        flashLightOff();
                    } else {
                        flashLightOn();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Your Mobile Device does not have FlashLight", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void findId() {
        mSwitch = findViewById(R.id.btn_switch);
    }

    private void flashLightOff() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            if (null != cameraManager) {
                String cameraId = cameraManager.getCameraIdList()[0];
                cameraManager.setTorchMode(cameraId, false);
                mFlashLightStatus = false;
                mSwitch.setBackgroundResource(R.drawable.btn_switch_off);
            }
        } catch (CameraAccessException e) {
        }
    }

    private void flashLightOn() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            if (null != cameraManager) {
                String cameraId = cameraManager.getCameraIdList()[0];
                cameraManager.setTorchMode(cameraId, true);
                mFlashLightStatus = true;
                mSwitch.setBackgroundResource(R.drawable.btn_switch_on);
            }
        } catch (CameraAccessException e) {
        }
    }

}
