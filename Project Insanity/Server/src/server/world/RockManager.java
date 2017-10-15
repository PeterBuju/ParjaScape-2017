/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.world;

import java.util.ArrayList;
import server.Server;
import server.model.objects.Rock;
import server.model.players.Client;

/**
 *
 * @author Alex Macocian
 */
public class RockManager {
    public static int timeToRespawn = 15;
    public static int timeToRemove = 60;
    public static ArrayList<Rock> rocks = new ArrayList<>();
    private static ArrayList<Rock> toDelete = new ArrayList<>();
    
    public static void RespawnRock(Rock rock){
        if (rock.IsUsed())
        {
            Server.objectManager.addObject(rock.GetInitObjId(), rock.GetPosX(), rock.GetPosY());
            rock.Respawn();
        }
    }
    
    public static void DepleteRock(Rock rock){
        if (!rock.IsUsed())
        {
            Server.objectManager.addObject(rock.GetUsedObjId(), rock.GetPosX(), rock.GetPosY());
            rock.Deplete();
        }
    }

    public static void AddRock(Client c) {
        if (!FindRock(c)){
            rocks.add(new Rock(c));
        }
    }
    
    public static boolean FindRock(Client c){
        int posX = c.objectX;
        int posY = c.objectY;
        for (Rock rock : rocks){
            if (rock.GetPosX() == posX && rock.GetPosY() == posY){
                return true;
            }
        } 
        return false;
    }
    
    public static boolean FindRock(int posX, int posY){
        for (Rock rock : rocks){
            if (rock.GetPosX() == posX && rock.GetPosY() == posY){
                return true;
            }
        } 
        return false;
    }
    
    public static Rock GetRock(Client c){
        int posX = c.objectX;
        int posY = c.objectY;
        for (Rock rock : rocks){
            if (rock.GetPosX() == posX && rock.GetPosY() == posY){
                return rock;
            }
        } 
        return null;
    }
    
    public static Rock GetRock(int posX, int posY){
        for (Rock rock : rocks){
            if (rock.GetPosX() == posX && rock.GetPosY() == posY){
                return rock;
            }
        } 
        return null;
    }
    
    public static void Process(){
        for (Rock rock : rocks){
            if (System.currentTimeMillis() - rock.GetRespawnTimer() >= timeToRespawn * 1000){
                RespawnRock(rock);
            }
            if (System.currentTimeMillis() - rock.GetLastClickedTimer()>= timeToRemove * 1000){
                toDelete.add(rock);
            }
        }
        if (toDelete.size() > 0){
            for(Rock rock : toDelete){
                RespawnRock(rock);
                rocks.remove(rock);
            }
            toDelete.clear();
        }
    }
}
