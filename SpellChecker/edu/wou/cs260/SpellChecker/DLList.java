package edu.wou.cs260.SpellChecker;

import java.util.*;

/**
 * File name: DLList.java
 * Programmer: Alexander Molodyh
 * Western Oregon University
 * Class CS260
 * Created: January 28 2017
 * Assignment: Lab #2: Double Linked Lists
 */

/**
 * @param <T>
 */
public class DLList<T> implements List<T>, CompareCount, Queue<T>
{
    private DLLNode head;
    private DLLNode tail;
    private Object[] dllData;
    private int compCount = 0;
    private int size = 0;
    private final int HEAD_INDEX = 0;

    public DLList()
    {
        this.size = 0;
        this.compCount = 0;
        this.dllData = new Object[0];
    }

    /**
     * This method returns the number of comparisons that was done during the
     * last call to contains (or just 0)
     *
     * @return The number of comparisons in the last call to contains
     */
    @Override
    public int getLastCompareCount()
    {
        return this.compCount;
    }

    @Override
    public int size()
    {
        return this.size;
    }

    @Override
    public boolean isEmpty()
    {
        return (this.size == 0);
    }

    @Override
    public boolean contains(Object o)
    {
        if(o != null)
        {
            DLLNode nTemp = this.head;
            this.compCount = 0;

            for(int i = 0; i < this.size; i++)
            {
                this.compCount++;
                if(nTemp.data.equals(o))
                {
                    //System.out.println("Found data " + nTemp.data.toString() + " With loop # " + i);
                    return true;
                }
                else
                    nTemp = nTemp.next;
            }
        }
        else
        {
            throw new NullPointerException("Object is null");
        }

        return false;
    }

    @Override
    public Iterator<T> iterator()
    {
        return new DLLIterator();
    }

    @Override
    public Object[] toArray()
    {
        Object[] temp = new Object[this.size()];

        for(int i = 0; i < this.size(); i++)
            temp[i] = this.get(i);

        return temp;
    }

    @Override
    public <T> T[] toArray(T[] t)
    {
        if(t.length < this.size)
        {
            T[] t1 = (T[]) new Object[this.size];

            for(int i = 0; i < this.size; i++)
                t1[i] = (T) this.get(i);

            return t;
        }
        else
        {
            for(int j = 0; j < this.size; j++)
                t[j] = (T) this.get(j);

            if(t.length > this.size())
                t[this.size] = null;
        }
        return t;
    }

    @Override
    public boolean add(T e)
    {
        if(e != null)
        {
            DLLNode temp = new DLLNode(e);
            if(this.tail == null)
            {
                this.head = temp;
                this.tail = temp;
            }
            else
            {
                temp.prev = this.tail;
                this.tail.next = temp;
                this.tail = temp;
            }
            this.size++;
        }
        else
            throw new NullPointerException("Object cannot be null");

        return true;
    }

    @Override
    public void add(int i, T e)
    {
        if(e != null)
        {
            DLLNode eTemp = new DLLNode(e);

            if(i == this.size) // If index is same as list size, add one
            // element to the end of the list
            {
                add(e);
            }
            else if(i < this.size && i > 0)
            {
                DLLNode nTemp = this.head;

                for(int j = 0; j < i; j++)
                    nTemp = nTemp.next;

                eTemp.prev = nTemp.prev; // Set new node previous to current
                // location previous
                eTemp.next = nTemp; // Set new next to current location next
                nTemp.prev.next = eTemp; // Set next of previous node to new
                // node
                nTemp.prev = eTemp; // Set previous of current node
                this.size++;
            }
            else if(i == HEAD_INDEX) // If index is zero, add one element at the head
            {
                eTemp.next = head;
                this.head.prev = eTemp;
                this.head = eTemp;
                this.size++;
            }
            else
            {
                throw new IndexOutOfBoundsException("Index out of bounds");
            }
        }
        else
        {
            throw new NullPointerException("Object cannot be null");
        }
    }

    @Override
    public boolean offer(T e)
    {
        if(e != null)
        {
            this.add(e);
            return true;
        }
        else
            throw new NullPointerException("Object is null");
    }

    @Override
    public T poll()
    {
        if(!this.isEmpty())
            return this.remove(HEAD_INDEX);
        else
            return null;
    }

    @Override
    public T element()
    {
        if(!this.isEmpty())
            return this.head.data;
        else
            throw new NoSuchElementException("There is no element to remove");
    }

    @Override
    public T peek()
    {
        if(!isEmpty())
            return this.element();
        else
            return null;
    }

    @Override
    public boolean containsAll(Collection<?> collection)
    {
        if(collection != null)
        {
            for(int i = 0; i < this.size(); i++)
            {
                Iterator<?> itr = collection.iterator();

                while(itr.hasNext())
                    if(!this.contains(itr.next()))
                        return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection)
    {
        if(collection != null)
        {
            Iterator<?> itr = collection.iterator();

            while(itr.hasNext())
                this.add((T) itr.next());
        }
        else
            throw new NullPointerException("Object is null");

        return true;
    }

    @Override
    public boolean addAll(int i, Collection<? extends T> collection)
    {
        if(collection != null && i <= this.size())
        {
            int index = i;
            Iterator<?> itr = collection.iterator();

            while(itr.hasNext())
            {
                this.add(index, (T) itr.next());
                index++;
            }
        }
        else if(collection == null)
            throw new NullPointerException("Object is null");
        else
            throw new IndexOutOfBoundsException("Invalid index");

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection)
    {
        if(collection != null)
        {
            Iterator<?> itr = collection.iterator();

            while(itr.hasNext())
                this.remove(itr.next());
        }
        else
            throw new NullPointerException("Object is null");

        return true;
    }

    @Override
    public boolean retainAll(Collection<?> collection)
    {
        if(collection != null)
        {
            for(int i = 0; i < this.size(); i++)
            {
                if(!collection.contains(this.get(i)))
                    this.remove(i);
            }
        }
        else
            throw new NullPointerException("Object is null");

        return true;
    }

    @Override
    public void clear()
    {
        head = null;
        tail = null;
        size = 0;
        compCount = 0;
    }

    @Override
    public T get(int i)
    {
        if(i >= 0 && i < this.size)
        {
            DLLNode nTemp = this.head;

            if(i == HEAD_INDEX)
            {
                return nTemp.data;
            }
            else
            {
                for(int j = 0; j < i; j++)
                    nTemp = nTemp.next;

                return nTemp.data;
            }
        }
        else
        {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    @Override
    public T set(int i, T e)
    {
        T temp = null;

        if(e != null)
        {
            DLLNode tempNode = this.getNode(i);
            temp = tempNode.data;
            tempNode.data = e;
        }

        return temp;
    }

    @Override
    public T remove()
    {
        if(!this.isEmpty())
            return this.remove(HEAD_INDEX);
        else
            throw new NoSuchElementException("There is no element to remove");
    }

    @Override
    public boolean remove(Object o)
    {
        if(o != null && this.contains(o))
        {
            if(this.size == 1 && this.head.data.equals(o))
            {
                this.head = null;
                this.tail = null;
            }
            else if(this.head.data.equals(o))
            {
                this.head.next.prev = null;
                this.head = this.head.next;
            }
            else if(this.tail.data.equals(o))
            {
                this.tail.prev.next = null;
                this.tail = this.tail.prev;
            }
            else
            {
                DLLNode nTemp = this.getNode(o);

                nTemp.prev.next = nTemp.next;
                nTemp.next.prev = nTemp.prev;
                nTemp = null;
            }

            this.size--;
            return true;
        }
        else if(o == null)
            throw new NullPointerException("Object is null");
        else if(!contains(o))
            return false;

        return false;
    }

    @Override
    public T remove(int i)
    {
        T element = null;

        if(i == HEAD_INDEX)
        {
            element = this.head.data;
            this.remove(this.head.data);
        }
        else if(i > 0 && i < size)
        {
            DLLNode node = getNode(i);
            element = node.data;
            this.remove(element);
        }
        else
        {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        return element;
    }

    @Override
    public int indexOf(Object o)
    {
        int index = 0;

        if(o != null)
        {
            for(int i = 0; i < this.size; i++)
            {
                if(o.equals(get(i)))
                    return i;
            }
        }
        else
            throw new NullPointerException("Object is null");
        return 0;
    }

    @Override
    public int lastIndexOf(Object o)
    {
        if(o != null)
        {
            int index = 0;
            for(int i = 0; i < this.size; i++)
            {
                if(o.equals(get(i)))
                    index = i;
            }

            return index;
        }
        else
            throw new NullPointerException("Object is null");
    }

    @Override
    public ListIterator<T> listIterator()
    {
        return new DLLListIterator();
    }

    @Override
    public ListIterator<T> listIterator(int i)
    {
        return new DLLListIterator(i);
    }

    @Override
    public List<T> subList(int i, int i1)
    {
        List<T> tList = new ArrayList<>();

        if(i < HEAD_INDEX || i1 > this.size || i > i1)
            throw new IndexOutOfBoundsException("index is out of bounds");
        else
        {
            DLLNode tNode = this.getNode(i);

            for(int j = i; j <= i1; j++)
            {
                tList.add(tNode.data);
                tNode = tNode.next;
            }
        }

        return tList;
    }

    private DLLNode getNode(Object o)
    {
        DLLNode nTemp = this.head;

        for(int i = 0; i < this.size; i++)
        {
            if(nTemp.data.equals(o))
                return nTemp;
            else
                nTemp = nTemp.next;
        }

        return null;
    }

    private DLLNode getNode(int pos)
    {
        if(pos == HEAD_INDEX)
        {
            return this.head;
        }
        else if(pos > 0 && pos < size)
        {
            DLLNode nTemp = this.head;

            for(int i = 0; i < pos; i++)
                nTemp = nTemp.next;

            return nTemp;
        }
        else
        {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    class DLLNode
    {
        // fields
        DLLNode prev;
        T data;
        DLLNode next;

        // methods
        // 3 constructors
        DLLNode()
        {
            this(null, null, null);
        }

        DLLNode(T d)
        {
            this(null, d, null);
        }

        DLLNode(DLLNode p, T d, DLLNode n)
        {
            prev = p;
            data = d;
            next = n;
        }
    }

    private class DLLIterator implements Iterator<T>
    {
        DLLNode curNode;
        int cursor;
        int lastMod;
        int min = 0;
        final int HAS_NEXT = 1;
        final int NEXT = 2;
        final int REMOVE = 3;

        public DLLIterator()
        {
            this.curNode = DLList.this.head;
            this.cursor = 0;
            this.lastMod = -1;
            this.min = 0;
        }

        @Override
        public boolean hasNext()
        {
            this.lastMod = this.HAS_NEXT;

            if(this.curNode != null)
                return true;
            else
                return false;
        }

        @Override
        public T next()
        {
            this.lastMod = this.NEXT;

            if(this.curNode != null)
            {
                T element = this.curNode.data;
                this.curNode = this.curNode.next;
                return element;
            }
            else
                throw new NullPointerException("Object is null");
        }

        @Override
        public void remove()
        {
            this.lastMod = this.REMOVE;

            if(DLList.this.size() > 0)
            {
                if(DLList.this.size() == 1)
                {
                    DLList.this.remove(0);
                }
                else
                {
                    DLList.this.remove(DLList.this.size() - 1);
                }

                this.cursor--;
            }
        }
    }

    private class DLLListIterator extends DLLIterator implements ListIterator<T>
    {

        public DLLListIterator()
        {
            super();
        }

        public DLLListIterator(int i)
        {
            super();
            this.cursor = i;
            this.min = i;
            this.curNode = DLList.this.getNode(i);
        }


        @Override
        public boolean hasPrevious()
        {
            if(this.cursor > this.min)
                return true;
            else
                return false;
        }

        @Override
        public T previous()
        {
            if(this.curNode != null)
            {
                if(this.cursor == DLList.this.size)
                {
                    this.cursor--;
                    return curNode.data;
                }
                else if(this.cursor < DLList.this.size && this.cursor >= this.min)
                {
                    T element = this.curNode.prev.data;
                    if(!(this.cursor == this.min))
                    {
                        this.curNode = this.curNode.prev;
                    }
                    this.cursor--;
                    return element;
                }
                else
                    throw new NoSuchElementException("No more elements");
            }
            else
                throw new NullPointerException("Object is null");
        }

        @Override
        public int nextIndex()
        {
            if(this.lastMod < 0)
                throw new IllegalStateException();
            else
                return this.hasNext() ? cursor : DLList.this.size;
        }

        @Override
        public int previousIndex()
        {
            if(this.lastMod < 0)
                throw new IllegalStateException();
            else if(this.cursor == DLList.this.size)
                return this.cursor - 1;
            else if(this.cursor == this.min)
                return -1;
            else
                return this.cursor - 1;
        }

        @Override
        public void set(Object o)
        {
            T element = (T) o;

            if(this.lastMod < 0)
                throw new IllegalStateException();
            else
            {
                try
                {
                    DLList.this.set(this.cursor, element);
                }
                catch(IndexOutOfBoundsException e)
                {
                    throw new ConcurrentModificationException();
                }
            }
        }

        @Override
        public void add(Object o)
        {
            if(this.cursor == DLList.this.HEAD_INDEX)
                DLList.this.add((T) o);
            else
                DLList.this.add(this.cursor - 1, (T) o);

            this.cursor++;
        }
    }
}

