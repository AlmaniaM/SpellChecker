package edu.wou.cs260.SpellChecker;

import java.util.*;

/**
 * File Name: ${FILE_NAME}
 * Created by Almania
 * Western Oregon University
 * Class: CS260
 * Created: 1/28/2017
 * Assignment:
 */
public class TestDLListWithMain
{
    public static void main(String[] args)
    {
        DLList<String> sList = new DLList<>();

        sList.add("s");
        sList.add("a");
        sList.add("b");
        sList.add("j");
        sList.add("a");
        sList.add("m");
        sList.add("j");

        ListIterator<String> lItr = sList.listIterator();

        while(lItr.hasNext())
        {
            System.out.println(lItr.next());
            System.out.println("Has Next? " + lItr.hasNext());
            System.out.println("Next Index: " + lItr.nextIndex());
        }

        /*System.out.println();

        while(lItr.hasPrevious())
        {
            System.out.println("Has Previous? " + lItr.hasPrevious());
            System.out.println("Previous Index: " + lItr.previousIndex());
            System.out.println(lItr.previous());
        }*/

        /*Queue<Integer> intQue = new LinkedList<>();

        intQue.add(new Integer(2));
        intQue.add(3);
        intQue.add(9);
        intQue.add(20);
        intQue.add(33);
        intQue.remove(9);
        int getInt = intQue.peek();

        for(Integer i : intQue)
        {
            System.out.println(i);
        }

        intQue.remove();

        System.out.println();

        for(Integer i : intQue)
        {
            System.out.println(i);
        }


        //Testing DLList
        DLList<Integer> dllList = new DLList<>();

        dllList.add(new Integer(2));
        dllList.add(3);
        dllList.add(9);
        dllList.add(20);
        dllList.add(33);
        dllList.offer(229);
        dllList.add(3, 223);

        for(Integer i : dllList)
        {
            System.out.println(i);
        }

        dllList.remove();

        System.out.println();

        for(Integer i : dllList)
        {
            System.out.println(i);
        }*/
    }
}
