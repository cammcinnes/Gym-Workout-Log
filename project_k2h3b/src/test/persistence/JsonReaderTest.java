package persistence;

import model.AllWorkouts;
import model.Workout;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// tests JsonReader Class in persistence package
//      These tests are primarily modeled off tests observed in Json Serialization demo
public class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader jsonReader = new JsonReader("./data/nonExistentFile");

        try {
            AllWorkouts aw = jsonReader.read();
            fail("IO exception is expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void testReaderEmptyAllWorkouts() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAllWorkouts.json");
        try {
            AllWorkouts aw = reader.read();
            assertEquals("My workouts", aw.getName());
            assertEquals(0, aw.getOrder());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGeneralAllWorkouts() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAllWorkouts.json");
        try {
            AllWorkouts aw = reader.read();
            assertEquals("My workouts", aw.getName());
            List<Workout> workouts = aw.getWorkouts();

            checkWorkout(1, "Sept 21 2001", 60, workouts.get(0));
            checkWorkout(2, "November 17 2001", 70, workouts.get(1));
        } catch (IOException e) {
            fail("Exception should not have been caught");
        }
    }
}
