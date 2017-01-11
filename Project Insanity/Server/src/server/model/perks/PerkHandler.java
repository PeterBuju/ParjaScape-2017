/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.perks;
import java.util.ArrayList;
import server.model.perks.Perk;
import server.model.players.Stats;
/**
 *
 * @author Alex Macocian
 */
public class PerkHandler {
    public static ArrayList<Perk> perkList = new ArrayList<>();
    
    public static void InitializePerks(){
        perkList.add(new Perk(0, "Critical Strike", "Critically strikes an enemy for 200% the normal damage.", Stats.GetStatId("Speed"), 6, 27));
        perkList.add(new Perk(1, "Evasiveness", "Reduces the chance of being hit by a percentage that scales with your Speed level", Stats.GetStatId("Speed"), 6, 12));
        perkList.add(new Perk(2, "Magnetic", "A succesful thieving action now gives gold ranging from 0 to 50 times your Speed level", Stats.GetStatId("Speed"), 5, 15));
        perkList.add(new Perk(3, "Deft Hands", "Thieving now gives a random amount of bonus experience ranging from 0 to your Speed level", Stats.GetStatId("Speed"), 5, 5));
        perkList.add(new Perk(4, "Air attunement", "Allows you to cast wind strike without rune cost", Stats.GetStatId("Wisdom"), 6, 15));
        perkList.add(new Perk(5, "Water attunement", "Allows you to cast water strike without rune cost", Stats.GetStatId("Wisdom"), 6, 21));
        perkList.add(new Perk(6, "Earth attunement", "Allows you to cast earth strike without rune cost", Stats.GetStatId("Wisdom"), 6, 28));
        perkList.add(new Perk(7, "Fire attunement", "Allows you to cast fire strike without rune cost", Stats.GetStatId("Wisdom"), 6, 35));
        perkList.add(new Perk(8, "Magical Fingers", "Runecrafting has a chance to produce more runes", Stats.GetStatId("Wisdom"), 5, 27));
        perkList.add(new Perk(9, "Persuasive", "Gives the ability to persuade certain people", Stats.GetStatId("Charisma"), 6, 3));
        perkList.add(new Perk(10, "Inspiration", "When talking to random people, they might offer you gifts", Stats.GetStatId("Charisma"), 7, 3));
        perkList.add(new Perk(11, "Adoration", "After peroming quests for people, they will give you periodical rewards", Stats.GetStatId("Charisma"), 8, 30));
        perkList.add(new Perk(12, "Renown", "People will offer you special interactions when talking to them", Stats.GetStatId("Charisma"), 9, 12));
        perkList.add(new Perk(13, "Golden tongue", "When buying an item, you have 3% chance of getting it for free", Stats.GetStatId("Charisma"), 10, 3));
    }
    
    public static Perk GetPerk(int id){
        for (Perk perk : perkList){
            if (perk.getId() == id){
                return perk;
            }
        }
        return null;
    }
    
    public static Perk GetPerk(String name){
        for (Perk perk : perkList){
            if (perk.getName().equalsIgnoreCase(name)){
                return perk;
            }
        }
        return null;
    }
}
