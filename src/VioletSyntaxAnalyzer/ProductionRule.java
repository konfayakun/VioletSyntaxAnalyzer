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
public class ProductionRule {
    NonTerminal producer;
    ArrayList<Term> products;
    boolean hasLambda;

    public ProductionRule(NonTerminal producer,ArrayList<Term> porducts){
        this.producer=producer;
        this.products=porducts;
        for( Term term: products)
            if(term.isLambda()) hasLambda=true;
    }
    
    public ProductionRule(NonTerminal producer,Term... porducts){
        this.producer=producer;
        products=new ArrayList<>();
        products.addAll(Arrays.asList(porducts));
        for( Term term: products)
            if(term.isLambda()) hasLambda=true;
    }
    
    public void addTrem(Term term){
        if(term.isLambda()) hasLambda=true;
        products.add(term);
    }

    @Override
    public String toString(){
        String str=producer+"";
        str+=" → ";
        str=products.stream().map((product) -> product+"|").reduce(str,String::concat);
        return str.substring(0,str.length()-1);
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof ProductionRule))return false;
        ProductionRule rule=(ProductionRule)obj;
        if(!producer.equals(rule.producer)||rule.products.size()!=products.size())return false;
        for(int i=0;i<products.size();i++){
            if(!products.get(i).equals(rule.products.get(i))) return false;
        }
        return true;
    }

    @Override
    public int hashCode(){
        int hash=7;
        hash=83*hash+Objects.hashCode(this.producer);
        hash+=23*hash+Objects.hashCode(this.products);
        return hash;
    }
    
    
    
}
