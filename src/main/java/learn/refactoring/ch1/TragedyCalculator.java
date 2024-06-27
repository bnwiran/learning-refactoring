package learn.refactoring.ch1;

import org.json.JSONObject;

public class TragedyCalculator extends PerformanceCalculator {
    public TragedyCalculator(JSONObject performance, JSONObject play) {
        super(performance, play);
    }
}
