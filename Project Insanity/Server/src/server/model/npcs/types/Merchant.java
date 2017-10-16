/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.npcs.types;

import java.util.ArrayList;
import server.model.items.Item;

/**
 *
 * @author amaco
 */
public class Merchant extends Greeter{
    ArrayList<Item> inventory = new ArrayList<>();
    
    @Override
    public void process(){
        super.process();
    }
}
