/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package VioletSyntaxAnalyzer;

import java.util.Objects;

/**
 *
 * @author Violets
 */
public abstract class Alphabet extends Object{
    
    String name;
    
    
    @Override
    public String toString(){
        if(isLambda())
            return "ε";
        return name;
    }
    
    boolean isLambda(){
        return "".equals(name);
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Alphabet))return false;
        return name.equals(((Alphabet)obj).name);
    }

    @Override
    public int hashCode(){
        int hash=7;
        hash=89*hash+Objects.hashCode(this.name);
        return hash;
    }
    
}
