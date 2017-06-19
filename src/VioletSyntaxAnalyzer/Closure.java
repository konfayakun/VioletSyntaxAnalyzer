/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package VioletSyntaxAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Violets
 */
public class Closure {
    ArrayList<ProductionRule> rules;

    public Closure(Grammar grammar,ProductionRule... rules){
        this.rules=new ArrayList<>();
        Set<NonTerminal> additionalNonTerminals=new HashSet<>();
        Set<NonTerminal> forbiden=new HashSet<>();
        for(ProductionRule rule:rules){
            this.rules.addAll(Utils.closureizeRule(rule,additionalNonTerminals,forbiden));
        }
        while(!additionalNonTerminals.isEmpty()){
            for(NonTerminal nonTerminal:additionalNonTerminals){
                forbiden.add(nonTerminal);
                ProductionRule rule= grammar.getRuleByProducer(nonTerminal);
                for(ProductionRule closurizedRule: Utils.closureizeRule(rule,additionalNonTerminals,forbiden)){
                    if(!this.rules.contains(closurizedRule)){
                        this.rules.add(closurizedRule);
                    }
                }
                additionalNonTerminals.remove(nonTerminal);
            }
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

    @Override
    public String toString(){
        String str="====================\n";
        for(ProductionRule rule:rules){
            str+=rule.toString()+"\n";
        }
        return str;
    }
    
    
    
    
    
}
