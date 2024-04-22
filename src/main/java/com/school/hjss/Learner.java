package com.school.hjss;

import java.util.ArrayList;
import java.util.List;

public class Learner {
    private static int index = 1 ;
    private int id;
    private String name;
    private Gender gender;
    private int age;
    private String phoneNumber;
    private String emergencyContact;
    private GradeLevel gradeLevel;
    private List<Lesson> bookedLessons;
    private List<Lesson> cancelledLessons;
    private List<Lesson> attendedLessons;

    public Learner(String name,Gender gender,int age ,String phoneNumber,String emergencyContact){
        if(age < 4 || age > 11)
            throw new IllegalArgumentException("\nA learner’s age needs to be between 4 and 11\n");

        this.id = Learner.index;
        Learner.index++;
        this.name = name;
        this.gender =gender;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.emergencyContact = emergencyContact;
        this.gradeLevel = GradeLevel.ZERO;

        this.bookedLessons = new ArrayList<>();
        this.cancelledLessons = new ArrayList<>();
        this.attendedLessons = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }


    public int getAge() {
        return age;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }


    public String getEmergencyContact() {
        return emergencyContact;
    }


    public GradeLevel getGradeLevel() {
        return gradeLevel;
    }


    protected List<Lesson> getBookedLessons() {
        return bookedLessons;
    }


    protected List<Lesson> getCancelledLessons() {
        return cancelledLessons;
    }

    protected void levelUpGrade(){
        if(!gradeLevel.equals(GradeLevel.FIVE))
            this.gradeLevel = GradeLevel.values()[this.gradeLevel.ordinal() + 1];
    }

    protected List<Lesson> getAttendedLessons() {
        return attendedLessons;
    }

    @Override
    public String toString() {
        return "Learner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", emergencyContact='" + emergencyContact + '\'' +
                ", gradeLevel=" + gradeLevel +
                ", bookedLessons=" + bookedLessons +
                ", cancelledLessons=" + cancelledLessons +
                ", attendedLessons=" + attendedLessons +
                '}';
    }
}
