package org.example;

public class VendingMachineApplication {
    public static void main(String[] args) {
        VendingMachine vendingMachine = new VendingMachine();
        Console console = new Console(vendingMachine);

        console.run();
    }
}