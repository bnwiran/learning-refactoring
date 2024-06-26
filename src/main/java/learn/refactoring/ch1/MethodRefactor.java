package learn.refactoring.ch1;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class MethodRefactor {

    private final JSONObject plays;

    public static void main(String[] args) throws URISyntaxException, IOException {
        final var main = new MethodRefactor();
        var classLoader = MethodRefactor.class.getClassLoader();
        var invoicesResource = Objects.requireNonNull(classLoader.getResource("learn/refactoring/ch1/invoices.json"));
        var invoicesJSON = new JSONArray(Files.readString(Path.of(invoicesResource.toURI())));

        System.out.println(main.getStatement(invoicesJSON));
    }

    public MethodRefactor() throws URISyntaxException, IOException {
        var playsResource = Objects.requireNonNull(getClass().getClassLoader().getResource("learn/refactoring/ch1/plays.json"));
        plays = new JSONObject(Files.readString(Path.of(playsResource.toURI())));
    }

    public String getStatement(JSONArray invoice) {
        var totalAmount = 0;
        var volumeCredits = 0.0;
        var result = new StringBuilder(String.format("Statement for %s\n",
                invoice.getJSONObject(0).getString("customer")));

        for (Object perfObj : invoice.getJSONObject(0).getJSONArray("performances")) {
            var performance = (JSONObject) perfObj;

            // add volume credits
            volumeCredits += volumeCreditsFor(performance);

            // print line for this order
            result.append(String.format("  %s: %s (%d seats)]\n", playFor(performance).getString("name"),
                    usd(amountFor(performance)/100.0),
                    performance.getInt("audience")));
            totalAmount += amountFor(performance);
        }

        result.append(String.format("Amount owed is %s\n", usd(totalAmount/100.0)));
        result.append(String.format("You earned %f credits\n", volumeCredits));

        return result.toString();
    }

    private String usd(double aNumber) {
        var locale = new Locale.Builder().setLanguage("en").setRegion("US").build();
        var currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        return currencyFormatter.format(aNumber);
    }

    private double volumeCreditsFor(JSONObject aPerformance) {
        var result = Math.max(aPerformance.getDouble("audience") - 30, 0);

        if ("comedy".equals(playFor(aPerformance).getString("type"))) {
            result += Math.floor(aPerformance.getDouble("audience") / 5);
        }

        return result;
    }

    private JSONObject playFor(JSONObject aPerformance) {
        return plays.getJSONObject(aPerformance.getString("playID"));
    }

    private int amountFor(JSONObject aPerformance) {
        int result;
        switch (playFor(aPerformance).getString("type")) {
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
                    playFor(aPerformance).getString("type")));
        }
        return result;
    }
}
