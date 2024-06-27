package learn.refactoring.ch1;

import org.json.JSONObject;

public class TragedyCalculator extends PerformanceCalculator {
    public TragedyCalculator(JSONObject performance, JSONObject play) {
        super(performance, play);
    }

    @Override
    public int amount() {
        var audience = performance.getInt("audience");
        var result = 40_000;

        if (audience > 30) {
            result += 1000 * (audience -  30);
        }

        return result;
    }
}
