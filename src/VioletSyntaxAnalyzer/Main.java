/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package VioletSyntaxAnalyzer;

import java.util.ArrayList;

/**
 *
 * @author Violets
 */
public class Main {
    public static void main(String[] args) throws Exception{
        Terminal a=new Terminal("a");
        Terminal b=new Terminal("b");
        Terminal c=new Terminal("c");
        Terminal d=new Terminal("d");
        NonTerminal A=new NonTerminal("A");
        NonTerminal B=new NonTerminal("B");
        NonTerminal C=new NonTerminal("C");
        Term s1=new Term(a,b);
        Term s2=new Term(b,b,a);
        Term s3=new Term(c,b,B,C,b);
        Term s4=new Term(C);
        Term s6=new Term(d);
        Term s5=new Term();
        ProductionRule pr1=new ProductionRule(B,s1,s2,s4);
        ProductionRule pr2=new ProductionRule(A,s5,s2,s3);
        ProductionRule pr3=new ProductionRule(C,s5,s6);
        
//        Grammar Grammar=new Grammar(pr1,pr2,pr3);
        Grammar grammar=Utils.readGrammarFromFile("2.grammar");
        System.out.println(grammar);
        
        NonTerminal E=new NonTerminal("E");
        NonTerminal E2=new NonTerminal("E2");
        NonTerminal T=new NonTerminal("T");
        NonTerminal T2=new NonTerminal("T2");
        NonTerminal F=new NonTerminal("F");
        
        System.out.println(Utils.wayToLambdaExists(B,grammar));
        ArrayList<Alphabet> alpha=new ArrayList<>();
        System.out.println("First E: "+Utils.getFirst(E,grammar));
        System.out.println("First E': "+Utils.getFirst(E2,grammar));
        System.out.println("First T: "+Utils.getFirst(T,grammar));
        System.out.println("First T': "+Utils.getFirst(T2,grammar));
        System.out.println("First F: "+Utils.getFirst(F,grammar));
        System.out.println("");
        System.out.println("Follow E: "+Utils.getFollow(E,grammar));
        System.out.println("Follow E': "+Utils.getFollow(E2,grammar));
        System.out.println("Follow T: "+Utils.getFollow(T,grammar));
        System.out.println("Follow T': "+Utils.getFollow(T2,grammar));
        System.out.println("Follow F: "+Utils.getFollow(F,grammar));
        System.out.println("");
        
        System.out.println(grammar.nonTerminals);
        System.out.println(grammar.terminals);
        TableObject tableObject=Utils.getLLTable(grammar);
        for(int i=0;i<tableObject.table.length;i++){
            for(int j=0;j<tableObject.table[0].length;j++){
                System.out.print(tableObject.table[i][j]+"\t");
            }
            System.out.println("");
        }
        Utils.visualizeTableJFrame(tableObject.table).setVisible(true);
        Utils.parseString("id.+.id.*.id","\\.",grammar);
        Utils.parseString("id.+.id.*.id.+","\\.",grammar);
        
        System.out.println("");
        System.out.println("Item toString:");
        Item testItem=new Item(E,E2,T,T2,F);
        System.out.println(testItem);
        System.out.println(testItem.getNextAlphabet());
        System.out.println(testItem.getNextAlphabet());
        System.out.println(testItem.getNextAlphabet());
        System.out.println(testItem);
        System.out.println("");
        
        System.out.println(grammar.rules.get(4));
        Utils.closureizeRules(grammar.rules.get(4));
        
        Closure closure=new Closure(grammar,grammar.rules.get(0));
        System.out.println(closure);
    }
}
