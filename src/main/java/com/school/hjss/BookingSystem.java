package com.school.hjss;

import java.io.InputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.IntStream;




public class BookingSystem {
    private final List<Learner> learners;
    private final List<Coach> coaches;
    private TimeTable timetable;
    private final Map<String , Coach> coachMap;
    private Learner selectedLearner;

    public BookingSystem(){
        learners = new ArrayList<>();
        coaches = new ArrayList<>();

        coachMap = new HashMap<>(4);
        Gender[] genders = new Gender[]{Gender.FEMALE,Gender.MALE,Gender.FEMALE};
        String[] names = new String[] {"Helen","Michael","Sarah"};
        for(int i = 0 ; i < names.length ; i++ ){
            Coach coach = new Coach(names[i],genders[i]);
            coachMap.put(names[i],coach);
            coaches.add(coach);
        }
        createTimetable();

        String[] learnerNames = {"Wicky", "Green", "Hector" , "Flankly" , "Crey", "Eva", "Nayomi" };
        String[] learnerGender = {"M", "M", "M" , "M" , "F", "F", "M" };
        int[] learnerAges = {8,9,10,8,11,6,7};
        String[] phoneNumbers = {"44647859653", "44878564265" , "44896587674", "44755864141" , "44634414385" , "44334281785" , "44534252436"  };

        String[] emergencyNumbers = {"44647859653", "44878564265" , "44896587674", "44755864141" , "44634414385" , "44334281785" , "44534252436" };
        for(int i = 0 ; i < learnerNames.length ; i++){
            String name = learnerNames[i];
            Gender gender;
            int age = learnerAges[i];
            switch (learnerGender[i].toUpperCase()){
                case "M" -> gender = Gender.MALE;
                case "F" -> gender = Gender.FEMALE;
                case "O" -> gender = Gender.OTHER;
                default -> {
                    return;
                }
            }
            Learner learner = new Learner(name, gender, age,phoneNumbers[i], emergencyNumbers[i]);
            learners.add(learner);
        }

    }

    public void addLearner(InputStream in){
        try {
            Learner learner =
                    parseLearnerFromInput(in);

            learners.add(learner);
        }
        catch (Exception e){
            System.err.println("\n" + e.getMessage() + ", please register again." + "\n");
        }
    }

    public Learner parseLearnerFromInput(InputStream in){

        Scanner sc = new Scanner(in);

        System.out.println("\nLearner Registration.");

        System.out.println("\nEnter Your Name : ");

        String name = sc.nextLine();

        System.out.println("\nPress M or m if Male, F or f if Female, O/o if others : ");

        
        Gender gender;
        switch (sc.nextLine().toUpperCase()){
            case "M":
                gender = Gender.MALE;
                break;

            case "F":
                gender = Gender.FEMALE;
                break;
            case "O" :
                gender = Gender.OTHER;
                break;
            default:
                throw new IllegalArgumentException("Wrong Input");
        }

        System.out.println("\nEnter your age : ");

        int age;
        String ageString = sc.nextLine();

        try {
            age = Integer.parseInt(ageString);
        }
        catch (Exception e){
            throw new IllegalArgumentException("");
        }

        System.out.println("\nEnter your phone number : ");

        String phoneString = sc.nextLine();

        try {
            Long.parseLong(phoneString);
        }
        catch (Exception e){
            throw new IllegalArgumentException("Phone Number : " + phoneString + " is not valid");
        }

        System.out.println("\nEnter your guardian's phone number : ");

        String emergencyString = sc.nextLine();

        try {
            Long.parseLong(emergencyString);
        }
        catch (Exception e){
            throw new IllegalArgumentException("Invalid phone guardian phone number : " + emergencyString);
        }

        Learner learner;

        learner = new Learner(name,gender,age,phoneString,emergencyString);

        System.out.println("\nCongratulations! You have been sucessfully registered with HJSS");
        System.out.println("\nYour HJSS Id is : " + learner.getId());

        return learner;
    }

    private void createTimetable(){

        Map<OperatingDays,List<String[]>> lessonMap = Map.of(
        		OperatingDays.MONDAY, List.of(new String[]{
                        "Helen",
                        "4to5",
                        "1"

                },new String[]{
                        "Helen",
                        "5to6",
                        "2"
                },
                        new String[]{
                                "Helen",
                                "6to7",
                                "3"
                        }
                ),
                OperatingDays.WEDNESDAY, List.of( new String[]{
                        "Michael",
                        "4to5",
                        "4"
                },
                        new String[]{
                                "Michael",
                                "5to6",
                                "5"
                        },
                        new String[]{
                                "Sarah",
                                "6to7",
                                "1"
                        }
                ),
                OperatingDays.FRIDAY, List.of(new String[]{
                        "Helen",
                        "4to5",
                        "3"
                },new String []{
                        "Helen",
                        "5to6",
                        "4"
                },
                        new String[]{
                                "Helen",
                                "6to7",
                                "5"
                        }
                ),
                OperatingDays.SATURDAY, List.of(new String[] {
                        "Michael",
                        "2to3",
                        "2"
                },
                        new String[]{
                                "Sarah",
                                "3to4",
                                "3"
                        }

                )
        );

        timetable = new TimeTable();



        Map<String,GradeLevel> intGradeLevelMap = Map.of("1",GradeLevel.ONE,
                "2",GradeLevel.TWO,
                "3",GradeLevel.THREE,
                "4", GradeLevel.FOUR,
                "5", GradeLevel.FIVE
                );

        Map<String,TimeSlot> stringTimeSlotMap = Map.of(
                "2to3",TimeSlot.TWOTOTHREEPM,
                "3to4",TimeSlot.THREETOFROURPM,
                "4to5",TimeSlot.FOURTOFIVEPM,
                "5to6", TimeSlot.FIVETOSIXPM,
                "6to7", TimeSlot.SIXTOSEVENPM
        );

        LocalDate localDate = LocalDate.now();

        for(int i = 0 ; i < 4 ; i ++){

            LocalDate firstDayOfNextWeek = localDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));

            List<LocalDate> remainingDays = localDate.datesUntil(firstDayOfNextWeek)
                    .toList();

            remainingDays.forEach(d -> addToTimeTable(d,lessonMap,stringTimeSlotMap,intGradeLevelMap));

            localDate = firstDayOfNextWeek;
        }
    }

    public void printTableMenu(InputStream in){
        Scanner sc = new Scanner(in);

        System.out.println( "\nPress (C) to View lessons taught by Coach\t" + "Press (G) to view lessons by GradeLevel\t\t" + "Press (D) to view Lessons by Day\t");

      

        switch (sc.nextLine().toUpperCase()){
            case "C" -> printTableByCoach(in);
            case "G" -> printTableByGradeLevel(in);
            case "D"-> printTableByDate(in);
        }
    }

    private void printTableByGradeLevel(InputStream in){

        IntStream.range(1, GradeLevel.values().length)
                .forEach(i ->
                    System.out.println( "Press " + i + " to view lessons at Grade " + GradeLevel.values()[i])
                );

        Scanner sc = new Scanner(in);
        String selection = sc.nextLine();
        int i;

        try {
            i = Integer.parseInt(selection);

            if(i < 0 || i > GradeLevel.values().length) {
                System.err.println("Invalid option : " + i);
                return;
            }
            List<Lesson> lessons = timetable.getLessonsByGrade(GradeLevel.values()[i]);
            printTable(lessons,in);
            selectLesson(lessons,in);
        }
        catch (Exception e){
            System.err.println("Invalid option : " + selection);
        }
    }

    private void printTableByCoach(InputStream in){

        System.out.println("\n");
        System.out.println("-".repeat(50));

        System.out.format("%-15s%-15s%-15s%n", "Id", "Coach" , "rating");

        System.out.println("-".repeat(50));

        IntStream.range(0,coaches.size())
                .forEach(i -> {
                    Coach coach = coaches.get(i);
                        System.out.format("%-15s%-15s%-15s%n", i + 1 , coach.getName(),coach.getAverageRating());
                });


        System.out.println("-".repeat(50) + "\n");

        System.out.println("Enter coach Id :\n");

        Scanner sc = new Scanner(in);
        String selection = sc.nextLine();
        int i;

        try {
            i = Integer.parseInt(selection);

            if(i < 0 || i > coaches.size()) {
            System.err.println("Invalid option : " + i);
            return;
        }
            List<Lesson> list = timetable.getLessonsByCoach(coaches.get(i - 1));
            printTable(list,in);
            selectLesson(list,in);
        }
        catch (Exception e){
            System.err.println("Invalid option : " + selection);
        }
    }

    private void printTableByDate(InputStream in){
        Scanner sc = new Scanner(in);
        for(int i = 0 ; i < OperatingDays.values().length ; i++ ){
            System.out.println("Press (" + i + ") to select " + OperatingDays.values()[i]);
        }

        String str = sc.nextLine();

        int i;

        try {
            i = Integer.parseInt(str);

            if(i < 0 || i > OperatingDays.values().length) {
                System.err.println("Invalid option : " + i);
                return;
            }
            List<Lesson> lessons = timetable.getLessonsByDay( DayOfWeek.valueOf(OperatingDays.values()[i].toString()));
            printTable(lessons,in);
            selectLesson(lessons,in);
        }
        catch (Exception e){
            System.err.println("Invalid option : " + str);
        }
    }



    public void addToTimeTable(LocalDate i , Map<OperatingDays,List<String[]>> lessonMap, Map<String,TimeSlot> stringTimeSlotMap, Map<String,GradeLevel> intGradeLevelMap){
        switch (i.getDayOfWeek()){
            case MONDAY,WEDNESDAY,FRIDAY,SATURDAY ->
                    lessonMap.get(OperatingDays.valueOf(i.getDayOfWeek().toString()))
                            .forEach(l -> {

                                Coach coach = coachMap.get(l[0]);
                                TimeSlot timeSlot = stringTimeSlotMap.get(l[1]);
                                GradeLevel gradeLevel = intGradeLevelMap.get(l[2]);

                                Lesson lesson = new Lesson(i,timeSlot,coach,gradeLevel);

                                timetable.addLesson(lesson);
                            });
        }
    }


    public void printTable(List<Lesson> list , InputStream in){


        System.out.println("-".repeat(90));

        System.out.format("%-15s%-15s%-15s%-15s%-15s%-15s%n", "NO : " , "Date" , "Day", "Coach" , "grade", "remainingSeats" );

        System.out.println("-".repeat(90));

        IntStream.range(0 , list.size())
                .forEach(i -> {
                    Lesson lesson = list.get(i);
                        System.out.format("%-15s%-15s%-15s%-15s%-15d%-15d%n",i + 1, lesson.getDate() ,lesson.getDate().getDayOfWeek().toString().toLowerCase() , lesson.getCoach().getName() , lesson.getGradeLevel().ordinal(), lesson.getVacancy());
                });

        System.out.println("-".repeat(90) + "\n");
    }

    public void printBookedLessons(InputStream in){
        List<Lesson> bookedLessons = selectedLearner.getBookedLessons();



        if(!bookedLessons.isEmpty()){
            printTable(bookedLessons,in);

            System.out.println("\nPress (A) to attend Lesson\t\tPress (C) to cancel lesson  \t\t\t Press (B) to go back to menu.\n");

            Scanner sc = new Scanner(in);

            String selection = sc.nextLine();

            switch (selection.toUpperCase()){
                case "C" -> cancelSelectedLesson(bookedLessons,in);
                case "A" -> attendLessonMenu(bookedLessons,in);
            }
        }

        else
            System.err.println("\nNo lessons Booked till now\n");
    }

    public void printAttendedLesson(InputStream in){

        List<Lesson> attendedLessons = selectedLearner.getAttendedLessons();

        if(!attendedLessons.isEmpty()){
            printTable(attendedLessons,in);

            System.out.println("\nPress (R) to Review Lesson\t\t Press (B) to go back to menu.\n");

            Scanner sc = new Scanner(in);

            String selection = sc.nextLine();

            switch (selection.toUpperCase()){
                case "R" : reviewLessonMenu(attendedLessons,in);
            }
        }

        else
            System.err.println("\nYou have not attended any lesson till now\n");
    }

    public void reviewLessonMenu(List<Lesson> list,InputStream in){

        System.out.println("\nSelect Lesson Id : ");

        Scanner sc = new Scanner(in);

        String selection = sc.nextLine();

        int i ;

        try {
            i = Integer.parseInt(selection);

            Lesson lesson = list.get(i -1);

            System.out.println("Give ur rating 1 to 5 (1: Very dissatisfied, 2: Dissatisfied, 3: Ok, 4: Satisfied, 5: Very Satisfied)");

            i = Integer.parseInt(sc.nextLine());

            if(i < 0 || i > 5)
                throw new IllegalArgumentException();

            reviewLesson(lesson,i);

            System.out.println("\n Reviewed lesson sucessfully\n");

            printTable(List.of(lesson),in);
        }
        catch (NumberFormatException e){
            switch (selection.toUpperCase()){
                case "B" -> printTableMenu(in);
                default -> System.err.println("\nInvalid selection : " + selection +"\n");
            }
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
    public void reviewLesson(Lesson lesson,int rating){
        lesson.reviewLesson(new Review(selectedLearner,rating));
        Coach coach = lesson.getCoach();
        coach.updateRating(rating);
    }

    public void cancelSelectedLesson(List<Lesson> list ,InputStream in){

        System.out.println("\nSelect Lesson Id : ");

        Scanner sc = new Scanner(in);

        String selection = sc.nextLine();

        int i ;

        try {
            i = Integer.parseInt(selection);

            Lesson lesson = list.get(i -1);

            cancelBooking(lesson);

            System.out.println("\n Lesson cancelled sucessfully\n");

            printTable(List.of(lesson),in);
        }
        catch (NumberFormatException e){
            switch (selection){
                case "B" , "b" -> printTableMenu(in);
                default -> System.err.println("\nInvalid selection : " + selection +"\n");
            }
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }


    public void selectLesson(List<Lesson> list,InputStream in){

        System.out.println("Select Lesson Id to book lesson  \t\t\t Press (B) to go back to menu.\n");

        Scanner sc = new Scanner(in);

        String selection = sc.nextLine();

        int i ;

        try {
            i = Integer.parseInt(selection);

            Lesson lesson = list.get(i -1);

            bookLesson(lesson);

            System.out.println("\n Lesson booked sucessfully\n");

            printTable(List.of(lesson),in);
        }
        catch (NumberFormatException e){
            switch (selection.toUpperCase()){
                case "B"  -> printTableMenu(in);
                default -> System.err.println("\nInvalid selection : " + selection +"\n");
            }
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }

    }

    public void selectUserTable(InputStream in){

        System.out.println("-".repeat(60));

        System.out.format("%-15s%-15s%-15s%-15s%n", "Id" , "Name" , "Age" , "gender");

        System.out.println("-".repeat(60));

        IntStream.range(0,learners.size())
                        .forEach(i -> {
                            Learner learner = learners.get(i);

                            System.out.format("%-15s%-15s%-15s%-15s%n", learner.getId() , learner.getName(),learner.getAge() ,learner.getGender() );
                        });

        System.out.println("-".repeat(60));

        System.out.println("\n Press learner Id to login.");

        Scanner sc = new Scanner(in);

        String selection = sc.nextLine();

        int i;

        try {
            i = Integer.parseInt(selection);

            if(i < 1 || i > learners.size() ) {
                throw new IllegalArgumentException();
            }
            selectedLearner = learners.get(i - 1);
            System.out.println("\n Sucessfully logged in as " + selectedLearner.getName() + "\n");
        }
        catch (Exception e){
            System.err.println("Invalid option : " + selection);
        }
    }

    public String getLoggedInUserName(){
        return selectedLearner.getName();
    }

    public int getLoggedInUserGrade(){
        return selectedLearner.getGradeLevel().ordinal();
    }

    public void bookLesson(Lesson lesson){
        lesson.bookLesson(selectedLearner);
        if(!selectedLearner.getBookedLessons().contains(lesson)){
            selectedLearner.getBookedLessons().add(lesson);
        }

    }

    public void printUserReport(){

        System.out.println("User Report : \n");

        System.out.println("-".repeat(100));

        System.out.format("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n", "Id" , "Name" , "Age" , "gender" , "Booked","Attended" , "Cancelled");

        System.out.println("-".repeat(100));
        learners.stream()
                        .forEach(i -> {
                            System.out.format("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n", i.getId() , i.getName() , i.getAge() , i.getGender() , i.getBookedLessons().size()  ,i.getAttendedLessons().size() , i.getCancelledLessons().size());
                        });


        System.out.println("-".repeat(100) + "\n");
    }


    public void printCoachReport(){

        System.out.println("\nCoach Report : \n");

        System.out.println("-".repeat(30));

        System.out.format("%-15s%-15s%n" , "Name" , "Avg rating");

        System.out.println("-".repeat(30));
        coaches.stream()
                .forEach(i -> {
                    System.out.format("%-15s%-15s%n", i.getName() , i.getAverageRating());
                });

        System.out.println("-".repeat(30) + "\n");
    }

    public void attendLessonMenu(List<Lesson> list,InputStream in){

        System.out.println("\nSelect Lesson Id : ");

        Scanner sc = new Scanner(in);

        String selection = sc.nextLine();

        int i ;

        try {
            i = Integer.parseInt(selection);

            Lesson lesson = list.get(i -1);

            attendLesson(lesson);

            printTable(List.of(lesson),in);
        }
        catch (NumberFormatException e){
            System.err.println("\nInvalid selection : " + selection +"\n");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public void attendLesson(Lesson lesson){
        if(lesson.getDate().isEqual(LocalDate.now())){
            selectedLearner.getAttendedLessons().add(lesson);
            selectedLearner.levelUpGrade();
            System.out.println("Attended Lesson Sucessfully !!");
        }
        else{
            System.err.println("\nAttendance marking is not yet available for this lesson.\n");
        }
    }

    public void cancelBooking(Lesson lesson){
        lesson.cancelBooking(selectedLearner);
        selectedLearner.getCancelledLessons().add(lesson);
        selectedLearner.getBookedLessons().removeIf(i -> i.getId() == lesson.getId());
        System.out.println("\n Cancelled booking sucessfully");
    }

    public int getLearnersCount(){
        return learners.size();
    }

    public boolean isLearnerSelected(){
        return selectedLearner == null;
    }
}
