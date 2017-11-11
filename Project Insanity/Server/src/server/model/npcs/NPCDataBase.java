/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.npcs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import server.Config;
import server.util.Misc;

/**
 *
 * @author amaco
 */
public class NPCDataBase {
    public static ArrayList<DBNPC> NPCDatabase = new ArrayList<>();
    
    public static void InitializeNPCDB(){
        LoadNPC("./data/Npcs/Npc_List.txt");
        //ImportXML();
        LoadAnimation("./data/Npcs/Animation_List.txt");
        ExportXML();
    }
    
    public static void ExportXML(){
        BufferedWriter file;
        try{
            file = new BufferedWriter(new FileWriter("./data/Npcs/NPCDB.xml"));
            file.write("<database>");
            file.newLine();
            for (DBNPC npc : NPCDatabase) {
                file.write("<npc>");
                file.newLine();
                file.write("<id>" + npc.id + "</id>");
                file.newLine();
                file.write("<name>" + npc.name + "</name>");
                file.newLine();
                file.write("<description>" + npc.description + "</description>");
                file.newLine();
                file.write("<tilesOccupied>" + npc.tilesOccupied + "</tilesOccupied>");
                file.newLine();
                file.write("<combat>" + npc.combat + "</combat>");
                file.newLine();
                file.write("<health>" + npc.hp + "</health>");
                file.newLine();
                file.write("</npc>");
                file.newLine();
            }
            file.write("</database>");
            file.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void ImportXML(){
        try{
            File file = new File("./data/Npcs/NPCDB.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            Element root = (Element)doc.getElementsByTagName("database").item(0);
            NodeList nl = root.getElementsByTagName("npc");
            for(int i = 0; i < nl.getLength(); i++){
                DBNPC npc = new DBNPC();
                Element npc_el = (Element)nl.item(i);
                Element id = (Element)npc_el.getElementsByTagName("id").item(0);
                npc.id = Integer.parseInt(id.getTextContent());
                
                Element name = (Element)npc_el.getElementsByTagName("name").item(0);
                npc.name = name.getTextContent();
                
                Element desc = (Element)npc_el.getElementsByTagName("description").item(0);
                npc.description = desc.getTextContent();
                
                Element tiles = (Element)npc_el.getElementsByTagName("tilesOccupied").item(0);
                npc.tilesOccupied = Integer.parseInt(tiles.getTextContent());
                
                Element combat = (Element)npc_el.getElementsByTagName("combat").item(0);
                npc.combat = Integer.parseInt(combat.getTextContent());
                
                Element hp = (Element)npc_el.getElementsByTagName("health").item(0);
                npc.hp = Integer.parseInt(hp.getTextContent());
                
                NPCDatabase.add(npc);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    static boolean LoadNPC(String FileName){
        boolean EndOfFile = false;
        int ReadMode = 0;
        String line = "";
        BufferedReader characterfile = null;
        try {
            characterfile = new BufferedReader(new FileReader("./" + FileName));
        } catch (FileNotFoundException fileex) {
            Misc.println(FileName + ": file not found.");
            return false;
        }
        try {
            line = characterfile.readLine();
        } catch (IOException ioexception) {
            Misc.println(FileName + ": error loading file.");
            return false;
        }
        while (EndOfFile == false && line != null) {
            String tokens[] = line.trim().split(":");
            tokens[0] = tokens[0].replace(" ", "");
            if (tokens[0].equalsIgnoreCase("Id")){ //DETECT FIRST LINE              
            }
            else{
                DBNPC npc = new DBNPC();
                npc.id = Integer.parseInt(tokens[0]);
                String[] stokens = tokens[1].split("-");
                stokens[0] = stokens[0].trim();
                stokens[1] = stokens[1].trim();
                npc.name = stokens[0];
                npc.description = stokens[1];
                tokens[2] = tokens[2].replace(" ", "");
                npc.tilesOccupied = Integer.parseInt(tokens[2]);
                tokens[3] = tokens[3].replace(" ", "");
                npc.combat = Integer.parseInt(tokens[3]);
                tokens[4] = tokens[4].replace(" ", "");
                npc.hp = Integer.parseInt(tokens[4]);
                NPCDatabase.add(npc);
                /*if (Config.SERVER_DEBUG)
                    Misc.println(npc.name);*/
            }
            try {
                line = characterfile.readLine();
            } catch (IOException ioexception1) {
                EndOfFile = true;
            }
        }
        try {
            characterfile.close();
        } catch (IOException ioexception) {
        }
        return false;
    }
    
    static boolean LoadAnimation(String FileName){
        boolean EndOfFile = false;
        int ReadMode = 0;
        String line = "";
        BufferedReader characterfile = null;
        try {
            characterfile = new BufferedReader(new FileReader("./" + FileName));
        } catch (FileNotFoundException fileex) {
            Misc.println(FileName + ": file not found.");
            return false;
        }
        try {
            line = characterfile.readLine();
        } catch (IOException ioexception) {
            Misc.println(FileName + ": error loading file.");
            return false;
        }
        while (EndOfFile == false && line != null) {
            line = line.replace(" ", "");
            String tokens[] = line.trim().split(":");
            if (tokens[0].equalsIgnoreCase("Id")){ //DETECT FIRST LINE              
            }
            else{
                DBNPC npc = FindDBNPC(Integer.parseInt(tokens[0]));
                npc.standAnim = Integer.parseInt(tokens[1]);
                npc.walkAnim = Integer.parseInt(tokens[2]);
                npc.rotate180Anim = Integer.parseInt(tokens[3]);
                npc.rotate90LeftAnim = Integer.parseInt(tokens[4]);
                npc.rotate90RightAnim = Integer.parseInt(tokens[5]);
            }
            try {
                line = characterfile.readLine();
            } catch (IOException ioexception1) {
                EndOfFile = true;
            }
        }
        try {
            characterfile.close();
        } catch (IOException ioexception) {
        }
        return false;
    }
    
    public static DBNPC FindDBNPC(int id){
        for(DBNPC npc : NPCDatabase){
            if (npc.id == id){
                return npc;
            }
        }
        return null;
    }
    
    public static DBNPC FindDBNPC(String name){
        for(DBNPC npc : NPCDatabase){
            if (npc.name.equalsIgnoreCase(name)){
                return npc;
            }
        }
        return null;
    }
}
