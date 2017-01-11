package server.model.players;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Future;

import org.apache.mina.common.IoSession;

import server.Config;
import server.Server;
import server.event.CycleEventHandler;
import server.model.items.ItemAssistant;
import server.model.shops.ShopAssistant;
import server.net.HostList;
import server.net.Packet;
import server.net.StaticPacketBuilder;
import server.util.Misc;
import server.util.Stream;
import server.model.players.skills.*;
import server.event.EventManager;
import server.event.Event;
import server.event.EventContainer;

public class Client extends Player {

	public byte buffer[] = null;
	public Stream inStream = null, outStream = null;
	private IoSession session;
	private ItemAssistant itemAssistant = new ItemAssistant(this);
	private ShopAssistant shopAssistant = new ShopAssistant(this);
	private TradeAndDuel tradeAndDuel = new TradeAndDuel(this);
	private PlayerAssistant playerAssistant = new PlayerAssistant(this);
	private CombatAssistant combatAssistant = new CombatAssistant(this);
	private ActionHandler actionHandler = new ActionHandler(this);
	private PlayerKilling playerKilling = new PlayerKilling(this);
	private DialogueHandler dialogueHandler = new DialogueHandler(this);
	private Queue<Packet> queuedPackets = new LinkedList<Packet>();
	private Potions potions = new Potions(this);
	private PotionMixing potionMixing = new PotionMixing(this);
	private Food food = new Food(this);
	/**
	 * Skill instances
	 */
	private Slayer slayer = new Slayer(this);
	private Runecrafting runecrafting = new Runecrafting(this);
	private Woodcutting woodcutting = new Woodcutting(this);
	private Mining mine = new Mining(this);
	private Agility agility = new Agility(this);
	private Cooking cooking = new Cooking(this);
	private Fishing fish = new Fishing(this);
	private Crafting crafting = new Crafting(this);
	private Smithing smith = new Smithing(this);
	private Prayer prayer = new Prayer(this);
	private Fletching fletching = new Fletching(this);
	private SmithingInterface smithInt = new SmithingInterface(this);
	private Farming farming = new Farming(this);
	private Thieving thieving = new Thieving(this);
	private Firemaking firemaking = new Firemaking(this);
	private Herblore herblore = new Herblore(this);
	
	private int somejunk;
	public int lowMemoryVersion = 0;
	public int timeOutCounter = 0;		
	public int returnCode = 2; 
	private Future<?> currentTask;
	
	public Client(IoSession s, int _playerId) {
		super(_playerId);
		this.session = s;
		synchronized(this) {
			outStream = new Stream(new byte[Config.BUFFER_SIZE]);
			outStream.currentOffset = 0;
		}
		inStream = new Stream(new byte[Config.BUFFER_SIZE]);
		inStream.currentOffset = 0;
		buffer = new byte[Config.BUFFER_SIZE];
	}     
        
	public void flushOutStream() {	
		if(disconnected || outStream.currentOffset == 0) return;
		synchronized(this) {	
			StaticPacketBuilder out = new StaticPacketBuilder().setBare(true);
			byte[] temp = new byte[outStream.currentOffset]; 
			System.arraycopy(outStream.buffer, 0, temp, 0, temp.length);
			out.addBytes(temp);
			session.write(out.toPacket());
			outStream.currentOffset = 0;
		}
    }
	
	public void sendClan(String name, String message, String clan, int rights) {
		outStream.createFrameVarSizeWord(217);
		outStream.writeString(name);
		outStream.writeString(message);
		outStream.writeString(clan);
		outStream.writeWord(rights);
		outStream.endFrameVarSize();
	}
	
	public static final int PACKET_SIZES[] = {
		0, 0, 0, 1, -1, 0, 0, 0, 0, 0, //0
		0, 0, 0, 0, 8, 0, 6, 2, 2, 0,  //10
		0, 2, 0, 6, 0, 12, 0, 0, 0, 0, //20
		0, 0, 0, 0, 0, 8, 4, 0, 0, 2,  //30
		2, 6, 0, 6, 0, -1, 0, 0, 0, 0, //40
		0, 0, 0, 12, 0, 0, 0, 8, 8, 12, //50
		8, 8, 0, 0, 0, 0, 0, 0, 0, 0,  //60
		6, 0, 2, 2, 8, 6, 0, -1, 0, 6, //70
		0, 0, 0, 0, 0, 1, 4, 6, 0, 0,  //80
		0, 0, 0, 0, 0, 3, 0, 0, -1, 0, //90
		0, 13, 0, -1, 0, 0, 0, 0, 0, 0,//100
		0, 0, 0, 0, 0, 0, 0, 6, 0, 0,  //110
		1, 0, 6, 0, 0, 0, -1, 0, 2, 6, //120
		0, 4, 6, 8, 0, 6, 0, 0, 0, 2,  //130
		0, 0, 0, 0, 0, 6, 0, 0, 0, 0,  //140
		0, 0, 1, 2, 0, 2, 6, 0, 0, 0,  //150
		0, 0, 0, 0, -1, -1, 0, 0, 0, 0,//160
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  //170
		0, 8, 0, 3, 0, 2, 0, 0, 8, 1,  //180
		0, 0, 12, 0, 0, 0, 0, 0, 0, 0, //190
		2, 0, 0, 0, 0, 0, 0, 0, 4, 0,  //200
		4, 0, 0, 0, 7, 8, 0, 0, 10, 0, //210
		0, 0, 0, 0, 0, 0, -1, 0, 6, 0, //220
		1, 0, 0, 0, 6, 0, 6, 8, 1, 0,  //230
		0, 4, 0, 0, 0, 0, -1, 0, -1, 4,//240
		0, 0, 6, 6, 0, 0, 0            //250
	};

	public void destruct() {
		if(session == null) 
			return;
                CycleEventHandler.getSingleton().stopEvents(this);
		//PlayerSaving.getSingleton().requestSave(playerId);
		getPA().removeFromCW();
		if (inPits)
			Server.fightPits.removePlayerFromPits(playerId);
		if (clanId >= 0)
			Server.clanChat.leaveClan(playerId, clanId);
		Misc.println("[DEREGISTERED]: "+playerName+"");
		HostList.getHostList().remove(session);
		disconnected = true;
		session.close();
		session = null;
		inStream = null;
		outStream = null;
		isActive = false;
		buffer = null;
		super.destruct();
	}
	
	
	public void sendMessage(String s) {
		synchronized (this) {
			if(getOutStream() != null) {
				outStream.createFrameVarSize(253);
				outStream.writeString(s);
				outStream.endFrameVarSize();
			}
		}
	}

	public void setSidebarInterface(int menuId, int form) {
		synchronized (this) {
			if(getOutStream() != null) {
				outStream.createFrame(71);
				outStream.writeWord(form);
				outStream.writeByteA(menuId);
			}
		}
	}	
	
        public void customInterface(int id){
            getPA().showInterface(id);
            getPA().sendFrame126("Close Window", id+1);//Close Text
            /*
            getPA().sendFrame126("", 3026);//Line 1
            getPA().sendFrame126("", 3027);//Line 2
            getPA().sendFrame126("", 3028);//Line 3
            getPA().sendFrame126("", 3029);//Line 4
            getPA().sendFrame126("", 3030);//Line 5
            getPA().sendFrame126("", 3031);//Line 6
            getPA().sendFrame126("", 3032);//Line 7
            getPA().sendFrame126("", 3033);//Line 8
            getPA().sendFrame126("", 3034);//Line 9
            getPA().sendFrame126("", 3035);//Line 10
            getPA().sendFrame126("", 3036);//Line 11
            getPA().sendFrame126("", 3037);//Line 12
            for (int i = 0; i < 12; i++){
                getPA().sendFrame126("", id + 2 + i);
            }
            flushOutStream();*/
        }
        
        int[] questIds = {640,682,663,13136,7332,7333,7334,7336,7383,
	7339,7338,7340,7346,7341,7342,7337,7343,7335,7344,7345,7347,7348,
	12772,673,7352,12129,8438,18517,15847,12852,7354,7355,7356,8679,
	7459,7357,14912,249,6024,191,15235,15592,6987,15098,15352,18306,
	15499,668,18684,6027,18157,15847,16128,12836,16149,15841,7353,
	7358,17510,7359,14169,10115,14604,7360,12282,13577,12839,7361,
	11857,7362,7363,7364,10135,4508,11907,7365,7366,7367,13389,7369,
	12389,13974,7370,8137,7371,12345,7372,8115,8576,12139,7373,7374,
	8969,7375,7376,1740,3278,7378,6518,7379,7380,7381,11858,9927,7349,
	7350,7351,13356,15487,7368,11132};
        
        public void showStats(){
            for (int i = 0; i <= 6; i++)
                getPA().sendFrame126(Stats.GetStatName(i) + " : " + stats[i], questIds[i+4]);       
        }
        
        public void clearQuestsTab(){
            Client c = this;
            for (int x : questIds){
                getPA().sendFrame126("", x);
            }
            getPA().sendFrame126("Character Tab", questIds[0]);
            //getPA().sendFrame126("", questIds[1]);MEMBER AREA TITLE
            getPA().sendFrame126("Base Stats", questIds[2]);
        }
        
        public void clearMusicTab(){
            getPA().sendFrame126("", 18275);
            getPA().sendFrame126("", 15493);
            getPA().sendFrame126("", 11941);
            getPA().sendFrame126("", 4287);
            getPA().sendFrame126("", 4288);
            getPA().sendFrame126("", 4289);
            getPA().sendFrame126("", 4290);
            getPA().sendFrame126("", 4291);
            getPA().sendFrame126("", 4292);
            getPA().sendFrame126("", 4293);
            getPA().sendFrame126("", 4294);
            getPA().sendFrame126("", 4295);
            getPA().sendFrame126("", 4296);
            getPA().sendFrame126("", 4297);
            getPA().sendFrame126("", 4298);
            getPA().sendFrame126("", 4299);
            getPA().sendFrame126("", 4300);
            getPA().sendFrame126("", 4301);
            getPA().sendFrame126("", 4302);
            getPA().sendFrame126("", 4303);
            getPA().sendFrame126("", 4304);
            getPA().sendFrame126("", 4305);
            getPA().sendFrame126("", 4306);
            getPA().sendFrame126("", 4307);
            getPA().sendFrame126("", 4308);
            getPA().sendFrame126("", 4309);
            getPA().sendFrame126("", 4310);
            getPA().sendFrame126("", 4311);
            getPA().sendFrame126("", 4312);
            getPA().sendFrame126("", 4313);
            getPA().sendFrame126("", 4314);
            getPA().sendFrame126("", 4315);
            getPA().sendFrame126("", 4316);
            getPA().sendFrame126("", 4317);
            getPA().sendFrame126("", 4318);
            getPA().sendFrame126("", 4319);
            getPA().sendFrame126("", 4320);
            getPA().sendFrame126("", 11134);
            getPA().sendFrame126("", 8935);
            getPA().sendFrame126("", 14872);
            getPA().sendFrame126("", 664);
            getPA().sendFrame126("", 8971);
            getPA().sendFrame126("", 7454);
            getPA().sendFrame126("", 4864);
            getPA().sendFrame126("", 12128);
            getPA().sendFrame126("", 14871);
            getPA().sendFrame126("", 12127);
            getPA().sendFrame126("", 8968);
            getPA().sendFrame126("", 12844);
            getPA().sendFrame126("", 6944);
            getPA().sendFrame126("", 10983);
            getPA().sendFrame126("", 4870);
            getPA().sendFrame126("", 8436);
            getPA().sendFrame126("", 6151);
            getPA().sendFrame126("", 4880);
            getPA().sendFrame126("", 8567);
            getPA().sendFrame126("", 677);
            getPA().sendFrame126("", 13385);
            getPA().sendFrame126("", 13352);
            getPA().sendFrame126("", 10128);
            getPA().sendFrame126("", 13353);
            getPA().sendFrame126("", 8973);
            getPA().sendFrame126("", 18302);
            getPA().sendFrame126("", 14453);
            getPA().sendFrame126("", 8936);
            getPA().sendFrame126("", 8142);
            getPA().sendFrame126("", 11483);
            getPA().sendFrame126("", 4404);
            getPA().sendFrame126("", 5996);
            getPA().sendFrame126("", 10111);
            getPA().sendFrame126("", 8565);
            getPA().sendFrame126("", 14242);
            getPA().sendFrame126("", 15494);
            getPA().sendFrame126("", 17508);
            getPA().sendFrame126("", 11476);
            getPA().sendFrame126("", 15476);
            getPA().sendFrame126("", 8972);
            getPA().sendFrame126("", 12390);
            getPA().sendFrame126("", 12584);
            getPA().sendFrame126("", 14455);
            getPA().sendFrame126("", 8980);
            getPA().sendFrame126("", 11484);
            getPA().sendFrame126("", 246);
            getPA().sendFrame126("", 18302);
            getPA().sendFrame126("", 674);
            getPA().sendFrame126("", 13900);
            getPA().sendFrame126("", 12849);
            getPA().sendFrame126("", 12810);
            getPA().sendFrame126("", 11095);
            getPA().sendFrame126("", 15842);
            getPA().sendFrame126("", 3320);
            getPA().sendFrame126("", 4321);
            getPA().sendFrame126("", 12848);
            getPA().sendFrame126("", 4879);
            getPA().sendFrame126("", 12289);
            getPA().sendFrame126("", 8974);
            getPA().sendFrame126("", 6298);
            getPA().sendFrame126("", 4323);
            getPA().sendFrame126("", 12835);
            getPA().sendFrame126("", 4324);
            getPA().sendFrame126("", 8575);
            getPA().sendFrame126("", 4325);
            getPA().sendFrame126("", 4441);
            getPA().sendFrame126("", 15887);
            getPA().sendFrame126("", 6943);
            getPA().sendFrame126("", 11108);
            getPA().sendFrame126("", 18784);
            getPA().sendFrame126("", 8572);
            getPA().sendFrame126("", 14243);
            getPA().sendFrame126("", 4326);
            getPA().sendFrame126("", 4327);
            getPA().sendFrame126("", 4437);
            getPA().sendFrame126("", 8568);
            getPA().sendFrame126("", 4328);
            getPA().sendFrame126("", 10129);
            getPA().sendFrame126("", 4329);
            getPA().sendFrame126("", 4330);
            getPA().sendFrame126("", 4331);
            getPA().sendFrame126("", 18516);
            getPA().sendFrame126("", 7030);
            getPA().sendFrame126("", 13780);
            getPA().sendFrame126("", 15070);
            getPA().sendFrame126("", 12126);
            getPA().sendFrame126("", 7044);
            getPA().sendFrame126("", 11142);
            getPA().sendFrame126("", 2802);
            getPA().sendFrame126("", 4332);
            getPA().sendFrame126("", 4333);
            getPA().sendFrame126("", 676);
            getPA().sendFrame126("", 8574);
            getPA().sendFrame126("", 4334);
            getPA().sendFrame126("", 14167);
            getPA().sendFrame126("", 12842);
            getPA().sendFrame126("", 14189);
            getPA().sendFrame126("", 12847);
            getPA().sendFrame126("", 11107);
            getPA().sendFrame126("", 18685);
            getPA().sendFrame126("", 4335);
            getPA().sendFrame126("", 4336);
            getPA().sendFrame126("", 5997);
            getPA().sendFrame126("", 4337);
            getPA().sendFrame126("", 4338);
            getPA().sendFrame126("", 4339);
            getPA().sendFrame126("", 4340);
            getPA().sendFrame126("", 4341);
            getPA().sendFrame126("", 4342);
            getPA().sendFrame126("", 11106);
            getPA().sendFrame126("", 15888);
            getPA().sendFrame126("", 12841);
            getPA().sendFrame126("", 4343);
            getPA().sendFrame126("", 15350);
            getPA().sendFrame126("", 8138);
            getPA().sendFrame126("", 13972);
            getPA().sendFrame126("", 675);
            getPA().sendFrame126("", 4344);
            getPA().sendFrame126("", 6843);
            getPA().sendFrame126("", 11481);
            getPA().sendFrame126("", 14602);
            getPA().sendFrame126("", 6945);
            getPA().sendFrame126("", 11881);
            getPA().sendFrame126("", 8434);
            getPA().sendFrame126("", 4345);
            getPA().sendFrame126("", 4346);
            getPA().sendFrame126("", 4506);
            getPA().sendFrame126("", 4347);
            getPA().sendFrame126("", 961);
            getPA().sendFrame126("", 12809);
            getPA().sendFrame126("", 17509);
            getPA().sendFrame126("", 16146);
            getPA().sendFrame126("", 4348);
            getPA().sendFrame126("", 2803);
            getPA().sendFrame126("", 15355);
            getPA().sendFrame126("", 1739);
            getPA().sendFrame126("", 4349);
            getPA().sendFrame126("", 13712);
            getPA().sendFrame126("", 4350);
            getPA().sendFrame126("", 11138);
            getPA().sendFrame126("", 4351);
            getPA().sendFrame126("", 4352);
            getPA().sendFrame126("", 14603);
            getPA().sendFrame126("", 4353);
            getPA().sendFrame126("", 4354);
            getPA().sendFrame126("", 4355);
            getPA().sendFrame126("", 11939);
            getPA().sendFrame126("", 8435);
            getPA().sendFrame126("", 4356);
            getPA().sendFrame126("", 6026);
            getPA().sendFrame126("", 8967);
            getPA().sendFrame126("", 16148);
            getPA().sendFrame126("", 13576);
            getPA().sendFrame126("", 1883);
            getPA().sendFrame126("", 247);
            getPA().sendFrame126("", 4357);
            getPA().sendFrame126("", 7452);
            getPA().sendFrame126("", 4358);
            getPA().sendFrame126("", 10131);
            getPA().sendFrame126("", 4359);
            getPA().sendFrame126("", 4360);
            getPA().sendFrame126("", 6185);
            getPA().sendFrame126("", 4361);
            getPA().sendFrame126("", 4362);
            getPA().sendFrame126("", 13360);
            getPA().sendFrame126("", 8569);
            getPA().sendFrame126("", 13361);
            getPA().sendFrame126("", 4364);
            getPA().sendFrame126("", 189);
            getPA().sendFrame126("", 4365);
            getPA().sendFrame126("", 189);
            getPA().sendFrame126("", 4365);
            getPA().sendFrame126("", 4366);
            getPA().sendFrame126("", 4367);
            getPA().sendFrame126("", 248);
            getPA().sendFrame126("", 8975);
            getPA().sendFrame126("", 11137);
            getPA().sendFrame126("", 190);
            getPA().sendFrame126("", 12047);
            getPA().sendFrame126("", 18304);
            getPA().sendFrame126("", 8116);
            getPA().sendFrame126("", 4368);
            getPA().sendFrame126("", 4369);
            getPA().sendFrame126("", 11477);
            getPA().sendFrame126("", 8570);
            getPA().sendFrame126("", 15074);
            getPA().sendFrame126("", 4370);
            getPA().sendFrame126("", 15889);
            getPA().sendFrame126("", 4371);
            getPA().sendFrame126("", 4507);
            getPA().sendFrame126("", 11109);
            getPA().sendFrame126("", 4372);
            getPA().sendFrame126("", 11136);
            getPA().sendFrame126("", 12336);
            getPA().sendFrame126("", 14186);
            getPA().sendFrame126("", 4373);
            getPA().sendFrame126("", 8117);
            getPA().sendFrame126("", 15293);
            getPA().sendFrame126("", 11133);
            getPA().sendFrame126("", 8139);
            getPA().sendFrame126("", 10114);
            getPA().sendFrame126("", 4374);
            getPA().sendFrame126("", 4375);
            getPA().sendFrame126("", 4376);
            getPA().sendFrame126("", 15495);
            getPA().sendFrame126("", 1893);
            getPA().sendFrame126("", 15077);
            getPA().sendFrame126("", 18466);
            getPA().sendFrame126("", 4377);
            getPA().sendFrame126("", 12288);
            getPA().sendFrame126("", 10132);
            getPA().sendFrame126("", 15590);
            getPA().sendFrame126("", 8573);
            getPA().sendFrame126("", 4378);
            getPA().sendFrame126("", 4379);
            getPA().sendFrame126("", 12845);
            getPA().sendFrame126("", 11940);
            getPA().sendFrame126("", 18690);
            getPA().sendFrame126("", 16147);
            getPA().sendFrame126("", 12287);
            getPA().sendFrame126("", 14188);
            getPA().sendFrame126("", 16127);
            getPA().sendFrame126("", 3277);
            getPA().sendFrame126("", 8433);
            getPA().sendFrame126("", 4380);
            getPA().sendFrame126("", 14190);
            getPA().sendFrame126("", 15496);
            getPA().sendFrame126("", 15497);
            getPA().sendFrame126("", 11478);
            getPA().sendFrame126("", 8976);
            getPA().sendFrame126("", 4382);
            getPA().sendFrame126("", 4383);
            getPA().sendFrame126("", 10112);
            getPA().sendFrame126("", 192);
            getPA().sendFrame126("", 14168);
            getPA().sendFrame126("", 4384);
            getPA().sendFrame126("", 13779);
            getPA().sendFrame126("", 15890);
            getPA().sendFrame126("", 11882);
            getPA().sendFrame126("", 13359);
            getPA().sendFrame126("", 4385);
            getPA().sendFrame126("", 4386);
            getPA().sendFrame126("", 4387);
            getPA().sendFrame126("", 7451);
            getPA().sendFrame126("", 12846);
            getPA().sendFrame126("", 15498);
            getPA().sendFrame126("", 4388);
            getPA().sendFrame126("", 4389);
            getPA().sendFrame126("", 12840);
            getPA().sendFrame126("", 8977);
            getPA().sendFrame126("", 11880);
            getPA().sendFrame126("", 10126);
            getPA().sendFrame126("", 1126);
            getPA().sendFrame126("", 5988);
            getPA().sendFrame126("", 8978);
            getPA().sendFrame126("", 12850);
            getPA().sendFrame126("", 4392);
            getPA().sendFrame126("", 4393);
            getPA().sendFrame126("", 4394);
            getPA().sendFrame126("", 4395);
            getPA().sendFrame126("", 12391);
            getPA().sendFrame126("", 4405);
            getPA().sendFrame126("", 8970);
            getPA().sendFrame126("", 4396);
            getPA().sendFrame126("", 5990);
            getPA().sendFrame126("", 12286);
            getPA().sendFrame126("", 11141);
            getPA().sendFrame126("", 12049);
            getPA().sendFrame126("", 188);
            getPA().sendFrame126("", 4397);
            getPA().sendFrame126("", 12851);
            getPA().sendFrame126("", 4398);
            getPA().sendFrame126("", 15591);
            getPA().sendFrame126("", 4399);
            getPA().sendFrame126("", 8979);
            getPA().sendFrame126("", 11482);
            getPA().sendFrame126("", 4401);
            getPA().sendFrame126("", 8141);
            getPA().sendFrame126("", 4402);
            getPA().sendFrame126("", 4434);
            getPA().sendFrame126("", 11906);
            getPA().sendFrame126("", 4403);
            getPA().sendFrame126("", 10127);
            getPA().sendFrame126("", 17524);
            getPA().sendFrame126("", 11883);
            getPA().sendFrame126("", 15234);
            getPA().sendFrame126("", 7460);
            getPA().sendFrame126("", 15180);
            getPA().sendFrame126("", 7453);
            getPA().sendFrame126("", 4881);
            getPA().sendFrame126("", 11140);
            getPA().sendFrame126("", 13575);
            getPA().sendFrame126("", 4435);
            getPA().sendFrame126("", 3276);
            getPA().sendFrame126("", 11135);
            getPA().sendFrame126("", 11139);
            getPA().sendFrame126("", 6025);
            getPA().sendFrame126("", 12048);
            getPA().sendFrame126("", 4436);
            getPA().sendFrame126("", 12843);
            getPA().sendFrame126("", 8432);
            getPA().sendFrame126("", 12808);
            getPA().sendFrame126("", 10133);
            getPA().sendFrame126("", 15826);
            getPA().sendFrame126("", 6297);
            getPA().sendFrame126("", 18305);
            getPA().sendFrame126("", 4406);
            getPA().sendFrame126("", 4407);
            getPA().sendFrame126("", 4408);
            getPA().sendFrame126("", 4409);
            getPA().sendFrame126("", 8437);
            getPA().sendFrame126("", 4410);
            getPA().sendFrame126("", 4411);
            getPA().sendFrame126("", 4412);
            getPA().sendFrame126("", 4413);
            getPA().sendFrame126("", 4414);
            getPA().sendFrame126("", 7461);
            getPA().sendFrame126("", 6013);
            getPA().sendFrame126("", 1890);
            getPA().sendFrame126("", 4415);
            getPA().sendFrame126("", 7382);
            getPA().sendFrame126("", 678);
            getPA().sendFrame126("", 4418);
            getPA().sendFrame126("", 4420);
            getPA().sendFrame126("", 4421);
            getPA().sendFrame126("", 6867);
            getPA().sendFrame126("", 15829);
            getPA().sendFrame126("", 8118);
            getPA().sendFrame126("", 4422);
            getPA().sendFrame126("", 4423);
            getPA().sendFrame126("", 4424);
            getPA().sendFrame126("", 4425);
            getPA().sendFrame126("", 10113);
            getPA().sendFrame126("", 4426);
            getPA().sendFrame126("", 8140);
            getPA().sendFrame126("", 13354);
            getPA().sendFrame126("", 8566);
            getPA().sendFrame126("", 13713);
            getPA().sendFrame126("", 4427);
            getPA().sendFrame126("", 4428);
            getPA().sendFrame126("", 14454);
            getPA().sendFrame126("", 6983);
            getPA().sendFrame126("", 4430);
            getPA().sendFrame126("", 15830);
            getPA().sendFrame126("", 6842);
            getPA().sendFrame126("", 4431);
            getPA().sendFrame126("", 4432);
            getPA().sendFrame126("", 8571);
            getPA().sendFrame126("", 4433);
            getPA().sendFrame126("", 15081);
            getPA().sendFrame126("", 15346);
            getPA().sendFrame126("", 5989);
            getPA().sendFrame126("", 1898);
            getPA().sendFrame126("", 13355);
        }
        
	public void initialize() {
		synchronized (this) {
                        getPA().sendFrame126(runEnergy+"%", 149);
                        clearQuestsTab();
                        clearMusicTab();
			outStream.createFrame(249);
			outStream.writeByteA(1);		// 1 for members, zero for free
			outStream.writeWordBigEndianA(playerId);
			for (int j = 0; j < Server.playerHandler.players.length; j++) {
				if (j == playerId)
					continue;
				if (Server.playerHandler.players[j] != null) {
					if (Server.playerHandler.players[j].playerName.equalsIgnoreCase(playerName))
						disconnected = true;
				}
			}
			for (int i = 0; i < 25; i++) {
				getPA().setSkillLevel(i, playerLevel[i], playerXP[i]);
				getPA().refreshSkill(i);
			}
			for(int p = 0; p < PRAYER.length; p++) { // reset prayer glows 
				prayerActive[p] = false;
				getPA().sendFrame36(PRAYER_GLOW[p], 0);	
			}
			//if (playerName.equalsIgnoreCase("Sanity")) {
				getPA().sendCrashFrame();
			//}
			getPA().handleWeaponStyle();
			getPA().handleLoginText();
			accountFlagged = getPA().checkForFlags();
			//getPA().sendFrame36(43, fightMode-1);
			getPA().sendFrame36(108, 0);//resets autocast button
			getPA().sendFrame36(172, 1);
			getPA().sendFrame107(); // reset screen
			getPA().setChatOptions(0, 0, 0); // reset private messaging options
			setSidebarInterface(1, 3917);
			setSidebarInterface(2, 638);
                        //setSidebarInterface(2, -1);
			setSidebarInterface(3, 3213);
			setSidebarInterface(4, 1644);
			setSidebarInterface(5, 5608);
			if(playerMagicBook == 0) {
				setSidebarInterface(6, 1151); //modern
			} else {
				setSidebarInterface(6, 12855); // ancient
			}
			correctCoordinates();
			setSidebarInterface(7, 18128);		
			setSidebarInterface(8, 5065);
			setSidebarInterface(9, 5715);
			setSidebarInterface(10, 2449);
			//setSidebarInterface(11, 4445); // wrench tab
			setSidebarInterface(11, 904); // wrench tab
			setSidebarInterface(12, 147); // run tab
			//setSidebarInterface(13, -1);
                        setSidebarInterface(13, 962);
			setSidebarInterface(0, 2423);
			sendMessage("@red@Welcome to "+Config.SERVER_NAME);
			//sendMessage("@blu@Beta will begin tonight, and continue until next wednesday (when I return)");
			//sendMessage("@blu@At which point, I will fix up bugs and hopefully release it publicly next weekend.");
			//sendMessage("@blu@ALL BETA ACCOUNTS WILL BE RESET - EXP is at 10x it will be @ normal release.");
			//sendMessage("@blu@The server is officially released! Enjoy the 1.5x exp boost for the first few days");
			getPA().showOption(4, 0,"Trade With", 3);
			getPA().showOption(5, 0,"Follow", 4);
			getItems().resetItems(3214);
			getItems().sendWeapon(playerEquipment[playerWeapon], getItems().getItemName(playerEquipment[playerWeapon]));
			getItems().resetBonus();
			getItems().getBonus();
			getItems().writeBonus();
			getItems().setEquipment(playerEquipment[playerHat],1,playerHat);
			getItems().setEquipment(playerEquipment[playerCape],1,playerCape);
			getItems().setEquipment(playerEquipment[playerAmulet],1,playerAmulet);
			getItems().setEquipment(playerEquipment[playerArrows],playerEquipmentN[playerArrows],playerArrows);
			getItems().setEquipment(playerEquipment[playerChest],1,playerChest);
			getItems().setEquipment(playerEquipment[playerShield],1,playerShield);
			getItems().setEquipment(playerEquipment[playerLegs],1,playerLegs);
			getItems().setEquipment(playerEquipment[playerHands],1,playerHands);
			getItems().setEquipment(playerEquipment[playerFeet],1,playerFeet);
			getItems().setEquipment(playerEquipment[playerRing],1,playerRing);
			getItems().setEquipment(playerEquipment[playerWeapon],playerEquipmentN[playerWeapon],playerWeapon);
			getCombat().getPlayerAnimIndex(getItems().getItemName(playerEquipment[playerWeapon]).toLowerCase());
			getPA().logIntoPM();
			getItems().addSpecialBar(playerEquipment[playerWeapon]);
			saveTimer = Config.SAVE_TIMER;
			saveCharacter = true;
			Misc.println("[REGISTERED]: "+playerName+"");
			handler.updatePlayer(this, outStream);
			handler.updateNPC(this, outStream);
			flushOutStream();
			getPA().clearClanChat();
			getPA().resetFollow();
			if (addStarter)
				getPA().addStarter();
			if (autoRet == 1)
				getPA().sendFrame36(172, 1);
			else
				getPA().sendFrame36(172, 0);
                        showStats();
		}
	}
	


	public void update() {
		synchronized (this) {
			handler.updatePlayer(this, outStream);
			handler.updateNPC(this, outStream);
			flushOutStream();
		}
	}
	
	public void logout() {
		synchronized (this) {
			if(System.currentTimeMillis() - logoutDelay > 10000) {
                                CycleEventHandler.getSingleton().stopEvents(this);
				outStream.createFrame(109);
				properLogout = true;
			} else {
				sendMessage("You must wait a few seconds from being out of combat to logout.");
			}
		}
	}
	
	public int packetSize = 0, packetType = -1;
	
	public void process() {
                if (runEnergy < 100) {
                    if (System.currentTimeMillis() > getPA().getAgilityRunRestore(this) + lastRunRecovery) {
                        runEnergy++;
                        lastRunRecovery = System.currentTimeMillis();
                        getPA().sendFrame126(runEnergy+"%", 149);
                    }
                }
		if (wcTimer > 0 && woodcut[0] > 0) {
			wcTimer--;
		} else if (wcTimer == 0 && woodcut[0] > 0) {
			getWoodcutting().cutWood();
		} else if (miningTimer > 0 && mining[0] > 0) {
			miningTimer--;
		} else if (miningTimer == 0 && mining[0] > 0) {
			getMining().mineOre();
		} else  if (smeltTimer > 0 && smeltType > 0) {
			smeltTimer--;
		} else if (smeltTimer == 0 && smeltType > 0) {
			getSmithing().smelt(smeltType);
		} else if (fishing && fishTimer > 0) {
			fishTimer--;
		} else if (fishing && fishTimer == 0) {
			getFishing().catchFish();
		}
		
                if (runEnergy < 100) {
                    if (System.currentTimeMillis() > getPA().getAgilityRunRestore(this) + lastRunRecovery) {
                        runEnergy++;
                        lastRunRecovery = System.currentTimeMillis();
                        getPA().sendFrame126(runEnergy+"%", 149);
                    }
                }
                
		if (System.currentTimeMillis() - lastPoison > 20000 && poisonDamage > 0) {
			int damage = poisonDamage/2;
			if (damage > 0) {
				sendMessage("Applying poison damage.");
				if (!getHitUpdateRequired()) {
					setHitUpdateRequired(true);
					setHitDiff(damage);
					updateRequired = true;
					poisonMask = 1;
				} else if (!getHitUpdateRequired2()) {
					setHitUpdateRequired2(true);
					setHitDiff2(damage);
					updateRequired = true;
					poisonMask = 2;
				}
				lastPoison = System.currentTimeMillis();
				poisonDamage--;
				dealDamage(damage);
			} else {
				poisonDamage = -1;
				sendMessage("You are no longer poisoned.");
			}	
		}
		
		if(System.currentTimeMillis() - duelDelay > 800 && duelCount > 0) {
			if(duelCount != 1) {
				forcedChat(""+(--duelCount));
				duelDelay = System.currentTimeMillis();
			} else {
				damageTaken = new int[Config.MAX_PLAYERS];
				forcedChat("FIGHT!");
				duelCount = 0;
			}
		}
	
		if(System.currentTimeMillis() - specDelay > Config.INCREASE_SPECIAL_AMOUNT) {
			specDelay = System.currentTimeMillis();
			if(specAmount < 10) {
				specAmount += .5;
				if (specAmount > 10)
					specAmount = 10;
				getItems().addSpecialBar(playerEquipment[playerWeapon]);
			}
		}
		
		if(clickObjectType > 0 && goodDistance(objectX + objectXOffset, objectY + objectYOffset, getX(), getY(), objectDistance)) {
			if(clickObjectType == 1) {
				getActions().firstClickObject(objectId, objectX, objectY);
			}
			if(clickObjectType == 2) {
				getActions().secondClickObject(objectId, objectX, objectY);
			}
			if(clickObjectType == 3) {
				getActions().thirdClickObject(objectId, objectX, objectY);
			}
		}
		
		if((clickNpcType > 0) && Server.npcHandler.npcs[npcClickIndex] != null) {			
			if(goodDistance(getX(), getY(), Server.npcHandler.npcs[npcClickIndex].getX(), Server.npcHandler.npcs[npcClickIndex].getY(), 1)) {
				if(clickNpcType == 1) {
					turnPlayerTo(Server.npcHandler.npcs[npcClickIndex].getX(), Server.npcHandler.npcs[npcClickIndex].getY());
					Server.npcHandler.npcs[npcClickIndex].facePlayer(playerId);
					getActions().firstClickNpc(npcType, Server.npcHandler.npcs[npcClickIndex].description);
				}
				if(clickNpcType == 2) {
					turnPlayerTo(Server.npcHandler.npcs[npcClickIndex].getX(), Server.npcHandler.npcs[npcClickIndex].getY());
					Server.npcHandler.npcs[npcClickIndex].facePlayer(playerId);
					getActions().secondClickNpc(npcType);
				}
				if(clickNpcType == 3) {
					turnPlayerTo(Server.npcHandler.npcs[npcClickIndex].getX(), Server.npcHandler.npcs[npcClickIndex].getY());
					Server.npcHandler.npcs[npcClickIndex].facePlayer(playerId);
					getActions().thirdClickNpc(npcType);
				}
			}
		}
		
		if(walkingToItem) {
			if(getX() == pItemX && getY() == pItemY || goodDistance(getX(), getY(), pItemX, pItemY,1)) {
				walkingToItem = false;
				Server.itemHandler.removeGroundItem(this, pItemId, pItemX, pItemY, true);
			}
		}
		
		if(followId > 0) {
			getPA().followPlayer();
		} else if (followId2 > 0) {
			getPA().followNpc();
		}
		
		getCombat().handlePrayerDrain();
		
		if(System.currentTimeMillis() - singleCombatDelay >  3300) {
			underAttackBy = 0;
		}
		if (System.currentTimeMillis() - singleCombatDelay2 > 3300) {
			underAttackBy2 = 0;
		}
		
		if(System.currentTimeMillis() - restoreStatsDelay >  60000) {
			restoreStatsDelay = System.currentTimeMillis();
			for (int level = 0; level < playerLevel.length; level++)  {
				if (playerLevel[level] < getLevelForXP(playerXP[level])) {
					if(level != 5) { // prayer doesn't restore
						playerLevel[level] += 1;
						getPA().setSkillLevel(level, playerLevel[level], playerXP[level]);
						getPA().refreshSkill(level);
					}
				} else if (playerLevel[level] > getLevelForXP(playerXP[level])) {
					playerLevel[level] -= 1;
					getPA().setSkillLevel(level, playerLevel[level], playerXP[level]);
					getPA().refreshSkill(level);
				}
			}
		}

		if(System.currentTimeMillis() - teleGrabDelay >  1550 && usingMagic) {
			usingMagic = false;
			if(Server.itemHandler.itemExists(teleGrabItem, teleGrabX, teleGrabY)) {
				Server.itemHandler.removeGroundItem(this, teleGrabItem, teleGrabX, teleGrabY, true);
			}
		}
		
		if(inWild()) {
			int modY = absY > 6400 ?  absY - 6400 : absY;
			wildLevel = (((modY - 3520) / 8) + 1);
			getPA().walkableInterface(197);
			if(Config.SINGLE_AND_MULTI_ZONES) {
				if(inMulti()) {
					getPA().sendFrame126("@yel@Level: "+wildLevel, 199);
				} else {
					getPA().sendFrame126("@yel@Level: "+wildLevel, 199);
				}
			} else {
				getPA().multiWay(-1);
				getPA().sendFrame126("@yel@Level: "+wildLevel, 199);
			}
			getPA().showOption(3, 0, "Attack", 1);
		} else if (inDuelArena()) {
			getPA().walkableInterface(201);
			if(duelStatus == 5) {
				getPA().showOption(3, 0, "Attack", 1);
			} else {
				getPA().showOption(3, 0, "Challenge", 1);
			}
		} else if(inBarrows()){
			getPA().sendFrame99(2);
			getPA().sendFrame126("Kill Count: "+barrowsKillCount, 4536);
			getPA().walkableInterface(4535);
		} else if (inCwGame || inPits) {
			getPA().showOption(3, 0, "Attack", 1);	
		} else if (getPA().inPitsWait()) {
			getPA().showOption(3, 0, "Null", 1);
		}else if (!inCwWait) {
			getPA().sendFrame99(0);
			getPA().walkableInterface(-1);
			getPA().showOption(3, 0, "Null", 1);
		}
		
		if(!hasMultiSign && inMulti()) {
			hasMultiSign = true;
			getPA().multiWay(1);
		}
		
		if(hasMultiSign && !inMulti()) {
			hasMultiSign = false;
			getPA().multiWay(-1);
		}

		if(skullTimer > 0) {
			skullTimer--;
			if(skullTimer == 1) {
				isSkulled = false;
				attackedPlayers.clear();
				headIconPk = -1;
				skullTimer = -1;
				getPA().requestUpdates();
			}	
		}
		
		if(isDead && respawnTimer == -6) {
			getPA().applyDead();
		}
		
		if(respawnTimer == 7) {
			respawnTimer = -6;
			getPA().giveLife();
		} else if(respawnTimer == 12) {
			respawnTimer--;
			startAnimation(0x900);
			poisonDamage = -1;
		}	
		
		if(respawnTimer > -6) {
			respawnTimer--;
		}
		if(freezeTimer > -6) {
			freezeTimer--;
			if (frozenBy > 0) {
				if (Server.playerHandler.players[frozenBy] == null) {
					freezeTimer = -1;
					frozenBy = -1;
				} else if (!goodDistance(absX, absY, Server.playerHandler.players[frozenBy].absX, Server.playerHandler.players[frozenBy].absY, 20)) {
					freezeTimer = -1;
					frozenBy = -1;
				}
			}
		}
		
		if(hitDelay > 0) {
			hitDelay--;
		}
		
		if(teleTimer > 0) {
			teleTimer--;
			if (!isDead) {
				if(teleTimer == 1 && newLocation > 0) {
					teleTimer = 0;
					getPA().changeLocation();
				}
				if(teleTimer == 5) {
					teleTimer--;
					getPA().processTeleport();
				}
				if(teleTimer == 9 && teleGfx > 0) {
					teleTimer--;
					gfx100(teleGfx);
				}
			} else {
				teleTimer = 0;
			}
		}	

		if(hitDelay == 1) {
			if(oldNpcIndex > 0) {
				getCombat().delayedHit(oldNpcIndex);
			}
			if(oldPlayerIndex > 0) {
				getCombat().playerDelayedHit(oldPlayerIndex);				
			}		
		}
		
		if(attackTimer > 0) {
			attackTimer--;
		}
		
		if(attackTimer == 1){
			if(npcIndex > 0 && clickNpcType == 0) {
				getCombat().attackNpc(npcIndex);
			}
			if(playerIndex > 0) {
				getCombat().attackPlayer(playerIndex);
			}
		} else if (attackTimer <= 0 && (npcIndex > 0 || playerIndex > 0)) {
			if (npcIndex > 0) {
				attackTimer = 0;
				getCombat().attackNpc(npcIndex);
			} else if (playerIndex > 0) {
				attackTimer = 0;
				getCombat().attackPlayer(playerIndex);
			}
		}
		
		if(timeOutCounter > Config.TIMEOUT) {
			disconnected = true;
		}
		
		timeOutCounter++;
		
		if(inTrade && tradeResetNeeded){
			Client o = (Client) Server.playerHandler.players[tradeWith];
			if(o != null){
				if(o.tradeResetNeeded){
					getTradeAndDuel().resetTrade();
					o.getTradeAndDuel().resetTrade();
				}
			}
		}
	}
	
	public void setCurrentTask(Future<?> task) {
		currentTask = task;
	}

	public Future<?> getCurrentTask() {
		return currentTask;
	}
	
	public synchronized Stream getInStream() {
		return inStream;
	}
	
	public synchronized int getPacketType() {
		return packetType;
	}
	
	public synchronized int getPacketSize() {
		return packetSize;
	}
	
	public synchronized Stream getOutStream() {
		return outStream;
	}
	
	public ItemAssistant getItems() {
		return itemAssistant;
	}
		
	public PlayerAssistant getPA() {
		return playerAssistant;
	}
	
	public DialogueHandler getDH() {
		return dialogueHandler;
	}
	
	public ShopAssistant getShops() {
		return shopAssistant;
	}
	
	public TradeAndDuel getTradeAndDuel() {
		return tradeAndDuel;
	}
	
	public CombatAssistant getCombat() {
		return combatAssistant;
	}
	
	public ActionHandler getActions() {
		return actionHandler;
	}
  
	public PlayerKilling getKill() {
		return playerKilling;
	}
	
	public IoSession getSession() {
		return session;
	}
	
	public Potions getPotions() {
		return potions;
	}
	
	public PotionMixing getPotMixing() {
		return potionMixing;
	}
	
	public Food getFood() {
		return food;
	}
	
	/**
	 * Skill Constructors
	 */
	public Slayer getSlayer() {
		return slayer;
	}
	
	public Runecrafting getRunecrafting() {
		return runecrafting;
	}
	
	public Woodcutting getWoodcutting() {
		return woodcutting;
	}
	
	public Mining getMining() {
		return mine;
	}
	
	public Cooking getCooking() {
		return cooking;
	}
	
	public Agility getAgility() {
		return agility;
	}
	
	public Fishing getFishing() {
		return fish;
	}
	
	public Crafting getCrafting() {
		return crafting;
	}
	
	public Smithing getSmithing() {
		return smith;
	}
	
	public Farming getFarming() {
		return farming;
	}
	
	public Thieving getThieving() {
		return thieving;
	}
	
	public Herblore getHerblore() {
		return herblore;
	}
	
	public Firemaking getFiremaking() {
		return firemaking;
	}
	
	public SmithingInterface getSmithingInt() {
		return smithInt;
	}
	
	public Prayer getPrayer() { 
		return prayer;
	}
	
	public Fletching getFletching() { 
		return fletching;
	}
	
	/**
	 * End of Skill Constructors
	 */
	
	public void queueMessage(Packet arg1) {
		synchronized(queuedPackets) {
			//if (arg1.getId() != 41)
				queuedPackets.add(arg1);
			//else
				//processPacket(arg1);
		}
	}
	
	public synchronized boolean processQueuedPackets() {
		Packet p = null;
		synchronized(queuedPackets) {
			p = queuedPackets.poll();
		}
		if(p == null) {
			return false;
		}
		inStream.currentOffset = 0;
		packetType = p.getId();
		packetSize = p.getLength();
		inStream.buffer = p.getData();
		if(packetType > 0) {
			//sendMessage("PacketType: " + packetType);
			PacketHandler.processPacket(this, packetType, packetSize);
		}
		timeOutCounter = 0;
		return true;
	}
	
	public synchronized boolean processPacket(Packet p) {
		synchronized (this) {
			if(p == null) {
				return false;
			}
			inStream.currentOffset = 0;
			packetType = p.getId();
			packetSize = p.getLength();
			inStream.buffer = p.getData();
			if(packetType > 0) {
				//sendMessage("PacketType: " + packetType);
				PacketHandler.processPacket(this, packetType, packetSize);
			}
			timeOutCounter = 0;
			return true;
		}
	}
	
	
	public void correctCoordinates() {
		if (inPcGame()) {
			getPA().movePlayer(2657, 2639, 0);
		}
		if (inFightCaves()) {
			getPA().movePlayer(absX, absY, playerId * 4);
			sendMessage("Your wave will start in 10 seconds.");
			EventManager.getSingleton().addEvent(new Event() {
				public void execute(EventContainer c) {
					Server.fightCaves.spawnNextWave((Client)Server.playerHandler.players[playerId]);
					c.stop();
				}
				}, 10000);
		
		}
	
	}
        
        public int getLocalX(){
            return absX;
        }
        
        public int getLocalY(){
            return absY;
        }
}
