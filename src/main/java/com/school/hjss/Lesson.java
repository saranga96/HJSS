package com.school.hjss;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Lesson {
    private static int index = 1; // Start index from 1
    private final int id;
    private final LocalDate date;
    private final TimeSlot timeSlot;
    private final Coach coach;
    private final GradeLevel gradeLevel;
    private final List<Learner> bookedLearners;
    private final List<Review> reviews;

    public Lesson(LocalDate date, TimeSlot time, Coach coach, GradeLevel gradeLevel) {
        this.id = index++;
        this.date = date;
        this.timeSlot = time;
        this.coach = coach;
        this.gradeLevel = gradeLevel;
        this.bookedLearners = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public Coach getCoach() {
        return coach;
    }

    public GradeLevel getGradeLevel() {
        return gradeLevel;
    }

    public int getVacancy() {
        return 4 - bookedLearners.size();
    }

    public void bookLesson(Learner learner) {
        if (bookedLearners.size() >= 4) {
            throw new IllegalArgumentException("This lesson is already fully booked.");
        }

        if (!canLearnerAttend(learner)) {
            throw new IllegalArgumentException("Grade " + learner.getGradeLevel() +
                    " learners cannot attend grade " + this.gradeLevel + " lesson.");
        }

        if (bookedLearners.contains(learner)) {
            throw new IllegalArgumentException("You have already booked this lesson.");
        }

        bookedLearners.add(learner);
    }

    public void cancelBooking(Learner learner) {
        bookedLearners.remove(learner);
    }

    public void reviewLesson(Review review) {
        reviews.add(review);
    }

    private boolean canLearnerAttend(Learner learner) {
        int learnerGrade = learner.getGradeLevel().ordinal();
        int lessonGrade = gradeLevel.ordinal();
        return learnerGrade == lessonGrade || learnerGrade + 1 == lessonGrade;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", date=" + date +
                ", timeSlot=" + timeSlot +
                ", coach=" + coach +
                ", gradeLevel=" + gradeLevel +
                ", bookedLearners=" + bookedLearners +
                '}';
    }
}
