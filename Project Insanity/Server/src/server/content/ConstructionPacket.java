/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.content;

import server.Config;
import server.model.players.Client;
import server.model.players.PacketType;
import server.util.Misc;

/**
 *
 * @author Alex Macocian
 */
public class ConstructionPacket implements PacketType{
    
    @Override
	public void processPacket(Client c, int packetType, int packetSize) {
            int id = c.getInStream().readUnsignedWordA(); //short = word
            int y = c.getInStream().readUnsignedWordA();
            int x = c.getInStream().readUnsignedWord();
            if (Config.SERVER_DEBUG)
                c.sendMessage("Clicked object id: " + id + " X: " + x + " Y: " + y);
            HouseManager.handleConstructionClick(c, id, x, y);
        }  
}
