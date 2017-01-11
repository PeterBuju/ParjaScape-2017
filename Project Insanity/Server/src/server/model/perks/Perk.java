/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.perks;
import server.model.players.Stats;
/**
 *
 * @author Alex Macocian
 */
public class Perk {
    
    private String name, description;
    private int id, requiredStatId, requiredStatLevel, requiredCombatLevel;
    public Perk(int id, String name, String description, int requiredStatId, int requiredStatLevel, int requiredCombatLevel){
        setName(name);
        setDescription(description);
        setRequiredStatId(requiredStatId);
        setRequiredStatLevel(requiredStatLevel);
        setRequiredCombatLevel(requiredCombatLevel);
        setId(id);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the requiredStatId
     */
    public int getRequiredStatId() {
        return requiredStatId;
    }

    /**
     * @param requiredStatId the requiredStatId to set
     */
    public void setRequiredStatId(int requiredStatId) {
        this.requiredStatId = requiredStatId;
    }

    /**
     * @return the requiredStatLevel
     */
    public int getRequiredStatLevel() {
        return requiredStatLevel;
    }

    /**
     * @param requiredStatLevel the requiredStatLevel to set
     */
    public void setRequiredStatLevel(int requiredStatLevel) {
        this.requiredStatLevel = requiredStatLevel;
    }

    /**
     * @return the requiredCombatLevel
     */
    public int getRequiredCombatLevel() {
        return requiredCombatLevel;
    }

    /**
     * @param requiredCombatLevel the requiredCombatLevel to set
     */
    public void setRequiredCombatLevel(int requiredCombatLevel) {
        this.requiredCombatLevel = requiredCombatLevel;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
}
