package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExerciseTest {
    private Exercise e;

    @BeforeEach
    public void setup() {
        e = new Exercise("pull up", 5, 10);
    }

    @Test
    public void testConstructor() {
        assertEquals("pull up", e.getName());
        assertEquals(10, e.getReps());
        assertEquals(5, e.getSets());
    }

    @Test
    public void testSetName() {
        e.setName("push ups");
        assertEquals("push ups", e.getName());
    }

    @Test
    public void testSetRepetitions() {
        e.setRepetitions(6);
        assertEquals(6, e.getReps());
    }

    @Test
    public void testSetAmountOfSets() {
        e.setAmountOfSets(5);
        assertEquals(5, e.getSets());
    }
}
