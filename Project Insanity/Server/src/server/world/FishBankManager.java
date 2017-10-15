/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.world;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;
import server.Server;
import server.clip.region.Region;
import server.model.npcs.DBNPC;
import server.model.npcs.NPC;
import server.model.npcs.NPCDataBase;
import server.model.npcs.NPCHandler;
import server.model.objects.FishBank;
import server.model.objects.Tree;
import server.model.players.Client;
import server.util.Misc;

/**
 *
 * @author Alex Macocian
 */
public class FishBankManager {
    public static int timeToRespawn = 1600;
    public static ArrayList<FishBank> fishBanks = new ArrayList<>();
    public static ArrayList<FishBank> toDelete = new ArrayList<>();
    public static int MAXDISTANCE = 2;
    public static int MAXTRIES = 50;
    
    public static void RespawnFishBank(FishBank fishBank){
        GetNewPosition(fishBank);
        fishBank.GetNPC().applyDead = true;
        fishBank.GetNPC().isDead = true;
        fishBank.GetNPC().updateRequired = true;
        fishBank.GetNPC().makeX = fishBank.GetCurX();
        fishBank.GetNPC().makeY = fishBank.GetCurY();
        fishBank.SetRespawnTimer(System.currentTimeMillis());
    }
    
    public static void GetNewPosition(FishBank fishbank){
        int initX = fishbank.GetPosX();
        int initY = fishbank.GetPosY();
        int height = fishbank.GetNPC().heightLevel;
        int tries = 0;
        do{
            int newX = Misc.random(MAXDISTANCE * 2) - MAXDISTANCE;
            newX += initX;
            int newY = Misc.random(MAXDISTANCE * 2) - MAXDISTANCE;
            newY += initY;
            if (CanMoveThere(newX, newY, height)){
                if (!FindFishBank(newX, newY)) {
                    fishbank.SetCurX(newX);
                    fishbank.SetCurY(newY);
                    Misc.println("Moved from: " + fishbank.GetPosX() + " " + fishbank.GetPosY() + " to " + fishbank.GetCurX()  + " " + fishbank.GetCurY());
                    return;
                }
            }
            tries++;
        } while (tries < MAXTRIES);
        Misc.println("Fishbank not moved");
        fishbank.SetCurX(fishbank.GetPosX());
        fishbank.SetCurY(fishbank.GetPosY());
    }
    
    public static boolean CanMoveThere(int newX, int newY, int height){
        boolean CurrentPos = Region.blockedNorth(newX, newY - 1, height);
        boolean North = !Region.blockedNorth(newX, newY, height);
        boolean East = !Region.blockedEast(newX, newY, height);
        boolean South = !Region.blockedSouth(newX, newY, height);
        boolean West = !Region.blockedWest(newX, newY, height);
        boolean NorthEast = !Region.blockedNorthEast(newX, newY, height);
        boolean NorthWest = !Region.blockedNorthWest(newX, newY, height);
        boolean SouthEast = !Region.blockedSouthEast(newX, newY, height);
        boolean SouthWest = !Region.blockedSouthWest(newX, newY, height);
        if (CurrentPos) {   //CHECKS IF CURRENT LOCATION IS NOT WALKABLE (AKA WATER)
            if (North && !East && !West && !South
                    || //LAND TO THE NORTH
                    !North && East && !West && !South
                    || //LAND TO THE EAST
                    !North && !East && West && !South
                    || //LAND TO THE WEST
                    !North && !East && !West && South
                    || //LAND TO THE SOUTH
                    North && East && !West && !South
                    || //LAND TO THE NORTH AND EAST
                    North && !East && West && !South
                    || //LAND TO THE NORTH AND WEST
                    !North && East && !West && South
                    || //LAND TO THE SOUTH AND EAST
                    !North && !East && West && South) //LAND TO THE SOUTH AND WEST
            {
                return true;
            }
        }
        return false;
    }
    
    public static void AddFishBank(NPC npc) {
        Misc.println_debug("Adding FishBank ID: " + npc.npcType);
        if (!FindFishBank(npc)){
            Misc.println_debug("FishBank added");
            fishBanks.add(new FishBank(npc));
        }else{
            if (GetFishBank(npc) != null){
                Misc.println_debug("FishBank exists");
            }
        }
    }
    
    public static boolean FindFishBank(NPC npc){
        int curX = npc.absX;
        int curY = npc.absY;
        return FindFishBank(curX, curY);
    }
    
    public static boolean FindFishBank(int posX, int posY){
        for (FishBank fishBank : fishBanks){
            if (fishBank.GetCurX() == posX && fishBank.GetCurY() == posY){
                return true;
            }
        } 
        return false;
    }
    
    public static FishBank GetFishBank(NPC npc){
        int posX = npc.absX;
        int posY = npc.absY;
        return GetFishBank(posX, posY);
    }
    
    public static FishBank GetFishBank(int posX, int posY){
        for (FishBank fishBank : fishBanks){
            if (fishBank.GetCurX() == posX && fishBank.GetCurY() == posY){
                return fishBank;
            }
        } 
        return null;
    }
    
    public static void Reinitialize(FishBank fishBank){
        fishBank.SetNPC(NPCHandler.npcs[fishBank.GetNPCId()]);
    }
    
    public static void Process(){
        for (FishBank fishBank : fishBanks){
            if (System.currentTimeMillis() - fishBank.GetRespawnTimer() > fishBank.GetRespawnTime()){
                RespawnFishBank(fishBank);
            }
            if (System.currentTimeMillis() - fishBank.GetRespawnTimer() > timeToRespawn){
                Reinitialize(fishBank);
            }
        }
    }
    
    public static void Initialize(){
        for (NPC npc : NPCHandler.npcs) {
            if (npc != null) {
                DBNPC dbnpc = NPCDataBase.FindDBNPC(npc.npcType);
                if (dbnpc != null){
                    if (dbnpc.name.equalsIgnoreCase("Fishing spot")){
                        AddFishBank(npc);
                    }
                }
                else if (npc.npcType == 635
                        || npc.npcType >= 1496 && npc.npcType <= 1500
                        || npc.npcType >= 1506 && npc.npcType <= 1536
                        || npc.npcType == 1542
                        || npc.npcType == 1544
                        || npc.npcType == 2146
                        || npc.npcType >= 2653 && npc.npcType <= 2655
                        || npc.npcType == 3317
                        || npc.npcType >= 3417 && npc.npcType <= 3419
                        || npc.npcType == 3657
                        || npc.npcType >= 3913 && npc.npcType <= 3915
                        || npc.npcType >= 4079 && npc.npcType <= 4082
                        || npc.npcType == 4316
                        || npc.npcType >= 4476 && npc.npcType <= 4477
                        || npc.npcType >= 4710 && npc.npcType <= 4714
                        || npc.npcType == 4928
                        || npc.npcType >= 5233 && npc.npcType <= 5234
                        || npc.npcType >= 5820 && npc.npcType <= 5821
                        || npc.npcType == 6371) {
                    AddFishBank(npc);
                }
            }
        }
    }
}
