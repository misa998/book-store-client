package com.misa.store.common;

public class Book extends Product {

    private String author;
    private String genre;
    private String language;

    public Book(){
        super(Integer.MAX_VALUE, "/", 0, 0);
        this.author = "/";
        this.genre = "/";
        this.language = "/";
    }

    public Book(int id, String name, int releaseYear, double userScore, String author, String genre, String language) {
        super(id, name, releaseYear, userScore);
        this.author = author;
        this.genre = genre;
        this.language = language;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "Book [author=" + author + ", genre=" + genre + ", language=" + language + "]";
    }
}
