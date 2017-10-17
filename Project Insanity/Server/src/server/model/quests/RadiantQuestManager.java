/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.quests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import server.Config;
import server.model.npcs.DBNPC;
import static server.model.npcs.NPCDataBase.FindDBNPC;
import server.util.Misc;
import server.world.Tile;

/**
 *
 * @author amaco
 */
public class RadiantQuestManager {
    public static ArrayList<RadiantQuest> radiantQuests = new ArrayList<>();
    
    public static void LoadRadiantQuests() {
        File path = new File("./data/Quests");
        for (File fileEntry : path.listFiles()) {
            if (fileEntry.isDirectory()) {
                
            } else {
                if (Config.SERVER_DEBUG){
                    Misc.println(fileEntry.getName());
                }
                RadiantQuest quest = LoadQuest(fileEntry);
                if (quest != null){
                    AddRadiantQuest(quest);
                }
            }
        }
    }

    static RadiantQuest LoadQuest(File file) {
        try {
            RadiantQuest quest = new RadiantQuest();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            
            Node root = doc.getElementsByTagName("Quest").item(0);
            Element root_elem = (Element)root;
            
            quest.id = Integer.parseInt(root_elem.getElementsByTagName("id").item(0).getTextContent());
            
            quest.name = root_elem.getElementsByTagName("name").item(0).getTextContent();
            
            quest.description = root_elem.getElementsByTagName("description").item(0).getTextContent();
            
            Node skillReq = root_elem.getElementsByTagName("skillLevelRequirements").item(0);
            Element skillReq_elem = (Element)skillReq;
            NodeList nList = skillReq_elem.getElementsByTagName("skill");
            quest.skillLevelRequirements = new int[nList.getLength()];
            for(int i = 0; i < nList.getLength(); i++){
                quest.skillLevelRequirements[i] = Integer.parseInt(nList.item(i).getTextContent());
            }
            
            quest.combatLevelRequirement = Integer.parseInt(root_elem.getElementsByTagName("combatLevelRequirement").item(0).getTextContent());
            
            nList = root_elem.getElementsByTagName("RadiantQuestReward");           
            for(int i = 0; i < nList.getLength(); i++){
                RadiantQuestReward reward = new RadiantQuestReward();
                Element rewardElem = (Element)nList.item(i);
                reward.itemId = Integer.parseInt(rewardElem.getElementsByTagName("itemId").item(0).getTextContent());
                reward.amount = Integer.parseInt(rewardElem.getElementsByTagName("amount").item(0).getTextContent());
                quest.itemRewards.add(reward);
            }
            
            Element expRewards = (Element) root_elem.getElementsByTagName("expRewards").item(0);
            nList = expRewards.getElementsByTagName("skill");
            quest.expRewards = new int[nList.getLength()];
            for(int i = 0; i < nList.getLength(); i++){
                quest.expRewards[i] = Integer.parseInt(nList.item(i).getTextContent());
            }
            quest.objectives = new ArrayList<>();
            Element objectives = (Element) root_elem.getElementsByTagName("objectives").item(0);
            nList = objectives.getElementsByTagName("objective");
            for(int i = 0; i < nList.getLength(); i++){
                RadiantQuestObjective objective = new RadiantQuestObjective();
                Element obj_elem = (Element)nList.item(i);
                
                objective.description = obj_elem.getElementsByTagName("description").item(0).getTextContent();
                
                String objectiveType = obj_elem.getElementsByTagName("objectiveType").item(0).getTextContent();
                switch(objectiveType){
                    case "KillNPC":
                        objective.objectiveType = RadiantQuestObjectives.Objectives.KillNPC;
                        break;
                    case "TalkToNPC":
                        objective.objectiveType = RadiantQuestObjectives.Objectives.TalkToNPC;
                        break;
                    case "GiveItemToNPC":
                        objective.objectiveType = RadiantQuestObjectives.Objectives.GiveItemToNPC;
                        break;
                    case "GetItem":
                        objective.objectiveType = RadiantQuestObjectives.Objectives.GetItem;
                        break;
                }
                
                objective.targetNPCId = Integer.parseInt(obj_elem.getElementsByTagName("targetNPCId").item(0).getTextContent());
                
                objective.objectiveInt = Integer.parseInt(obj_elem.getElementsByTagName("objectiveInt").item(0).getTextContent());
                
                int x,y,z;
                Element location = (Element)obj_elem.getElementsByTagName("location").item(0);
                x = Integer.parseInt(location.getElementsByTagName("x").item(0).getTextContent());
                y = Integer.parseInt(location.getElementsByTagName("y").item(0).getTextContent());
                z = Integer.parseInt(location.getElementsByTagName("height").item(0).getTextContent());
                objective.location = new Tile(x,y,z);          
                
                objective.locationAreaSize = Integer.parseInt(obj_elem.getElementsByTagName("locationAreaSize").item(0).getTextContent());
                
                objective.score = Integer.parseInt(obj_elem.getElementsByTagName("score").item(0).getTextContent());
                
                quest.objectives.add(objective);
            }
            
            if (Config.SERVER_DEBUG) {
                Misc.println("---------------------");
                Misc.println(quest.id + "");
                Misc.println(quest.name);
                Misc.println(quest.description);
                Misc.println(quest.combatLevelRequirement + "");
                for (int i : quest.skillLevelRequirements) {
                    Misc.println("Skill: " + i);
                }
                for (int i : quest.expRewards){
                    Misc.println("Reward: " + i);
                }
                for (RadiantQuestReward reward : quest.itemRewards){
                    Misc.println("ItemReward: " + reward.itemId + " Amount: " + reward.amount);
                }
                for (RadiantQuestObjective objective : quest.objectives){
                    Misc.println("Objective");
                    Misc.println("\t" + objective.description);
                    Misc.println("\t" + objective.objectiveType.toString());
                    Misc.println("\t" + objective.objectiveInt);
                    Misc.println("\t" + objective.targetNPCId);
                    Misc.println("\t" + objective.score);
                    Misc.println("\t" + "x: " + objective.location.getTileX() + " y: " + objective.location.getTileY() + " z: " + objective.location.getTileHeight());
                    Misc.println("\t" + objective.locationAreaSize);
                }
                Misc.println("---------------------");
            }
            
            return quest;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    static void AddRadiantQuest(RadiantQuest quest) {
        int index = 0;
        for (RadiantQuest q : radiantQuests) {
            index++;
        }
        quest.id = index;
        radiantQuests.add(index, quest);
    }

    static void RemoveRadiantQuest(RadiantQuest quest) {
        radiantQuests.remove(quest);
    }

    static void RemoveRadiantQuest(int index) {
        radiantQuests.remove(index);
    }
}
