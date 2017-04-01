package edu.wou.cs260.SpellChecker;

import java.util.Iterator;

/**
 * File Name: ${FILE_NAME}
 * Created by: Alexander Molodyh
 * Western Oregon University
 * Class: CS260
 * Created: 3/9/2017
 * Assignment:
 */
public class TestHashSetWithMain
{

    public static void main(String[] args)
    {
        /*OpenChainHashSet<String> set = new OpenChainHashSet<>(20);

        set.add("Hello");
        set.add("There");
        set.add("How");
        set.add("Are");
        set.add("You?");

        Iterator<String> hIterator = set.iterator();


        while(hIterator.hasNext())
        {
            System.out.println(hIterator.next());
        }*/

        int sum = 0;
        OpenChainHashSet<Integer> testSet = new OpenChainHashSet<Integer>( 3);
        testSet.clear( );
        testSet.add( 30);
        testSet.add( 20);
        testSet.add( 10);
        testSet.add( 40);
        testSet.add( 50);

        Iterator<Integer> it = testSet.iterator( );
        while (it.hasNext( )) {
            int cur = it.next();
            System.out.println(cur);
            sum = sum + cur;
        }

        System.out.println("\nThe sum is: " + sum);
    }
}
