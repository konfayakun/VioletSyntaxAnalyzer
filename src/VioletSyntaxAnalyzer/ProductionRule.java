/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package VioletSyntaxAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;

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
        
    }
    
    
    
}
