/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package VioletSyntaxAnalyzer;

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
        Grammar Grammar=Utils.readGrammarFromFile("2.Grammar");
        System.out.println(Grammar);
        
        NonTerminal E=new NonTerminal("E");
        NonTerminal E2=new NonTerminal("E2");
        NonTerminal T=new NonTerminal("T");
        NonTerminal T2=new NonTerminal("T2");
        NonTerminal F=new NonTerminal("F");
        
        System.out.println(Utils.wayToLambdaExists(B,Grammar));
        
        System.out.println("First E: "+Utils.getFirst(E,Grammar));
        System.out.println("First E': "+Utils.getFirst(E2,Grammar));
        System.out.println("First T: "+Utils.getFirst(T,Grammar));
        System.out.println("First T': "+Utils.getFirst(T2,Grammar));
        System.out.println("First F: "+Utils.getFirst(F,Grammar));
        System.out.println("");
        System.out.println("Follow E: "+Utils.getFollow(E,Grammar));
        System.out.println("Follow E': "+Utils.getFollow(E2,Grammar));
        System.out.println("Follow T: "+Utils.getFollow(T,Grammar));
        System.out.println("Follow T': "+Utils.getFollow(T2,Grammar));
        System.out.println("Follow F: "+Utils.getFollow(F,Grammar));
        System.out.println("");
        
        System.out.println(Grammar.nonTerminals);
        System.out.println(Grammar.terminals);
        TableObject tableObject=Utils.getLLTable(Grammar);
        for(int i=0;i<tableObject.table.length;i++){
            for(int j=0;j<tableObject.table[0].length;j++){
                System.out.print(tableObject.table[i][j]+"\t");
            }
            System.out.println("");
        }
        Utils.visualizeTableJFrame(tableObject.table).setVisible(true);
        Utils.parseString("id.+.id.*.id","\\.",Grammar);
        Utils.parseString("id.+.id.*.id.+","\\.",Grammar);
        
    }
}
