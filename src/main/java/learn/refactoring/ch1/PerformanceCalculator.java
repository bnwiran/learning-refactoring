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

    public int amount() {
        int result;
        switch (play.getString("type")) {
            case "tragedy" -> {
                result = 40_000;
                if (performance.getInt("audience") > 30) {
                    result += 1000 * (performance.getInt("audience") -  30);
                }
            }
            case "comedy" ->{
                result= 30_000;
                if (performance.getInt("audience") > 20) {
                    result += 10_000 + 500 * (performance.getInt("audience") -  20);
                }
                result += 300 * performance.getInt("audience");
            }
            default -> throw new RuntimeException(String.format("Unknown type: %s",
                    play.getString("type")));
        }
        return result;
    }
}
