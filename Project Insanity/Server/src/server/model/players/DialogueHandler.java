package server.model.players;

public class DialogueHandler {

	private Client c;
	
	public DialogueHandler(Client client) {
		this.c = client;
	}
	
	/**
	 * Handles all talking
	 * @param dialogue The dialogue you want to use
	 * @param npcId The npc id that the chat will focus on during the chat
	 */
	public void sendDialogues(int dialogue, int npcId) {
		c.talkingNpc = npcId;
                String[] dialogueList;
		switch(dialogue) {
		case 0:
			c.talkingNpc = -1;
			c.getPA().removeAllWindows();
			c.nextChat = 0;
			break;
		case 1:
			sendStatement("You found a hidden tunnel! Do you want to enter it?");
			c.dialogueAction = 1;
			c.nextChat = 2;
			break;
		case 2:
			sendOption2("Yea! I'm fearless!",  "No way! That looks scary!");
			c.dialogueAction = 1;
			c.nextChat = 0;
			break;
		case 3:
			sendNpcChat4("Hello!", "My name is Duradel and I am a master of the slayer skill.", "I can assign you a slayer task suitable to your combat level.", 
			"Would you like a slayer task?", c.talkingNpc, "Duradel");
			c.nextChat = 4;
		break;
		case 5:
			sendNpcChat4("Hello adventurer...", "My name is Kolodion, the master of this mage bank.", "Would you like to play a minigame in order ", 
						"to earn points towards recieving magic related prizes?", c.talkingNpc, "Kolodion");
			c.nextChat = 6;
		break;
		case 6:
			sendNpcChat4("The way the game works is as follows...", "You will be teleported to the wilderness,", 
			"You must kill mages to recieve points,","redeem points with the chamber guardian.", c.talkingNpc, "Kolodion");
			c.nextChat = 15;
		break;
		case 11:
			sendNpcChat4("Hello!", "My name is Duradel and I am a master of the slayer skill.", "I can assign you a slayer task suitable to your combat level.", 
			"Would you like a slayer task?", c.talkingNpc, "Duradel");
			c.nextChat = 12;
		break;
		case 12:
			sendOption2("Yes I would like a slayer task.", "No I would not like a slayer task.");
			c.dialogueAction = 5;
		break;
		case 13:
			sendNpcChat4("Hello!", "My name is Duradel and I am a master of the slayer skill.", "I see I have already assigned you a task to complete.", 
			"Would you like me to give you an easier task?", c.talkingNpc, "Duradel");
			c.nextChat = 14;
		break;
		case 14:
			sendOption2("Yes I would like an easier task.", "No I would like to keep my task.");
			c.dialogueAction = 6;
		break;
		case 15:
			sendOption2("Yes I would like to play", "No, sounds too dangerous for me.");
			c.dialogueAction = 7;
		break;
		case 16:
			sendOption2("I would like to reset my barrows brothers.", "I would like to fix all my barrows");
			c.dialogueAction = 8;
		break;
		case 17:
			sendOption5("Air", "Mind", "Water", "Earth", "More");
			c.dialogueAction = 10;
			c.dialogueId = 17;
			c.teleAction = -1;
		break;
		case 18:
			sendOption5("Fire", "Body", "Cosmic", "Astral", "More");
			c.dialogueAction = 11;
			c.dialogueId = 18;
			c.teleAction = -1;
		break;
		case 19:
			sendOption5("Nature", "Law", "Death", "Blood", "More");
			c.dialogueAction = 12;
			c.dialogueId = 19;
			c.teleAction = -1;
		break;
                case 20:
                    dialogueList = new String[4];
                    dialogueList[0] = "Welcome to @gre@PARJASCAPE!";
                    dialogueList[1] = "This is a test chat";
                    dialogueList[2] = "You'll have to choose a starting class";
                    dialogueList[3] = "Oh, and you have an ugly ass character";
                    sendNpcChat(dialogueList, c.talkingNpc, "Recruiter");
                    c.nextChat = 21;
                break;
                case 21:
                    sendPlayerChat1("Of mai!");
                    c.nextChat = 22;
                break;
                case 22:
                    dialogueList = new String[4];
                    dialogueList[0] = "Mage";
                    dialogueList[1] = "Archer";
                    dialogueList[2] = "Warrior";
                    dialogueList[3] = "Return";
                    sendOption(dialogueList, "Choose a profession");
                    c.sendMessage("Length: " + dialogueList.length);
                    c.dialogueAction = 21;
                }
	}
	
	/*
	 * Information Box
	 */
	
	public void sendStartInfo(String text, String text1, String text2, String text3, String title) {
		c.getPA().sendFrame126(title, 6180);
		c.getPA().sendFrame126(text, 6181);
		c.getPA().sendFrame126(text1, 6182);
		c.getPA().sendFrame126(text2, 6183);
		c.getPA().sendFrame126(text3, 6184);
		c.getPA().sendFrame164(6179);
	}
	
	/*
	 * Options
	 */
	
        private void sendOption (String[] list){
                for (int i = 0; i < 5; i++){
                    if (list.length == 2){
                       sendOption(list[0], list[1]);
                    }
                    else if (list.length == 3){
                        sendOption3(list[0], list[1], list[2]);
                    }
                    else if (list.length == 4){
                        sendOption4(list[0], list[1], list[2], list[3]);
                    }
                    else if (list.length == 5){
                        sendOption5(list[0], list[1], list[2], list[3], list[4]);
                    }                   
                }
        }
        
        private void sendOption (String[] list, String title){
                for (int i = 0; i < 5; i++){
                    if (list.length == 2){
                       sendOption(list[0], list[1]);
                    }
                    else if (list.length == 3){
                        sendOption3(list[0], list[1], list[2]);
                    }
                    else if (list.length == 4){
                        sendOption4(list[0], list[1], list[2], list[3]);
                    }
                    else if (list.length == 5){
                        sendOption5(list[0], list[1], list[2], list[3], list[4]);
                    }                   
                }
                c.getPA().sendFrame126(title, 2493);
        }
        
	private void sendOption(String s, String s1) {
		c.getPA().sendFrame126("Select an Option", 2470);
	 	c.getPA().sendFrame126(s, 2471);
		c.getPA().sendFrame126(s1, 2472);
		c.getPA().sendFrame126("Click here to continue", 2473);
		c.getPA().sendFrame164(13758);
	}	
	
	private void sendOption2(String s, String s1) {
		c.getPA().sendFrame126("Select an Option", 2460);
		c.getPA().sendFrame126(s, 2461);
		c.getPA().sendFrame126(s1, 2462);
		c.getPA().sendFrame164(2459);
	}
	
	private void sendOption3(String s, String s1, String s2) {
		c.getPA().sendFrame126("Select an Option", 2460);
		c.getPA().sendFrame126(s, 2471);
		c.getPA().sendFrame126(s1, 2472);
		c.getPA().sendFrame126(s2, 2473);
		c.getPA().sendFrame164(2459);
	}
	
	public void sendOption4(String s, String s1, String s2, String s3) {
		c.getPA().sendFrame126("Select an Option", 2481);
		c.getPA().sendFrame126(s, 2482);
		c.getPA().sendFrame126(s1, 2483);
		c.getPA().sendFrame126(s2, 2484);
		c.getPA().sendFrame126(s3, 2485);
		c.getPA().sendFrame164(2480);
	}
	
	public void sendOption5(String s, String s1, String s2, String s3, String s4) {
		c.getPA().sendFrame126("Select an Option", 2493);
		c.getPA().sendFrame126(s, 2494);
		c.getPA().sendFrame126(s1, 2495);
		c.getPA().sendFrame126(s2, 2496);
		c.getPA().sendFrame126(s3, 2497);
		c.getPA().sendFrame126(s4, 2498);
		c.getPA().sendFrame164(2492);
	}

	/*
	 * Statements
	 */
	
	private void sendStatement(String s) { // 1 line click here to continue chat box interface
		c.getPA().sendFrame126(s, 357);
		c.getPA().sendFrame126("Click here to continue", 358);
		c.getPA().sendFrame164(356);
	}
	
	/*
	 * Npc Chatting
	 */
	private void sendNpcChat(String[] list, int ChatNpc, String name){
                c.getPA().sendFrame200(4901, 591);
		c.getPA().sendFrame126(name, 4902);
                for (int i = 0; i < 4; i++){
                    if (list[i] != ""){
                        c.getPA().sendFrame126(list[i], 4903 + i);
                    }
                }
		c.getPA().sendFrame75(ChatNpc, 4901);
		c.getPA().sendFrame164(4900);
        }
	private void sendNpcChat1(String s, int ChatNpc, String name) {
                c.getPA().sendFrame200(4901, 591);
		c.getPA().sendFrame126(name, 4902);
		c.getPA().sendFrame126(s, 4903);
		c.getPA().sendFrame75(ChatNpc, 4901);
		c.getPA().sendFrame164(4900);
	}
	
        private void sendNpcChat2(String s, String s1, int ChatNpc, String name){
                c.getPA().sendFrame200(4901, 591);
		c.getPA().sendFrame126(name, 4902);
		c.getPA().sendFrame126(s, 4903);
		c.getPA().sendFrame126(s1, 4904);
		c.getPA().sendFrame75(ChatNpc, 4901);
		c.getPA().sendFrame164(4900);
        }
        
	private void sendNpcChat4(String s, String s1, String s2, String s3, int ChatNpc, String name) {
		c.getPA().sendFrame200(4901, 591);
		c.getPA().sendFrame126(name, 4902);
		c.getPA().sendFrame126(s, 4903);
		c.getPA().sendFrame126(s1, 4904);
		c.getPA().sendFrame126(s2, 4905);
		c.getPA().sendFrame126(s3, 4906);
		c.getPA().sendFrame75(ChatNpc, 4901);
		c.getPA().sendFrame164(4900);
	}
	
	/*
	 * Player Chating Back
	 */
	
        private void sendPlayerChat(String[] list){
                c.getPA().sendFrame200(987, 591);
		c.getPA().sendFrame126(c.playerName, 988);
                for (int i = 0; i < 4; i++){
                    if (list[i] != ""){
                        c.getPA().sendFrame126(list[i], 989 + i);
                    }
                }
		c.getPA().sendFrame185(987);
		c.getPA().sendFrame164(986);
        }
        
	private void sendPlayerChat1(String s) {
		c.getPA().sendFrame200(969, 591);
		c.getPA().sendFrame126(c.playerName, 970);
		c.getPA().sendFrame126(s, 971);
		c.getPA().sendFrame185(969);
		c.getPA().sendFrame164(968);
	}
	
	private void sendPlayerChat2(String s, String s1) {
		c.getPA().sendFrame200(974, 591);
		c.getPA().sendFrame126(c.playerName, 975);
		c.getPA().sendFrame126(s, 976);
		c.getPA().sendFrame126(s1, 977);
		c.getPA().sendFrame185(974);
		c.getPA().sendFrame164(973);
	}
	
	private void sendPlayerChat3(String s, String s1, String s2) {
		c.getPA().sendFrame200(980, 591);
		c.getPA().sendFrame126(c.playerName, 981);
		c.getPA().sendFrame126(s, 982);
		c.getPA().sendFrame126(s1, 983);
		c.getPA().sendFrame126(s2, 984);
		c.getPA().sendFrame185(980);
		c.getPA().sendFrame164(979);
	}
	
	private void sendPlayerChat4(String s, String s1, String s2, String s3) {
		c.getPA().sendFrame200(987, 591);
		c.getPA().sendFrame126(c.playerName, 988);
		c.getPA().sendFrame126(s, 989);
		c.getPA().sendFrame126(s1, 990);
		c.getPA().sendFrame126(s2, 991);
		c.getPA().sendFrame126(s3, 992);
		c.getPA().sendFrame185(987);
		c.getPA().sendFrame164(986);
	}
        
        public static void HandleDialogue(int action, Client c){
            int option = -1;
            if (action >= 9190 && action <= 9194 || action >= 9157 && action <= 9158 || action <= 9181 && action  >= 9178)
                option = GetSelectedOption(action);
            
            if (c.dialogueAction == 21){    //STARTER DIALOGUE
                if (option == 0){
                    c.getPA().MageStarter();
                    c.tutorial = 1;
                }
                if (option == 1){
                    c.getPA().ArcherStarter();
                    c.tutorial = 1;
                }
                if (option == 2){
                    c.getPA().WarriorStarter();
                    c.tutorial = 1;
                }
            }
            c.dialogueAction = -1;
            c.getPA().removeAllWindows();
        }
        
        public static int GetSelectedOption(int action){
            int selectedOption = -1;
            if (action == 9190)
                selectedOption = 0;
            else if (action == 9191)
                selectedOption = 1;
            else if (action == 9192)
                selectedOption = 2;
            else if (action == 9193)
                selectedOption = 3;
            else if (action == 9194)
                selectedOption = 4;
            else if (action == 9157)
                selectedOption = 0;
            else if (action == 9158)
                selectedOption = 1;
            else if (action == 9178)
                selectedOption = 0;
            else if (action == 9179)
                selectedOption = 1;
            else if (action == 9180)
                selectedOption = 2;
            else if (action == 9181)
                selectedOption = 3;
            return selectedOption;
        }
}
