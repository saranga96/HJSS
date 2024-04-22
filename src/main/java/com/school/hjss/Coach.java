package com.school.hjss;



public class Coach {
    private final String name;
    private final Gender gender;
    private double averageRating;
    private int numRatings;
    private double ratingSum;

    public Coach(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
        this.averageRating = 0;
        this.numRatings = 0;
        this.ratingSum = 0;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void updateRating(double rating){
        this.numRatings++;
        this.ratingSum += rating;
        this.averageRating = ratingSum / numRatings;
    }
}
