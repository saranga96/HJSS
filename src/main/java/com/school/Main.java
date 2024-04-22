package com.school;


import java.util.Scanner;

import com.school.hjss.BookingSystem;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        BookingSystem bookingSystem = new BookingSystem();

        registerLearner(bookingSystem);

        mainMenu(bookingSystem);
    }

    private static void registerLearner(BookingSystem bookingSystem) {
        while (bookingSystem.isLearnerSelected()) {
            System.out.println("\nThe Hatfield Junior Swimming School\n");
            System.out.println("Press (N) to register a new learner\t" + "Press (L) if already registered\n");

            switch (scanner.nextLine().toUpperCase()) {
                case "N":
                    bookingSystem.addLearner(System.in);
                    break;
                case "L":
                    bookingSystem.selectUserTable(System.in);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void mainMenu(BookingSystem bookingSystem) {
        while (true) {
            System.out.println("\nThe Hatfield Junior Swimming School\n");
            System.out.println("Logged in as: " + bookingSystem.getLoggedInUserName() + " Grade: " + bookingSystem.getLoggedInUserGrade() + "\n");
            System.out.println("Press (V) to view timetable & Book lessons\t\t" + "Press (B) to view Booked Lessons\t\tPress (A) to view Attended lessons\n\n" + "Press (U) to Print learner report\t\t\t\t" + "Press (C) to print Coach report\t\t\t\t" + "Press (E) to exit program\n");

            switch (scanner.nextLine().toUpperCase()) {
                case "V":
                    bookingSystem.printTableMenu(System.in);
                    break;
                case "B":
                    bookingSystem.printBookedLessons(System.in);
                    break;
                case "A":
                    bookingSystem.printAttendedLesson(System.in);
                    break;
                case "E":
                    return;
                case "U":
                    bookingSystem.printUserReport();
                    break;
                case "C":
                    bookingSystem.printCoachReport();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
