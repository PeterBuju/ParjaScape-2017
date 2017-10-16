/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.npcs.types;

import server.world.Path;
import java.util.ArrayList;
import server.model.factions.Faction;

/**
 *
 * @author amaco
 */
public class Patroller extends BaseNPC{
    public Faction faction;
    public ArrayList<Path> path;
    
    @Override
    public void process(){
        super.process();
    }
}
