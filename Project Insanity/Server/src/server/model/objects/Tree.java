/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.objects;

import server.model.players.Client;
import server.util.Misc;
import server.world.RockManager;
import server.world.TreeManager;

/**
 *
 * @author Alex Macocian
 */
public class Tree extends BaseObject{
    
    public Tree(Client c){
        super(c);
        SetMaxCharges(1);
        SetCharges();
    }
    
    public boolean CutTree(){
        SetLastClickedTimer();
        if (charges > 1){
            charges--;
            return true;
        }
        charges--;
        TreeManager.CutTree(this);
        return false;
    }
}
