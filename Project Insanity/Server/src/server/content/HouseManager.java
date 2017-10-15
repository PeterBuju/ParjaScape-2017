/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.content;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import server.Config;
import server.model.players.Client;
import server.model.players.PlayerHandler;
import static server.model.players.packets.Commands.SendSyntaxError;
import server.util.Misc;

/**
 *
 * @author Alex Macocian
 */
public class HouseManager {
    public static int initX = 50, initY = 50, initHeight = 0;
    public static ArrayList<House> houses = new ArrayList<>();
    public static final String HOUSELOCATION = "./Data/houses/";
    public static int[][] objectData = {
        //Build obj id, Real obj id, obj type
        {15361, 4525, 10}
    };
    

    public static void CreateHouse(Client c){
        if(!HasHouse(c)){
            int height = GetNewHeightLevel();
            if (height == -1){
                Misc.println("Error allocating height level");
                return;
            }
            Misc.println("Created house at heighlevel " + height);
            House house = new House(c, height);
            houses.add(house);
            SaveHouse(house);
            SortHouses();
        }
    }
    
    public static void EnterHouse(Client c){
        GetHouse(c).EnterHouse(c);
    }
    
    public static void EnterHouse(Client c, Client host){
        GetHouse(host).EnterHouse(c);
    }
    
    public static void EnterHouse(Client c, int houseNumber){
        houses.get(houseNumber).EnterHouse(c);
    }
    
    public static void LeaveHouse(Client c){
        c.inHouse = false;
        c.getPA().movePlayer(c.oldLocation[0], c.oldLocation[1], c.oldLocation[2]);
    }
    
    public static boolean HasHouse(Client c){
        for (House house : houses){
            if (house.ownerName.equalsIgnoreCase(c.playerName)){
                return true;
            }
        }
        return false;
    }
    
    public static House GetHouse(Client c){
        for (House house : houses){
            if (house.ownerName.equalsIgnoreCase(c.playerName)){
                return house;
            }
        }
        return null;
    }
    
    public static House GetHouse(String name){
        for (House house : houses){
            if (house.ownerName.equalsIgnoreCase(name)){
                return house;
            }
        }
        return null;
    }
    
    public static int GetNewHeightLevel(){
        return (houses.size() + 1) * 4;
    }
    
    public static void SortHouses(){
        Collections.sort(houses, (a, b) -> new Integer(b.GetHeightLevel()).compareTo(a.GetHeightLevel()));
    }
    
    public static void SaveHouses(){
        for(House house : houses){
            SaveHouse(house);
        }
    }
    
    public static void LoadHouses(){
        File folder = new File(HOUSELOCATION);
        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                Misc.println(fileEntry.getName());
                House house = LoadHouse(fileEntry.getAbsolutePath());
                houses.add(house);
            }
        }
        SortHouses();
    }
   
    public static boolean SaveHouse(House house) {
        BufferedWriter housefile = null;
        try {
            housefile = new BufferedWriter(new FileWriter(HOUSELOCATION + house.ownerName + ".house"));

            /*VARIABLES*/
            housefile.write("[VARIABLES]", 0, 11);
            housefile.newLine();
            housefile.write("ownerName = ", 0, 12);
            housefile.write(house.ownerName, 0, house.ownerName.length());
            housefile.newLine();
            housefile.write("heightLevel = ", 0, 14);
            housefile.write(String.valueOf(house.GetHeightLevel()), 0, String.valueOf(house.GetHeightLevel()).length());
            housefile.newLine();
            housefile.write("allowVisitors = ", 0, 16);
            housefile.write(String.valueOf(house.allowsVisitors), 0, String.valueOf(house.allowsVisitors).length());
            housefile.newLine();
            housefile.newLine();
            /*ROOMS*/
            housefile.write("[ROOMS]", 0, 7);
            housefile.newLine();
            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 13; j++) {
                    for (int k = 0; k < 4; k++) {
                        if (house.rooms[i][j][k] != null) {
                            housefile.write("room = ", 0, 7);
                            housefile.write(Integer.toString(i), 0, Integer.toString(i).length());
                            housefile.write("	", 0, 1);
                            housefile.write(Integer.toString(j), 0, Integer.toString(j).length());
                            housefile.write("	", 0, 1);
                            housefile.write(Integer.toString(k), 0, Integer.toString(k).length());
                            housefile.write("	", 0, 1);
                            String type = GetRoomType(house.rooms[i][j][k]);
                            housefile.write(type, 0, type.length());
                            housefile.write("	", 0, 1);
                            String rotation = Integer.toString(house.rooms[i][j][k].rot).toString();
                            housefile.write(rotation, 0, rotation.length());
                            housefile.newLine();
                        }
                    }
                }
            }
            housefile.newLine();
            /*OBJECTS*/
            housefile.write("[OBJECTS]", 0, 7);
            housefile.newLine();
            for (HouseObject obj : house.objects){
                housefile.write("object = ", 0, 9);
                housefile.write(Integer.valueOf(obj.id).toString(), 0, Integer.valueOf(obj.id).toString().length());
                housefile.write("	", 0, 1);
                housefile.write(Integer.valueOf(obj.x).toString(), 0, Integer.valueOf(obj.x).toString().length());
                housefile.write("	", 0, 1);
                housefile.write(Integer.valueOf(obj.y).toString(), 0, Integer.valueOf(obj.y).toString().length());
                housefile.write("	", 0, 1);
                housefile.write(Integer.valueOf(obj.z).toString(), 0, Integer.valueOf(obj.z).toString().length());
                housefile.write("	", 0, 1);
                housefile.write(Integer.valueOf(obj.rot).toString(), 0, Integer.valueOf(obj.rot).toString().length());
                housefile.write("	", 0, 1);
                housefile.newLine();
            }
            /*BLACKLIST*/
            housefile.write("[BLACKLIST]", 0, 11);
            housefile.newLine();
            for (String name : house.blacklist){
                housefile.write("playerName = ", 0, 13);
                housefile.write(name, 0, name.length());
                housefile.newLine();
            }
            /*EOF*/
            housefile.newLine();
            housefile.write("[EOF]", 0, 5);
            housefile.newLine();
            housefile.newLine();
            housefile.close();
        } catch (IOException ioexception) {
            Misc.println(house.ownerName + ": error writing house file.");
            return false;
        }
        return true;
    }

    public static House LoadHouse(String path) {
        String line = "";
        String token = "";
        String token2 = "";
        String[] token3 = new String[3];
        
        House house = new House();
        
        boolean EndOfFile = false;
        int ReadMode = 0;
        BufferedReader housefile = null;
        boolean File1 = false;

        try {
            housefile = new BufferedReader(new FileReader(path));
            File1 = true;
        } catch (FileNotFoundException fileex1) {
        }

        if (File1) {
            //new File ("./characters/"+playerName+".txt");
        } else {
            Misc.println(path + ": house file not found.");
            return null;
        }
        try {
            line = housefile.readLine();
        } catch (IOException ioexception) {
            Misc.println(path + ": error loading house file.");
            return null;
        }
        while (EndOfFile == false && line != null) {
            line = line.trim();
            int spot = line.indexOf("=");
            if (spot > -1) {
                token = line.substring(0, spot);
                token = token.trim();
                token2 = line.substring(spot + 1);
                token2 = token2.trim();
                token3 = token2.split("\t");
                switch (ReadMode) {
                    case 2:
                        if (token.equals("room")) {
                            Room room = RoomType.DEFAULT;
                            if (token3[3].equalsIgnoreCase("GARDEN")){
                                room = RoomType.GARDEN;                           
                            } else if (token3[3].equalsIgnoreCase("THRONE")){
                                room = RoomType.THRONE;        
                            } else if (token3[3].equalsIgnoreCase("GAME")){
                                room = RoomType.GAME;        
                            } else if (token3[3].equalsIgnoreCase("FLOOR2")){
                                room = RoomType.FLOOR2;        
                            } else if (token3[3].equalsIgnoreCase("PARLOUR")){
                                room = RoomType.PARLOUR;        
                            } else if (token3[3].equalsIgnoreCase("KITCHEN")){
                                room = RoomType.KITCHEN;        
                            } else if (token3[3].equalsIgnoreCase("DINING")){
                                room = RoomType.DINING;        
                            } else if (token3[3].equalsIgnoreCase("WORKSHOP")){
                                room = RoomType.WORKSHOP;        
                            } else if (token3[3].equalsIgnoreCase("BEDROOM")){
                                room = RoomType.BEDROOM;        
                            } else if (token3[3].equalsIgnoreCase("SKILLHALL")){
                                room = RoomType.SKILLHALL;        
                            } else if (token3[3].equalsIgnoreCase("COMBAT")){
                                room = RoomType.COMBAT;        
                            } else if (token3[3].equalsIgnoreCase("QUEST_HALL")){
                                room = RoomType.QUEST_HALL;        
                            } else if (token3[3].equalsIgnoreCase("STUDY")){
                                room = RoomType.STUDY;        
                            } else if (token3[3].equalsIgnoreCase("COSTUME_ROOM")){
                                room = RoomType.COSTUME_ROOM;        
                            } else if (token3[3].equalsIgnoreCase("CHAPEL")){
                                room = RoomType.CHAPEL;        
                            } else if (token3[3].equalsIgnoreCase("PORTAL_CHAMBER")){
                                room = RoomType.PORTAL_CHAMBER;        
                            } else if (token3[3].equalsIgnoreCase("FORMAL_GARDEN")){
                                room = RoomType.FORMAL_GARDEN;        
                            } else if (token3[3].equalsIgnoreCase("THRONE_ROOM")){
                                room = RoomType.THRONE_ROOM;        
                            } else if (token3[3].equalsIgnoreCase("OUBILIETTE")){
                                room = RoomType.OUBILIETTE;        
                            } else if (token3[3].equalsIgnoreCase("CORRIDOR_DUNGEON")){
                                room = RoomType.CORRIDOR_DUNGEON;        
                            } else if (token3[3].equalsIgnoreCase("JUNCTION_DUNGEON")){
                                room = RoomType.JUNCTION_DUNGEON;        
                            } else if (token3[3].equalsIgnoreCase("STAIRS DUNGEON")){
                                room = RoomType.STAIRS_DUNGEON;        
                            } else if (token3[3].equalsIgnoreCase("TREASURE_ROOM")){
                                room = RoomType.TREASURE_ROOM;        
                            }
                            house.rooms[Integer.parseInt(token3[0])][Integer.parseInt(token3[1])][Integer.parseInt(token3[2])] = room;
                            room.rot = Integer.parseInt(token3[4]);
                        }
                        break;
                    case 1:
                        if (token.equalsIgnoreCase("ownerName")){
                            house.ownerName = token2;
                        } else if (token.equalsIgnoreCase("heightLevel")) {
                            house.SetHeightLevel(Integer.parseInt(token2));
                        } else if (token.equalsIgnoreCase("allowVisitors")) {
                            if (token2.equalsIgnoreCase("True")){
                                house.allowsVisitors = true;
                            } else {
                                house.allowsVisitors = false;
                            }
                        }
                        break;
                    case 3:
                        if (token.equalsIgnoreCase("playerName")){
                            house.blacklist.add(token2);
                        }
                        break;
                    case 4:
                        if (token.equals("object")) {
                            int id = Integer.parseInt(token3[0]);
                            int x = Integer.parseInt(token3[1]);
                            int y = Integer.parseInt(token3[2]);
                            int z = Integer.parseInt(token3[3]);
                            int rot = Integer.parseInt(token3[4]);
                            HouseObject obj = new HouseObject(id, x, y, z, rot);
                            house.objects.add(obj);
                        }
                }
            } else {
                if (line.equals("[ROOMS]")) {
                    ReadMode = 2;
                } else if(line.equals("[VARIABLES]")){
                    ReadMode = 1;
                } else if(line.equals("[OBJECTS]")){
                    ReadMode = 4;
                } else if(line.equals("[BLACKLIST]")){
                    ReadMode = 3;
                } else if (line.equals("[EOF]")) {
                    try {
                        housefile.close();
                    } catch (IOException ioexception) {
                    }
                    return house;
                }
            }
            try {
                line = housefile.readLine();
            } catch (IOException ioexception1) {
                EndOfFile = true;
            }
        }
        try {
            housefile.close();
        } catch (IOException ioexception) {
        }
        return house;
    }
    
    public static boolean IsInHisHouse(Client c){
        if(0 <= c.heightLevel - GetHouse(c).GetHeightLevel() && c.heightLevel - GetHouse(c).GetHeightLevel() <= 4)
            return true;
        return false;
    }

    public static int getObjType(int id) {
        for (int i = 0; i < objectData.length; i++) {
            if (objectData[i][0] == id) {
                return objectData[i][2];
            }
        }
        return 10;
    }

    public static int getObjId(int id) {
        for (int i = 0; i < objectData.length; i++) {
            if (objectData[i][0] == id) {
                return objectData[i][1];
            }
        }
        return -1;
    }
    
    public static void handleConstructionClick(Client player, int id, int x, int y) {
        int locX = player.absX % 8;
        int locY = player.absY % 8;
        if (IsInHisHouse(player)) {
            switch (id) {
                case 15364:
                case 15361:
                    createObject(player, getObjId(id), x, y, getObjType(id), player.objRot);
                    break;
                case 15314:
                case 15313:
                    Room room = GetRoomToBuild(player);
                    if (locX == 3 && locY == 0 || locX == 4 && locY == 0) {//LEFT
                        GetHouse(player).AddRoom(player, room, player.absX / 8, (player.absY - 1) / 8);
                    }
                    if (locX == 0 && locY == 3 || locX == 0 && locY == 4) {//TOP
                        room.rot = 1;
                        GetHouse(player).AddRoom(player, room, (player.absX - 1) / 8, player.absY / 8);
                    }
                    if (locX == 3 && locY == 7 || locX == 4 && locY == 7) {//RIGHT
                        room.rot = 2;
                        GetHouse(player).AddRoom(player, room, player.absX / 8, (player.absY + 1) / 8);
                    }
                    if (locX == 7 && locY == 3 || locX == 7 && locY == 4) {//BOTTOM
                        room.rot = 3;
                        GetHouse(player).AddRoom(player, room, (player.absX + 1) / 8, player.absY / 8);
                    }
                    break;
            }
            player.getDH().sendDialogues(62, 0);
        }
        else {
            player.sendMessage("You are only allowed to build in your house!");
        }
    }
    
    public static void createObject(Client player, int id, int x, int y, int type, int face) {
        if (player != null) {
            HouseObject object = new HouseObject(id, x, y, player.heightLevel, type, face);
            player.objectId = id;
            player.objRot = face;
            new server.model.objects.Object(object.id, object.y, object.x, player.playerId * 4, player.objRot, getObjType(object.id), object.id, 0);
            /*object.id = id;
			object.x = player.objectX;
			object.y = player.objectY;
			player.getOutStream().createFrame(85);
			player.getOutStream().writeByteC(object.y - player.getMapRegionY() * 8);
			player.getOutStream().writeByteC(object.x - player.getMapRegionX() * 8);
			player.getOutStream().createFrame(101);
			player.getOutStream().writeByteC(object.rot);
			player.getOutStream().writeByte(0);
			player.getOutStream().createFrame(151);
			player.getOutStream().writeByteS(0);
			player.getOutStream().writeWordBigEndian(object.id);
			player.getOutStream().writeByteS(object.rot);
			player.flushOutStream();*/
            GetHouse(player).objects.add(object);
            GetHouse(player).LoadObjects(player);
        }
    }
    
    public static Room GetRoomToBuild(Client c){
        Room type = RoomType.CORRIDOR_DUNGEON;
        Room room = new Room(type.x, type.y, type.z, type.rot);
        return room;
    }

    public static boolean handleButtons(Client c, int actionButtonId) {
        if (IsInHisHouse(c)) {
            switch (actionButtonId) {
                case 9168:
                    if (c.objRot > 3) {
                        c.objRot++;
                    }
                    createObject(c, getObjId(c.objId), c.objectX, c.objectY, getObjType(c.objId), c.objRot);
                    GetHouse(c).LoadObjects(c);
                    break;
            }
        }
        return false;
    }

    public static String GetRoomType(Room room) {
        if (IsTheSameRoom(room, RoomType.DEFAULT)) {
            return "DEFAULT";
        } else if (IsTheSameRoom(room, RoomType.GARDEN)) {
            return "GARDEN";
        } else if (IsTheSameRoom(room, RoomType.THRONE)) {
            return "THRONE";
        } else if (IsTheSameRoom(room, RoomType.GAME)) {
            return "GAME";
        } else if (IsTheSameRoom(room, RoomType.FLOOR2)) {
            return "FLOOR2";
        } else if (IsTheSameRoom(room, RoomType.PARLOUR)) {
            return "PARLOUR";
        } else if (IsTheSameRoom(room, RoomType.KITCHEN)) {
            return "KITCHEN";
        } else if (IsTheSameRoom(room, RoomType.DINING)) {
            return "DINING";
        } else if (IsTheSameRoom(room, RoomType.WORKSHOP)) {
            return "WORKSHOP";
        } else if (IsTheSameRoom(room, RoomType.BEDROOM)) {
            return "BEDROOM";
        } else if (IsTheSameRoom(room, RoomType.SKILLHALL)) {
            return "SKILLHALL";
        } else if (IsTheSameRoom(room, RoomType.COMBAT)) {
            return "COMBAT";
        } else if (IsTheSameRoom(room, RoomType.QUEST_HALL)) {
            return "QUEST_HALL";
        } else if (IsTheSameRoom(room, RoomType.STUDY)) {
            return "STUDY";
        } else if (IsTheSameRoom(room, RoomType.COSTUME_ROOM)) {
            return "COSTUME_ROOM";
        } else if (IsTheSameRoom(room, RoomType.CHAPEL)) {
            return "CHAPEL";
        } else if (IsTheSameRoom(room, RoomType.PORTAL_CHAMBER)) {
            return "PORTAL_CHAMBER";
        } else if (IsTheSameRoom(room, RoomType.FORMAL_GARDEN)) {
            return "FORMAL_GARDEN";
        } else if (IsTheSameRoom(room, RoomType.THRONE_ROOM)) {
            return "THRONE_ROOM";
        } else if (IsTheSameRoom(room, RoomType.OUBILIETTE)) {
            return "OUBILIETTE";
        } else if (IsTheSameRoom(room, RoomType.CORRIDOR_DUNGEON)) {
            return "CORRIDOR_DUNGEON";
        } else if (IsTheSameRoom(room, RoomType.JUNCTION_DUNGEON)) {
            return "JUNCTION_DUNGEON";
        } else if (IsTheSameRoom(room, RoomType.STAIRS_DUNGEON)) {
            return "STAIRS_DUNGEON";
        } else if (IsTheSameRoom(room, RoomType.TREASURE_ROOM)) {
            return "TREASURE_ROOM";
        }
        return "";
    }
    
    public static boolean IsTheSameRoom(Room room1, Room room2){
        if (room1.x == room2.x && room1.y == room2.y && room1.z == room2.z){
            return true;
        }
        if (room1.GetLocationHash() == room2.GetLocationHash()){
            return true;
        }
        return false;
    }

    public static void ParseCommand(Client c, String command) {
        String[] args = command.split(" ");
        if (args[0].equalsIgnoreCase("help")) {
            if (args.length == 1) {
                c.sendMessage("Use ::construction.commands for a list of commands");
                c.sendMessage("Use ::construction.help <command> for help regarding a command");
            } else if (args.length == 2) {
                if (args[1].equalsIgnoreCase("showHouse")) {
                    c.sendMessage("Usage: ::construction.showHouse [<id>]");
                    c.sendMessage("Teleports the player to their house. If an id is specified, it teleports the player");
                    c.sendMessage("to the house with the specified id.");
                } else if (args[1].equalsIgnoreCase("makeHouse")) {
                    c.sendMessage("Usage: ::construction.makeHouse");
                    c.sendMessage("Creates a new house if it didn't exist for the current player");
                } else if (args[1].equalsIgnoreCase("showHouses")) {
                    c.sendMessage("Usage: ::construction.showHouses");
                    c.sendMessage("Lists all the houses currently created");
                } else if (args[1].equalsIgnoreCase("addRoom")) {
                    c.sendMessage("Usage: ::construction.addroom <x> <y> <height> <type>");
                    c.sendMessage("Adds a room at the specified location");
                } else if (args[1].equalsIgnoreCase("deleteHouses")) {
                    c.sendMessage("Usage: ::construction.deleteHouses");
                    c.sendMessage("Deletes all the houses from the server");
                } else if (args[1].equalsIgnoreCase("remakeHouse")) {
                    c.sendMessage("Usage: ::construction.remakeHouse <playerId>");
                    c.sendMessage("Reinitializes the house of the player with the specified Id");
                } else {
                    SendSyntaxError(c, "construction.help <command>");
                }
            } else {
                SendSyntaxError(c, "construction.help <command>");
            }
        } else if (args[0].equalsIgnoreCase("commands")){
            c.sendMessage("Fishing commands:");
            c.sendMessage(" showHouse");
            c.sendMessage(" makeHouse");
            c.sendMessage(" showHouses");
            c.sendMessage(" addRoom");
            c.sendMessage(" deleteHouses");
            c.sendMessage(" remakeHouse");
        } else if (args[0].equalsIgnoreCase("showHouse")) {
            if (args.length == 1)
                EnterHouse(c);
            else {
                if (args.length == 2){
                    try{
                        EnterHouse(c, Integer.parseInt(args[1]));
                    }
                    catch(Exception e){
                        SendSyntaxError(c, "construction.showHouse [<id>]");
                    }
                }
            }
        } else if (args[0].equalsIgnoreCase("makeHouse")) {
            CreateHouse(c);
        } else if (args[0].equalsIgnoreCase("showHouses")) {
            for (int i = 0; i < houses.size(); i++) {
                c.sendMessage("Id: " + i + " owner: " + houses.get(i).ownerName + " heightLevel: " + houses.get(i).GetHeightLevel());
            }
        } else if (args[0].equalsIgnoreCase("addRoom")) {
            if (IsInHisHouse(c)) {
                Misc.println(args.length+"");
                try
                {
                    Room room = RoomType.GetRoom(args[4]);
                    GetHouse(c).AddRoom(c, room, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                }
                catch(Exception e){
                    e.printStackTrace();
                    SendSyntaxError(c, "construction.addroom <x> <y> <height> <type>");
                }
            } else {
                c.sendMessage("You can only add rooms to your house");
            }
        } else if (args[0].equalsIgnoreCase("deleteHouses")) {
            houses.clear();
        } else if (args[0].equalsIgnoreCase("remakeHouse")) {
            try{
                int id = Integer.parseInt(args[1]);
                House house = GetHouse((Client)PlayerHandler.players[id]);
                houses.remove(house);
                house = new House((Client)PlayerHandler.players[id], house.heightLevel);
                houses.add(house);
                SortHouses();
            }catch(Exception e){
                SendSyntaxError(c, "construction.remakeHouse <playerId>");
            }
        } else {
            SendSyntaxError(c, "construction.help");
        }
    }
}
