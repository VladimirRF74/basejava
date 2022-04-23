package com.urise.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainStream {

    public static void main(String[] args) {
        int[] values = {1, 2, 3, 3, 2, 3, 10, 9, 0, -1, 7};
        int[] values1 = {9, 8, 0, 1};
        int sum = minValue(values);
        int sum1 = minValue(values1);

        System.out.println(sum + ", " + sum1);

        List<Integer> integerList = new ArrayList<>();
        for (Integer i : values) {
            integerList.add(i);
        }
        System.out.println(oddOrEvenOptional(integerList));
        System.out.println(oddOrEven(integerList));
    }

    static int minValue(int[] value) {
        return Arrays.stream(value)
                .filter(x -> x > 0 & x < 10)
                .distinct()
                .sorted()
                .reduce(0, (multiplier, number) -> multiplier * 10 + number);
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        List<Integer> even = integers.stream().filter(integer -> integer % 2 == 0).collect(Collectors.toList());
        List<Integer> odd = integers.stream().filter(integer -> integer % 2 != 0).collect(Collectors.toList());
        int sum = integers.stream().reduce(0, Integer::sum);
        if (sum % 2 != 0) {
            return even;
        }
        return odd;
    }

    static List<Integer> oddOrEvenOptional(List<Integer> integers) {
        Map<Boolean, List<Integer>> map = integers.stream()
                .collect(Collectors.partitioningBy(x -> x % 2 == 0, Collectors.toList()));
        return map.get(map.get(false).size() % 2 != 0);
    }
}