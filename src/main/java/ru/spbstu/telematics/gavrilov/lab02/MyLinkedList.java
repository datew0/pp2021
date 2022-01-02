package ru.spbstu.telematics.gavrilov.lab02;

import java.util.*;

public class MyLinkedList<T> extends AbstractSequentialList<T> implements List<T> {
    private int size = 0;
    private Node<T> head;

    private static class Node<T> {
        T payload;
        Node<T> next;

        Node(T payload, Node<T> next){
            this.payload = payload;
            this.next = next;
        }
    }

    private class MyListIter implements ListIterator<T> {
        private Node<T> next;
        private int nextIndex;
        private Node<T> lastReturned;

        MyListIter(int index) {
            next = (index == size) ? null : node(index);
            nextIndex = index;
        }

        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }

        @Override
        public T next() {
            if(!hasNext()) throw new NoSuchElementException();

            lastReturned = next;
            next = next.next;
            nextIndex++;
            return lastReturned.payload;
        }

        @Override
        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        @Override
        public T previous() {
            if (!hasPrevious()) throw new NoSuchElementException();

            lastReturned = next = (next == null) ? node(size-1) : node(nextIndex-1);
            nextIndex--;
            return next.payload;
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return nextIndex - 1;
        }
        @Override
        public void remove() {
            if (lastReturned == null) throw new IllegalStateException();

            Node<T> lastNext = lastReturned.next;
            displace(lastReturned);
            if (next == lastReturned)
                next = lastNext;
            else nextIndex--;
            lastReturned = null;
        }

        @Override
        public void set(T t) {
            if (lastReturned == null) throw new IllegalStateException();
            lastReturned.payload = t;
        }

        @Override
        public void add(T t) {
            lastReturned = null;
            if(next == null) placeLast(t);
            else placeBefore(t,next);
            nextIndex++;
        }
    }

    public MyLinkedList() {}

    public MyLinkedList(Collection<? extends T> c) {
        this();
        importCollection(0,c);
    }

    private boolean isValidIndex(int index){
        return (index >= 0) && (index <= size);
    }

    Node<T> node(int index) {
        Node<T> tmpNode = head;
        while(index-- > 0){
            tmpNode = tmpNode.next;
        }
        return tmpNode;
    }

    private void placeFirst(T item){
        Node<T> newFirst = new Node<>(item,head);
        head = newFirst;
        size++;
    }
    private void placeAt(int index, T item){
        if(!isValidIndex(index)) throw new IndexOutOfBoundsException("Index: " + index + ", size: " + size);
        if(size == 0){
            head = new Node<>(item, null);;
        }
        if(index == size) {
            placeLast(item);
            return;
        }
        else {
            int counter = index - 1;
            Node<T> tmpPrev = head;
            while (--counter > 0) {
                tmpPrev = tmpPrev.next;
            }
            tmpPrev.next = new Node<>(item,tmpPrev.next);
        }
        size++;
    }
    private void placeLast(T item){
        Node<T> newLast = new Node<>(item, null);
        if(size == 0){
            head = newLast;
        }
        else {
            int counter = size;
            Node<T> tmpLast = head;
            while (--counter > 0) {
                tmpLast = tmpLast.next;
            }
            tmpLast.next = newLast;
        }
        size++;
    }
    private void placeBefore(T item, Node<T> s){
        if (size == 0) return;
        if (size == 1) {
            if (head == s){
               placeFirst(item);
            }
            return;
        }
        Node<T> tmp = head;
        while(head.next != s){
            tmp = tmp.next;
        }
        tmp.next = new Node<>(item,s);
        size++;
    }

    T displaceFirst(){
        if (size == 0) return null;

        T item = head.payload;
        Node<T> toDel = head;
        head = toDel.next;
        toDel.payload = null;
        toDel.next = null;
        size--;
        return item;
    }
    T displace(Node<T> node) {
        T item = node.payload;
        if(size == 0) return null;

        Node<T> prev = head;
        while(prev.next != node && prev.next != null)
            prev = prev.next;

        prev.next = node.next;
        node.next = null;

        node.payload = null;
        size--;
        return item;
    }
    T displaceLast(){
        if(size == 1) return displaceFirst();
        if(size == 0) return null;

        Node<T> preLast = head;
        while (preLast.next.next != null) {
            preLast = preLast.next;
        }
        T item = preLast.next.payload;
        preLast.next.payload = null;
        preLast.next = null;
        size--;
        return item;
    }


    private boolean importCollection(int index, Collection<? extends T> c){
        if(!isValidIndex(index)) throw new IndexOutOfBoundsException("Index " + index + "is invalid");

        Object[] arr = c.toArray();
        if (arr.length == 0) return false;

        if(size == 0){
            for(Object o: arr){
                placeLast((T) o);
            }
        }
        else{
            for(int i=0;i<arr.length;i++){
                placeAt(index+i,(T) arr[i]);
            }
        }
        return true;
    }




    @Override
    public ListIterator<T> listIterator(int index) {
        return new MyListIter(index);
    }

    public void addFirst(T t) {
        placeFirst(t);
    }

    public void add(int index, T item){
        placeAt(index, item);
    }

    @Override
    public T remove(int index) {
        if(!isValidIndex(index)) throw new IndexOutOfBoundsException("Index " + index + "is invalid");
        return displace(node(index));
    }

    @Override
    public int size() {
        return size;
    }

    public boolean contains(Object o){
        return indexOf(o) >= 0;
    }
}
