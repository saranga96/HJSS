package com.hjssTest;
import org.junit.jupiter.api.Test;
import com.school.hjss.Gender;
import com.school.hjss.Learner;
import static org.junit.jupiter.api.Assertions.*;

class TestLearner {

    @Test
    void constructorShouldInstantiateProperly() {
        String name = "John Doe";
        String phoneNumber = "44523852694";
        String emergencyContact = "44546567294";
        Learner johnDoe = new Learner(name, Gender.MALE, 9, phoneNumber, emergencyContact);
        assertAll(
            () -> assertEquals(name, johnDoe.getName()),
            () -> assertEquals(Gender.MALE, johnDoe.getGender()),
            () -> assertEquals(9, johnDoe.getAge()),
            () -> assertEquals(phoneNumber, johnDoe.getPhoneNumber()),
            () -> assertEquals(emergencyContact, johnDoe.getEmergencyContact())
        );
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForDisapprovedAge() {
        assertThrows(IllegalArgumentException.class, () -> new Learner("John Doe", Gender.MALE, 16, "44523852694", "44546567294"),
            "A learner’s age needs to be between 4 and 11");
    }

    @Test
    void shouldIncrementId() {
        Learner learner = new Learner("John", Gender.MALE, 10, "44444555444", "4444554444");
        int start = learner.getId();
        for (int i = 1; i < 10; i++) {
            Learner learner1 = new Learner("John", Gender.MALE, 10, "44444555444", "4444554444");
            assertEquals(start + i, learner1.getId());
        }
    }
}
