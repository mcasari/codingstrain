package com.codingstrain.filescopy;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class MyClass {
  public static void main(String args[]) {
    class BadUser {
        private final String email;
        BadUser(String email) { this.email = email; }
    
        @Override
        public boolean equals(Object o) {
            return o instanceof BadUser u && email.equals(u.email);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(email);
        }
    }
    
    HashSet sss = new HashSet();
    sss.addAll(List.of(new BadUser("a@x.com")));
    System.out.println(sss.contains(new BadUser("a@x.com")));
  }
}