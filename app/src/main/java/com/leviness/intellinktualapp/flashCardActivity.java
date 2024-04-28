package com.leviness.intellinktualapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class flashCardActivity extends AppCompatActivity {

    FlashcardAdapter adapter;
    List<flashCard> flashCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card);

        // Declare RecyclerView and other views
        RecyclerView flashCardRV = findViewById(R.id.flashCard_RV);
        ImageButton homeButton = findViewById(R.id.flashCardHome);
        ImageButton addFlashcard = findViewById(R.id.add_flashCard_button);

        flashCards = createSampleFlashcards(); // Method to create sample flashcards

        adapter = new FlashcardAdapter(flashCards);
        flashCardRV.setAdapter(adapter);
        flashCardRV.setLayoutManager(new LinearLayoutManager(this));

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(flashCardActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });

        addFlashcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View popupView = LayoutInflater.from(flashCardActivity.this).inflate(R.layout.add_flash_card,null);
                EditText questionET = popupView.findViewById(R.id.question_ET);
                EditText AnswerET = popupView.findViewById(R.id.Answer_ET);
                ImageButton addCard = popupView.findViewById(R.id.addflashcarddone_ib);

                AlertDialog.Builder builder = new AlertDialog.Builder(flashCardActivity.this);
                builder.setView(popupView);
                AlertDialog dialog = builder.create();
                dialog.show();

                addCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String question = questionET.getText().toString();
                        String answer = AnswerET.getText().toString();


                        if (!question.isEmpty() && !answer.isEmpty()) {

                            flashCard newFlashcard = new flashCard(question, answer);


                            flashCards.add(newFlashcard);
                            adapter.notifyDataSetChanged();


                            dialog.dismiss();
                        } else {
                            // Show an error message if either question or answer is empty
                            Toast.makeText(flashCardActivity.this, "Please enter both question and answer", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
    }


    public List<flashCard> createSampleFlashcards() {
        List<flashCard> flashCards = new ArrayList<>();
        flashCards.add(new flashCard("Question 1", "Answer 1"));
        flashCards.add(new flashCard("Question 2", "Answer 2"));
        return flashCards;
    }

    public static class FlashcardAdapter extends RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder> {
        private List<flashCard> flashcards;

        public FlashcardAdapter(List<flashCard> flashcards) {
            this.flashcards = flashcards;
        }

        @Override
        public FlashcardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flashcard, parent, false);
            return new FlashcardViewHolder(view);
        }

        @Override
        public void onBindViewHolder(FlashcardViewHolder holder, int position) {
            flashCard flashcard = flashcards.get(position);
            holder.questionTextView.setText(flashcard.getQuestion());
            holder.answerTextView.setText(flashcard.getAnswer());
        }

        @Override
        public int getItemCount() {
            return flashcards.size();
        }

        public static class FlashcardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView questionTextView;
            private TextView answerTextView;
            private boolean isFront = true;

            public FlashcardViewHolder(@NonNull View itemView) {
                super(itemView);
                questionTextView = itemView.findViewById(R.id.questionTextView);
                answerTextView = itemView.findViewById(R.id.answerTextView);
                answerTextView.setVisibility(View.GONE);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (isFront) {
                    answerTextView.setVisibility(View.VISIBLE);
                    isFront = false;
                } else {
                    answerTextView.setVisibility(View.GONE);
                    isFront = true;
                }
            }

            public void bind(flashCard flashcard) {
                questionTextView.setText(flashcard.getQuestion());
                answerTextView.setText(flashcard.getAnswer());
            }
        }

    }
}
