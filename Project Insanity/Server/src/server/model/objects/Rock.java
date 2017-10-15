/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.objects;

import server.model.players.Client;
import server.util.Misc;
import server.world.RockManager;

/**
 *
 * @author Alex Macocian
 */
public class Rock extends BaseObject{
    
    public Rock(Client c){
        super(c);
        if (initobjId == 2092 || initobjId == 2093)
            SetMaxCharges(5);
        else
            SetMaxCharges(1);
        SetCharges();
    }
    
    public boolean MineRock(){
        SetLastClickedTimer();
        if (charges > 1){
            charges--;
            return true;
        }
        charges--;
        RockManager.DepleteRock(this);
        return false;
    }
}
