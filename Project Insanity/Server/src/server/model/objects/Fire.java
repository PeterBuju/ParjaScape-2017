/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.objects;

import server.model.players.Client;

/**
 *
 * @author Alex Macocian
 */
public class Fire extends BaseObject{
    int playerId;
    boolean alive;
    
    public Fire(Client c){
        super(c);
        this.posX = c.absX;
        this.posY = c.absY;
        playerId = c.playerId;
        alive = true;
        respawnTimer = System.currentTimeMillis();
    }
    
    public double GetBurningTime(){
        return respawnTimer;
    }
    
    public int GetPlayerId(){
        return playerId;
    }
    
    public void KillFire(){
        alive = false;
    }
    
    public boolean GetFireStatus(){
        return alive;
    }
}
