package com.school.hjss;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeTable {
    private final List<Lesson> lessons = new ArrayList<>();
    private final Map<DayOfWeek, List<Lesson>> dayLessonMap = new HashMap<>();
    private final Map<Coach, List<Lesson>> coachLessonMap = new HashMap<>();
    private final Map<GradeLevel, List<Lesson>> gradeLevelListMap = new HashMap<>();

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);

        // Update coachLessonMap
        List<Lesson> coachLessons = coachLessonMap.computeIfAbsent(lesson.getCoach(), k -> new ArrayList<>());
        coachLessons.add(lesson);

        // Update dayLessonMap
        List<Lesson> dayLessons = dayLessonMap.computeIfAbsent(lesson.getDate().getDayOfWeek(), k -> new ArrayList<>());
        dayLessons.add(lesson);

        // Update gradeLevelListMap
        List<Lesson> gradeLevelLessons = gradeLevelListMap.computeIfAbsent(lesson.getGradeLevel(), k -> new ArrayList<>());
        gradeLevelLessons.add(lesson);
    }

    public List<Lesson> getLessonsByDay(DayOfWeek dayOfWeek) {
        return dayLessonMap.getOrDefault(dayOfWeek, new ArrayList<>());
    }

    public List<Lesson> getLessonsByCoach(Coach coach) {
        return coachLessonMap.getOrDefault(coach, new ArrayList<>());
    }

    public List<Lesson> getLessonsByGrade(GradeLevel gradeLevel) {
        return gradeLevelListMap.getOrDefault(gradeLevel, new ArrayList<>());
    }
}
