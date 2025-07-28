package org.example;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;


public class VendingMachine {
    public static final String YOUR_BALANCE_IS_NOW = " Your balance is now: ";
    private BigDecimal userBalance = new BigDecimal("0").setScale(2);
    private Map<BigDecimal, Integer> coinMap;
    private Map<VMItem, Integer> productMap;

    public VendingMachine() {
        this.coinMap = populateCoinMap();
        this.productMap = populateProductMap();
    }

    private Map<BigDecimal, Integer> populateCoinMap() {
        Map<BigDecimal, Integer> coinMap = new LinkedHashMap<>();
        coinMap.put(new BigDecimal("0.05").setScale(2), 100);
        coinMap.put(new BigDecimal("0.10").setScale(2), 100);
        coinMap.put(new BigDecimal("0.20").setScale(2), 100);
        coinMap.put(new BigDecimal("0.50").setScale(2), 100);
        coinMap.put(new BigDecimal("1").setScale(2), 100);
        coinMap.put(new BigDecimal("2").setScale(2), 100);
        return coinMap;
    }

    private Map<VMItem, Integer> populateProductMap() {
        Map<VMItem, Integer> productMap = new LinkedHashMap<>();
        productMap.put(new VMItem("C", "Coke", new BigDecimal("1.50").setScale(2)), 100);
        productMap.put(new VMItem("P", "Pepsi", new BigDecimal("1.45").setScale(2)), 100);
        productMap.put(new VMItem("W", "Water", new BigDecimal("0.90").setScale(2)), 100);
        return productMap;
    }

    public BigDecimal getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(BigDecimal newBalance) {
        this.userBalance = newBalance;
    }

    public Map<BigDecimal, Integer> getCoinMap() {
        return coinMap;
    }

    public Map<VMItem, Integer> getProductMap() {
        return productMap;
    }

    private String printBalance() {
        return YOUR_BALANCE_IS_NOW + getUserBalance();
    }

    public String insertCoin(BigDecimal coin) {
        String status = "Inserted coin " + coin + " is invalid!" + printBalance();
        BigDecimal coinType = getCoinType(coin);
        if (coinType != null) {
            setUserBalance(getUserBalance().add(coinType));
            coinMap.put(coinType, coinMap.get(coinType) + 1);
            status = "Inserted coin " + coinType + " is valid! " + printBalance();
        }
        return status;
    }


    public BigDecimal getCoinType(BigDecimal insertedCoin) {
        for (Map.Entry<BigDecimal, Integer> entry : coinMap.entrySet()) {
            BigDecimal coinType = entry.getKey();
            int coinsComparison = coinType.compareTo(insertedCoin);

            if (coinsComparison == 0) {
                return coinType;
            }
        }
        return null;
    }

    public List<String> cancelRequest() {
        List<String> statusList = new ArrayList<>();

        if (getUserBalance().compareTo(new BigDecimal("0").setScale(2)) > 0) {
            statusList.add("Trying to cancel the request!" + printBalance());
            statusList.addAll(getChange());
        } else {
            statusList.add("No request to cancel!" + printBalance());
        }

        return statusList;
    }

    public List<String> getChange() {
        List<String> stringList = new ArrayList<>();
        BigDecimal changeAmount = getUserBalance();

        List<BigDecimal> reverseOrderedKeys = new ArrayList<>(coinMap.keySet());
        Collections.reverse(reverseOrderedKeys);

        int changeCoins = getChangePerCoin(reverseOrderedKeys, stringList);

        if (changeCoins > 0) {
            stringList.add("Total change provided " + changeAmount + "!" + printBalance());
        } else {
            stringList.add("No change coins left!" + printBalance());
        }

        return stringList;
    }

    private int getChangePerCoin(List<BigDecimal> reverseOrderedKeys, List<String> stringList) {
        int countCoins = 0;
        for (BigDecimal coinType : reverseOrderedKeys) {

            while (getUserBalance().compareTo(coinType) >= 0 && coinMap.get(coinType) > 0) {
                setUserBalance(getUserBalance().subtract(coinType));
                coinMap.put(coinType, coinMap.get(coinType) - 1);
                stringList.add("Provided as change 1 coin of value: " + coinType);
            }
        }
        return countCoins;
    }

    public List<String> checkProducts(String option) {
        List<String> statusList = new ArrayList<>();
        VMItem product = getProductType(option);
        if (product != null) {
            statusList.add(option + " has been selected!");
            statusList.addAll(retrieveProduct(product));
        } else {
            statusList.add(option + " is not a valid command! Please select a valid option!");
        }
        return statusList;
    }

    public VMItem getProductType(String product) {
        for (Map.Entry<VMItem, Integer> entry : productMap.entrySet()) {
            VMItem productType = entry.getKey();
            boolean productComparison = productType.getCode().equalsIgnoreCase(product);
            boolean productStock = entry.getValue() >= 1;
            if (productComparison && productStock) {
                return productType;
            }
        }
        return null;
    }

    public List<String> retrieveProduct(VMItem productType) {
        List<String> statusList = new ArrayList<>();

        if (getUserBalance().compareTo(productType.getPrice()) >= 0) {
            setUserBalance(getUserBalance().subtract(productType.getPrice()));
            productMap.put(productType, productMap.get(productType) - 1);
            statusList.add("Requested product " + productType.getName() + " has been received! " + printBalance());

            if (getUserBalance().compareTo(new BigDecimal("0").setScale(2)) > 0) {
                statusList.add("Trying to return change: " + getUserBalance());
                statusList.addAll(getChange());
            }
        } else {
            statusList.add("Requested product " + productType.getName() + " is too expensive! Please add more coins!" + printBalance());
        }
        return statusList;
    }

    public List<String> resetProdcuts() {
        List<String> statusList = new ArrayList<>();

        for (Map.Entry<VMItem, Integer> entry : productMap.entrySet()) {
            VMItem productType = entry.getKey();
            Integer productStock = productMap.get(productType);

            if (productStock != 100) {
                productMap.put(productType, 100);
                statusList.add("Stock of product " + productType.getName() + " has been reset to 100 units.");
            } else {
                statusList.add("Stock of product " + productType.getName() + " is already at 100 units.");
            }
        }
        return statusList;
    }

    public List<String> resetCoins() {
        List<String> statusList = new ArrayList<>();

        for (Map.Entry<BigDecimal, Integer> entry : coinMap.entrySet()) {
            BigDecimal coinType = entry.getKey();
            Integer coinStock = coinMap.get(coinType);

            if (coinStock != 100) {
                coinMap.put(coinType, 100);
                statusList.add("Stock of coin " + coinType + " has been reset to 100 units.");
            } else {
                statusList.add("Stock of coin " + coinType + " is already at 100 units.");
            }
        }
        return statusList;
    }


}
