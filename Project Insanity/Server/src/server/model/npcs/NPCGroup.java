/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.npcs;

import java.util.ArrayList;

/**
 *
 * @author Alex Macocian
 */
public class NPCGroup {
    private String name;
    public ArrayList<Attacker> attackers = new ArrayList<>();

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
    
    public boolean isAttacker(int attackerId){
        for(Attacker attacker : attackers){
            if (attacker.id == attackerId){
                return true;
            }
        }
        return false;
    }

    public Attacker getAttacker(int attackerId) {
        for (Attacker attacker : attackers) {
            if (attacker.id == attackerId) {
                return attacker;
            }
        }
        return null;
    }

    public void addAttacker(int attackerId) {
        if (isAttacker(attackerId)) {
            Attacker attacker = getAttacker(attackerId);
            attacker.timer = System.currentTimeMillis();
        } else {
            Attacker attacker = new Attacker();
            attacker.id = attackerId;
            attacker.timer = System.currentTimeMillis();
            attackers.add(attacker);
        }
    }
    
    public void removeAttacker(int attackerId){
        if (isAttacker(attackerId)){
            Attacker attacker = getAttacker(attackerId);
            attackers.remove(attacker);
        }
    }
    
    public void removeAttacker(Attacker attacker){
        attackers.remove(attacker);
    }
    
    public void specialAggroAttacker(int attackerId, double duration){
        if (isAttacker(attackerId)){
            Attacker attacker = getAttacker(attackerId);
            removeAttacker(attacker);
            attackers.add(0, attacker);
            attacker.priority++;
            attacker.priorityTimer = System.currentTimeMillis();
            attacker.priorityDuration = duration;
        }
    }
}
