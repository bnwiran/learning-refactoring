package learn.refactoring.ch1;

import org.json.JSONObject;

public abstract class PerformanceCalculator {
    protected final JSONObject performance;
    protected final JSONObject play;

    public PerformanceCalculator(JSONObject performance, JSONObject play) {
        this.performance = performance;
        this.play = play;
    }

    public JSONObject play() {
        return play;
    }

    public abstract int amount();

    public double volumeCredits() {
        return Math.max(performance.getDouble("audience") - 30, 0);
    }
}
