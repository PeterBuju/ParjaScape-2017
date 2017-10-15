/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.world;

import java.util.ArrayList;
import server.Server;
import server.model.objects.Rock;
import server.model.objects.Tree;
import server.model.players.Client;
import static server.world.RockManager.rocks;

/**
 *
 * @author Alex Macocian
 */
public class TreeManager {
    public static int timeToRemove = 60;
    public static int timeToRespawn = 15;
    public static ArrayList<Tree> trees = new ArrayList<>();
    public static ArrayList<Tree> toDelete = new ArrayList<>();
    
    public static void RespawnTree(Tree tree){
        if (tree.IsUsed())
        {
            Server.objectManager.addObject(tree.GetInitObjId(), tree.GetPosX(), tree.GetPosY());
            tree.Respawn();
        }
    }
    
    public static void CutTree(Tree tree){
        if (!tree.IsUsed())
        {
            Server.objectManager.addObject(tree.GetUsedObjId(), tree.GetPosX(), tree.GetPosY());
            tree.Deplete();
        }
    }

    public static void AddTree(Client c) {
        if (!FindTree(c)){
            trees.add(new Tree(c));
        }
    }
    
    public static boolean FindTree(Client c){
        int posX = c.objectX;
        int posY = c.objectY;
        for (Tree tree : trees){
            if (tree.GetPosX() == posX && tree.GetPosY() == posY){
                return true;
            }
        } 
        return false;
    }
    
    public static boolean FindTree(int posX, int posY){
        for (Tree tree : trees){
            if (tree.GetPosX() == posX && tree.GetPosY() == posY){
                return true;
            }
        } 
        return false;
    }
    
    public static Tree GetTree(Client c){
        int posX = c.objectX;
        int posY = c.objectY;
        for (Tree tree : trees){
            if (tree.GetPosX() == posX && tree.GetPosY() == posY){
                return tree;
            }
        } 
        return null;
    }
    
    public static Tree GetTree(int posX, int posY){
        for (Tree tree : trees){
            if (tree.GetPosX() == posX && tree.GetPosY() == posY){
                return tree;
            }
        } 
        return null;
    }
    
    public static void Process(){
        for (Tree tree : trees){
            if (System.currentTimeMillis() - tree.GetRespawnTimer() >= timeToRespawn * 1000){
                RespawnTree(tree);
            }
            if (System.currentTimeMillis() - tree.GetLastClickedTimer()>= timeToRemove * 1000){
                toDelete.add(tree);
            }
        }
        if (toDelete.size() > 0) {
            for (Tree tree : toDelete) {
                RespawnTree(tree);
                trees.remove(tree);
            }
            toDelete.clear();
        }
    }
}
