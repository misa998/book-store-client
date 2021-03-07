package com.misa.store.common;

public class Product {
    private String name;
    private int releaseYear;
    private double userScore;
    private int id;

    public Product(int id, String name, int releaseYear, double userScore) {
        this.id=id;
        this.name = name;
        this.releaseYear = releaseYear;
        this.userScore = userScore;
    }

    public Product() {}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getReleaseYear() {
        return releaseYear;
    }
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
    public double getUserScore() {
        return userScore;
    }
    public void setUserScore(double userScore) {
        this.userScore = userScore;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "Product [name=" + name + ", releaseYear=" + releaseYear + ", userScore=" + userScore + ", id=" + id + "]";
    }
}
