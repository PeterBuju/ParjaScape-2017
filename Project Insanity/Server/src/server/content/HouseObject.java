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
public class HouseObject {

    public int x;
    public int y;
    public int z;
    public int id;
    public int rot;

    public HouseObject(int id, int x, int y, int z, int type, int face) {
        this.rot = type << 2 | face & 3;
        this.x = x;
        this.y = y;
        this.z = z;
        this.id = id;
    }
    
    public HouseObject(int id, int x, int y, int z, int rot){
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.rot = rot;
    }
}
