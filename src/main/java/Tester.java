package main.java;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

/**
 * Created by cezar on 5/28/17.
 */
public class Tester {
    public static void main(String[] args) throws Exception {
        /*TestData object = (TestData & Serializable) getObj();
        object.print();*//*
//        String str = (String & Serializable) getObj();
        DoubleUnaryOperator ex = (DoubleUnaryOperator & Serializable) t -> t * t;
        System.out.println(ex.applyAsDouble(666));
        RS rs = ((RS & Serializable & Runnable & Cloneable) () -> System.out.println());
        rs.run();
        ObjectOutputStream ois = new ObjectOutputStream(System.out);
        ois.writeObject(rs);
        rs.run();*/
        Cons cons = new Cons(666);
    }

    private static Object getObj() {
        return new TestData();
    }
}

class TestData {
    public void print() {
        System.out.println("print");
    }
}

interface RS extends Runnable {

}
/*
interface SDoubleUnaryOperator extends DoubleUnaryOperator, Serializable {

}
*/

interface I<T extends Runnable & Serializable> {

}

class Cons extends Par {

    {
        System.out.println(2);
    }

    {
        System.out.println(3);
    }
    public Cons(int a) {
        super(a);
        System.out.println(1);
    }

    {
        System.out.println(4);
    }

    {
        System.out.println(5);
    }
}


class Par {
    public Par(int a) {
        System.out.println(a);
    }
}

