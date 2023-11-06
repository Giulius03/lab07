package it.unibo.inner.test.impl;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import it.unibo.inner.api.IterableWithPolicy;

/**
 * This class represents an iterable with policy array
 * 
 * @param <T> type of the elements of the array
 */
public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T>{
    private T[] array;
    private Predicate<T> filter;

    /**
     * Constructor with one parameter
     * 
     * @param vector array of the elements to iterate, with the Predicate class' test method that always returns true
     */
    public IterableWithPolicyImpl(final T[] vector) {
        this(vector, new Predicate<T>() {
            public boolean test(T element) {
                return true;
            }
        });
    }

    /**
     * Constructor with two elements
     * 
     * @param vector array of elements to iterate
     * @param filter filter to apply to the iteration
     */
    public IterableWithPolicyImpl(final T[] vector, final Predicate<T> filter) {
        this.array = vector;
        this.filter = filter;
    }

    /**
     * This method returns an iterator with a filter
     * 
     * @return an iterator using a filter
     */
    public InnerIterator iterator() {
        return new InnerIterator();
    }

    /**
     * This method initializes the filter of the iteration passed with the parameter
     * 
     * @param filter the filter to apply to the iteration
     */
    public void setIterationPolicy(final Predicate<T> filter) {
        this.filter = filter;
    }

    /**
     * This method represents the array through a string 
     * 
     * @return a string that represents the array of elements
     */
    public String toString() {
        return Arrays.toString(array);
    }
    
    /** 
     * This inner class represents the iterator that will be used to iterate the elements through the filter
    */
    private class InnerIterator implements Iterator<T> {
        private int currentIndex;
        
        /**
         * This method checks if the next element respects the filter directives
         * 
         * @return true if the elements aren't over and if the next element respects the filter directives, false if one of these
         *         conditions aren't respected 
         */
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

        /**
         * This method returns the next element of the array
         * 
         * @return the next element of the array, if there is one
         * @throws NoSuchElementException if the elements to iterate are over
         */
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