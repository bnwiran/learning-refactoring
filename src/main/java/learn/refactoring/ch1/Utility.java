package learn.refactoring.ch1;

import org.json.JSONArray;
import org.json.JSONObject;

public class Utility {
    public static StatementData createStatementData(JSONArray invoice, JSONObject plays) {
        var customer = invoice.getJSONObject(0).getString("customer");
        var performances = invoice.getJSONObject(0).getJSONArray("performances");
        performances = enrichPerformances(performances, plays);

        var statementData = new StatementData();
        statementData.customer = customer;
        statementData.performances = performances;
        statementData.totalAmount = totalAmount(statementData);
        statementData.totalVolumeCredits = totalVolumeCredits(statementData);
        return statementData;
    }

    private static JSONArray enrichPerformances(JSONArray performances, JSONObject plays) {
        var length = performances.length();
        for (int i = 0; i < length; i++) {
            var performance = performances.getJSONObject(i);
            var calculator = new PerformanceCalculator(performance, playFor(performance, plays));
            performance.put("play", calculator.play());
            performance.put("amount", amountFor(performance));
            performance.put("volumeCredits", volumeCreditsFor(performance));
        }
        return performances;
    }

    private static double totalAmount(StatementData statementData) {
        var result = 0.0;

        for (Object perfObj : statementData.performances) {
            var performance = (JSONObject) perfObj;
            result += performance.getDouble("amount");
        }

        return result;
    }

    private static double totalVolumeCredits(StatementData statementData) {
        var result = 0.0;
        for (Object perfObj : statementData.performances) {
            var performance = (JSONObject) perfObj;
            result += performance.getDouble("volumeCredits");
        }
        return result;
    }

    private static double volumeCreditsFor(JSONObject aPerformance) {
        var result = Math.max(aPerformance.getDouble("audience") - 30, 0);

        if ("comedy".equals(aPerformance.getJSONObject("play").getString("type"))) {
            result += Math.floor(aPerformance.getDouble("audience") / 5);
        }

        return result;
    }

    private static JSONObject playFor(JSONObject aPerformance, JSONObject plays) {
        return plays.getJSONObject(aPerformance.getString("playID"));
    }

    private static int amountFor(JSONObject aPerformance) {
        int result;
        switch (aPerformance.getJSONObject("play").getString("type")) {
            case "tragedy" -> {
                result = 40_000;
                if (aPerformance.getInt("audience") > 30) {
                    result += 1000 * (aPerformance.getInt("audience") -  30);
                }
            }
            case "comedy" ->{
                result= 30_000;
                if (aPerformance.getInt("audience") > 20) {
                    result += 10_000 + 500 * (aPerformance.getInt("audience") -  20);
                }
                result += 300 * aPerformance.getInt("audience");
            }
            default -> throw new RuntimeException(String.format("Unknown type: %s",
                    aPerformance.getJSONObject("play").getString("type")));
        }
        return result;
    }
}
