package com.example.midterms;

public class Bill {
    int previous;
    int current;
    Pipe type;
    int pack;
    int month;

    public Bill(int previous, int current, Pipe type, int pack, int month) {
        this.previous = previous;
        this.current = current;
        this.type = type;
        this.pack = pack;
        this.month = month;
    }

    public double consumption() {
        int diff = current - previous;
        return diff * type.diameter;
    }

    // TODO Milestone 3: Calculate bill.
    public double get_bill() {
        switch (pack) {
            case 1:
                return consumption() * 22.50;
            case 2:
                if (consumption() < 20) {
                    return consumption() * 15.75;
                }
                else if (consumption() < 50) {
                    return 500 + 12 * (consumption() - 20);
                } else {
                    return 800 + 10 * (consumption() - 50);
                }
            default:
                if (consumption() < 100) {
                    return 850;
                } else if (consumption() <= 250) {
                    return 1500;
                } else {
                    return 2750;
                }
        }
    }
}
