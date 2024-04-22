package com.hjssTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockitoAnnotations;

import com.school.hjss.Coach;
import com.school.hjss.Gender;
import com.school.hjss.GradeLevel;
import com.school.hjss.Learner;
import com.school.hjss.Lesson;
import com.school.hjss.TimeSlot;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestLesson {

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void constructorShouldInstantiateProperly() {
        LocalDate date = LocalDate.of(2024, 3, 10);
        TimeSlot timeSlot = TimeSlot.FIVETOSIXPM;
        Coach coach = new Coach("John Doe", Gender.MALE);
        GradeLevel gradeLevel = GradeLevel.TWO;
        Lesson lesson = new Lesson(date, timeSlot, coach, gradeLevel);

        assertEquals(date, lesson.getDate());
        assertEquals(timeSlot, lesson.getTimeSlot());
        assertEquals(coach, lesson.getCoach());
        assertEquals(gradeLevel, lesson.getGradeLevel());
        assertEquals(4, lesson.getVacancy());
    }

    @ParameterizedTest
    @MethodSource("gradeLevelProvider")
    void bookingLessonShouldThrowExceptionForLearnerNotEligible(GradeLevel gradeLevel) {
        Coach coach = mock(Coach.class);
        Learner learner = mock(Learner.class);
        when(learner.getGradeLevel()).thenReturn(gradeLevel);

        Lesson lesson = new Lesson(LocalDate.now(), TimeSlot.FIVETOSIXPM, coach, GradeLevel.FOUR);

        assertThrows(IllegalArgumentException.class, () -> lesson.bookLesson(learner));
    }

    static List<GradeLevel> gradeLevelProvider() {
        return List.of(GradeLevel.ONE, GradeLevel.TWO, GradeLevel.ZERO);
    }
}
