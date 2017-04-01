package edu.wou.cs260.SpellChecker;

/**
 * Class AVLTreeSet This is a Binary Search Tree Data Structure.
 * File Name: AVLTreeSet.java
 * Created by: Alexander Molodyh
 * Western Oregon University
 * Class: CS260
 * Created: 2/22/2017
 * Assignment: Part #4B
 */
public class AVLTreeSet<T extends Comparable<T>> extends BSTreeSet<T>
{
    @Override
    public boolean add(T t)
    {
        if(t == null)
            throw new NullPointerException("Object is null");
        else
            this.root = this.addNode(root, t);

        return true;
    }

    @Override
    public boolean remove(Object o)
    {
        return super.remove(o);
    }

    private int getBalanceValue(Node n)
    {
        if(n == null)
            return 0;
        else
        {
            return this.getHeight(n.rChild) - this.getHeight(n.lChild);
        }
    }

    private Node singleLeftRotate(Node n)
    {
        Node temp = n.rChild;

        n.rChild = temp.lChild;
        temp.lChild = n;
        this.fixHeight(n);
        this.fixHeight(temp);

        return temp;
    }

    private Node singleRightRotate(Node n)
    {
        Node temp = n.lChild;

        n.lChild = temp.rChild;
        temp.rChild = n;
        this.fixHeight(n);
        this.fixHeight(temp);

        return temp;
    }

    private Node doubleLeftRotate(Node n)
    {
        n.rChild = this.singleRightRotate(n.rChild);
        return this.singleLeftRotate(n);
    }

    private Node doubleRightRotate(Node n)
    {
        n.lChild = this.singleLeftRotate(n.lChild);
        return this.singleRightRotate(n);
    }

    private Node balance(Node n)
    {
        if(n == null)
            return null;
        else
        {
            int balanceVal = this.getBalanceValue(n);

            if((balanceVal < 2) && (balanceVal > -2))
            {
                this.fixHeight(n);
                return n;
            }
            else if(balanceVal < -1)
            {
                if(this.getBalanceValue(n.lChild) > 0)
                    return this.doubleRightRotate(n);
                else
                    return this.singleRightRotate(n);
            }
            else
            {
                if(this.getBalanceValue(n.rChild) < 0)
                    return this.doubleLeftRotate(n);
                else
                    return this.singleLeftRotate(n);
            }
        }
    }

    @Override
    protected Node addNode(Node n, T t)
    {
        if(n == null)
        {
            this.size++;
            return new Node(t);
        }
        else if(t.compareTo(n.item) <= SMALLER_OBJ)
        {
            if(n.lChild != null)
                n.lChild = this.addNode(n.lChild, t);
            else
                n.lChild = this.addNode(n.lChild, t);
        }
        else
        {
            if(n.rChild != null)
                n.rChild = this.addNode(n.rChild, t);
            else
                n.rChild = this.addNode(n.rChild, t);
        }

        this.fixHeight(n);

        return this.balance(n);
    }
}







