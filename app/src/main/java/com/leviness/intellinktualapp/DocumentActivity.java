package com.leviness.intellinktualapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.util.List;


public class DocumentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.document_activity);

        // Retrieve captured images from Intent extras
        List<File> capturedImages = getIntent().getParcelableExtra("capturedImages");

        // Button Declarations
        ImageButton documentsHome = findViewById(R.id.document_home);
        ImageButton launchCamera = findViewById(R.id.document_camera);
        RecyclerView imageList = findViewById(R.id.image_RV);


        // Initialize RecyclerView
        imageList.setLayoutManager(new LinearLayoutManager(this));

        if (capturedImages != null && !capturedImages.isEmpty()) {
            // Set RecyclerView adapter if list is not empty
            imageList.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
                @NonNull
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    ImageView imageView = new ImageView(parent.getContext());
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    imageView.setAdjustViewBounds(true);
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    return new RecyclerView.ViewHolder(imageView) {};
                }

                @Override
                public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                    ImageView imageView = (ImageView) holder.itemView;
                    // Load image into ImageView using a library like Glide or Picasso
                    // For simplicity, we'll just set the image path directly
                    imageView.setImageURI(Uri.fromFile(capturedImages.get(position)));
                }

                @Override
                public int getItemCount() {
                    return capturedImages.size();
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
}