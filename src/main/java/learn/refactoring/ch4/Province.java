package learn.refactoring.ch4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Province {
    private final String name;
    private int totalProduction;
    private int demand;
    private double price;
    private final List<Producer> producers;

    public Province(String name, int demand, double price) {
        this.name = name;
        this.totalProduction = 0;
        this.demand = demand;
        this.price = price;
        this.producers = new ArrayList<>();
    }

    public String name() {
        return name;
    }

    public List<Producer> producers() {
        return producers;
    }

    public void addProducer(String name, double cost, int production) {
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

    public Province demand(int demand) {
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

    public int shortfall() {
        return demand - totalProduction;
    }

    public double profit() {
        return demandValue() - demandCost();
    }

    public double demandCost() {
        var remainingDemand = demand;
        var result = 0.0;

        var sortedProducers = producers.stream().sorted((p1, p2) -> Double.compare(p1.cost(), p2.cost())).toList();
        for (var producer : sortedProducers) {
            final var contribution = Math.min(remainingDemand, producer.production());
            remainingDemand -= contribution;
            result += contribution * producer.cost();
        }

        return result;
    }

    public double demandValue() {
        return satisfiedDemand() * price;
    }

    private double satisfiedDemand() {
        return Math.min(demand, totalProduction);
    }
}
