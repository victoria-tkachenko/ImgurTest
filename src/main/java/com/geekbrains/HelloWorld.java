package com.geekbrains;

import java.util.Arrays;

public class HelloWorld {

    private  int[] arr;

    public HelloWorld() {
        arr = new int[1000000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 1000000);
        }
    }

    public int foo() {
        return 1;
    }

    public int badFoo (int x) {
        return x / 0;
    }

    public int sum (int a, int b) {
        return a + b;
    }

    public int[] longFoo() {
// Неэффективный вид сортировки
//        for (int i = 0; i < arr.length; i++) {
//            for (int j = 0; j < arr.length - 1; j++) {
//                if (arr[j] > arr[j + 1]) {
//                    int tmp = arr[j];
//                    arr[j] = arr[j + 1];
//                    arr [j + 1] = tmp;
//                }
//            }
//        }
        Arrays.sort(arr);
        return arr;
    }
}

