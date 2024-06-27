package learn.refactoring.ch1;

import org.json.JSONObject;

public class ComedyCalculator extends PerformanceCalculator {
    public ComedyCalculator(JSONObject performance, JSONObject play) {
        super(performance, play);
    }
}
