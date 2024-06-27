package learn.refactoring.ch1;

import org.json.JSONObject;

public class ComedyCalculator extends PerformanceCalculator {
    public ComedyCalculator(JSONObject performance, JSONObject play) {
        super(performance, play);
    }

    @Override
    public int amount() {
        var audience = performance.getInt("audience");
        var result = 30_000;

        if (audience> 20) {
            result += 10_000 + 500 * (audience -  20);
        }

        result += 300 * audience;

        return result;
    }

    @Override
    public double volumeCredits() {
        return super.volumeCredits() + Math.floor(performance.getDouble("audience") / 5);
    }
}
