/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.npcs;
import java.util.ArrayList;
import server.model.npcs.NPCGroup;
/**
 *
 * @author Alex Macocian
 */
public class NPCGroupHandler {
    public static ArrayList<NPCGroup> Groups = new ArrayList<>();
    public static double Timer;
    
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
            group.setAttackerId(-1);
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
    
    public static NPCGroup GetGroup(int id){
        if (Groups.get(id).getName() != null && !Groups.get(id).getName().equalsIgnoreCase("")){
            return Groups.get(id);
        }
        else
            return null;
    }
    
    public static void SetAttackerId(String name, int attackerid){
        for (NPCGroup group : Groups){
            if(group.getName().equalsIgnoreCase(name)){
                group.setAttackerId(attackerid);
            }
        }
    }
    
    public static void ResetAttackerId(){
        for (NPCGroup group : Groups){
            group.setAttackerId(-1);
        }
    }
}
