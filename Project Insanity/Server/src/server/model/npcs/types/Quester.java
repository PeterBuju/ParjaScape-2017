/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.npcs.types;

import java.util.ArrayList;
import server.model.quests.RadiantQuest;

/**
 *
 * @author amaco
 */
public class Quester extends Greeter{
    public ArrayList<RadiantQuest> quests;
    
    @Override
    public void process(){
        super.process();
    }
}
