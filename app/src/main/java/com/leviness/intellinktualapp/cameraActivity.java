package com.leviness.intellinktualapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class cameraActivity extends AppCompatActivity {


    int cameraFacing = CameraSelector.LENS_FACING_BACK;

    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean o) {
            if(o){
                //startCamera(cameraFacing);
            }
        }
    });

   private ImageButton takePicture,pictureDone;
   private  PreviewView documentView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        takePicture=findViewById(R.id.capture_button);
         pictureDone = findViewById(R.id.camera_check_button);

        documentView = findViewById(R.id.camera_preview);

       if(ContextCompat.checkSelfPermission(cameraActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            activityResultLauncher.launch(Manifest.permission.CAMERA);

        }
        else{
            //startCamera(cameraFacing);
        }
    }

    /*public void startCamera(int cameraFacing){
        //int aspectRatio = aspectRatio(documentView.getWidth(),documentView.getHeight());
        ListenableFuture<ProcessCameraProvider> listenableFuture = ProcessCameraProvider.getInstance(this);

        listenableFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try{
                    ProcessCameraProvider cameraProvider = (ProcessCameraProvider) listenableFuture.get();

                    Preview preview = new Preview.Builder().setTargetAspectRatio(aspectRatio).build();
                    ImageCapture imageCapture = new ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY).setTargetRotation(getWindowManager().getDefaultDisplay().getRotation()).build();
                    CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(cameraFacing).build();
                    cameraProvider.unbindAll();

                    Camera camera = cameraProvider.bindToLifecycle(this,cameraSelector,preview,imageCapture);

                    takePicture.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(ContextCompat.checkSelfPermission(cameraActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                                activityResultLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            }
                            else{
                                takePicture(imageCapture);
                            }

                        }
                    });

                    preview.setSurfaceProvider(documentView.getSurfaceProvider());
                } catch (ExecutionException|InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }*/

    public void takePicture(ImageCapture imageCapture){
        final File file = new File(getExternalFilesDir(null),System.currentTimeMillis()+".jpeg");
        ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(file).build();

        imageCapture.takePicture(outputFileOptions, Executors.newCachedThreadPool(), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(cameraActivity.this,"Image Saved"+file.getPath(),Toast.LENGTH_SHORT).show();


                    }
                });
               // startCamera(cameraFacing);
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {

            }
        });
    }

    private int aspectRatio(int width, int height) {

        double previewRatio = (double) Math.max(width, height)/Math.min(width, height);

        if(Math.abs(previewRatio-4.0/3.0)<=Math.abs(previewRatio-16.0/9.0)){
            return AspectRatio.RATIO_4_3;
        }
        return AspectRatio.RATIO_16_9;
    }


}
