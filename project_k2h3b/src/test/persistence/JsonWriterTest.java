package persistence;

import model.AllWorkouts;
import model.Exercise;
import model.Workout;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// tests JsonWriter Class in persistence package
//      These tests are primarily modeled off tests observed in Json Serialization demo
public class JsonWriterTest extends JsonTest {

    @Test
    public void testWriterInvalidFile() {
        try {
            AllWorkouts aw = new AllWorkouts("Cam's workouts");
            JsonWriter writer = new JsonWriter(".data/my\0wrong:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void testWriterEmptyAllWorkouts() {
        try {
            AllWorkouts aw = new AllWorkouts("Cam's workouts");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAllWorkouts.json");
            writer.open();
            writer.write(aw);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAllWorkouts.json");
            aw = reader.read();
            assertEquals("Cam's workouts", aw.getName());
            assertEquals(0, aw.getOrder());
        } catch (IOException e) {
            fail("Exception should not be caught");
        }
    }

    @Test
    public void testWriterGeneralAllWorkouts() {
        try {
            AllWorkouts aw = new AllWorkouts("Cam's workouts");
            aw.addWorkout(new Workout(60, "Sept 21, 2010"));
            Exercise e1 = new Exercise("Push ups", 3, 15);
            aw.get(0).addExercise(e1);
            aw.addWorkout(new Workout(70, "November 17 2010"));
            Exercise e2 = new Exercise("Pull ups", 3, 15);
            aw.get(1).addExercise(e2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAllWorkouts.json");
            writer.open();
            writer.write(aw);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAllWorkouts.json");
            aw = reader.read();
            assertEquals("Cam's workouts", aw.getName());
            List<Workout> workouts = aw.getWorkouts();
            assertEquals(2, aw.getOrder());
            checkWorkout(1, "Sept 21, 2010", 60, workouts.get(0));
            checkWorkout(2, "November 17 2010", 70, workouts.get(1));

        } catch (IOException e) {
            fail("Exception should not have been caught");
        }
    }
}
