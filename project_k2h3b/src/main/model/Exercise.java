package model;

import org.json.JSONObject;
import persistence.Writable;

// Describes name of the exercise, amount of sets and repetitions performed
public class Exercise implements Writable {
    private String name; // names exercise
    private int sets;    // amount of times reps are performed
    private int reps;    // amount of times the exercise is performed

    // EFFECTS: creates a exercise with name, sets and reps
    public Exercise(String name, int sets, int reps) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
    }

    // setters

    public void setName(String name) {
        this.name = name;
    }

    public void setAmountOfSets(int sets) {
        this.sets = sets;
    }

    public void setRepetitions(int reps) {
        this.reps = reps;
    }

    //getters

    public String getName() {
        return name;
    }

    public int getReps() {
        return reps;
    }

    public int getSets() {
        return sets;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("sets", sets);
        json.put("reps", reps);

        return json;
    }
}
