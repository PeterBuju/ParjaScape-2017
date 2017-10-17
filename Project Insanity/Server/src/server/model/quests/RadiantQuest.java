/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.quests;

import java.util.ArrayList;
import server.model.items.Item;

/**
 *
 * @author amaco
 */
public class RadiantQuest {
    public int id;
    public String name, description;
    public ArrayList<Item> itemRewards;
    public int expReward;
    public ArrayList<RadiantQuestObjective> objectives;
    
    public void Start(){
        
    }
    
    public void OnObjectiveFinished(){
        
    }
    
    public void Finish(){
        
    }
}
