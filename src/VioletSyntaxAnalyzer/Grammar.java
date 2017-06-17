/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package VioletSyntaxAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 *
 * @author Violets
 */
public class Grammar {
    ArrayList<ProductionRule> rules;
    HashSet<Terminal> terminals;
    HashSet<NonTerminal> nonTerminals;
    
    public Grammar(ProductionRule... rules){
        this.rules=new ArrayList<>();
        this.rules.addAll(Arrays.asList(rules));
        terminals=new HashSet<>();
        nonTerminals=new HashSet<>();
        defineGrammarAlphabet();
    }
    void addProductionRule(ProductionRule rule){
        rules.add(rule);
        nonTerminals.add(rule.producer);
        for(Term term: rule.products){
            for(Alphabet alphabet:term.alphas){
                if(alphabet instanceof Terminal) terminals.add((Terminal)alphabet);
            }
        }
        
    }
    ProductionRule getRuleByProducer(NonTerminal producer){
        for( ProductionRule rule : rules)
            if(producer.equals(rule.producer)) return rule;
        return null;
    }
    
    private void defineGrammarAlphabet(){
        for(ProductionRule rule:rules){
            nonTerminals.add(rule.producer);
        }
        for(ProductionRule rule: rules){
            for(Term term: rule.products){
                for(Alphabet alphabet:term.alphas){
                    if(alphabet instanceof Terminal) terminals.add((Terminal)alphabet);
                }
            }
        }
    }
    public NonTerminal getFirstNonTerminal(){
        return rules.get(0).producer;
    }

    @Override
    public String toString(){
        String str="";
        str=rules.stream().map((rule) -> rule+"\n").reduce(str,String::concat);
        return str;
    }
    
}
