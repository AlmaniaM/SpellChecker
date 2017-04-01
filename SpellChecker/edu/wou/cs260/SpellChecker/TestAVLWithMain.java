package edu.wou.cs260.SpellChecker;

import java.util.Iterator;

/**
 * File Name: ${FILE_NAME}
 * Created by: Alexander Molodyh
 * Western Oregon University
 * Class: CS260
 * Created: 3/2/2017
 * Assignment:
 */
public class TestAVLWithMain
{
    public static void main(String[] args)
    {
        AVLTreeSet<Integer> testSet = new AVLTreeSet();

        testSet.add(30);
        testSet.add(20);
        testSet.add(10);
        testSet.add(40);
        testSet.add(50);
        testSet.add(60);

        //Iterator<Integer> iterator = testSet.iterator();

        testSet.startPrint();

//        while(iterator.hasNext())
//        {
//            System.out.println(iterator.next());
//        }
    }
}
