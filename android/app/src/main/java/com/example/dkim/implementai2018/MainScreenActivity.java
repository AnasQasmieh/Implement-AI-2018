package com.example.dkim.implementai2018;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainScreenActivity extends AppCompatActivity {
    final static int CAMERA_CODE = 1010;

    MaterialButton takePhoto;
    MaterialButton chooseGallery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        takePhoto = findViewById(R.id.takePhoto);
        chooseGallery = findViewById(R.id.chooseGallery);


    }

    private void checkAndAskForPermission() {
        if (!isSmsPermissionGranted()) {
            requestReadAndSendSmsPermission();
        }
    }


    /**
     * Check if we have Camera permission
     */
    private Boolean isSmsPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Request runtime Camera permission
     */
    private void requestReadAndSendSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
            // You may display a non-blocking explanation here, read more in the documentation:
            // https://developer.android.com/training/permissions/requesting.html
        }
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE };
        ActivityCompat.requestPermissions(this, permissions, CAMERA_CODE);
    }

    /**
     * Catch permission results.
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted. yay.

                } else {
                    // permission denied
                }
                break;
            default:
                break;
        }
    }
}
