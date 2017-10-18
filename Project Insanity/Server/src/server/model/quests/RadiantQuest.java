/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.quests;

import java.util.ArrayList;
import server.model.items.Item;
import server.model.players.Client;

/**
 *
 * @author amaco
 */
public class RadiantQuest {
    public int id;
    public String name, description;
    public ArrayList<RadiantQuestReward> itemRewards;
    public int[] skillLevelRequirements;
    public int combatLevelRequirement;
    public int[] expRewards;
    public ArrayList<RadiantQuestObjective> objectives;
    boolean finished = false;
    
    public boolean objectivesFinished(){
        for(RadiantQuestObjective obj : objectives){
            if (obj.GetStatus() != RadiantQuestObjectives.Status.Finished){
                return false;
            }
        }
        return true;
    }
    
    public RadiantQuestObjective returnCurrentObjective(){
        for(RadiantQuestObjective obj : objectives){
            if (obj.GetStatus() != RadiantQuestObjectives.Status.Finished){
                return obj;
            }
        }
        return null;
    }
    
    public void FinishQuest(Client c){
        if (!finished) {
            for (RadiantQuestReward reward : itemRewards) {
                c.getItems().addItem(reward.itemId, reward.amount);
            }
            for (int i = 0; i < expRewards.length; i++) {
                c.getPA().addSkillXP(i, expRewards[i]);
            }
            finished = true;
        }
    }
    
    public boolean isFinished(){
        return finished;
    }
}
