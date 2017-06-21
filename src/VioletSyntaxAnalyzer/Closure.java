/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package VioletSyntaxAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.Collections;
import java.util.HashMap;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Violets
 */
public class Closure {
    ArrayList<ProductionRule> rules;
    HashMap<Alphabet,Closure> relations=new HashMap<>();

    public Closure(Grammar grammar,ProductionRule... rules){
        this.rules=new ArrayList<>();
        Set<NonTerminal> additionalNonTerminals=new HashSet<>();
        Set<NonTerminal> forbiden=new HashSet<>();
        for(ProductionRule rule:rules){
            this.rules.addAll(Utils.closureizeRule(rule,additionalNonTerminals,forbiden));
        }

        int i=0;
        while(!additionalNonTerminals.isEmpty()){
            Set<NonTerminal> buffer=new HashSet<>();
            Set<NonTerminal> toRemove=new HashSet<>();
            for(NonTerminal nonTerminal:additionalNonTerminals){
                forbiden.add(nonTerminal);
                ProductionRule rule= grammar.getRuleByProducer(nonTerminal);
                for(ProductionRule closurizedRule: Utils.closureizeRule(rule,buffer,forbiden)){

                    if(!this.rules.contains(closurizedRule)){
                        this.rules.add(closurizedRule);
                    }
                }

                i++;
                toRemove.add(nonTerminal);
//                if(i>=additionalNonTerminals.size())return;
            }
            additionalNonTerminals.addAll(buffer);
            additionalNonTerminals.removeAll(toRemove);

        }
    }
    
    public Closure(ArrayList<ProductionRule> rules){
        this.rules=rules;
    }
    
    Set<Alphabet> getPossibleActions(){
        
        Set<Alphabet> possible=new HashSet<>();
        for(ProductionRule rule: rules){
            for(Term term:rule.products){
                try{
                    possible.add(((Item)term).getCurrentAlphabet());
                    
                } catch(Exception e){
                }
            }
        }
        return possible;
    }

    ArrayList<Closure> nextGeneration(Grammar grammer){
        ArrayList<Closure> nextgener=new ArrayList<>();
        Set<ProductionRule> newClosureRules=new HashSet<>();
        Set<Alphabet> possibleActions=getPossibleActions();
        if(possibleActions.isEmpty())return nextgener;
        for(Alphabet possibleAction: possibleActions){
            for(ProductionRule rule:rules){
                try{
                    for(Term term: rule.products){
                        Item item=(Item) ((Item)term).clone();
                        if(item.getCurrentAlphabet().equals(possibleAction)){
                            item.getNextAlphabet();
                            newClosureRules.add(new ProductionRule(rule.producer,item));
                        }
                    }
                }catch(Exception e){}
            }
            ProductionRule newRules[]=newClosureRules.toArray(new ProductionRule[0]);
            newClosureRules=new HashSet<>();
//            System.out.println(possibleAction+" "+newRules.length);
//            for(ProductionRule rul:newRules){
//                System.out.println(rul);
//            }
//            System.out.println("");
            Closure next=new Closure(grammer,newRules);
//            System.out.println(next);
            relations.put(possibleAction,next);
            nextgener.add(next);
        }
        return nextgener;
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

        return str+"-------------------------------\n";

    }
    
    
    
    
    
}
