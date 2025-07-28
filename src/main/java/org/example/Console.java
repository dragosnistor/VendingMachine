package org.example;

import java.math.BigDecimal;
import java.util.*;

public class Console {

    public static final String X = "X";
    public static final String SR = "SR";
    public static final String RE = "RE";
    public static final String ER = "ER";
    public static final String VENDOR_KEY = "VendorKey";
    private final VendingMachine vendingMachine;

    public Console(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            List<String> status = new ArrayList<>();
            boolean isVendor = false;

            while (true) {
                printUserBalance();
                printListOfCoins(isVendor);
                printListOfProducts(isVendor);
                printEndOfMenu(isVendor);
                printStatus(status);
                status.clear();

                if (scanner.hasNextBigDecimal()) {
                    BigDecimal insertedCoin = scanner.nextBigDecimal().setScale(2);
                    status.add(vendingMachine.insertCoin(insertedCoin));
                    scanner.nextLine();
                } else {
                    String insertedOption = scanner.nextLine();

                    if (insertedOption.equalsIgnoreCase(X)) {
                        status.addAll(vendingMachine.cancelRequest());
                    } else if (insertedOption.equalsIgnoreCase(SR + VENDOR_KEY)) {
                        isVendor = true;
                        status.add("Show secret vendor menu!");
                    } else if (insertedOption.equalsIgnoreCase(RE + VENDOR_KEY)) {
                        status.addAll(vendingMachine.resetProdcuts());
                        status.addAll(vendingMachine.resetCoins());
                    } else if (insertedOption.equalsIgnoreCase(ER + VENDOR_KEY)) {
                        isVendor = false;
                    } else {
                        status.addAll(vendingMachine.checkProducts(insertedOption));
                    }

                }

            }
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }

    }

    private void printUserBalance() {
        printLongDelimiter();
        System.out.println("Current balance: " + vendingMachine.getUserBalance());
        printShortDelimiter();
    }

    public void printListOfCoins(boolean isVendor) {
        System.out.println("Insert accepted coins: ");
        Map<BigDecimal, Integer> coinMap = vendingMachine.getCoinMap();

        if (isVendor) {
            System.out.println(coinMap.toString());
        } else {
            System.out.println(Arrays.toString(coinMap.keySet().toArray()));
        }
        printEmptyRow();
    }

    public void printListOfProducts(boolean isVendor) {
        System.out.println("Buy products:");
        Map<VMItem, Integer> productMap = vendingMachine.getProductMap();
        int productCount = 0;

        for (Map.Entry<VMItem, Integer> entry : productMap.entrySet()) {
            if (entry.getValue() >= 1) {
                productCount++;
                String stringIfVendor = isVendor ? ", Stock: " + entry.getValue() : "";
                System.out.println(entry.getKey().toString() + stringIfVendor);
            }
        }

        if (productCount == 0) {
            System.out.println("ALL PRODUCTS ARE OUT OF STOCK!");
        }
        printEmptyRow();
    }

    public void printEndOfMenu(boolean isVendor) {
        System.out.println("Other options:");
        System.out.println("[" + X + "] Cancel Request");
        System.out.println("[" + SR + "] Start Reset");
        if (isVendor) {
            System.out.println("[" + RE + "] Reset Products & Coins");
            System.out.println("[" + ER + "] End Reset");
        } else {
            printEmptyRow();
            printEmptyRow();
        }
    }

    public void printStatus(List<String> status) {
        printShortDelimiter();
        System.out.println("STATUS OF LAST OPERATION: ");
        for (String line : status) {
            System.out.println(line);
        }
        printLongDelimiter();
    }

    private void printLongDelimiter() {
        System.out.println("============================================================");
    }

    private void printShortDelimiter() {
        System.out.println("==============================");
    }

    public void printEmptyRow() {
        System.out.println();
    }

}