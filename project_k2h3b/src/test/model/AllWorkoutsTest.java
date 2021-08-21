package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AllWorkoutsTest {
    private AllWorkouts myAllWorkouts;

    @BeforeEach
    public void setup() {
        myAllWorkouts = new AllWorkouts("Cam's workouts");

        myAllWorkouts.addWorkout(new Workout(65, "4 July 2020"));
        myAllWorkouts.addWorkout(new Workout(75, "6 July 2020"));
    }

    @Test
    public void testAddWorkout() {
        assertEquals(2, myAllWorkouts.getOrder());
        assertEquals("4 July 2020", myAllWorkouts.get(0).getDate());
        assertEquals(65, myAllWorkouts.get(0).getMinutes());

        assertEquals("6 July 2020", myAllWorkouts.get(1).getDate());
        assertEquals(75, myAllWorkouts.get(1).getMinutes());
    }

    @Test
    public void testSelectExists() {
        Workout w2 = myAllWorkouts.get(1);

        assertEquals("6 July 2020", w2.getDate());
        assertEquals(75, w2.getMinutes());
    }

    @Test
    public void testSelectDoesNotExists() {
        Workout w2 = myAllWorkouts.get(7);

        assertNull(w2);
    }
}
