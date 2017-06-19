/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package VioletSyntaxAnalyzer;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicBorders;

/**
 *
 * @author Violets
 */
public class Utils {
    public static boolean allNonTerminal(Term term){
        return term.alphas.stream().noneMatch((alpha) -> (alpha instanceof Terminal));
    }
    public static boolean wayToLambdaExists(NonTerminal start,Grammar Grammar){
        boolean exists=true;
        ProductionRule rule=Grammar.getRuleByProducer(start);
        if(rule==null){
            System.out.println("NotFound!");
            return false;
        }
        if(rule.hasLambda) return true;
        for(Term term: rule.products){
            if(!allNonTerminal(term) || term.containsAlphabet(start)) continue;
            for(Alphabet alpha: term.alphas){
                exists&=wayToLambdaExists((NonTerminal)alpha,Grammar);
            }
            if(exists)return true;
        }
        return false;
    }
    
    public static Set<Alphabet> getFirst(Alphabet alphabet,Grammar Grammar){
        Set<Alphabet> first=new HashSet<>();
        if(alphabet instanceof Terminal){
            first.add(alphabet);
            return first;
        }
        ProductionRule rule=Grammar.getRuleByProducer((NonTerminal)alphabet);
        if(rule==null){
//            System.out.println("Not Found!!*****************************(in First founder)");
            return null;
        }
        for(Term term: rule.products){
            if(term.isLambda()){
                first.add(new Terminal(""));
                continue;
            }
            for(Alphabet alp:term.alphas){
                if(alp instanceof Terminal || !wayToLambdaExists((NonTerminal)alp,Grammar)){
                    first.addAll(getFirst(alp,Grammar));
                    break;
                }
                first.addAll(getFirst((NonTerminal)alp,Grammar));
            }
        }
        return first;
    }
    
    
    public static Set<Alphabet> getFollow(Alphabet alphabet,Grammar Grammar){
        Set<Alphabet> follow=new HashSet<>();
        if(alphabet.equals(Grammar.rules.get(0).producer)) follow.add(new Terminal("$"));
        for(ProductionRule rule: Grammar.rules){
            for(Term term : rule.products){
                for(int i=0;i<term.alphas.size();i++){
                    if(alphabet.equals(term.alphas.get(i))){
                        int j=i+1;
                        try{
                            while(term.alphas.get(j) instanceof NonTerminal && wayToLambdaExists((NonTerminal)term.alphas.get(j),Grammar)){
                                for(Alphabet alph: getFirst(term.alphas.get(j),Grammar)){
                                    if(!alph.isLambda())follow.add(alph);
                                }
                                j++;
                            }
                            for(Alphabet alph: getFirst(term.alphas.get(j),Grammar)){
                                    if(!alph.isLambda())follow.add(alph);
                                }
                        }catch(Exception e){
                            if(!rule.producer.equals(alphabet))follow.addAll(getFollow(rule.producer,Grammar));
                        }
                    }
                }
            }
        }
        return follow;
    }
    
    public static TableObject getLLTable(Grammar Grammar){
        Object table[][]=new Object[Grammar.nonTerminals.size()+1][Grammar.terminals.size()+2];
        table[0][0]="X";
        HashMap<Alphabet,Integer> nonTerminalIndexes=new HashMap<>();
        HashMap<Alphabet,Integer> terminalIndexes=new HashMap<>();
        int i=1;
        for(NonTerminal nonTerminal: Grammar.nonTerminals){
            table[i][0]=nonTerminal;
            nonTerminalIndexes.put(nonTerminal,i);
            i++;
        }
        i=1;
        for(Terminal terminal:Grammar.terminals){
            table[0][i]=terminal;
            terminalIndexes.put(terminal,i);
            i++;
        }
        table[0][i]=new Terminal("$");
        terminalIndexes.put(new Terminal("$"),i);
        
        for(ProductionRule rule:Grammar.rules){
            for(Term term:rule.products){
                ProductionRule buffer=new ProductionRule(rule.producer,term);
                if(!term.isLambda()){
                    Set<Alphabet> first=getFirst(term.alphas.get(0),Grammar);
                    for(Alphabet alpha:first){
                        table[nonTerminalIndexes.get(rule.producer)][terminalIndexes.get(alpha)]=buffer;
                    }
                }
                
            }
            if(wayToLambdaExists(rule.producer,Grammar)){
                Set<Alphabet> follow=getFollow(rule.producer,Grammar);
                ProductionRule buffer=new ProductionRule(rule.producer,new Term());
                for(Alphabet alphabet:follow){
                    table[nonTerminalIndexes.get(rule.producer)][terminalIndexes.get(alphabet)]=buffer;
                }
            }
        }
        
        return new TableObject(table,nonTerminalIndexes,terminalIndexes);
    }
    
    public static ArrayList<ProductionRule> closureizeRules(ProductionRule rule){
        ArrayList<ProductionRule> rules=new ArrayList<>();
        for(Term term:rule.products){
            ProductionRule newRule=new ProductionRule(rule.producer,new Item(term.alphas));
            rules.add(newRule);
        }
        System.out.println(rules);
        return rules;
    }
    
    public static ArrayList<ProductionRule> closureizeRule(ProductionRule rule,Set<NonTerminal> additionalNonTerminals,Set<NonTerminal> forbidden){
        ArrayList<ProductionRule> rules=new ArrayList<>();
        for(Term term:rule.products){
            if(term instanceof Item){
                if(!term.isLambda() && ((Item)term).getCurrentAlphabet() instanceof NonTerminal && !forbidden.contains(((Item)term).getCurrentAlphabet())){
                    additionalNonTerminals.add((NonTerminal)((Item)term).getCurrentAlphabet());
                }
                continue;
            }
            if(!term.isLambda() && term.alphas.get(0) instanceof NonTerminal && ! forbidden.contains(term.alphas.get(0))){
                additionalNonTerminals.add((NonTerminal)term.alphas.get(0));
            }
            ProductionRule newRule=new ProductionRule(rule.producer,new Item(term.alphas));
            rules.add(newRule);
        }
        System.out.println(rules);
        return rules;
    }
    
    public static void parseString(String inputString,String seprator,Grammar Grammar){
        System.out.println("Start parsing process (processing \""+inputString+"\") :");
        TableObject tableObject=getLLTable(Grammar);
        Stack<Alphabet> stack=new Stack<>();
        stack.push(new Terminal("$"));
        stack.push(Grammar.getFirstNonTerminal());
        Queue<Alphabet> input=new LinkedList<>();
        for(String str:inputString.split(seprator)){
            if(str.matches("[A-Z]+[0-9]*"))
                input.add(new NonTerminal(str));
            else
                input.add(new Terminal(str));
        }
            input.add(new Terminal("$"));
        while(true){
//            System.out.println(stack);
//            System.out.println(input);
            Alphabet stackTop=stack.pop();
            Alphabet lookAhed=input.element();
            if(stackTop instanceof Terminal){
                if(lookAhed instanceof Terminal){
                    if(lookAhed.equals(new Terminal("$"))){
                        System.out.println("Finished!");
                        return;
                    }
                    input.remove();
                    continue;
                }
                else{
                    System.out.println("Error");
                    return;
                }
            }
            Object buffer=tableObject.table[tableObject.nonTerminalIndexes.get(stackTop)][tableObject.terminalIndexes.get(lookAhed)];
            if(buffer==null){
                System.out.println("Error!!");
                return;
            }
            ProductionRule rule=(ProductionRule)buffer;
            System.out.println(rule);
            for(int i=rule.products.get(0).alphas.size()-1;i>=0;i--){
                stack.push(rule.products.get(0).alphas.get(i));
            }
            
        }
    }
    
    
    public static JFrame visualizeTableJFrame(Object[][] table){
        JFrame frame=new JFrame("Table");
        frame.setPreferredSize(new Dimension(500,500));
        JPanel panel=new JPanel();
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        
        panel.setLayout(new GridLayout(table.length,table[0].length,2,2));
        for(int i=0;i<table.length;i++){
            for(int j=0;j<table[0].length;j++){
                String str=(table[i][j]==null)?"Error":table[i][j].toString();
                JLabel lbl=new JLabel(str);
                if(i==0 || j==0)lbl.setFont(new Font(Font.SANS_SERIF,Font.BOLD,24));
                lbl.setBorder(BasicBorders.getTextFieldBorder());
                lbl.setVerticalAlignment(JLabel.CENTER);
                lbl.setHorizontalAlignment(JLabel.CENTER);
                panel.add(lbl);
                
            }
        }
        
        return frame;
    }
    
    
    public static Grammar readGrammarFromFile(String address,String... seprators) throws Exception{
        Grammar Grammar=new Grammar();
        if(seprators.length>3) throw new Exception("Invalid arguments!");
        String producerSeprator=":",productsSeprator=",",alphabetSeprator="\\.";
        try{
            producerSeprator=seprators[0];
            productsSeprator=seprators[1];
            alphabetSeprator=seprators[2];
        } catch(Exception e){
        }
        ArrayList<String> fileLines=(ArrayList<String>) Files.readAllLines(Paths.get(address));
        for(String line:fileLines){
            String[] firstShrink=line.split(producerSeprator);
            NonTerminal producer=new NonTerminal(firstShrink[0]);
            ProductionRule rule=new ProductionRule(producer);
            String[] secondShrink=firstShrink[1].split(productsSeprator);
            if(firstShrink[1].startsWith(productsSeprator)|| firstShrink[1].endsWith(productsSeprator))
                rule.addTrem(new Term());
            for(String termString:secondShrink){
                if("".equals(termString)){
                    rule.addTrem(new Term());
                    continue;
                }
                ArrayList<Alphabet> termAlphas=new ArrayList<>();
                String[] thirdShrink=termString.split(alphabetSeprator);
                for(String alpha:thirdShrink){
                    Alphabet newAlpha;
                    if(alpha.matches("[A-Z]+[0-9]*"))   
                       newAlpha=new NonTerminal(alpha);
                    else
                        newAlpha=new Terminal(alpha);
                    termAlphas.add(newAlpha);
                }
                Term newTerm=new Term(termAlphas);
                rule.addTrem(newTerm);
            }
            Grammar.addProductionRule(rule);
        }
        return Grammar;
    }
}
