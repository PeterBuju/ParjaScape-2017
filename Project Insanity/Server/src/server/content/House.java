/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.content;

import java.io.BufferedReader;
import java.util.ArrayList;
import server.model.players.Client;
import server.model.players.Palette;
import server.util.Misc;

/**
 *
 * @author Alex Macocian
 */
public class House {

    public String ownerName;
    public ArrayList<Client> visitors = new ArrayList<>();
    public ArrayList<String> blacklist = new ArrayList<>();
    public ArrayList<HouseObject> objects = new ArrayList<HouseObject>();
    public Room[][][] rooms = new Room[13][13][4];
    boolean allowsVisitors = false;
    int heightLevel;

    public int GetHeightLevel() {
        return heightLevel;
    }
    
    public void SetHeightLevel(int heightLevel){
        this.heightLevel = heightLevel;
    }

    public House(Client c, int heightLevel) {
        ownerName = c.playerName;
        this.heightLevel = heightLevel;
        InitializeRooms();
    }

    public House(String name, int heightLevel) {
        ownerName = name;
        this.heightLevel = heightLevel;
        InitializeRooms();
    }
    
    public House(){
        InitializeRooms();
    }
    
    public boolean isOwner(Client c) {
        if (c.playerName.equals(ownerName)) {
            return true;
        }
        return false;
    }

    public void EnterHouse(Client c) {
        c.inHouse = true;
        if (isOwner(c)) {    //ENTER HOUSE
            if (!c.inHouse) {
                c.oldLocation[0] = c.absX;
                c.oldLocation[1] = c.absY;
                c.oldLocation[2] = c.heightLevel;
            }
        } else {
            if (!visitors.contains(c)) {
                visitors.add(c);
            }
            if (!allowsVisitors) {
                c.sendMessage(ownerName + " doesn't allow visitors into his house!");
                return;
            }
            if (blacklist.contains(c.playerName)) {
                c.sendMessage(ownerName + " has blocked you from accessing his house!");
                return;
            }
            if (!c.inHouse) {
                c.oldLocation[0] = c.absX;
                c.oldLocation[1] = c.absY;
                c.oldLocation[2] = c.heightLevel;
            }
            c.buildMode = false;
        }
        c.getPA().movePlayer(HouseManager.initX, HouseManager.initY, HouseManager.initHeight + heightLevel);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
            @Override
            public void run() {
                ShowHouse(c);
            }
        },
                100
        );
    }

    public void LeaveHouse(Client c) {
        c.inHouse = false;
        if (!isOwner(c)) {
            visitors.remove(c);
        }
        c.getPA().movePlayer(c.oldLocation[0], c.oldLocation[1], c.oldLocation[2]);
    }

    public void KickAllGuests() {
        for (Client c : visitors) {
            c.inHouse = false;
            c.getPA().movePlayer(c.oldLocation[0], c.oldLocation[1], c.oldLocation[2]);
        }
        visitors.clear();
    }
    
    private void InitializeRooms(){
        for (int i = 0; i < 13; i++)
            for (int j = 0; j < 13; j++){
                for (int k = 1; k < 4; k++)                   
                    rooms[i][j][k] = null;
                rooms[i][j][0] = RoomType.DEFAULT;
            }
        rooms[6][6][0] = RoomType.GARDEN;
    }
    
    public void ShowHouse(Client player) {
        player.outStream.createFrameVarSizeWord(241);
        player.outStream.writeWordA(player.mapRegionY + 6);
        player.outStream.initBitAccess();
        for (int z = 0; z < 4; z++) {
            for (int x = 0; x < 13; x++) {
                for (int y = 0; y < 13; y++) {
                    Room room = rooms[x][y][z];
                    player.getOutStream().writeBits(1, room != null ? 1 : 0);
                    if (room != null) {
                        Misc.println("Sending room: " + x + " " + y + " " + z);
                        player.getOutStream().writeBits(26, room.GetLocationHash());
                    }
                }
            }
        }
        player.getOutStream().finishBitAccess();
        player.getOutStream().writeWord(player.mapRegionX + 6);
        player.getOutStream().endFrameVarSizeWord();
        player.flushOutStream();
        player.inHouse = true;
        LoadObjects(player);
    }
    
    public void ReloadHouse(Client player){
        int x = player.absX;
        int y = player.absY;
        int height = player.heightLevel;
        player.getPA().movePlayer(HouseManager.initX, HouseManager.initY, HouseManager.initHeight + heightLevel);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
            @Override
            public void run() {
                ShowHouse(player);
            }
        },
                100
        );
        player.getPA().movePlayer(x, y, height);
    }
    
    public void LoadObjects(Client c) {
        for (HouseObject object : objects) {
            /*visiter.getOutStream().createFrame(85);
			visiter.getOutStream().writeByteC(object.y - visiter.getMapRegionY() * 8);
			visiter.getOutStream().writeByteC(object.x - visiter.getMapRegionX() * 8);
			visiter.getOutStream().createFrame(101);
			visiter.getOutStream().writeByteC(object.rot);
			visiter.getOutStream().writeByte(0);
			visiter.getOutStream().createFrame(151);
			visiter.getOutStream().writeByteS(0);
			visiter.getOutStream().writeWordBigEndian(object.id);
			visiter.getOutStream().writeByteS(object.rot);
			visiter.flushOutStream();*/
            new server.model.objects.Object(object.id, object.y, object.x, c.playerId * 4, c.objRot, HouseManager.getObjType(object.id), object.id, 0);
            System.out.println(c.objRot);
        }
    }
    
    public void AddRoom(Client player, Room room, int slotX, int slotY) {
        rooms[slotX][slotY][player.heightLevel - heightLevel] = room;
        ReloadHouse(player);
    }
    
    public void AddRoom(Client player, Room room, int slotX, int slotY, int height) {
        if (height < 0 || height > 3){
            return;
        }
        rooms[slotX][slotY][height] = room;
        ReloadHouse(player);
    }
    
}
