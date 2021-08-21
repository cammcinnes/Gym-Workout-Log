package persistence;

import model.AllWorkouts;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Exercise;
import model.Workout;
import org.json.*;

// Represents a reader that reads AllWorkouts from JSON data stored in file
// Citation: This class models code in JsonReader Class from JsonSerializationDemo
public class JsonReader {
    private String original;

    //EFFECTS: constructs a reader to read from original file
    public JsonReader(String original) {
        this.original = original;
    }

    // EFFECTS: reads AllWorkouts from file and returns it;
    //          throws IOException if an error occurs reading data from file
    public AllWorkouts read() throws IOException {
        String jsonData = readFile(original);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAllWorkouts(jsonObject);
    }

    // EFFECTS: reads source file as a string and returns it
    public String readFile(String original) throws IOException {
        StringBuilder builder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(original), StandardCharsets.UTF_8)) {
            stream.forEach(builder::append);
        }

        return builder.toString();
    }

    // EFFECTS: parses ALlWorkouts from JSON object and returns it
    private AllWorkouts parseAllWorkouts(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        AllWorkouts aw = new AllWorkouts(name);
        addWorkouts(aw, jsonObject);
        return aw;
    }

    // MODIFIES: aw
    // EFFECTS: parses workouts from JSON object and adds them to AllWorkouts
    private void addWorkouts(AllWorkouts aw, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("workouts");
        for (Object json : jsonArray) {
            JSONObject nextWorkout = (JSONObject) json;
            addWorkout(aw, nextWorkout);
        }
    }

    // MODIFIES: aw
    // EFFECTS: parses workout from JSON object and adds it to AllWorkouts
    private void addWorkout(AllWorkouts aw, JSONObject jsonObject) {
        String date = jsonObject.getString("date");
        int minutes = jsonObject.getInt("minutes");
        Workout workout = new Workout(minutes, date);

        JSONArray jsonArray = jsonObject.getJSONArray("description");
        for (Object json : jsonArray) {
            JSONObject nextExercise = (JSONObject) json;
            addExercise(workout, nextExercise);
        }

        aw.addWorkout(workout);
    }

    // MODIFIES: w
    // EFFECTS: parses exercises from JSON object and adds it to description
    private void addExercise(Workout w, JSONObject nextExercise) {
        String name = nextExercise.getString("name");
        int sets = nextExercise.getInt("sets");
        int reps = nextExercise.getInt("reps");
        Exercise e1 = new Exercise(name, sets, reps);
        w.addExercise(e1);
    }
}
