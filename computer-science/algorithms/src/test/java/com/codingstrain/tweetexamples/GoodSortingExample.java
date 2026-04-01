package com.codingstrain.tweetexamples;
import java.util.*;

public class GoodSortingExample {

    static class Person {
        String name;
        Integer age;

        Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        Integer getAge() {
            return age;
        }

        @Override
        public String toString() {
            return name + " (" + age + ")";
        }
    }

    public static void main(String[] args) {
        List<Person> people = Arrays.asList(
                new Person("Alice", 30),
                new Person("Bob", null),   // null age handled safely
                new Person("Charlie", 25)
        );

        //Safe: null ages go LAST
        people.sort(
                Comparator.comparing(
                        Person::getAge,
                        Comparator.nullsFirst(Integer::compareTo)
                )
        );

        people.forEach(System.out::println);
    }
}