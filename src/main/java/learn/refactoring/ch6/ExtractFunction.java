package learn.refactoring.ch6;

public class ExtractFunction {

    void printOwing(Invoice invoice) {
        printBanner();
        var outstanding = calculateOutstanding();

        printDetails(invoice, outstanding);
    }

    private void printDetails(Invoice invoice, double outstanding) {
        System.out.printf("name: %s\n", invoice.customer());
        System.out.printf("amount: %f\n", outstanding);
    }

    double calculateOutstanding() {
        return 0;
    }

    void printBanner() {
    }
}
