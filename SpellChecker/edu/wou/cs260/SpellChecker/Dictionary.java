package edu.wou.cs260.SpellChecker;

//===================================================================

/**
 * @author Mitchel Fry
 * @version 1.1
 */
public class Dictionary implements CompareCount
{
    public static final int DL_LIST = 0;
    public static final int BST_LIST = 1;
    public static final int AVL_LIST = 2;
    public static final int OCHS = 3;

    private boolean isGui = false;

    //Must change the dictType to one of the constants
    // above if you want to test a different container
    private int dictType = OCHS;
    private int hashSize = 150000;

    // instance variables
    //You will replace "TestList with YOUR container ADT
    //private OpenChainHashSet<String> dictWords;
    private Object dictWords;

    private static final boolean DEBUG = true;

    // ======================== Constructors =========================

    /**
     * The dictionary for a simple spell checking program
     */
    public Dictionary()
    {
        try
        {
            //Again, replace TestList with YOUR container ADT
            dictWords = new OpenChainHashSet<String>(hashSize);
        }
        catch(Exception e)
        {
            System.err.println("Exception in creating Dictionary");
        }
    }
    /**
     * The dictionary for a simple spell checking program
     */
    public Dictionary(int dictType, int hashSize)
    {
        isGui = true;

        try
        {
            //Again, replace TestList with YOUR container ADT
            setDictionaryADT(dictType, hashSize);
        }
        catch(Exception e)
        {
            System.err.println("Exception in creating Dictionary");
        }
    }

    public void setDictADT(int dictType)
    {
        this.setDictionaryADT(dictType, this.hashSize);
    }

    public void setDictADT(int dictType, int hashSize)
    {
        this.setDictionaryADT(dictType, hashSize);
    }

    public void setHashSize(int hashSize)
    {
        this.hashSize = hashSize;
    }

    /**
     * Accessor Method for lastCompareCount
     *
     * @return lastCompareCount
     */
    @Override
    public int getLastCompareCount()
    {
        // Just a wrapper for the interface
        //return dictWords.getLastCompareCount();
        return this.getLastCompareCount(this.dictType);
    }

    /**
     * Adds a word to the dictionary container
     *
     * @param newWord The word to add to the dictionary
     */
    public void addWord(String newWord)
    {
        try
        {
            //dictWords.add((newWord));
            this.addWord(this.dictType, newWord);
            if(DEBUG && !isGui)
            {
                System.err.println("Word added to dictionary: " + newWord);
            }
        }
        catch(Exception e)
        {
            System.err.println("Exception in adding string to dictWords");
        }
    }

    /**
     * Checks to see if the word exists in the container
     *
     * @param sWord The word to check
     * @return Returns a true/false if word was found
     */
    public boolean checkWord(String sWord)
    {
        // check for found or end of dictionary
        //boolean correct = dictWords.contains(sWord.toLowerCase());
        //boolean correct = ((OpenChainHashSet<String >) dictWords).contains(sWord.toLowerCase());
        return this.containsWord(this.dictType, sWord);
    }

    private boolean containsWord(int dictType, String cWord)
    {
        switch(dictType)
        {
            case DL_LIST:
                return ((DLList<String>) this.dictWords).contains(cWord);
            case BST_LIST:
                return ((BSTreeSet<String>) this.dictWords).contains(cWord);
            case AVL_LIST:
                return ((AVLTreeSet<String>) this.dictWords).contains(cWord);
            case OCHS:
                return ((OpenChainHashSet<String>) this.dictWords).contains(cWord);
        }
        return false;
    }

    private void addWord(int dictType, String adWord)
    {
        switch(dictType)
        {
            case DL_LIST:
                ((DLList<String>) this.dictWords).add(adWord);
                break;
            case BST_LIST:
                ((BSTreeSet<String>) this.dictWords).add(adWord);
                break;
            case AVL_LIST:
                ((AVLTreeSet<String>) this.dictWords).add(adWord);
                break;
            case OCHS:
                ((OpenChainHashSet<String>) this.dictWords).add(adWord);
                break;
        }
    }

    private void setDictionaryADT(int dictType, int hashSize)
    {
        this.dictType = dictType;

        switch(dictType)
        {
            case DL_LIST:
                dictWords = new DLList<String>();
                break;
            case BST_LIST:
                dictWords = new BSTreeSet<String>();
                break;
            case AVL_LIST:
                dictWords = new AVLTreeSet<String>();
                break;
            case OCHS:
                dictWords = new OpenChainHashSet<String>(hashSize);
                break;
        }
    }

    private int getLastCompareCount(int dictType)
    {
        switch(dictType)
        {
            case DL_LIST:
                return ((DLList<String>) this.dictWords).getLastCompareCount();
            case BST_LIST:
                return ((BSTreeSet<String>) this.dictWords).getLastCompareCount();
            case AVL_LIST:
                return ((AVLTreeSet<String>) this.dictWords).getLastCompareCount();
            case OCHS:
                return ((OpenChainHashSet<String>) this.dictWords).getLastCompareCount();
        }
        return 0;
    }
}
