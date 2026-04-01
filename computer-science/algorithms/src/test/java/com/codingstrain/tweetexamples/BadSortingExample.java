package com.codingstrain.tweetexamples;
import java.util.*;

public class BadSortingExample {

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
                new Person("Bob", null),   // ⚠️ null age
                new Person("Charlie", 25)
        );

        try {
			//This crashes at runtime
			people.sort(Comparator.comparing(Person::getAge));
		} catch (Exception e) {
			System.out.println("Error! - " + e.getMessage());
		}

        people.forEach(System.out::println);
    }
}