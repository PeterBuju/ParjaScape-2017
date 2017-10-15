/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.content;

import server.model.players.Palette;

/**
 *
 * @author Alex Macocian
 */
public class RoomType{
    //public static final Room DEFAULT = new Room(1864, 5056, 0, 0);
    public static final Room DEFAULT = new Room(1856, 5056, 0, 0);
    public static final Room GARDEN = new Room(1872, 5064, 0, 0);
    public static final Room THRONE = new Room(1904, 5096, 0, 0);
    public static final Room GAME = new Room(1864, 5104, 0, 0);
    public static final Room FLOOR2 = new Room(1903, 5095, 0, 0);
    public static final Room PARLOUR = new Room(1856, 5112, 0, 0);
    public static final Room KITCHEN = new Room(1872, 5112, 0, 0);
    public static final Room DINING = new Room(1890, 5112, 0, 0);
    public static final Room WORKSHOP = new Room(1856, 5096, 0, 0);
    public static final Room BEDROOM = new Room(1904, 5112, 0, 0);
    public static final Room SKILLHALL = new Room(1880, 5104, 0, 0);
    public static final Room COMBAT = new Room(1880, 5088, 0, 0);
    public static final Room QUEST_HALL = new Room(1912, 5104, 0, 0);
    public static final Room STUDY = new Room(1888, 5096, 0, 0);
    public static final Room COSTUME_ROOM = new Room(1904, 5064, 0, 0);
    public static final Room CHAPEL = new Room(1872, 5096, 0, 0);
    public static final Room PORTAL_CHAMBER = new Room(1864, 5088, 0, 0);
    public static final Room FORMAL_GARDEN = new Room(1872, 5064, 0, 0);
    public static final Room THRONE_ROOM = new Room(1904, 5080, 0, 0);
    public static final Room OUBILIETTE = new Room(1904, 5080, 0, 0);
    public static final Room CORRIDOR_DUNGEON = new Room(1888, 5080, 0, 0);
    public static final Room JUNCTION_DUNGEON = new Room(1856, 5080, 0, 0);
    public static final Room STAIRS_DUNGEON = new Room(1872, 5080, 0, 0);
    public static final Room TREASURE_ROOM = new Room(1912, 5088, 0, 0);
    
    public static Room GetRoom(String name) {
        Room room = RoomType.DEFAULT;
        if (name.equalsIgnoreCase("GARDEN")) {
            room = RoomType.GARDEN;
        } else if (name.equalsIgnoreCase("THRONE")) {
            room = RoomType.THRONE;
        } else if (name.equalsIgnoreCase("GAME")) {
            room = RoomType.GAME;
        } else if (name.equalsIgnoreCase("FLOOR2")) {
            room = RoomType.FLOOR2;
        } else if (name.equalsIgnoreCase("PARLOUR")) {
            room = RoomType.PARLOUR;
        } else if (name.equalsIgnoreCase("KITCHEN")) {
            room = RoomType.KITCHEN;
        } else if (name.equalsIgnoreCase("DINING")) {
            room = RoomType.DINING;
        } else if (name.equalsIgnoreCase("WORKSHOP")) {
            room = RoomType.WORKSHOP;
        } else if (name.equalsIgnoreCase("BEDROOM")) {
            room = RoomType.BEDROOM;
        } else if (name.equalsIgnoreCase("SKILLHALL")) {
            room = RoomType.SKILLHALL;
        } else if (name.equalsIgnoreCase("COMBAT")) {
            room = RoomType.COMBAT;
        } else if (name.equalsIgnoreCase("QUEST_HALL")) {
            room = RoomType.QUEST_HALL;
        } else if (name.equalsIgnoreCase("STUDY")) {
            room = RoomType.STUDY;
        } else if (name.equalsIgnoreCase("COSTUME_ROOM")) {
            room = RoomType.COSTUME_ROOM;
        } else if (name.equalsIgnoreCase("CHAPEL")) {
            room = RoomType.CHAPEL;
        } else if (name.equalsIgnoreCase("PORTAL_CHAMBER")) {
            room = RoomType.PORTAL_CHAMBER;
        } else if (name.equalsIgnoreCase("FORMAL_GARDEN")) {
            room = RoomType.FORMAL_GARDEN;
        } else if (name.equalsIgnoreCase("THRONE_ROOM")) {
            room = RoomType.THRONE_ROOM;
        } else if (name.equalsIgnoreCase("OUBILIETTE")) {
            room = RoomType.OUBILIETTE;
        } else if (name.equalsIgnoreCase("CORRIDOR_DUNGEON")) {
            room = RoomType.CORRIDOR_DUNGEON;
        } else if (name.equalsIgnoreCase("JUNCTION_DUNGEON")) {
            room = RoomType.JUNCTION_DUNGEON;
        } else if (name.equalsIgnoreCase("STAIRS DUNGEON")) {
            room = RoomType.STAIRS_DUNGEON;
        } else if (name.equalsIgnoreCase("TREASURE_ROOM")) {
            room = RoomType.TREASURE_ROOM;
        }
        return room;
    }
}
