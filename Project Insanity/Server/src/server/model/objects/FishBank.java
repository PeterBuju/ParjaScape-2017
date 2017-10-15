/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.objects;

import server.Config;
import server.model.npcs.NPC;
import server.model.npcs.NPCHandler;
import server.model.players.Client;
import server.util.Misc;
import server.world.FishBankManager;

/**
 *
 * @author Alex Macocian
 */
public class FishBank{
    int curX, curY;
    int posX, posY, npcId;
    NPC npc;
    double respawnTimer;
    int respawnTime;
    public FishBank(NPC npc){
        this.npc = npc;
        npcId = npc.npcId;
        posX = npc.absX;
        posY = npc.absY;
        curX = posX;
        curY = posY;
        respawnTimer = System.currentTimeMillis();
        respawnTime = (Misc.random(Config.MAXRESPAWNTIME - Config.MINRESPAWNTIME) + Config.MINRESPAWNTIME) * 1000;
    }
    
    public int GetRespawnTime(){
        return respawnTime;
    }
    
    public void SetCurX(int x){
        curX = x;
    }
    
    public void SetCurY(int y){
        curY = y;
    }
    
    public int GetCurX(){
        return curX;
    }
    
    public int GetCurY(){
        return curY;
    }
    
    public NPC GetNPC(){
        return npc;
    }
    
    public void SetNPC(NPC npc){
        this.npc = npc;
        this.npcId = npc.npcId;
    }
    
    public void SetRespawnTimer(double respawnTimer){
        this.respawnTimer = respawnTimer;
    }
    
    public double GetRespawnTimer(){
        return respawnTimer;
    }
    
    public int GetPosX(){
        return posX;
    }
    
    public int GetPosY(){
        return posY;
    }
    
    public int GetNPCId(){
        return npcId;
    }
}
