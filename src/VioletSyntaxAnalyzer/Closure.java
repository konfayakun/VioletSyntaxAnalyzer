/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package VioletSyntaxAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author Violets
 */
public class Closure {
    ArrayList<ProductionRule> rules;

    public Closure(Grammar Grammar,boolean ready,ProductionRule... rules){
        this.rules=new ArrayList<>();
        this.rules.addAll(Arrays.asList(rules));
        if(ready) return;
        for(ProductionRule rule:rules){
            
        }
    }
    
    public Closure(ArrayList<ProductionRule> rules){
        this.rules=rules;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Closure))return false;
        Closure closure=(Closure)obj;
        if(closure.rules.size()!=rules.size())return false;
        for(int i=0;i<rules.size();i++){
            if(!rules.get(i).equals(closure.rules.get(i)))return false;
        }
        return true;
    }

    @Override
    public int hashCode(){
        int hash=5;
        hash=29*hash+Objects.hashCode(this.rules);
        return hash;
    }
    
    
    
}
