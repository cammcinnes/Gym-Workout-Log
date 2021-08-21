package persistence;

import org.json.JSONObject;

// Citation: This class utilizes code in Writable interface from JsonSerializationDemo
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
