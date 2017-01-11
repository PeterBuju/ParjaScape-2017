/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.npcs;

/**
 *
 * @author Alex Macocian
 */
public class NPCGroup {
    private String name;
    private int attackerId;

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
     * @return the attackerId
     */
    public int getAttackerId() {
        return attackerId;
    }

    /**
     * @param attackerId the attackerId to set
     */
    public void setAttackerId(int attackerId) {
        this.attackerId = attackerId;
    }
}
