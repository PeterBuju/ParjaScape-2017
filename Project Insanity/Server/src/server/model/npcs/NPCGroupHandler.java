/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.npcs;
import java.util.ArrayList;
import java.util.Random;
import server.Server;
import server.model.npcs.NPCGroup;
import static server.model.npcs.NPCHandler.npcs;
import server.model.players.Client;
import server.util.Misc;
/**
 *
 * @author Alex Macocian
 */
public class NPCGroupHandler {
    public static ArrayList<NPCGroup> Groups = new ArrayList<>();
    static Random rnd = new Random();
    static ArrayList<Attacker> toRemove = new ArrayList<>();
    public static int removeAttacker = 30;
    
    public static void Process(){
        for(NPCGroup group : Groups){
            for(Attacker attacker : group.attackers){
                if(System.currentTimeMillis() - attacker.timer > removeAttacker * 1000){
                    toRemove.add(attacker);
                }
                if(System.currentTimeMillis() - attacker.priorityTimer > attacker.priorityDuration){
                    attacker.priority--;
                    if (attacker.priority > 0){
                        attacker.priorityTimer = System.currentTimeMillis();
                    }
                    else{
                        attacker.priorityTimer = 0;
                        attacker.priorityDuration = 0;
                    }
                }
            }
            if (toRemove.size() > 0) {
                for (Attacker attacker : toRemove)
                    group.removeAttacker(attacker);
            }
        }
    }
    
    public static boolean ContainsGroup(String name){
        for (NPCGroup group : Groups){
            if (group.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }
    
    public static void AddGroup(String name){
        if (!ContainsGroup(name)){
            NPCGroup group = new NPCGroup();
            group.setName(name);
            Groups.add(group);
        }
    }
    
    public static NPCGroup GetGroup(String name){
        if (ContainsGroup(name)){
            for (NPCGroup group : Groups){
                if (group.getName().equalsIgnoreCase(name)){
                    return group;
                }
            }
        }
        return null;
    }
  
    public static NPCGroup GetGroup(int id) {
        if (Groups.get(id).getName() != null && !Groups.get(id).getName().equalsIgnoreCase("")) {
            return Groups.get(id);
        } else {
            return null;
        }
    }

    public static void AddAtacker(String name, int attackerid) {
        for (NPCGroup group : Groups) {
            if (group.getName().equalsIgnoreCase(name)) {
                group.addAttacker(attackerid);
            }
        }
    }

    public static Attacker GetAttacker(NPCGroup group, NPC npc) {
        ArrayList<Attacker> possibleTargets = new ArrayList<>();
        boolean hasAttackers = false;
        try {
            for (Attacker attacker : group.attackers) {
                possibleTargets.add(attacker);
                hasAttackers = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (hasAttackers) {
                ArrayList<Attacker> toRemove = new ArrayList<>();
                int minimumProximity = GetProximity(npc, (Client) Server.playerHandler.players[possibleTargets.get(0).id]) - possibleTargets.get(0).priority;
                for (Attacker attacker : possibleTargets) {
                    if (minimumProximity > GetProximity(npc, (Client) Server.playerHandler.players[attacker.id]) - attacker.priority) {
                        minimumProximity = GetProximity(npc, (Client) Server.playerHandler.players[attacker.id]) - attacker.priority;
                    }
                }
                for (Attacker attacker : possibleTargets) {
                    if (GetProximity(npc, (Client) Server.playerHandler.players[attacker.id]) - attacker.priority > minimumProximity) {
                        toRemove.add(attacker);
                    }
                }
                for (Attacker attacker : toRemove) {
                    possibleTargets.remove(attacker);
                }
                if (possibleTargets.size() >= 1) {
                    int chosen = (int)Math.random() * possibleTargets.size();
                    return possibleTargets.get(chosen);
                }
                else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static int GetProximity(NPC npc, Client client){
        int proximity = -1;
        int prox, proy;
        prox = npc.absX - client.absX;
        if (prox < 0)
            prox = -prox;
        proy = npc.absY - client.absY;
        if (proy < 0)
            proy = -proy;
        proximity = prox + proy;
        return proximity;
    }
}
