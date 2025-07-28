package org.example;

import java.math.BigDecimal;

public class VMItem {

    private String code;
    private String name;
    private BigDecimal price;

    public VMItem(String code, String name, BigDecimal price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "[" + this.code + "] " + this.name + ", Price: " + this.price;
    }

}
