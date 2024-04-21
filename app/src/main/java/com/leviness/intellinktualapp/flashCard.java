package com.leviness.intellinktualapp;

public class flashCard {
    private String question;
    private String answer;

    public flashCard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public static String getQuestion() {
        return question;
    }

    public static String getAnswer() {
        return answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
