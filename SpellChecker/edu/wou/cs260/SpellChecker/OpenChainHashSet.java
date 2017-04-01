package edu.wou.cs260.SpellChecker;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * File Name: OpenChainHashSet is a Hash set data structure.
 * Created by: Alexander Molodyh
 * Western Oregon University
 * Class: CS260
 * Created: 3/6/2017
 * Assignment: Part 5A
 */
public class OpenChainHashSet<E> implements Set<E>, CompareCount
{
    private DLList<E>[] hashTable;
    private int tableSize = 0;
    private int modCount = 0;
    private int currentSize = 0;

    public OpenChainHashSet(int tableSize)
    {

        this.modCount = 0;
        this.tableSize = tableSize;
        this.currentSize = 0;
        this.hashTable = (DLList<E>[]) new DLList[tableSize];
    }

    /**
     * This method returns the number of comparisons that was done during
     * the last call to contains (or just 0)
     *
     * @return The number of comparisons in the last call to contains
     */
    @Override
    public int getLastCompareCount()
    {
        return this.modCount;
    }

    @Override
    public int size()
    {
        return this.currentSize;
    }

    @Override
    public boolean isEmpty()
    {
        return (this.currentSize == 0);
    }

    @Override
    public boolean contains(Object o)
    {
        if(o == null)
            throw new NullPointerException("Object cannot be null");
        else
        {
            this.modCount = 0;

            E e = (E) o;
            int index = Math.abs(e.hashCode() % this.tableSize);

            this.modCount++;
            if(this.hashTable[index] == null)
                return false;
            else
            {
                boolean contains = this.hashTable[index].contains(e);
                this.modCount += this.hashTable[index].getLastCompareCount();
                return contains;
            }
        }
    }

    @Override
    public Iterator<E> iterator()
    {
        return new OCIterator<>();
    }

    @Override
    public Object[] toArray()
    {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] ts)
    {
        return null;
    }

    @Override
    public boolean add(E e)
    {
        if(e == null)
            throw new NullPointerException("Object cannot be null");
        else
        {
            int index = Math.abs(e.hashCode() % this.tableSize);

            this.checkReHash(index);

            if(this.hashTable[index] == null)
            {
                this.hashTable[index] = new DLList<>();
                this.hashTable[index].add(e);
            }
            else if(this.hashTable[index].contains(e))
                return false;
            else
                this.hashTable[index].add(e);

            this.currentSize++;
        }
        return true;
    }

    @Override
    public boolean remove(Object o)
    {
        if(o == null)
            throw new NullPointerException("Object cannot be null");
        else
        {
            int index = Math.abs(o.hashCode() % this.tableSize);

            if(this.hashTable[index] == null)
                return false;
            else if(!this.hashTable[index].contains(o))
                return false;
            else
            {
                this.currentSize--;
                return this.hashTable[index].remove(o);
            }

        }
    }

    @Override
    public boolean containsAll(Collection<?> collection)
    {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection)
    {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection)
    {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection)
    {
        return false;
    }

    @Override
    public void clear()
    {
        this.hashTable = (DLList<E>[]) new DLList[this.tableSize];
        this.currentSize = 0;
        this.modCount = 0;
    }

    private class OCIterator<E> implements java.util.Iterator<E>
    {
        private Iterator<E> curIter;
        private DLList<E> dlList;
        private E next = null; // next item
        private E current = null; //Current item
        private int index = 0; //Current index


        OCIterator()
        {
            if(tableSize > 0)
            {
                while(index < tableSize && (this.dlList = (DLList<E>) hashTable[index++]) == null)
                {
                    System.out.println("In Loop and index is: " + index);
                }

                System.out.println("After Loop and index is: " + index);

                System.out.println("DLList is: " + dlList.size());

                if(dlList != null)
                    this.curIter = dlList.iterator();
            }
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext()
        {
            if(curIter.hasNext())
                return true;
            else if(!curIter.hasNext())
            {
                if(this.index < tableSize)
                {
                    while(index < tableSize && (this.dlList = (DLList<E>) hashTable[index++]) == null);

                    if(dlList != null)
                        this.curIter = dlList.iterator();
                    else
                        return false;
                }
                else
                    return false;
            }
            return true;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public E next()
        {
            return this.curIter.next();
        }

        /**
         * Removes from the underlying collection the last element returned
         * by this iterator (optional operation).  This method can be called
         * only once per call to {@link #next}.  The behavior of an iterator
         * is unspecified if the underlying collection is modified while the
         * iteration is in progress in any way other than by calling this
         * method.
         *
         * @throws UnsupportedOperationException if the {@code remove}
         *                                       operation is not supported by this iterator
         * @throws IllegalStateException         if the {@code next} method has not
         *                                       yet been called, or the {@code remove} method has already
         *                                       been called after the last call to the {@code next}
         *                                       method
         */
        @Override
        public void remove()
        {
            this.curIter.remove();
            currentSize--;
        }
    }

    private void checkReHash(int index)
    {
        if(this.tableSize < index)
        {
            System.out.println("Rehashing............");
            this.tableSize = index * 2;
            this.hashTable = transferTable(this.hashTable);
        }
    }

    private DLList<E>[] transferTable(DLList<E>[] oldTable)
    {
        DLList<E>[] newTable = (DLList<E>[]) new DLList[this.tableSize];

        for(DLList<E> l : oldTable)
        {
            if(l != null)
            {
                Iterator<E> i = l.iterator();

                while(i.hasNext())
                {
                    E e = i.next();
                    int index = Math.abs(e.hashCode() % this.tableSize);
                    if(newTable[index] == null)
                    {
                        newTable[index] = new DLList<E>();
                        newTable[index].add(e);
                    }
                    else
                        newTable[index].add(e);

                }
            }
        }
        return newTable;
    }









}
