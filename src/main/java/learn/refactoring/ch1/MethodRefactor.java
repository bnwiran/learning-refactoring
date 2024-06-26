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

    public static void main(String[] args) throws URISyntaxException, IOException {
        final var main = new MethodRefactor();
        var classLoader = MethodRefactor.class.getClassLoader();
        var invoicesResource = Objects.requireNonNull(classLoader.getResource("learn/refactoring/ch1/invoices.json"));
        var invoicesJSON = new JSONArray(Files.readString(Path.of(invoicesResource.toURI())));

        var playsResource = Objects.requireNonNull(classLoader.getResource("learn/refactoring/ch1/plays.json"));
        var plays = new JSONObject(Files.readString(Path.of(playsResource.toURI())));

        System.out.println(main.getStatement(invoicesJSON, plays));
    }

    public String getStatement(JSONArray invoice, JSONObject plays) {
        var locale = new Locale.Builder().setLanguage("en").setRegion("US").build();
        var currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        var totalAmount = 0;
        var volumeCredits = 0.0;
        var result = new StringBuilder(String.format("Statement for %s\n",
                invoice.getJSONObject(0).getString("customer")));

        for (Object perfObj : invoice.getJSONObject(0).getJSONArray("performances")) {
            var performance = (JSONObject) perfObj;
            var thisAmount = amountFor(performance, playFor(plays, performance));

            // add volume credits
            volumeCredits += Math.max(performance.getInt("audience") - 30, 0);
            // add extra credit for every ten comedy attendees
            if ("comedy".equals(playFor(plays, performance).getString("type"))) {
                volumeCredits += Math.floor(performance.getDouble("audience") / 5);
            }

            // print line for this order
            result.append(String.format("  %s: %s (%d seats)]\n", playFor(plays, performance).getString("name"),
                    currencyFormatter.format(thisAmount/100.0),
                    performance.getInt("audience")));
            totalAmount += thisAmount;
        }

        result.append(String.format("Amount owed is %s\n", currencyFormatter.format(totalAmount/100.0)));
        result.append(String.format("You earned %f credits\n", volumeCredits));

        return result.toString();
    }

    private JSONObject playFor(JSONObject plays, JSONObject aPerformance) {
        return plays.getJSONObject(aPerformance.getString("playID"));
    }

    private int amountFor(JSONObject aPerformance, JSONObject play) {
        int result;
        switch (play.getString("type")) {
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
            default -> throw new RuntimeException(String.format("Unknown type: %s", play.getString("type")));
        }
        return result;
    }
}
