package com.hjssTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.school.hjss.BookingSystem;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class TestBookingSystem {

    @ParameterizedTest
    @ValueSource(strings = {"wicky\nM\n8\n44647859653\n44647859653"})
    void shouldAddUser(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        BookingSystem bookingSystem = new BookingSystem();

        assertDoesNotThrow(() -> {
            bookingSystem.addLearner(System.in);
        });

        assertNotEquals(0, bookingSystem.getLearnersCount());
    }

    @ParameterizedTest
    @ValueSource(strings = {"wicky\nN\n10\n44454544\n4448454"})
    void shouldThrowExceptionOnWrongCredentials(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        BookingSystem bookingSystem = new BookingSystem();

        assertThrows(IllegalArgumentException.class, () -> {
            bookingSystem.parseLearnerFromInput(System.in);
        });
    }
}
