package com.leviness.intellinktualapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MeetingRoomActivity extends AppCompatActivity {

    private static MessageAdapter adapter;
    private static List<Message> messageList = new ArrayList<>();
    private EditText messageInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_room);

        FirebaseApp.initializeApp(this);
        FirebaseMessaging.getInstance().subscribeToTopic("meetingRoom");

        // Initialize RecyclerView
        RecyclerView messageView = findViewById(R.id.recyclerViewMessages);
        messageView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageAdapter(messageList);
        messageView.setAdapter(adapter);

        // Setup buttons and input field
        ImageButton meetingHome = findViewById(R.id.meetingRoom_Home);
        Button sendButton = findViewById(R.id.buttonSend);
        messageInput = findViewById(R.id.editTextMessage);

        meetingHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(MeetingRoomActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        // Add a message listener
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
    }

    private void sendMessage() {
        String messageText = messageInput.getText().toString().trim();
        if (!messageText.isEmpty()) {
            // Add the message to the list
            Message message = new Message(messageText);
            messageList.add(message);
            adapter.notifyItemInserted(messageList.size() - 1);

            // Send the message via FCM
            FirebaseMessaging.getInstance().send(new RemoteMessage.Builder("meetingRoom")
                    .setMessageId(Integer.toString(new Random().nextInt()))
                    .setData(Collections.singletonMap("message", messageText))
                    .build());

            // Clear the input field
            messageInput.setText("");
        }
    }


    public static class FirebaseMessageHandler extends FirebaseMessagingService {
        @Override
        public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
            super.onMessageReceived(remoteMessage);
            if (remoteMessage.getData().size() > 0) {
                // Handle data payload
                String messageText = remoteMessage.getData().get("message");
                Message message = new Message(messageText);
                messageList.add(message);
                adapter.notifyDataSetChanged();
            }
        }
    }

    // Message class to represent a message
    public static class Message {
        private String text;

        public Message(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    // RecyclerView adapter to display messages
    public static class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
        private List<Message> messageList;

        public MessageAdapter(List<Message> messageList) {
            this.messageList = messageList;
        }

        @NonNull
        @Override
        public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
            return new MessageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
            Message message = messageList.get(position);
            holder.messageTextView.setText(message.getText());
        }

        @Override
        public int getItemCount() {
            return messageList.size();
        }

        public static class MessageViewHolder extends RecyclerView.ViewHolder {
            TextView messageTextView;

            public MessageViewHolder(@NonNull View itemView) {
                super(itemView);
                messageTextView = itemView.findViewById(R.id.messageTextView);
            }
        }
    }
}