package learn.refactoring.ch4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Province {
    private final String name;
    private double totalProduction;
    private double demand;
    private double price;
    private final List<Producer> producers;

    public Province(String name, double demand, double price) {
        this.name = name;
        this.totalProduction = 0;
        this.demand = demand;
        this.price = price;
        this.producers = new ArrayList<>();
    }

    public String name() {
        return name;
    }

    public Iterator<Producer> producers() {
        return producers.iterator();
    }

    public void addProducer(String name, double cost, double production) {
        var producer = new Producer(name, this, cost, production);

        producers.add(producer);
        totalProduction += producer.production();
    }

    public double totalProduction() {
        return totalProduction;
    }

    public Province addProduction(double totalProduction) {
        this.totalProduction += totalProduction;
        return this;
    }

    public double demand() {
        return demand;
    }

    public Province demand(double demand) {
        this.demand = demand;
        return this;
    }

    public double price() {
        return price;
    }

    public Province price(double price) {
        this.price = price;
        return this;
    }

    public double shortfall() {
        return demand - totalProduction;
    }
}
