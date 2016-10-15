package com.jkys.moye;

/**
 * Created by frio on 16/10/15.
 */
public class Word {
    protected String name;

    public Word(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
