import java.util.*;
import java.io.*;
public class Homework2 {
    public static void main(String[] args) throws ExceptionClass {
        LispList list = new NonEmptyList("A",
                new NonEmptyList("B",
                        new NonEmptyList("C",
                                new EmptyList())));
        System.out.println(list);

        LispList a = new EmptyList();
        System.out.println(a);

        LispList b = new NonEmptyList("C", a);
        System.out.println(b);

        LispList c = new NonEmptyList("B", b);
        System.out.println(c);

        LispList d = new NonEmptyList("A", c);
        System.out.println(d);

        LispList listTwo = LispList.NIL.cons("C").cons("B").cons("A");
        System.out.println(listTwo);

        System.out.println(listTwo.length()); // 3
        listTwo = listTwo.cons("M"); // [M, A, B, C]

        System.out.println(listTwo);
        System.out.println(listTwo.length()); // 4
        System.out.println(c.merge(d));
        System.out.println(d.contains("A"));

    }
}

interface LispList {
    Object head();
    LispList tail();
    boolean isEmpty();
    LispList cons(Object object);
    LispList snoc(Object object);
    LispList merge(LispList other) throws ExceptionClass;
    public int length();

    boolean contains(Object obj);
    public static LispList NIL = new EmptyList();
}

class EmptyList implements LispList {
    public boolean isEmpty() {
        return true;
    }

    public LispList tail() {
        throw new NoSuchElementException("Empty list has no tail.");
    }

    public Object head() {
        throw new NoSuchElementException("Empty list has no head.");
    }

    public String toString() {
        return "";
    }

    public int length() {
        return 0;
    }

    public LispList cons(Object object) {
        return new NonEmptyList(object, this);
    }

    public LispList snoc(Object object) {
        return new NonEmptyList(object, this);
    }

    public LispList merge(LispList other) throws ExceptionClass {
        if (other.isEmpty()) {
            return this;
        }
        throw new ExceptionClass("Merge not implemented for EmptyList.");
    }
    public boolean contains(Object obj) {
        return false;
    }
}

class NonEmptyList implements LispList {
    private Object head;
    private LispList tail;

    public NonEmptyList(Object head, LispList tail) {
        this.head = head;
        this.tail = tail;
    }

    public Object head() {
        return this.head;
    }

    public LispList tail() {
        return this.tail;
    }

    public boolean isEmpty() {
        return false;
    }

    public int length() {
        return 1 + this.tail.length();
    }

    public String toString() {
        StringBuilder answer = new StringBuilder("[" + this.head());
        LispList a = this.tail();
        while (!a.isEmpty()) {
            answer.append(", ").append(a.head());
            a = a.tail();
        }
        return answer.append("]").toString();
    }

    public LispList cons(Object object) {
        return new NonEmptyList(object, this);
    }

    public LispList snoc(Object object) {
        return new NonEmptyList(this.head, this.tail.snoc(object));
    }

    public LispList merge(LispList other) throws ExceptionClass {
        if (other.isEmpty()) {
            return this;
        }

        LispList a = this, b = other, result = new EmptyList();
        while (!a.isEmpty() || !b.isEmpty()) {
            if (!a.isEmpty()) {
                result = result.snoc(a.head());
                a = a.tail();
            }
            if (!b.isEmpty()) {
                result = result.snoc(b.head());
                b = b.tail();
            }
        }
        return result;
    }
    public boolean contains(Object obj) {
        if (this.head.equals(obj)) {
            return true;
        }
        return this.tail.contains(obj);
    }
}

class ExceptionClass extends Exception {
    public ExceptionClass(String message) {
        super(message);
    }
}
