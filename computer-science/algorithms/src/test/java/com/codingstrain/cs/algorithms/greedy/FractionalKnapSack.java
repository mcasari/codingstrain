package com.codingstrain.cs.algorithms.greedy;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.Test;
 

public class FractionalKnapSack {
     
    private static double getMaximumProfit(Item[] items, double capacity)
    {

        Arrays.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item item1, Item item2)
            {
                double fraction1 = item1.getProfit() / item1.getWeight();
                double fraction2 = item2.getProfit() / item2.getWeight();
 
                if (fraction1 < fraction2) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
 
        double maxProfit = 0;
 
        for (Item item : items) {
 
            int w = item.getWeight();
            int p = item.getProfit();
 
            if (capacity - w >= 0) {
                capacity = capacity - w;
                maxProfit += p;
            } else {
                double fraction = (capacity / w);
                maxProfit += (p * fraction);
                capacity = capacity - (w * fraction);
                break;
            }
        }
 
        return maxProfit;
    }

    @Test
    public void fractionalKnapSackTest() throws Exception {
        FractionalKnapSack fractionalKnapSack = new FractionalKnapSack();
        Item[] arr = { fractionalKnapSack.new Item(10, 5), fractionalKnapSack.new Item(20, 15), fractionalKnapSack.new Item(30, 6) };
        int capacity = 10;
        double maxProfit = getMaximumProfit(arr, capacity);
        System.out.println(maxProfit);
    }

    class Item {
        int profit;
        int weight;
        
        public Item(int profit, int weight) {
            this.profit = profit;
            this.weight = weight;
        }
        
        public int getProfit() {
            return profit;
        }

        public int getWeight() {
            return weight;
        }
    }
}