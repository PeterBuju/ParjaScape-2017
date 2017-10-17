/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.quests;

import server.model.players.Client;
import server.world.Tile;

/**
 *
 * @author amaco
 */
public class RadiantQuestObjective {
    public String description;
    public RadiantQuestObjectives.Objectives objectiveType;
    public int targetNPCId;
    public int objectiveInt;
    public Tile location;
    public int locationAreaSize;
    public int score;

    public RadiantQuestObjectives.Status GetStatus() {
        if (score == 0) {
            return RadiantQuestObjectives.Status.Started;
        } else if (score > 0 && score < objectiveInt) {
            return RadiantQuestObjectives.Status.InProgress;
        } else if (score == objectiveInt) {
            return RadiantQuestObjectives.Status.Finished;
        }
        return RadiantQuestObjectives.Status.NotStarted;
    }
    
    public void AddScore(Client c){
        if(location != null){
            if (c.absX > location.getTileX() - locationAreaSize/2 && c.absX < location.getTileX() + locationAreaSize/2 &&
                c.absY > location.getTileY() - locationAreaSize/2 && c.absY < location.getTileY() + locationAreaSize/2){
                
                score++;
            }
        }
        else{
            score++;
        }
    }
}
