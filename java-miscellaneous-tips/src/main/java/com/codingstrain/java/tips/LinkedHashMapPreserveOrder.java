package com.codingstrain.java.tips;

import java.util.*;

class LinkedHashMapPreserveOrder{  
 public static void main(String args[]){  

   LinkedHashMap<Integer,String> preserveOrderMap = new LinkedHashMap<Integer,String>();  
   preserveOrderMap.put(1,"First");  
   preserveOrderMap.put(2,"Second");  
   preserveOrderMap.put(3,"Third");
   preserveOrderMap.put(4,"Fourth");

   for(Map.Entry mapEntry: preserveOrderMap.entrySet()){  
        System.out.println(mapEntry.getKey() + " - " + mapEntry.getValue());  
   }  
 }  
}  