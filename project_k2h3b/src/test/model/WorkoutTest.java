package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkoutTest {
    private Workout myWorkout;

    @BeforeEach
    public void setup() {
        myWorkout = new Workout(90, "November 17, 2020");
    }

    @Test
    public void testConstructor() {
        assertEquals("November 17, 2020", myWorkout.getDate());
        assertEquals(90, myWorkout.getMinutes());
        assertEquals(0, myWorkout.descriptionSize());
    }

    @Test
    public void testSetDate() {
        myWorkout.setDate("Nov 17, 2001");
        assertEquals("Nov 17, 2001", myWorkout.getDate());
    }

    @Test
    public void testSetMinutes() {
        myWorkout.setMinutes(25);
        assertEquals(25, myWorkout.getMinutes());
    }

    @Test
    public void testAddExercise() {
        Exercise e1 = new Exercise("push up", 4, 20);
        Exercise e2 = new Exercise("pull up", 4, 22);
        myWorkout.addExercise(e1);
        myWorkout.addExercise(e2);

        assertEquals(2, myWorkout.descriptionSize());
        assertEquals("push up", myWorkout.descriptionGet(0).getName());
        assertEquals(4, myWorkout.descriptionGet(0).getSets());
        assertEquals(20, myWorkout.descriptionGet(0).getReps());

        assertEquals("pull up", myWorkout.descriptionGet(1).getName());
        assertEquals(4, myWorkout.descriptionGet(1).getSets());
        assertEquals(22, myWorkout.descriptionGet(1).getReps());
    }

    @Test
    public void testToString() {
        assertEquals("Workout", myWorkout.toString());
    }
}
