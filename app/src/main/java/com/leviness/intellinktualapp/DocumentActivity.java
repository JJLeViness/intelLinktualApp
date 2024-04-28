package com.leviness.intellinktualapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class DocumentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.document_activity);

        // Retrieve captured images from Intent extras
        List<String> capturedImagePaths = getIntent().getStringArrayListExtra("capturedImagePaths");

        // Button Declarations
        ImageButton documentsHome = findViewById(R.id.document_home);
        ImageButton launchCamera = findViewById(R.id.document_camera);
        RecyclerView imageList = findViewById(R.id.image_RV);


        // Initialize RecyclerView
        imageList.setLayoutManager(new LinearLayoutManager(this));

        if (capturedImagePaths != null && !capturedImagePaths.isEmpty()) {
            // Set RecyclerView adapter if list is not empty
            imageList.setAdapter(new RecyclerView.Adapter<ImageViewHolder>() {
                @NonNull
                @Override
                public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View itemView = getLayoutInflater().inflate(R.layout.item_image, parent, false);
                    return new ImageViewHolder(itemView);
                }

                @Override
                public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
                    String imagePath = capturedImagePaths.get(position);
                    Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                    holder.imageView.setImageBitmap(bitmap);
                }

                @Override
                public int getItemCount() {
                    return capturedImagePaths.size();
                }
            });
        } else {

            imageList.setVisibility(View.GONE); // Hide RecyclerView

        }

        // Click listeners for buttons
        documentsHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(DocumentActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });

        launchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(DocumentActivity.this, cameraActivity.class);
                startActivity(cameraIntent);
            }
        });




    }
    private static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

}