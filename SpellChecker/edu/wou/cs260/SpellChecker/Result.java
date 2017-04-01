/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wou.cs260.SpellChecker;

import javafx.beans.property.*;

/**
 * Class Name: Result.java
 *
 * Description: Result stores the results from testing Abstract Data Type structures.
 * @author Almania
 */
public class Result
{
    private StringProperty name;
    private StringProperty dict;
    private StringProperty tFile;
    private LongProperty twc;
    private LongProperty tcw;
    private LongProperty tiw;
    private LongProperty acpw;
    private LongProperty rfc;

    public Result()
    {
        name = new SimpleStringProperty();
        dict = new SimpleStringProperty();
        tFile = new SimpleStringProperty();
        twc = new SimpleLongProperty();
        tcw = new SimpleLongProperty();
        tiw = new SimpleLongProperty();
        acpw = new SimpleLongProperty();
        rfc = new SimpleLongProperty();
    }


    public StringProperty getNameProperty()
    {
        return name;
    }

    public String getName()
    {
        return name.get();
    }

    public String getDict()
    {
        return dict.get();
    }

    public StringProperty dictProperty()
    {
        return dict;
    }

    public String gettFile()
    {
        return tFile.get();
    }

    public StringProperty tFileProperty()
    {
        return tFile;
    }

    public Long getTwc()
    {
        return twc.get();
    }

    public Long getTcw()
    {
        return tcw.get();
    }

    public Long getTiw()
    {
        return tiw.get();
    }

    public Long getAcpw()
    {
        return acpw.get();
    }

    public Long getRfc()
    {
        return rfc.get();
    }

    public void setDict(String dict)
    {
        this.dict.set(dict);
    }

    public void settFile(String tFile)
    {
        this.tFile.set(tFile);
    }

    public void setName(String name)
    {
        this.name.set(name);
    }

    public void setTwc(LongProperty twc)
    {
        this.twc = twc;
    }

    public void setTcw(LongProperty tcw)
    {
        this.tcw = tcw;
    }

    public void setTiw(LongProperty tiw)
    {
        this.tiw = tiw;
    }

    public void setAcpw(LongProperty acpw)
    {
        this.acpw = acpw;
    }

    public void setRfc(LongProperty rfc)
    {
        this.rfc = rfc;
    }
}
