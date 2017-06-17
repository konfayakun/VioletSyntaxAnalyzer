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
public class Term {
    ArrayList<Alphabet> alphas;
    
    
    public Term(Alphabet... atoms){
        alphas=new ArrayList<>();
        alphas.addAll(Arrays.asList(atoms));
        
    }

    public Term(ArrayList<Alphabet> alphas){
        this.alphas=alphas;
        
    }
    boolean isLambda(){
        return alphas.isEmpty();
    }
    boolean containsAlphabet(Alphabet alpha){
        return alphas.stream().anyMatch((alph) -> (alph.equals(alpha)));
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Term))return false;
        Term term=(Term)obj;
        if(term.alphas.size() != alphas.size()) return false;
        for(int i=0;i<alphas.size();i++){
            if(!alphas.get(i).equals(term.alphas.get(i))) return false;
        }
        return true;
    }

    @Override
    public int hashCode(){
        int hash=7;
        hash=79*hash+Objects.hashCode(this.alphas);
        return hash;
    }
        
    

    @Override
    public String toString(){
        String str="";
        str=alphas.stream().map((alpha) -> ""+alpha).reduce(str,String::concat);
        if("".equals(str)) return "ε";
        return str;
    }
    
    
}
