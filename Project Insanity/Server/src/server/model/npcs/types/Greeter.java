/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.npcs.types;

import java.util.ArrayList;
import server.model.factions.Faction;
import server.model.players.Client;

/**
 *
 * @author amaco
 */
public class Greeter extends BaseNPC{
    public String greetingMessage;
    public Faction faction;
    public ArrayList<Client> greetedPlayers = new ArrayList<>();
    
    @Override
    public void process(){
        super.process();
    }
}
