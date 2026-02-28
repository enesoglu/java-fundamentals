package com.epam.training.book.domain;

import com.epam.training.book.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Verse implements Text {
    private Part parent;
    private final int number;
    private final String content;

    public Verse(int number, String content) {
        this.number = number;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setParent(Part parent) {
        this.parent = parent;
    }

    @Override
    public int getNumberOfWords() {
        // Remove punctuation and split by whitespace
        String cleanedContent = content.replaceAll("[^a-zA-Z0-9\\s]", "");
        String[] words = cleanedContent.trim().split("\\s+");
        // Filter out empty strings
        return (int) Arrays.stream(words).filter(w -> !w.isEmpty()).count();
    }

    @Override
    public List<Verse> getVersesContainingWord(String word) {
        List<Verse> result = new ArrayList<>();
        // Check if the verse contains the whole word
        String[] words = content.replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+");
        for (String w : words) {
            if (w.equals(word)) {
                result.add(this);
                break;
            }
        }
        return result;
    }

    public String format() {
        String bookName = parent.getParent().getTitle();
        int partNumber = parent.getNumber();
        return bookName + " " + partNumber + "," + number + " \"" + content + "\"";
    }
}
