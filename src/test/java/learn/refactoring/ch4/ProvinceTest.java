package learn.refactoring.ch4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProvinceTest {

    @Test
    public void shortfall() {
        final var province = getSampleProvince();

        Assertions.assertEquals(province.shortfall(), 5);
    }

    private static Province getSampleProvince() {
        var name = "Asia";
        var demand = 30;
        var price = 20;
        var province = new Province(name, demand, price);

        province.addProducer("Byzantium", 10, 9);
        province.addProducer("Attalia", 12, 10);
        province.addProducer("Sinope", 10, 6);

        return province;
    }
}