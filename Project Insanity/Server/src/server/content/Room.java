/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.content;

/**
 *
 * @author Alex Macocian
 */
public class Room {

    public int x, y, z, rot;
    
    
    public Room(int x, int y, int z, int rot) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.rot = rot;
    }
    
    public int GetLocationHash(){
        return x / 8 << 14 | y / 8 << 3 | z % 4 << 24 | rot % 4 << 1;
    } 
}
