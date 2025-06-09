package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.*;

@Data
@ToString
@AllArgsConstructor
class Pojo2 {
    private int number;
    private String fname;
    private String lname;
    private String avatar;
    private String email;
}

@SuppressWarnings("ALL")
public class StreamsQuest {
    public static List<Integer> getNumbers() {
        return Arrays.asList(1, 2, 3, 1, 5, 4, 1, 5, 6, 7, 8, 9, 10);
    }

    public static List<String> getStrings() {
        return Arrays.asList("apple", "orange", "banana", "grape", "watermelon", "pear");
    }

    public static List<Pojo2> getDummyData() {
        List<Pojo2> dummyData = new ArrayList<>();
        dummyData.add(new Pojo2(1, "John", "Doe", "avatar1.png", "john.doe@example.com"));
        dummyData.add(new Pojo2(2, "Jane", "Doe", "avatar2.png", "jane.doe@example.com"));
        dummyData.add(new Pojo2(3, "Jim", "Beam", "avatar3.png", "jim.beam@example.com"));
        dummyData.add(new Pojo2(4, "Jack", "Daniels", "avatar4.png", "jack.daniels@example.com"));
        dummyData.add(new Pojo2(5, "John", "Walker", "avatar5.png", "johnny.walker@example.com"));
        return dummyData;
    }

    public static void main(String[] args) {
        System.out.println("Hare Krishna");

        Integer c = getNumbers()
                .stream()
                .reduce(0, (x, num) -> x + 1);
        System.out.println(c);

        //Sum of squares of even numbers
        Integer sqs = getNumbers()
                .stream()
                .filter((x) -> x % 2 == 0)
                .map(x -> (x) * (x))
                .reduce(0, Integer::sum);//reduce(0, (x, num) -> x + num)
        System.out.println(sqs);



        //Filter the string having length greater than 4 and then grouping by length and counting number
        Map<Integer, Long> collect = getStrings()
                .stream()
                .filter(x -> x.length() >= 5)
                .collect(Collectors.groupingBy(String::length, Collectors.counting()));
        System.out.println(collect);

        //Second-Highest Number in the List
        Optional<Integer> first = getNumbers()
                .stream()
                .sorted((x, y) -> y - x)
                .skip(1)
                .findFirst();
        System.out.println(first.orElse(-1));

        //Sorting the map based on value
        LinkedHashMap<Integer, Long> collect3 = collect
                .entrySet()
                .stream()
                .sorted(((m1, m2) -> Math.toIntExact(m1.getValue() - m2.getValue()))).collect(Collectors.toMap(m1->m1.getKey(), m1->m1.getValue(), (e1, e2) -> e1, LinkedHashMap::new));
        System.out.println("============================="+collect3);

        //Reversing the words in the string
        String revWrd = Arrays
                .stream("Krishna Radhe".split(" "))
                .collect(Collectors.collectingAndThen(Collectors.toList(), (l) -> {
                    Collections.reverse(l);
                    return l;
                }))
                .stream()
                .collect(Collectors.joining(" "));
        System.out.println(revWrd);

        //Distinct element and then sort
        List<Integer> numbers = new ArrayList<>();
        numbers.addAll(List.of(1, 5, 7, 2, 2, 2, 9, 5, 4, 2, 11, 5, 5, 5, 2, 2, 2, 4, 5, 1, 2, 3, 4, 4, 1, 1, 1, 1, 1));
        numbers
                .stream()
                .distinct()
                .sorted()
                .peek(System.out::print).forEach(System.out::println);

        //Number with the highest frequency
        Consumer<Map.Entry<Integer, Long>> consumer = (x) -> {
            System.out.println("number having high frequency : " + x.getKey() + " and appeared " + x.getValue() + " times.");
        };

        Optional<Map.Entry<Integer, Long>> first1 = numbers
                .stream()
                .collect(Collectors.groupingBy((i) -> i, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(((Map.Entry<Integer, Long> m1, Map.Entry<Integer, Long> m2) -> Math.toIntExact(m2.getValue() - m1.getValue())))
                .findFirst();
        consumer.accept(first1.get());

        //Longest word in the sentence
        String sentence = "Hare Krishna ok and the Sun of ok things in Gandharvika the ok world and the place where they should live ok";
        Arrays
                .stream(sentence.split(" "))
                .collect(Collectors.groupingBy(String::length))
                .entrySet()
                .stream()
                .sorted((m1, m2) -> {
                    return m2.getValue().get(0).length() - m1.getValue().get(0).length();
                })
                .limit(1)
                .forEach(System.out::println);
        Arrays.stream(sentence.split(" ")).sorted((x, y) -> y.length() - x.length()).limit(1).forEach(System.out::println);

        //Word having the highest frequency
        Map<String, Long> collect1 = Arrays.stream(sentence.split(" ")).collect(Collectors.groupingBy(i -> i, Collectors.counting()));
        Map.Entry<String, Long> stringLongEntry = collect1
                .entrySet()
                .stream()
                .sorted((x, y) -> Math.toIntExact(y.getValue() - x.getValue()))
                .findFirst()
                .get();
        System.out.println("word is *" + stringLongEntry.getKey() + "* and frequency is *" + stringLongEntry.getValue() + "*");

        //partitioning the list to even and odd and calculating the sum
        Consumer<List<Integer>> con = (l) -> {
            int sum = 0;
            for (int i : l) {
                sum += i;
            }
            System.out.println("Sum of numbers is :" + sum);

        };

        //joiningBy " " which are having same length
        List<Integer> aList = new ArrayList<Integer>();
        aList.add(4);
        getNumbers()
                .stream()
                .collect(Collectors.partitioningBy(x -> x % 2 == 0))
                .entrySet()
                .stream()
                .forEach(x -> con.accept(x.getValue()));

        Map<Integer, String> collect2 = Arrays
                .stream(sentence.split(" "))
                .collect(Collectors.groupingBy(String::length, Collectors.joining(" ")));
        System.out.println(collect2);

        getDummyData()
                .stream()
                .collect(Collectors.groupingBy(x -> x.getFname()))
                .forEach((k, v) -> System.out.println(k + ": " + v));

        String input = "hare krishna hare krishna hare hare";
        Map.Entry<Character, Long> characterLongEntry = input
                .chars()
                .mapToObj(o -> (char) o).filter(Character::isLetter)
                .collect(Collectors.groupingBy(x -> x, Collectors.counting()))
                .entrySet().stream().sorted((m1, m2) -> Math.toIntExact(m2.getValue() - m1.getValue())).findFirst().get();
        System.out.println(characterLongEntry.getValue() + "  " + characterLongEntry.getKey());

        //top k=3 frequent elements in the list.
        numbers.stream().collect(Collectors.groupingBy(i -> i, Collectors.counting())).entrySet().stream().sorted((x,y)->Math.toIntExact(y.getValue()-x.getValue())).limit(3).forEach(System.out::println);

        //map of String and Integer
        Map<String, Integer> stringIntegerMap = new HashMap<>();
        stringIntegerMap.put("one", 1);
        stringIntegerMap.put("two", 2);
        stringIntegerMap.put("three", 3);
        stringIntegerMap.put("four",1);
        stringIntegerMap.put("five",3);
        stringIntegerMap.put("six",1);
        stringIntegerMap.put("seven",2);
        stringIntegerMap.put("eight",5);
        stringIntegerMap.entrySet().stream().collect(Collectors.groupingBy(i->i.getValue(),Collectors.mapping(i->i.getKey(),Collectors.toList()))).entrySet().stream().
                sorted((x,y)->x.getValue().size()-y.getValue().size()).forEach(System.out::println);

    }
}
