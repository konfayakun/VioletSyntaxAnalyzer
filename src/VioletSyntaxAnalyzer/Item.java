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
public class Item extends Term{
    int location;
    public Item(Alphabet... atoms){
        super(atoms);
        location=0;
    }

    public Item(ArrayList<Alphabet> alphas){
        super(alphas);
        location=0;
    }
    public Alphabet getNextAlphabet() throws Exception{
        if(location>=alphas.size()) throw new Exception("Item is Finished");
        location++;
        return alphas.get(location-1);
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Item)) return false;
        return super.equals(obj) && ((Item)obj).location==location;
    }

    @Override
    public int hashCode(){
        int hash=7;
        hash=71*hash+this.location+super.hashCode();
        return hash;
    }
    
}
