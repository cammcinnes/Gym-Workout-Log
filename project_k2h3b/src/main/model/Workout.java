package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// An event with date, duration in minutes and seconds and a description marked by a order
// Citation: exercisesToJson modeled by thingiesToJson in Workroom class of Json Serialization Demo
public class Workout implements Writable {
    private String date;            // the date when the workout was performed
    private Integer minutes;         // how many minutes the workout took
    private final List<Exercise> description; // a list of exercises performed in workout

    // EFFECTS: creates a workout with date, duration in minutes and seconds,
    //          and a empty list of exercises
    public Workout(int minutes, String date) {
        this.date = date;
        this.minutes = minutes;
        description = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds an exercise to description
    public void addExercise(Exercise e) {
        description.add(e);
    }

    // EFFECTS: get the number of exercise in description
    public Integer descriptionSize() {
        return description.size();
    }

    // EFFECTS: returns exercise at i in description
    public Exercise descriptionGet(int i) {
        return description.get(i);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String newDate) {
        date = newDate;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer t) {
        minutes = t;
    }

    // setters

    public List<Exercise> getDescription() {
        return description;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("date", date);
        json.put("minutes", minutes);
        json.put("description", exercisesToJson());

        return json;
    }

    private JSONArray exercisesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Exercise e : description) {
            jsonArray.put(e.toJson());
        }
        return jsonArray;
    }

    @Override
    public String toString() {
        return "Workout";
    }
}
