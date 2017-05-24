//package test.java;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by cezar on 5/24/17.
// */
//public class Main {
//    public static void main_(String[] args) {
//        //1
//       // System.out.println((IntStream.of(1, 2, 3).map(x -> x * x).sum()));
//        //2
//       // System.out.println((fib(3)));
//        //3
//        int[][] a = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
//        System.out.println(a[1][2] + a [0][0]);
//        //4
//        System.out.println(f(6));
//        System.out.println(3 | 4);
//        System.out.println(Node.height(null));
//    }
//
//    static int fib(int n) {
//
//        return fib(n - 1) + fib(n - 2);
//
//    }
//
//    static int f(int n) {
//
//        if (n <= 0) {
//
//            return 0;
//
//        }
//
//
//        return n + f(n / 2);
//
//    }
//
//    static class Node {
//
//        Node left;
//
//        Node right;
//
//
//        static int height(Node node) {
//
//            if (node == null) {
//
//                return 0;     }
//
//
//            int left = height(node.left);
//
//            int right = height(node.right);
//
//            return Math.max(left, right);
//
//        }
//
//    }
//
//    class Animal {}
//
//    class Dog extends Animal {}
//
//
//    public static void main(String[] args) {
//
//        List<Dog> dogs = new ArrayList<>();
//
//        List<Animal> animals = dogs;
//
//    }
//
//
//}
