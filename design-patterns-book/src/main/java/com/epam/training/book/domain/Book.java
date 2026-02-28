package com.epam.training.book.domain;

import com.epam.training.book.Text;

import java.util.ArrayList;
import java.util.List;

public class Book implements Text {
    private final String title;
    private final List<Part> parts = new ArrayList<>();

    public Book(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void addPart(Part part) {
        part.setParent(this);
        parts.add(part);
    }

    @Override
    public int getNumberOfWords() {
        return parts.stream().mapToInt(Part::getNumberOfWords).sum();
    }

    @Override
    public List<Verse> getVersesContainingWord(String word) {
        List<Verse> result = new ArrayList<>();
        for (Part part : parts) {
            result.addAll(part.getVersesContainingWord(word));
        }
        return result;
    }
}
