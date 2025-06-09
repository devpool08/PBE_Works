package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

@Data
@AllArgsConstructor
class Pojo implements Comparable<Pojo> {
    int id;
    String name;
    int age;

    @Override
    public int compareTo(Pojo p) {
        return this.id - p.id;
    }
}

class CosCom implements Comparator<Pojo> {
    @Override
    public int compare(Pojo p1, Pojo p2) {
        return p1.id - p2.id;
    }
}

public class ComparatorQuest {
    public static void main(String[] args) {
        List<Pojo> list = new ArrayList<>();
        list.add(new Pojo(1, "Alice", 30));
        list.add(new Pojo(3, "Bob", 25));
        list.add(new Pojo(2, "Charlie", 35));
        list.add(new Pojo(4, "Diana", 28));
        list.add(new Pojo(5, "Eve", 22));


        // Case 2: Anonymous Comparator
        Comparator<Pojo> com = (p1, p2) -> p1.id - p2.id;

        // Sorting using different methods
        list.sort(null); // Case 1: Natural ordering (Comparable)
        list.sort(com); // Case 2: Anonymous Comparator
        list.sort(new CosCom()); // Case 3: Custom Comparator class
        list.sort(Comparator.comparing(Pojo::getId).thenComparing(Pojo::getName)); // Case 4: Method reference
    }
}
