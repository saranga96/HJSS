package com.hjssTest;
import org.junit.jupiter.api.Test;
import com.school.hjss.Coach;
import com.school.hjss.Gender;
import static org.junit.jupiter.api.Assertions.*;

public class TestCoach {

    @Test
    void constructorShouldInstantiateProperlyWithMale() {
        String name = "John";
        Gender gender = Gender.MALE;
        Coach coach = new Coach(name, gender);
        assertAll(
            () -> assertEquals(name, coach.getName()),
            () -> assertEquals(gender, coach.getGender())
        );
    }

    @Test
    void constructorShouldInstantiateProperlyWithFemale() {
        String name = "Rose";
        Gender gender = Gender.FEMALE;
        Coach coach = new Coach(name, gender);
        assertAll(
            () -> assertEquals(name, coach.getName()),
            () -> assertEquals(gender, coach.getGender())
        );
    }

    @Test
    void shouldUpdateRatingProperly() {
        Coach coach = new Coach("John", Gender.MALE);
        double initialRating = coach.getAverageRating();
        coach.updateRating(4);
        assertEquals(4, coach.getAverageRating());
    }
}
