package com.spring.integration.basic.aggregator;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class Order {
    private String type;
    private int amount;

    public String getType(){
        return this.type;
    }

    @Override
    public String toString(){
        return "Order[ type=" + this.type + " ,amount= " + this.amount + "]" ;
    }
}
