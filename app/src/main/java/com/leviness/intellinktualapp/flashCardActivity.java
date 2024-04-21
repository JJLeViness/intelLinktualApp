package com.leviness.intellinktualapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class flashCardActivity extends AppCompatActivity {

    FlashcardAdapter adapter;
    List<flashCard> flashCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card);

        //declare buttons
        ImageButton homeButton = findViewById(R.id.flashCardHome);
         RecyclerView flashCard = findViewById(R.id.flashCard_RV);
         flashCard.setLayoutManager(new LinearLayoutManager(this));
         flashCards = createSampleFlashcards();

        adapter = new FlashcardAdapter( flashCards);
        flashCard.setAdapter(adapter);




        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(flashCardActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });
    }
    public class FlashcardAdapter extends RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder> {

        private List<flashCard> flashcards;

        public FlashcardAdapter(List<flashCard> flashcards) {
            this.flashcards = flashcards;
        }

        @NonNull
        @Override
        public FlashcardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flashcard, parent, false);
            return new FlashcardViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FlashcardViewHolder holder, int position) {
            flashCard flashcard = flashcards.get(position);
            holder.bind(flashcard);
        }

        @Override
        public int getItemCount() {
            return flashcards.size();
        }

        public class FlashcardViewHolder extends RecyclerView.ViewHolder {
            private TextView questionTextView;
            private TextView answerTextView;

            public FlashcardViewHolder(@NonNull View itemView) {
                super(itemView);
                questionTextView = itemView.findViewById(R.id.questionTextView);
                answerTextView = itemView.findViewById(R.id.answerTextView);
            }

            public void bind(flashCard flashcard) {
                questionTextView.setText(flashcard.getQuestion());
                answerTextView.setText(flashcard.getAnswer());
            }

        }
    }
    public List<flashCard> createSampleFlashcards(){
        List<flashCard> flashCards = new ArrayList<>();
        flashCards.add(new flashCard("Question 1", "Answer 1"));
        flashCards.add(new flashCard("Question 2", "Answer 2"));
        return flashCards;
    }
}
