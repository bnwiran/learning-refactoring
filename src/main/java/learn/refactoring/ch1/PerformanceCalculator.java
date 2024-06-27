package learn.refactoring.ch1;

import org.json.JSONObject;

public class PerformanceCalculator {
    private final JSONObject performance;
    private final JSONObject play;

    public PerformanceCalculator(JSONObject performance, JSONObject play) {
        this.performance = performance;
        this.play = play;
    }

    public JSONObject play() {
        return play;
    }
}
