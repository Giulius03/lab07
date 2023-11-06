package it.unibo.inner.test.impl;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import it.unibo.inner.api.IterableWithPolicy;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T>{
    private T[] array;
    private Predicate<T> filter;

    public IterableWithPolicyImpl(final T[] vector) {
        this(vector, new Predicate<T>() {
            public boolean test(T element) {
                return true;
            }
        });
    }

    public IterableWithPolicyImpl(final T[] vector, final Predicate<T> filter) {
        this.array = vector;
        this.filter = filter;
    }

    public InnerIterator iterator() {
        return new InnerIterator();
    }

    public void setIterationPolicy(final Predicate<T> filter) {
        this.filter = filter;
    }

    public String toString() {
        return Arrays.toString(array);
    }
    
    private class InnerIterator implements Iterator<T> {
        private int currentIndex;
        
        public boolean hasNext() {
            while (this.currentIndex < IterableWithPolicyImpl.this.array.length) {
                T elem = IterableWithPolicyImpl.this.array[this.currentIndex];
                if (filter.test(elem)) {
                    return true;
                }
                this.currentIndex++;
            }
            return false;
        }

        public T next() {
            if (hasNext()) {
                T element = IterableWithPolicyImpl.this.array[this.currentIndex];
                this.currentIndex++;
                return element;
            }
            throw new NoSuchElementException();        
        }
    }
}