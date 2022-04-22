package com.urise.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class MainStream {

    public static void main(String[] args) {
        int[] values = {1, 2, 3, 3, 2, 3, 10, 9, 0, -1, 7};
        int[] values1 = {9, 8, 0, 1};
        int[] valuesError = {0, 11, -2}; //Значение вне диапазона, кидаем исключение.
        int sum = minValue(values);
        int sum1 = minValue(values1);
//        int sum2 = minValue(valuesError);
        System.out.println(sum + ", " + sum1 + ", ");

        List<Integer> integerList = new ArrayList<>();
        for (Integer i : values) {
            integerList.add(i);
        }

        System.out.println(oddOrEven(integerList));
    }

    static int minValue(int[] value) {
        OptionalInt optionalInt = Arrays.stream(value)
                .filter(x -> x > 0 & x < 10)
                .distinct()
                .sorted()
                .reduce((multiplier, number) -> multiplier * 10 + number);
        if (optionalInt.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return optionalInt.getAsInt();
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        List<Integer> even = integers.stream().filter(integer -> integer % 2 == 0).collect(Collectors.toList());
        List<Integer> odd = integers.stream().filter(integer -> integer % 2 != 0).collect(Collectors.toList());
        int sum = integers.stream().reduce(Integer::sum).get();
        if (sum % 2 == 0) {
            return even;
        }
        return odd;
    }
}