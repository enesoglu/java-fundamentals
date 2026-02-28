package com.epam.training.book.domain;

import com.epam.training.book.Text;

import java.util.ArrayList;
import java.util.List;

public class Part implements Text {
    private Book parent;
    private final int number;
    private final List<Verse> verses = new ArrayList<>();

    public Part(int number) {
        this.number = number;
    }

    public void setParent(Book parent) {
        this.parent = parent;
    }

    public Book getParent() {
        return parent;
    }

    public int getNumber() {
        return number;
    }

    public void addVerse(Verse verse) {
        verse.setParent(this);
        verses.add(verse);
    }

    @Override
    public int getNumberOfWords() {
        return verses.stream().mapToInt(Verse::getNumberOfWords).sum();
    }

    @Override
    public List<Verse> getVersesContainingWord(String word) {
        List<Verse> result = new ArrayList<>();
        for (Verse verse : verses) {
            result.addAll(verse.getVersesContainingWord(word));
        }
        return result;
    }
}
