package com.leviness.intellinktualapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class DocumentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.document_activity);

        //Button Declarations
        ImageButton documentsHome=findViewById(R.id.document_home);
        ImageButton launchCamera = findViewById(R.id.document_camera);

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