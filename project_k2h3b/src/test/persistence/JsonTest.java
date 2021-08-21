package persistence;

import model.Exercise;
import model.Workout;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkWorkout(int order, String date, int minutes, Workout w) {
        assertEquals(date, w.getDate());
        assertEquals(minutes, w.getMinutes());

        for (Exercise e: w.getDescription()) {
           checkExercise(e.getName(), e.getSets(), e.getReps(), e);
        }

    }

    private void checkExercise(String name, int sets, int reps, Exercise exercise) {
        assertEquals(name, exercise.getName());
        assertEquals(sets, exercise.getSets());
        assertEquals(reps, exercise.getReps());
    }
}
