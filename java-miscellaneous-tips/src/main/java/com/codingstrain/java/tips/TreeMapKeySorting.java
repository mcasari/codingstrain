package com.codingstrain.java.tips;

import java.util.*;

class TreeMapKeySorting {
	
	
  public static void main(String args[]){  

    TreeMap<String, String> treeMap = new TreeMap<>();
    treeMap.put("z", "value1");
    treeMap.put("c", "value2");
    treeMap.put("a", "value3");
    treeMap.put("b", "value4");

   for(Map.Entry<String, String> mapEntry: treeMap.entrySet()){  
        System.out.println(mapEntry.getKey() + " - " + mapEntry.getValue());  
   }  
 }
	
}