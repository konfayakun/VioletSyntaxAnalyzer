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
public class Terminal extends Alphabet{
    final static Terminal LAMBDA=new Terminal("");
    public Terminal(String name){
        this.name=name.toLowerCase();
    }
    
}
