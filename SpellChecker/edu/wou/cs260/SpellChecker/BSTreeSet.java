package edu.wou.cs260.SpellChecker;

import java.util.*;

/**
 * Class BSTreeSet This is a Binary Search Tree Data Structure.
 * <p>
 * File Name: BSTreeSet.java
 * Created by: Alexander Molodyh
 * Western Oregon University
 * Class: CS260
 * Created: 2/21/2017
 * Assignment: Part #3B
 */
public class BSTreeSet<T extends Comparable<T>> implements Set<T>, CompareCount
{
    protected int compCount = 0;
    protected int size = 0;
    protected Node root = null;
    protected Node successor = null;
    protected Node predecessor = null;

    protected final int SMALLER_OBJ = -1, EQUAL_TO = 0, LARGER_OBJ = 1;


    public BSTreeSet()
    {
        this.size = 0;
        this.compCount = 0;
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
        return compCount;
    }

    @Override
    public int size()
    {
        return this.size;
    }

    @Override
    public boolean isEmpty()
    {
        return (this.size <= 0);
    }

    @Override
    public boolean contains(Object o)
    {
        if(o != null)
            return this.containsItem(this.root, ((T) o));
        else
            throw new NullPointerException("Object is null");
    }

    @Override
    public Iterator<T> iterator()
    {
        return new BSTIterator();
    }

    @Override
    public Object[] toArray()
    {
        ArrayList<Object> temp = new ArrayList<>();
        this.populatePreOrder(this.root, temp);
        return temp.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] t1s)
    {
        return null;
    }

    @Override
    public boolean add(T t)
    {
        if(t != null)
            this.root = this.addNode(root, t);
        else
            throw new NullPointerException("Object is null");

        return true;
    }

    @Override
    public boolean remove(Object o)
    {
        if(o != null)
        {
            T t = (T) o;
            if(this.root == null)
                return false;
            else if(this.root.item.equals(t))
            {
                Node temp = new Node();
                temp.lChild = this.root;
                boolean removed = this.removeNode(temp, this.root, t);
                this.root = temp.lChild;
                return removed;
            }
            else
                return this.removeNode(this.root, this.root, t);
        }
        else
            throw new NullPointerException("Object is null");
    }

    @Override
    public boolean containsAll(Collection<?> collection)
    {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection)
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
        this.root = null;
        this.size = 0;
        this.compCount = 0;

        //System.out.println("\nClear\n");
    }

    private void populatePreOrder(Node cur, ArrayList<Object> obj)
    {
       if(cur == null)
           return;

       populatePreOrder(cur.lChild, obj);
       obj.add(cur.item);
       populatePreOrder(cur.rChild, obj);
    }

    private boolean removeNode(Node parent, Node curNd, T t)
    {
        if(t.compareTo(curNd.item) <= SMALLER_OBJ)
        {
            if(curNd.lChild != null)
                return this.removeNode(curNd, curNd.lChild, t);
            else
                return false;
        }
        else if(t.compareTo(curNd.item) >= LARGER_OBJ)
        {
            if(curNd.rChild != null)
                return this.removeNode(curNd, curNd.rChild, t);
            else
                return false;
        }
        else
        {
            if(curNd.lChild != null && curNd.rChild != null)
            {
                Node temp = this.getSuccessor(curNd.lChild);
                curNd.item = temp.item;
                return this.removeNode(curNd, curNd.lChild, temp.item);
            }
            else if(parent.lChild == curNd)
            {
                parent.lChild = (curNd.lChild != null) ? curNd.lChild : curNd.rChild;
                this.size--;
                return true;
            }
            else if(parent.rChild == curNd)
            {
                parent.rChild = (curNd.rChild != null) ? curNd.rChild : curNd.lChild;
                this.size--;
                return true;
            }

            return false;
        }
    }

    private Node getSuccessor(Node curNd)
    {
        if(curNd.rChild == null)
            return curNd;
        else
            return this.getSuccessor(curNd.rChild);
    }

    private boolean containsItem(Node n, T t)
    {
        if(n != null)
        {
            int compareTo = t.compareTo(n.item);

            this.compCount++;

            if(t.equals(n.item)) return true;
            else if(compareTo <= SMALLER_OBJ && n.lChild != null && this.containsItem(n.lChild, t)) return true;
            else if(compareTo >= LARGER_OBJ && n.rChild != null && this.containsItem(n.rChild, t)) return true;
        }

        return false;
    }

    /**
     * This method prints the whole set displaying the items and their heights.
     * This method is only for testing.
     */
    public void startPrint()
    {
        this.printSet(this.root);
    }

    private void printSet(Node n)
    {
        if(n == null)
            return;

        System.out.println("The item is: " + n.item + " and the height is: " + n.height);

        this.printSet(n.lChild);
        this.printSet(n.rChild);
    }

    protected Node addNode(Node n, T t)
    {
        if(n == null)
        {
            this.size++;
            Node fNode = new Node(t);
            this.fixHeight(fNode);
            return fNode;
        }
        else if(t.compareTo(n.item) <= SMALLER_OBJ)
        {
            if(n.lChild != null)
                this.addNode(n.lChild, t);
            else
                n.lChild = this.addNode(n.lChild, t);
        }
        else
        {
            if(n.rChild != null)
                this.addNode(n.rChild, t);
            else
                n.rChild = this.addNode(n.rChild, t);
        }

        this.fixHeight(n);

        return n;
    }

    protected Node fixHeight(Node n)
    {
        if(n == null)
            return null;
        else
        {
            n.height = Math.max(this.getHeight(n.lChild), this.getHeight(n.rChild)) + 1;
            return n;
        }
    }

    protected int getHeight(Node n)
    {

        if(n != null)
            return n.height;
        else
            return -1;
    }

    private class BSTIterator implements Iterator<T>
    {
        private Queue<T> queue;
        private Iterator<T> iterator;

        public BSTIterator()
        {
            queue = new DLList<T>();
            this.breadthTravel(root);
            this.iterator = queue.iterator();
        }

        @Override
        public boolean hasNext()
        {
            return this.iterator.hasNext();
        }

        @Override
        public T next()
        {
            return this.iterator.next();
        }

        @Override
        public void remove()
        {
            this.iterator.remove();
        }

        private void breadthTravel(Node n)
        {
            Queue<Node> qNode = new DLList<>();

            qNode.offer(n);
            while(!qNode.isEmpty())
            {
                Node node = qNode.poll();
                this.visit(node);

                if(node.lChild != null)
                    qNode.offer(node.lChild);
                if(node.rChild != null)
                    qNode.offer(node.rChild);
            }

        }

        private void visit(Node n)
        {
            if(n != null)
                this.queue.offer(n.item);
        }
    }

    class Node
    {
        // fields
        T item;
        int height;
        Node lChild;
        Node rChild;

        // methods
        // 3 constructors
        Node() {this(null, null, null);}

        Node(T item) {this(null, item, null);}

        Node(Node lChild, T item, Node rChild)
        {
            this.lChild = lChild;
            this.item = item;
            this.rChild = rChild;
            height = 0;
        }
    }
}
