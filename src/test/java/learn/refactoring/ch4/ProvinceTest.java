package learn.refactoring.ch4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProvinceTest {

    private Province province;

    @BeforeEach
    public void setUp() {
        province = getSampleProvince();
    }

    @Test
    public void shortfall() {
        assertEquals(5, province.shortfall());
    }

    @Test
    public void profit() {
        assertEquals(230, province.profit());
    }

    @Test
    public void changeProduction() {
        province.producers().getFirst().production(20);
        assertEquals(-6, province.shortfall());
        assertEquals(292, province.profit());
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