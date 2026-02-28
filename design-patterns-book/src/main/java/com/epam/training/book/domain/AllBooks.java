package com.epam.training.book.domain;

import com.epam.training.book.Text;

import java.util.ArrayList;
import java.util.List;

public class AllBooks implements Text {
    private final List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public int getNumberOfWords() {
        return books.stream().mapToInt(Book::getNumberOfWords).sum();
    }

    @Override
    public List<Verse> getVersesContainingWord(String word) {
        List<Verse> result = new ArrayList<>();
        for (Book book : books) {
            result.addAll(book.getVersesContainingWord(word));
        }
        return result;
    }
}
