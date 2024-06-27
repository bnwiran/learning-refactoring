package learn.refactoring.ch1;

import org.json.JSONObject;

public class PerformanceCalculator {
    private final JSONObject performance;

    public PerformanceCalculator(JSONObject performance) {
        this.performance = performance;
    }
}
