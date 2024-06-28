package learn.refactoring.ch4;

public class Producer {
    private final String name;
    private final Province province;
    private double cost;
    private double production;

    Producer(String name, Province province, double cost, double production) {
        this.name = name;
        this.province = province;
        this.cost = cost;
        this.production = production;
    }

    public String name() {
        return name;
    }

    public double cost() {
        return cost;
    }

    public Producer cost(double cost) {
        this.cost = cost;
        return this;
    }

    public double production() {
        return production;
    }

    public Producer production(double newProduction) {
        province.addProduction(newProduction - production);
        production = newProduction;
        return this;
    }
}
