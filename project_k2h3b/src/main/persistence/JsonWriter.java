package persistence;

import model.AllWorkouts;

import org.json.JSONObject;

import java.io.*;

// Represents a writer that writes a JSOn representation of allWorkouts to file
// Citation: the code from this class is primarily modeled
//           by the code in JsonWriter of JsonSerializationDemo
public class JsonWriter {
    private static final int TAB = 4;
    private String destination;
    private PrintWriter writer;

    //EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer;
    //          throws FileNotFoundException if destination file cannot
    //          be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of all workouts to file
    public void write(AllWorkouts aw) {
        JSONObject json = aw.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}
