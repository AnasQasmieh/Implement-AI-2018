package com.example.dkim.implementai2018;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.example.dkim.implementai2018.api.MyRetrofitFactory;
import com.example.dkim.implementai2018.api.plantDisease.ICustomVisionService;
import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import timber.log.Timber;

public class MainScreenActivity extends AppCompatActivity {
    final static int CAMERA_CODE = 1010;

    final static int CAMERA_PIC_REQUEST = 1;
    final static int CHOOSE_FROM_GALLERY = 2;

    MaterialButton takePhoto;
    MaterialButton chooseGallery;

    private String imageFilePath;
    private Uri imageFileUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        setUpButtons();
    }

    private void setUpButtons() {
        takePhoto = findViewById(R.id.takePhoto);
        chooseGallery = findViewById(R.id.chooseGallery);

        takePhoto.setOnClickListener(v -> {
            checkAndAskForPermission();

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = createImageFile();


                if (photoFile != null) {
                    imageFileUri = FileProvider.getUriForFile(
                            this,
                            "com.example.dkim.implementai2018.provider",
                            photoFile
                    );

                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                } else {
                        Timber.e("error creating new file");
                }
            }
        });

        chooseGallery.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), CHOOSE_FROM_GALLERY);
        });
    }

    private File createImageFile() {
        // Save photo to a file, so that we can access it in gallery.
        // File name will be some number.jpg
        String filename = String.format("%s", System.currentTimeMillis());
        Timber.d("file name will be %s", filename);
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photoFile = null;
        try {
            photoFile = File.createTempFile(
                    filename,
                    ".jpg",
                    storageDir
            );
        } catch (IOException ex) {
            Timber.e(ex.getMessage());
        }
        if (photoFile != null) {
            imageFilePath = photoFile.getAbsolutePath();
        } else {
            Timber.e("new file path is null");
        }
        return photoFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST){
            if (data != null) {
                sendImageToAzure(imageFilePath);
            }
        }
        else if (requestCode == CHOOSE_FROM_GALLERY){
            Uri imageUri = data.getData();
            String path = getRealPathFromURI(this, imageUri);
            sendImageToAzure(path);
        }
    }

    private void sendImageToAzure(String imageFilePath) {
        File image = new File(imageFilePath);

        ICustomVisionService customVisionService = MyRetrofitFactory.INSTANCE.getCustomVisionService();
        RequestBody abody = RequestBody.create(
                MediaType.parse("application/octet-stream"),
                image);
        Disposable getPredictionDisposable = customVisionService.getPrediction(abody)
            .subscribeOn(Schedulers.io())
            .subscribe(plantClassificationResponse -> Timber.d(plantClassificationResponse.toString()),
                    throwable -> Timber.e(throwable.toString()));
    }

    private String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            Timber.e("getRealPathFromURI Exception : " + e.toString());
            return "";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void checkAndAskForPermission() {
        if (!isSmsPermissionGranted()) {
            requestCameraAndStoragePermission();
        }
    }


    /**
     * Check if we have Camera permission
     */
    private Boolean isSmsPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Request runtime Camera permission
     */
    private void requestCameraAndStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            // You may display a non-blocking explanation here, read more in the documentation:
            // https://developer.android.com/training/permissions/requesting.html
        }
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE };
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
