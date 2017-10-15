/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.players;

/**
 *
 * @author Alex Macocian
 */
public class Recipe {
    public int preCookedId;
    public int succesfulCookedId;
    public int failedCookedId;
    public int gainExperience;
    public int requiredLevel;
    public int stopBurnLevel;
    
    public Recipe(int preId, int succesfulId, int failedId, int stopBurnLvl, int lvl, int exp){
        preCookedId = preId;
        succesfulCookedId = succesfulId;
        failedCookedId = failedId;
        gainExperience = exp;
        requiredLevel = lvl;
        stopBurnLevel = stopBurnLvl;
    }
}
