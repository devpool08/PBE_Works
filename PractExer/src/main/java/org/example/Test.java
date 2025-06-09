package org.example;


import java.util.stream.*;
import java.util.*;
import java.util.function.*;

@SuppressWarnings("all")
public class Test {
    public static void main(String[] args) {
        Long collect = Arrays
                .stream("jcnsj ondwonf ofnoin oifnoin".split(" "))
                .count();
        System.out.println(collect);

        Map<Boolean, List<String>> collect1 = Arrays
                .stream("jcnsj ondwonf ofnoin oifnoin".split(" "))
                .collect(Collectors
                        .partitioningBy(x -> x.length() > 5));
        collect1
                .get(true)
                .forEach(System.out::println);


        String string = Arrays
                .stream("Hello World".split(" "))
                .map(x -> new StringBuilder(x).reverse())
                .collect(Collectors
                        .collectingAndThen(Collectors
                                        .toList(), (x) -> {
                                    Collections.reverse(x);
                                    return x;
                                }
                        ))
                .stream()
                .collect(Collectors.joining(" "))
                .toString();
        System.out.println(string);

        //First Non repeating Charater
        String input = "My mom makes mango milkshake and mango muffins";
        char[] charArray = input.toLowerCase().toCharArray();
        Optional<Map.Entry<Character, Long>> first = IntStream
                .range(0, charArray.length)
                .mapToObj(i -> charArray[i])
                .collect(Collectors.groupingBy(i -> i
                        , LinkedHashMap::new
                        , Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((x, y) -> Math.toIntExact(x.getValue() - y.getValue()))
                .findFirst();
        System.out.println();
        System.out.println("First Non repeating Charater");
        System.out.println(first.get().getKey());
        Map<Character, Integer> freq = new LinkedHashMap<>();
        for (char c : charArray) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        for (Map.Entry<Character, Integer> c : freq.entrySet()) {
            if (c.getValue() == 1) {
                System.out.println(c.getKey());
                break;
            }
        }
        System.out.println();
        System.out.println("Most freq word");
        Map<String, Integer> fq = new LinkedHashMap<>();
        Optional<Map.Entry<String, Long>> first1 = Arrays
                .stream(input.split(" "))
                .collect(Collectors
                        .groupingBy(i -> i
                                , LinkedHashMap::new
                                , Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((x, y) -> Math.toIntExact(y.getValue() - x.getValue()))
                .findFirst();
        System.out.println(first1.get().getKey());
        for (String s : input.split(" ")) {
            fq.put(s, fq.getOrDefault(s, 0) + 1);
        }
        Optional<Map.Entry<String, Integer>> first2 = fq.entrySet()
                .stream()
                .sorted((x, y) ->
                        Math.toIntExact(y.getValue() - x.getValue())).findFirst();
        System.out.println(first2.get().getKey());
        System.out.println();

        String[] stArr = {"listen", "silent", "enlist", "hello", "olleh", "afa"};
        System.out.println("pelindrome");
        Arrays
                .stream(stArr)
                .filter(x -> new StringBuilder(x).reverse().toString().equals(x))
                .collect(Collectors.toList())
                .forEach(System.out::println);

        BiPredicate<String, String> isAna = (x, y) -> {
            if (x.length() != y.length()) {
                return false;
            }
            for (char c : x.toCharArray()) {
                long xc = x.chars().filter(ch -> ch == c).count();
                long yc = y.chars().filter(ch -> ch == c).count();
                if (xc != yc) {
                    return false;
                }
            }
            return true;
        };
        System.out.println();
        System.out.println("anagram");
        for (int i = 0; i < stArr.length - 1; i++) {
            for (int j = i + 1; j < stArr.length; j++) {
                if (isAna.test(stArr[i], stArr[j])) {
                    System.out.println((stArr[i] + " and " + stArr[j]));
                }
            }
        }
        System.out.println();
        System.out.println("remove dublicate while preserving order");
        int[] arr = {3, 5, 3, 2, 5, 7, 2};
        Arrays
                .stream(arr)
                .distinct()
                .boxed()/// ///////// IMP ////////// //
                .collect(Collectors.toList())
                .forEach(System.out::println);

        System.out.println();
        Set<Integer> set = new LinkedHashSet<>();
        for (int i : arr) {
            set.add(i);
        }
        int[] arr2 = {1, 2, 3, 4, 5};
        int k = 2;
        int n = arr2.length;
        int[] rotated = new int[n];
        System.arraycopy(arr2, k, rotated, 0, n - k);
        System.arraycopy(arr2, 0, rotated, n - k, k);

        System.out.println("K rotate");
        Arrays.stream(rotated).forEach(System.out::println);

        System.out.println();
        List<Integer> list1 = Arrays.stream(new int[]{1, 3, 5, 7})
                .boxed() // Convert int[] to List<Integer>
                .collect(Collectors.toList());
        List<Integer> list2 = Arrays.stream(new int[]{3, 7, 8, 9})
                .boxed()
                .collect(Collectors.toList());
        List<Integer> intersection = list1.stream()
                .filter(list2::contains)
                .collect(Collectors.toList());
        System.out.println("Intersection of two list");
        intersection.forEach(System.out::println);

        //Find the Frequency of Each Digit in an Integer Array
        int[] numbers = {123, 456, 789, 123, 456};
        Map<Integer, Integer> digitFrequency = new LinkedHashMap<>();
        for (int num : numbers) {
            while (num > 0) {
                int digit = num % 10;
                digitFrequency.put(digit, digitFrequency.getOrDefault(digit, 0) + 1);
                num /= 10;
            }
        }
        System.out.println(digitFrequency);

        int[] arr3 = {0, 1, 6, 0, 2, 9, 0, 3, 4, 0, 5, 0};
        int f = 0;
        int b = arr3.length - 1;

        while (f <= b) {
            if (arr3[f] == 0 && arr3[b] != 0) {
                int temp = arr3[f];
                arr3[f] = arr3[b];
                arr3[b] = temp;
                f++;
                b--;
            } else if (arr3[f] != 0) {
                f++;
            } else {
                b--;
            }
        }

        Arrays.stream(arr3).forEach(System.out::println);


        int[] arr4 = {1, 5, 7, -1, 5};
        int sum = 6;

        Map<Integer, Integer> map = new HashMap<>();

        for (int value : arr4) {
            int complement = sum - value;
            if (map.containsKey(complement)) {
                for (int i = 0; i < map.get(complement); i++) {
                    System.out.println("(" + complement + ", " + value + ")");
                }
            }
            map.put(value, map.getOrDefault(value, 0) + 1);
        }

        List<String> list = Arrays.asList("apple", "banana", "cherry", "date", "clderberry", "aig", "grape", "biwi", "lemon", "mango");
        Map<Character, List<String>> charMap = new LinkedHashMap<>();
        for (String s : list) {
            charMap.computeIfAbsent(s.charAt(0), l -> new ArrayList<String>()).add(s);
        }
        System.out.println(charMap);
        String ip = "Hello World";
        String as = "";
        for (String s : ip.split(" ")) {
            as += new StringBuilder(s).reverse().toString();
            as += " ";
        }
        System.out.println(as);


        int[] a = {1, 5, 7, -1, 5};
        int s = 6;
        List<List<Integer>> an = new ArrayList<>();
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] + a[j] == s) {
                    an.add(new ArrayList<>(Arrays.asList(a[i], a[j])));
                }
            }
        }
        System.out.println(an);

        List<Integer> l1 = Arrays.asList(9, 9);  // 99
        List<Integer> l2 = Arrays.asList(1);     // 1

        List<Integer> result = new ArrayList<>();
        int c = 0, i = l1.size() - 1, j = l2.size() - 1;
        while (i >= 0 || j >= 0 || c > 0) {
            int n1 = (i >= 0) ? l1.get(i--) : 0;
            int n2 = (j >= 0) ? l2.get(j--) : 0;
            int ad = n1 + n2 + c;
            result.add(0, ad % 10);
            c = ad / 10;
        }
        System.out.println(result);
    }
}


