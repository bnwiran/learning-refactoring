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

import static learn.refactoring.ch1.Utility.createStatementData;

public class MethodRefactor {

    static class StatementData {
        String customer;
        JSONArray performances;
        double totalAmount;
        double totalVolumeCredits;
    }

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
        return renderPlainText(createStatementData(invoice, plays));
    }

    private String renderPlainText(StatementData statementData) {
        var result = new StringBuilder(String.format("Statement for %s\n", statementData.customer));

        for (Object perfObj : statementData.performances) {
            var performance = (JSONObject) perfObj;

            // print line for this order
            result.append(String.format("  %s: %s (%d seats)\n", performance.getJSONObject("play").getString("name"),
                    usd(performance.getDouble("amount")/100.0),
                    performance.getInt("audience")));
        }

        var totalAmount = statementData.totalAmount;
        result.append(String.format("Amount owed is %s\n", usd(totalAmount/100.0)));
        result.append(String.format("You earned %f credits\n", statementData.totalVolumeCredits));

        return result.toString();
    }

    private String usd(double aNumber) {
        var locale = new Locale.Builder().setLanguage("en").setRegion("US").build();
        var currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        return currencyFormatter.format(aNumber);
    }
}
