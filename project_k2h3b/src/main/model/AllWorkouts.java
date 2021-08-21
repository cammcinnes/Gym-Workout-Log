package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

// A map with a given name to hold workouts
// Citation: workoutsToJson modeled by thingiesToJson in Workroom class of Json Serialization Demo
public class AllWorkouts implements Writable {
    private final Map<Integer, Workout> myWorkouts;
    private final String name;
    private int order;

    // EFFECTS: creates a Map with a name to store workouts
    public AllWorkouts(String name) {
        myWorkouts = new HashMap<>();
        this.name = name;
        order = 0;
    }

    // MODIFIES: this
    // EFFECTS: add workout to all workouts
    public void addWorkout(Workout w) {
        myWorkouts.put(order, w);
        increment();
    }

    // EFFECTS: if i workout exists returns workout i; otherwise null
    public Workout get(int i) {
        return myWorkouts.get(i);
    }

    // EFFECTS: return a unmodifiable list of workouts in this all workouts
    public List<Workout> getWorkouts() {
        List<Workout> array = new ArrayList<>();

        for (int i = 0; i < order; i++) {
            array.add(myWorkouts.get(i));
        }
        return Collections.unmodifiableList(array);
    }

    public int getOrder() {
        return order;
    }

    public String getName() {
        return name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("workouts", workoutsToJson());
        return json;
    }

    // EFFECTS: returns workouts in all workouts as a JSON array
    private JSONArray workoutsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < order; i++) {
            Workout w = myWorkouts.get(i);
            jsonArray.put(w.toJson());
        }

        return jsonArray;
    }

    // MODIFIES: this
    // EFFECTS: increments order by 1
    private void increment() {
        order += 1;
    }
}
