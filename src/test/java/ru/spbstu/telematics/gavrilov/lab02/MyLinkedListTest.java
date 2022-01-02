package ru.spbstu.telematics.gavrilov.lab02;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;

public class MyLinkedListTest {

    MyLinkedList<Integer> my;
    LinkedList<Integer> fromJDK;

    @Before
    public void init(){
        my = new MyLinkedList<>();
        fromJDK = new LinkedList<>();
    }
    @Test
    public void testEmptyList(){
        Assert.assertEquals("Lengths are not equal", my.size(), fromJDK.size());
    }
    @Test
    public void testInitialization(){
        my = new MyLinkedList<>(Arrays.asList(-2,-1,0,1,2));
        fromJDK = new LinkedList<>(Arrays.asList(-2,-1,0,1,2));
        Assert.assertEquals("Contents are not equal", fromJDK, my);
    }
    @Test
    public void testSize(){
        Assert.assertEquals("Sizes are not equal if empty", fromJDK.size(), my.size());

        my = new MyLinkedList<>(Arrays.asList(-2,-1,0,1,2));
        fromJDK = new LinkedList<>(Arrays.asList(-2,-1,0,1,2));

        Assert.assertEquals("Sizes are not equal after init from collection", fromJDK.size(), my.size());

        my.add(90);
        fromJDK.add(90);

        Assert.assertEquals("Sizes are not equal after add()", fromJDK.size(), my.size());

        my.remove(Integer.valueOf(-1));
        fromJDK.remove(Integer.valueOf(-1));

        Assert.assertEquals("Sizes are not equal after remove()", fromJDK.size(), my.size());

    }
    @Test
    public void testContains(){
        Assert.assertEquals(fromJDK.contains(4),my.contains(4));

        my.add(10);
        fromJDK.add(10);

        Assert.assertEquals(fromJDK.contains(10),my.contains(10));
        Assert.assertEquals(fromJDK.contains(5),my.contains(5));

        my.remove(Integer.valueOf(10));
        fromJDK.remove(Integer.valueOf(10));

        Assert.assertEquals(fromJDK.contains(10),my.contains(10));
    }
    @Test
    public void testAdd(){
        my.add(3);
        my.addFirst(5);
        my.add(1);

        fromJDK.add(3);
        fromJDK.addFirst(5);
        fromJDK.add(1);

        Assert.assertEquals("Contents are not equal", fromJDK,my);
    }
    @Test
    public void testRemove(){
        my = new MyLinkedList<>(Arrays.asList(-2,-1,0,1,2));
        fromJDK = new LinkedList<>(Arrays.asList(-2,-1,0,1,2));

        fromJDK.remove(Integer.valueOf(0));
        fromJDK.remove(Integer.valueOf(4));

        my.remove(Integer.valueOf(0));
        my.remove(Integer.valueOf(4));

        Assert.assertEquals("Contents are not equal after remove", fromJDK,my);
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGet(){
        my = new MyLinkedList<>(Arrays.asList(-2,-1,0,1,2));
        fromJDK = new LinkedList<>(Arrays.asList(-2,-1,0,1,2));

        Assert.assertEquals(fromJDK.get(2), my.get(2));

        my = new MyLinkedList<>();
        fromJDK = new LinkedList<>();
        my.add(Integer.valueOf(4));
        fromJDK.add(Integer.valueOf(4));

        Assert.assertEquals(fromJDK.get(0), my.get(0));

        my = new MyLinkedList<>();
        fromJDK = new LinkedList<>();

        //Exception must be thrown
        Assert.assertEquals(fromJDK.get(2), my.get(2));
    }
}
