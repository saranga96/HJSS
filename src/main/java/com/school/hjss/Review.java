package com.school.hjss;



public class Review {
    private final Learner learner;
    private final int rating;

    public Review(Learner learner, int rating) {
        this.learner = learner;
        this.rating = rating;
    }

    public Learner getLearner() {
        return learner;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Review{" +
                "learner=" + learner +
                ", rating=" + rating +
                '}';
    }
}

