package learn.refactoring.ch1;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

class MethodRefactorTest {

    @Test
    public void statement() throws URISyntaxException, IOException {
        var expected = """
                Statement for BigCo
                  Hamlet: $650.00 (55 seats)]
                  As You Like It: $580.00 (35 seats)]
                  Othello: $500.00 (40 seats)]
                Amount owed is $1,730.00
                You earned 47.000000 credits
                """;

        final var main = new MethodRefactor();
        var classLoader = MethodRefactor.class.getClassLoader();
        var invoicesResource = Objects.requireNonNull(classLoader.getResource("learn/refactoring/ch1/invoices.json"));
        var invoicesJSON = new JSONArray(Files.readString(Path.of(invoicesResource.toURI())));

        var playsResource = Objects.requireNonNull(classLoader.getResource("learn/refactoring/ch1/plays.json"));
        var plays = new JSONObject(Files.readString(Path.of(playsResource.toURI())));

        var actual = main.getStatement(invoicesJSON, plays);
        Assertions.assertEquals(expected, actual);
    }
}