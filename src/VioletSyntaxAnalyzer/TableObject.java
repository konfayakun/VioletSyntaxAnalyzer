/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package VioletSyntaxAnalyzer;

import java.util.HashMap;
import javax.swing.JFrame;

/**
 *
 * @author Violets
 */
public class TableObject {
    Object[][] table;
    HashMap<Alphabet,Integer> nonTerminalIndexes,terminalIndexes;

    public TableObject(Object[][] table,HashMap<Alphabet,Integer> nonTerminalIndexes,HashMap<Alphabet,Integer> terminalIndexes){
        this.table=table;
        this.nonTerminalIndexes=nonTerminalIndexes;
        this.terminalIndexes=terminalIndexes;
    }
    
}
