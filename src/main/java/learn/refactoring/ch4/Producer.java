package learn.refactoring.ch4;

public class Producer {
    private final String name;
    private final Province province;
    private double cost;
    private int production;

    Producer(String name, Province province, double cost, int production) {
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

    public int production() {
        return production;
    }

    public Producer production(int newProduction) {
        province.addProduction(newProduction - production);
        production = newProduction;
        return this;
    }
}
