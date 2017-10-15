/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.world;

import java.util.ArrayList;
import server.Server;
import server.model.objects.Fire;
import server.model.objects.Objects;
import server.model.players.Client;
import server.model.players.skills.Firemaking;

/**
 *
 * @author Alex Macocian
 */
public class FireManager {
    public static int maxFires = 3;
    public static int timeToDie = 60;
    public static int timeToDisappear = 75;
    public static boolean allowFireRemoval = true;

    public static ArrayList<Fire> fires = new ArrayList<>();

    public static boolean AddFire(Client c, int objId) {
        if (CanAddFire(c)) {
            CreateFire(c, objId);
            return true;
        } else {
            if (allowFireRemoval) {
                RemoveFirstFireFromPlayer(c);
                CreateFire(c, objId);
                return true;
            }
            else {
                return false;
            }
        }
    }

    private static boolean CanAddFire(Client c) {
        int count = 0;
        for (Fire fire : fires) {
            if (c.playerId == fire.GetPlayerId()) {
                count++;
            }
        }
        if (count >= maxFires) {
            return false;
        } else {
            return true;
        }
    }

    private static void CreateFire(Client c, int objId) {
        //Objects fire2 = new Objects(-1, c.getX(), c.getY(), c.heightLevel, -1, 10, 60);
        //Objects fireobj = new Objects(2728, c.getX(), c.getY(), c.heightLevel, -1, 10, 3);
        //Server.objectHandler.addObject(fireobj);
        //Server.objectHandler.addObject(fire2);
        Fire fire = new Fire(c);
        if (!fires.contains(fire)){
            fires.add(fire);
            Server.objectManager.addObject(objId, fire.GetPosX(), fire.GetPosY());
        }
    }
    
    private static boolean RemoveFirstFireFromPlayer(Client c){
        Fire fire = GetFirstFireFromPlayer(c);
        if (fire != null){
            RemoveFire(fire);
            return true;
        }
        else {
            return false;
        }
    }
    
    private static Fire GetFirstFireFromPlayer(Client c){
        for (Fire fire : fires){
            if (fire.GetPlayerId() == c.playerId)
                return fire;
        }
        return null;
    }
    
    public static void RemoveFire(Fire fire){
        Server.objectManager.removeObject(fire.GetPosX(), fire.GetPosY());
        fires.remove(fire);
    }
    
    public static void ExtinguishFire(Fire fire){
        Server.objectManager.addObject(714, fire.GetPosX(), fire.GetPosY());
        fire.KillFire();
    }
    
    public static void Process(){
        ArrayList<Fire> toDelete = new ArrayList<>();
        for (Fire fire : fires){
            if (System.currentTimeMillis() - fire.GetBurningTime() > timeToDie * 1000 && fire.GetFireStatus()){
                ExtinguishFire(fire);
            }
            else if (System.currentTimeMillis() - fire.GetBurningTime() > timeToDisappear * 1000){
                //RemoveFire(fire);
                toDelete.add(fire);
            }
        }
        for (Fire fire : toDelete)
            RemoveFire(fire);
    }
}
