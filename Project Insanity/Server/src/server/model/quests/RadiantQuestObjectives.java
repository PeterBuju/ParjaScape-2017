/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.quests;

/**
 *
 * @author amaco
 */
public class RadiantQuestObjectives {
    public enum Objectives{
        KillNPC,
        TalkToNPC,
        GiveItemToNPC,
        GetItem
    }
    
    public enum Status{
        NotStarted,
        Started,
        InProgress,
        Finished
    }
}
