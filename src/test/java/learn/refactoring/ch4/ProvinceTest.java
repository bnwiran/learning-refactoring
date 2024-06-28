package learn.refactoring.ch4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProvinceTest {

    private Province asia;

    @BeforeEach
    public void setUp() {
        asia = getSampleProvince("Asia");
    }

    @Test
    public void shortfall() {
        assertEquals(5, asia.shortfall());
    }

    @Test
    public void profit() {
        assertEquals(230, asia.profit());
    }

    @Test
    public void changeProduction() {
        asia.producers().getFirst().production(20);
        assertEquals(-6, asia.shortfall());
        assertEquals(292, asia.profit());
    }

    @Test
    public void noProducers() {
        var noProducers = new Province("No producers", 30, 20);
        assertEquals(30, noProducers.shortfall());
        assertEquals(0, noProducers.profit());
    }

    private static Province getSampleProvince(String name) {
        var demand = 30;
        var price = 20;
        var province = new Province(name, demand, price);

        province.addProducer("Byzantium", 10, 9);
        province.addProducer("Attalia", 12, 10);
        province.addProducer("Sinope", 10, 6);

        return province;
    }
}