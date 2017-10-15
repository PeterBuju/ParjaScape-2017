public final class RSInterface {

	public void swapInventoryItems(int i, int j) {
		int k = inv[i];
		inv[i] = inv[j];
		inv[j] = k;
		k = invStackSizes[i];
		invStackSizes[i] = invStackSizes[j];
		invStackSizes[j] = k;
	}

	public static void unpack(StreamLoader streamLoader, TextDrawingArea textDrawingAreas[], StreamLoader streamLoader_1) {
		aMRUNodes_238 = new MRUNodes(50000);
		Stream stream = new Stream(streamLoader.getDataForName("data"));
		int i = -1;
		stream.readUnsignedWord();
		interfaceCache = new RSInterface[61000];
		while (stream.currentOffset < stream.buffer.length) {
			int k = stream.readUnsignedWord();
			if (k == 65535) {
				i = stream.readUnsignedWord();
				k = stream.readUnsignedWord();
			}
			RSInterface rsInterface = interfaceCache[k] = new RSInterface();
			rsInterface.id = k;
			rsInterface.parentID = i;
			rsInterface.type = stream.readUnsignedByte();
			rsInterface.atActionType = stream.readUnsignedByte();
			rsInterface.contentType = stream.readUnsignedWord();
			rsInterface.width = stream.readUnsignedWord();
			rsInterface.height = stream.readUnsignedWord();
			rsInterface.opacity = (byte) stream.readUnsignedByte();
			rsInterface.hoverType = stream.readUnsignedByte();
			if (rsInterface.hoverType != 0)
				rsInterface.hoverType = (rsInterface.hoverType - 1 << 8) + stream.readUnsignedByte();
			else
				rsInterface.hoverType = -1;
			int i1 = stream.readUnsignedByte();
			if (i1 > 0) {
				rsInterface.anIntArray245 = new int[i1];
				rsInterface.anIntArray212 = new int[i1];
				for (int j1 = 0; j1 < i1; j1++) {
					rsInterface.anIntArray245[j1] = stream.readUnsignedByte();
					rsInterface.anIntArray212[j1] = stream.readUnsignedWord();
				}

			}
			int k1 = stream.readUnsignedByte();
			if (k1 > 0) {
				rsInterface.valueIndexArray = new int[k1][];
				for (int l1 = 0; l1 < k1; l1++) {
					int i3 = stream.readUnsignedWord();
					rsInterface.valueIndexArray[l1] = new int[i3];
					for (int l4 = 0; l4 < i3; l4++)
						rsInterface.valueIndexArray[l1][l4] = stream.readUnsignedWord();

				}

			}
			if (rsInterface.type == 0) {
				rsInterface.drawsTransparent = false;
				rsInterface.scrollMax = stream.readUnsignedWord();
				rsInterface.isMouseoverTriggered = stream.readUnsignedByte() == 1;
				int i2 = stream.readUnsignedWord();
				rsInterface.children = new int[i2];
				rsInterface.childX = new int[i2];
				rsInterface.childY = new int[i2];
				for (int j3 = 0; j3 < i2; j3++) {
					rsInterface.children[j3] = stream.readUnsignedWord();
					rsInterface.childX[j3] = stream.readSignedWord();
					rsInterface.childY[j3] = stream.readSignedWord();
				}
			}
			if (rsInterface.type == 1) {
				stream.readUnsignedWord();
				stream.readUnsignedByte();
			}
			if (rsInterface.type == 2) {
				rsInterface.inv = new int[rsInterface.width * rsInterface.height];
				rsInterface.invStackSizes = new int[rsInterface.width * rsInterface.height];
				rsInterface.aBoolean259 = stream.readUnsignedByte() == 1;
				rsInterface.isInventoryInterface = stream.readUnsignedByte() == 1;
				rsInterface.usableItemInterface = stream.readUnsignedByte() == 1;
				rsInterface.aBoolean235 = stream.readUnsignedByte() == 1;
				rsInterface.invSpritePadX = stream.readUnsignedByte();
				rsInterface.invSpritePadY = stream.readUnsignedByte();
				rsInterface.spritesX = new int[20];
				rsInterface.spritesY = new int[20];
				rsInterface.sprites = new Sprite[20];
				for (int j2 = 0; j2 < 20; j2++) {
					int k3 = stream.readUnsignedByte();
					if (k3 == 1) {
						rsInterface.spritesX[j2] = stream.readSignedWord();
						rsInterface.spritesY[j2] = stream.readSignedWord();
						String s1 = stream.readString();
						if (streamLoader_1 != null && s1.length() > 0) {
							int i5 = s1.lastIndexOf(",");
							rsInterface.sprites[j2] = method207(Integer.parseInt(s1.substring(i5 + 1)), streamLoader_1, s1.substring(0, i5));
						}
					}
				}
				rsInterface.actions = new String[5];
				for (int l3 = 0; l3 < 5; l3++) {
					rsInterface.actions[l3] = stream.readString();
					if (rsInterface.actions[l3].length() == 0)
						rsInterface.actions[l3] = null;
					if (rsInterface.parentID == 1644)
						rsInterface.actions[2] = "Operate";
					if(rsInterface.parentID == 3824)
                		rsInterface.actions[4] = "Buy 100";
				}
			}
			if (rsInterface.type == 3)
				rsInterface.aBoolean227 = stream.readUnsignedByte() == 1;
			if (rsInterface.type == 4 || rsInterface.type == 1) {
				rsInterface.centerText = stream.readUnsignedByte() == 1;
				int k2 = stream.readUnsignedByte();
				if (textDrawingAreas != null)
					rsInterface.textDrawingAreas = textDrawingAreas[k2];
				rsInterface.textShadow = stream.readUnsignedByte() == 1;
			}
			if (rsInterface.type == 4) {
				rsInterface.message = stream.readString().replaceAll("RuneScape", "" + Client.clientName);
				rsInterface.aString228 = stream.readString();
			}
			if (rsInterface.type == 1 || rsInterface.type == 3 || rsInterface.type == 4)
				rsInterface.textColor = stream.readDWord();
			if (rsInterface.type == 3 || rsInterface.type == 4) {
				rsInterface.anInt219 = stream.readDWord();
				rsInterface.anInt216 = stream.readDWord();
				rsInterface.anInt239 = stream.readDWord();
			}
			if (rsInterface.type == 5) {
				rsInterface.drawsTransparent = false;
				String s = stream.readString();
				if (streamLoader_1 != null && s.length() > 0) {
					int i4 = s.lastIndexOf(",");
					rsInterface.disabledSprite = method207(Integer.parseInt(s.substring(i4 + 1)), streamLoader_1, s.substring(0, i4));
				}
				s = stream.readString();
				if (streamLoader_1 != null && s.length() > 0) {
					int j4 = s.lastIndexOf(",");
					rsInterface.enabledSprite = method207(Integer.parseInt(s.substring(j4 + 1)), streamLoader_1, s.substring(0, j4));
				}
			}
			if (rsInterface.type == 6) {
				int l = stream.readUnsignedByte();
				if (l != 0) {
					rsInterface.anInt233 = 1;
					rsInterface.mediaID = (l - 1 << 8) + stream.readUnsignedByte();
				}
				l = stream.readUnsignedByte();
				if (l != 0) {
					rsInterface.anInt255 = 1;
					rsInterface.anInt256 = (l - 1 << 8) + stream.readUnsignedByte();
				}
				l = stream.readUnsignedByte();
				if (l != 0)
					rsInterface.anInt257 = (l - 1 << 8) + stream.readUnsignedByte();
				else
					rsInterface.anInt257 = -1;
				l = stream.readUnsignedByte();
				if (l != 0)
					rsInterface.anInt258 = (l - 1 << 8) + stream.readUnsignedByte();
				else
					rsInterface.anInt258 = -1;
				rsInterface.modelZoom = stream.readUnsignedWord();
				rsInterface.modelRotation1 = stream.readUnsignedWord();
				rsInterface.modelRotation2 = stream.readUnsignedWord();
			}
			if (rsInterface.type == 7) {
				rsInterface.inv = new int[rsInterface.width * rsInterface.height];
				rsInterface.invStackSizes = new int[rsInterface.width * rsInterface.height];
				rsInterface.centerText = stream.readUnsignedByte() == 1;
				int l2 = stream.readUnsignedByte();
				if (textDrawingAreas != null)
					rsInterface.textDrawingAreas = textDrawingAreas[l2];
				rsInterface.textShadow = stream.readUnsignedByte() == 1;
				rsInterface.textColor = stream.readDWord();
				rsInterface.invSpritePadX = stream.readSignedWord();
				rsInterface.invSpritePadY = stream.readSignedWord();
				rsInterface.isInventoryInterface = stream.readUnsignedByte() == 1;
				rsInterface.actions = new String[5];
				for (int k4 = 0; k4 < 5; k4++) {
					rsInterface.actions[k4] = stream.readString();
					if (rsInterface.actions[k4].length() == 0)
						rsInterface.actions[k4] = null;
				}

			}
			if (rsInterface.atActionType == 2 || rsInterface.type == 2) {
				rsInterface.selectedActionName = stream.readString();
				rsInterface.spellName = stream.readString();
				rsInterface.spellUsableOn = stream.readUnsignedWord();
			}

			if (rsInterface.type == 8)
				rsInterface.message = stream.readString();

			if (rsInterface.atActionType == 1 || rsInterface.atActionType == 4 || rsInterface.atActionType == 5 || rsInterface.atActionType == 6) {
				rsInterface.tooltip = stream.readString();
				if (rsInterface.tooltip.length() == 0) {
					if (rsInterface.atActionType == 1)
						rsInterface.tooltip = "Ok";
					if (rsInterface.atActionType == 4)
						rsInterface.tooltip = "Select";
					if (rsInterface.atActionType == 5)
						rsInterface.tooltip = "Select";
					if (rsInterface.atActionType == 6)
						rsInterface.tooltip = "Continue";
				}
			}
		}
		aClass44 = streamLoader;
		constructLunar();
		priceChecker(textDrawingAreas);
		equipmentScreen(textDrawingAreas);
		equipmentTab(textDrawingAreas);
		clanChatSetup(textDrawingAreas);
		clanChatTab(textDrawingAreas);
		configureLunar(textDrawingAreas);
		quickCurses(textDrawingAreas);
		quickPrayers(textDrawingAreas);
		edgevilleHomeTeleport(textDrawingAreas);
		//optionTab1(textDrawingAreas);
		questTab(textDrawingAreas);
		nightmareZone(textDrawingAreas);
		slayerInterface(textDrawingAreas);
		slayerInterfaceSub1(textDrawingAreas);
		slayerInterfaceSub2(textDrawingAreas);
		bank(textDrawingAreas);
		wrenchTab(textDrawingAreas);
		itemsKeptOnDeath(textDrawingAreas);
		itemsOnDeathDATA(textDrawingAreas);
		itemsOnDeath(textDrawingAreas);
		aMRUNodes_238 = null;
	}
	
	public static void removeConfig(int id) {
		@SuppressWarnings("unused")
		RSInterface rsi = interfaceCache[id] = new RSInterface();
	}
	
	public static void wrenchTab(TextDrawingArea[] tda) {
		RSInterface rsi = addInterface(904); // Main Tab

		addSprite(31001, 0, "Interfaces/WrenchTab/main tab/sprite");

		addHoverButton(31002, "Interfaces/WrenchTab/main tab/display", 2, 40,
				40, "Display Test", -1, 31003, 1);
		addHoveredButton(31003, "Interfaces/WrenchTab/main tab/display", 4, 40,
				40, 31004);

		addHoverButton(31005, "Interfaces/WrenchTab/main tab/audio", 1, 40, 40,
				"Audio Test", -1, 31006, 1);
		addHoveredButton(31006, "Interfaces/WrenchTab/main tab/audio", 3, 40,
				40, 31007);

		addHoverButton(31008, "Interfaces/WrenchTab/main tab/chat", 1, 40, 40,
				"Chat Test", -1, 31009, 1);
		addHoveredButton(31009, "Interfaces/WrenchTab/main tab/chat", 3, 40,
				40, 31010);

		addHoverButton(31011, "Interfaces/WrenchTab/main tab/controls", 1, 40,
				40, "Controls Test", -1, 31012, 1);
		addHoveredButton(31012, "Interfaces/WrenchTab/main tab/controls", 3,
				40, 40, 31013);

		addConfigHover(31014, 4, 31015, 1, 2,
				"Interfaces/WrenchTab/main tab/aid", 40, 40, 304, 1,
				"Toggle Accept Aid", 31016, 1, 2,
				"Interfaces/WrenchTab/main tab/aid", 31017, "", "", 12, 20);

		addConfigHover(31018, 4, 31019, 1, 2,
				"Interfaces/WrenchTab/main tab/run", 40, 40, 504, 0,
				"Toggle run", 31020, 1, 2, "Interfaces/WrenchTab/main tab/run",
				31021, "", "", 12, 20);

		addButton(31022, 1, "Interfaces/WrenchTab/main tab/house", 40, 40,
				"Open House Options", 1);

		addButton(31023, 1, "Interfaces/WrenchTab/main tab/bond", 40, 40,
				"View Membership Bonds", 1);

		addConfigHover(31024, 4, 31025, 1, 2,
				"Interfaces/WrenchTab/main tab/orbs", 40, 40, 306, 1,
				"Toggle Data Orbs", 31026, 1, 2,
				"Interfaces/WrenchTab/main tab/orbs", 31027, "", "", 12, 20);

		addConfigHover(31028, 4, 31029, 1, 2,
				"Interfaces/WrenchTab/main tab/roof", 40, 40, 307, 1,
				"Toggle roof-removal", 31030, 1, 2,
				"Interfaces/WrenchTab/main tab/roof", 31031, "", "", 12, 20);

		addConfigHover(31032, 4, 31033, 1, 2,
				"Interfaces/WrenchTab/main tab/xp", 40, 40, 308, 1,
				"Toggle 'Remaining XP'", 31034, 1, 2,
				"Interfaces/WrenchTab/main tab/xp", 31035, "", "", 12, 20);

		addText(149, "", 16750623, false, true, 52, tda, 1);

		addSprite(31035, 0, "Interfaces/WrenchTab/sun");

		addSprite(31036, 0, "Interfaces/WrenchTab/bar");

		addConfigHover(31037, 4, 31038, 2, 1, "Interfaces/WrenchTab/bar", 16,
				16, 505, 1, "Adjust Screen Brightness", 31039, 2, 1,
				"Interfaces/WrenchTab/bar", 31108, "", "", 12, 20);

		addConfigHover(31109, 4, 31110, 2, 1, "Interfaces/WrenchTab/bar", 16,
				16, 506, 1, "Adjust Screen Brightness", 31111, 2, 1,
				"Interfaces/WrenchTab/bar", 31112, "", "", 12, 20);

		addConfigHover(31113, 4, 31114, 2, 1, "Interfaces/WrenchTab/bar", 16,
				16, 507, 1, "Adjust Screen Brightness", 31115, 2, 1,
				"Interfaces/WrenchTab/bar", 31116, "", "", 12, 20);

		addConfigHover(31117, 4, 31118, 2, 1, "Interfaces/WrenchTab/bar", 16,
				16, 508, 1, "Adjust Screen Brightness", 31119, 2, 1,
				"Interfaces/WrenchTab/bar", 31120, "", "", 12, 20);

		setChildren(32, rsi);
		setBounds(31001, 3, 45, 0, rsi);
		setBounds(31002, 6, 3, 1, rsi); // Display
		setBounds(31003, 6, 3, 2, rsi);
		setBounds(31005, 52, 3, 3, rsi); // Audio
		setBounds(31006, 52, 3, 4, rsi);
		setBounds(31008, 98, 3, 5, rsi); // Chat
		setBounds(31009, 98, 3, 6, rsi);
		setBounds(31011, 144, 3, 7, rsi); // Controls
		setBounds(31012, 144, 3, 8, rsi);

		setBounds(31014, 6, 221, 9, rsi); // Aid
		setBounds(31016, 6, 221, 10, rsi); // Aid

		setBounds(31018, 52, 221, 11, rsi); // Run
		setBounds(31020, 52, 221, 12, rsi); // Run

		setBounds(31022, 98, 221, 13, rsi); // House
		setBounds(31023, 144, 221, 14, rsi); // Membership Bonds

		setBounds(31024, 23, 119, 15, rsi); // Orbs
		setBounds(31026, 23, 119, 16, rsi); // Orbs

		setBounds(31028, 75, 119, 17, rsi); // Roof
		setBounds(31030, 75, 119, 18, rsi); // Roof

		setBounds(31032, 127, 119, 19, rsi); // Remaining-XP
		setBounds(31034, 127, 119, 20, rsi); // Remaining-XP

		setBounds(149, 57, 241, 21, rsi); // Run Text

		setBounds(31035, 9, 69, 22, rsi); // Sun
		setBounds(31036, 53, 77, 23, rsi); // Bar

		setBounds(31037, 61, 78, 24, rsi); // Button
		setBounds(31039, 61, 78, 25, rsi); // Button

		setBounds(31109, 92, 78, 26, rsi); // Button
		setBounds(31111, 92, 78, 27, rsi); // Button

		setBounds(31113, 124, 78, 28, rsi); // Button
		setBounds(31115, 124, 78, 29, rsi); // Button

		setBounds(31117, 156, 78, 30, rsi); // Button
		setBounds(31119, 156, 78, 31, rsi); // Button

		rsi = addInterface(31040); // Audio Tab

		addHoverButton(31041, "Interfaces/WrenchTab/main tab/display", 1, 40,
				40, "Display Test", -1, 31042, 1);
		addHoveredButton(31042, "Interfaces/WrenchTab/main tab/display", 3, 40,
				40, 31043);

		addHoverButton(31044, "Interfaces/WrenchTab/main tab/audio", 2, 40, 40,
				"Audio Test", -1, 31045, 1);
		addHoveredButton(31045, "Interfaces/WrenchTab/main tab/audio", 4, 40,
				40, 31046);

		addSprite(31121, 0, "Interfaces/WrenchTab/sprite");

		addSprite(31122, 1, "Interfaces/WrenchTab/sprite");

		addSprite(31123, 2, "Interfaces/WrenchTab/sprite");

		addSprite(31124, 0, "Interfaces/WrenchTab/bar");
		addSprite(31125, 0, "Interfaces/WrenchTab/bar");
		addSprite(31126, 0, "Interfaces/WrenchTab/bar");

		addConfigHover(31128, 4, 31129, 2, 1, "Interfaces/WrenchTab/bar", 16,
				16, 509, 1, "Adjust Music Volume", 31130, 2, 1,
				"Interfaces/WrenchTab/bar", 31131, "", "", 12, 20);

		addConfigHover(31132, 4, 31133, 2, 1, "Interfaces/WrenchTab/bar", 16,
				16, 510, 1, "Adjust Music Volume", 31134, 2, 1,
				"Interfaces/WrenchTab/bar", 31135, "", "", 12, 20);

		addConfigHover(31136, 4, 31137, 1, 2, "Interfaces/WrenchTab/bar", 16,
				16, 511, 1, "Adjust Music Volume", 31138, 1, 2,
				"Interfaces/WrenchTab/bar", 31139, "", "", 12, 20);

		addConfigHover(31140, 4, 31141, 2, 1, "Interfaces/WrenchTab/bar", 16,
				16, 512, 1, "Adjust Music Volume", 31142, 2, 1,
				"Interfaces/WrenchTab/bar", 31143, "", "", 12, 20);

		addConfigHover(31144, 4, 31145, 1, 2, "Interfaces/WrenchTab/bar", 16,
				16, 513, 1, "Adjust Sound Effect Volume", 31146, 1, 2,
				"Interfaces/WrenchTab/bar", 31147, "", "", 12, 20);

		addConfigHover(31148, 4, 31149, 2, 1, "Interfaces/WrenchTab/bar", 16,
				16, 514, 1, "Adjust Sound Effect Volume", 31150, 2, 1,
				"Interfaces/WrenchTab/bar", 31151, "", "", 12, 20);

		addConfigHover(31152, 4, 31153, 2, 1, "Interfaces/WrenchTab/bar", 16,
				16, 515, 1, "Adjust Sound Effect Volume", 31154, 2, 1,
				"Interfaces/WrenchTab/bar", 31155, "", "", 12, 20);

		addConfigHover(31156, 4, 31157, 2, 1, "Interfaces/WrenchTab/bar", 16,
				16, 516, 1, "Adjust Sound Effect Volume", 31158, 2, 1,
				"Interfaces/WrenchTab/bar", 31159, "", "", 12, 20);

		addConfigHover(31160, 4, 31161, 1, 2, "Interfaces/WrenchTab/bar", 16,
				16, 517, 1, "Adjust Area Sound Effect Volume", 31162, 1, 2,
				"Interfaces/WrenchTab/bar", 31163, "", "", 12, 20);

		addConfigHover(31164, 4, 31165, 2, 1, "Interfaces/WrenchTab/bar", 16,
				16, 518, 1, "Adjust Area Sound Effect Volume", 31166, 2, 1,
				"Interfaces/WrenchTab/bar", 31167, "", "", 12, 20);

		addConfigHover(31168, 4, 31169, 2, 1, "Interfaces/WrenchTab/bar", 16,
				16, 519, 1, "Adjust Area Sound Effect Volume", 31170, 2, 1,
				"Interfaces/WrenchTab/bar", 31171, "", "", 12, 20);

		addConfigHover(31172, 4, 31173, 2, 1, "Interfaces/WrenchTab/bar", 16,
				16, 520, 1, "Adjust Area Sound Effect Volume", 31174, 2, 1,
				"Interfaces/WrenchTab/bar", 31175, "", "", 12, 20);

		setChildren(46, rsi);

		setBounds(31001, 3, 45, 0, rsi);
		setBounds(31041, 6, 3, 1, rsi); // Display
		setBounds(31042, 6, 3, 2, rsi);
		setBounds(31044, 52, 3, 3, rsi); // Audio
		setBounds(31045, 52, 3, 4, rsi);
		setBounds(31008, 98, 3, 5, rsi); // Chat
		setBounds(31009, 98, 3, 6, rsi);
		setBounds(31011, 144, 3, 7, rsi); // Controls
		setBounds(31012, 144, 3, 8, rsi);
		setBounds(31014, 6, 221, 9, rsi); // Aid
		setBounds(31016, 6, 221, 10, rsi); // Aid
		setBounds(31018, 52, 221, 11, rsi); // Run
		setBounds(31020, 52, 221, 12, rsi); // Run
		setBounds(31022, 98, 221, 13, rsi); // House
		setBounds(31023, 144, 221, 14, rsi); // Membership Bonds
		setBounds(149, 57, 241, 15, rsi); // Run Text
		setBounds(31121, 12, 68, 16, rsi);
		setBounds(31122, 12, 117, 17, rsi);
		setBounds(31123, 12, 166, 18, rsi);
		setBounds(31124, 51, 76, 19, rsi); // Bar
		setBounds(31125, 51, 124, 20, rsi); // Bar
		setBounds(31126, 51, 173, 21, rsi); // Bar

		setBounds(31128, 59, 77, 22, rsi); // Bar
		setBounds(31130, 59, 77, 23, rsi); // Bar

		setBounds(31132, 90, 77, 24, rsi); // Bar
		setBounds(31134, 90, 77, 25, rsi); // Bar

		setBounds(31136, 121, 77, 26, rsi); // Bar
		setBounds(31138, 121, 77, 27, rsi); // Bar

		setBounds(31140, 152, 77, 28, rsi); // Bar
		setBounds(31142, 152, 77, 29, rsi); // Bar

		setBounds(31144, 59, 125, 30, rsi); // Bar
		setBounds(31146, 59, 125, 31, rsi); // Bar

		setBounds(31148, 90, 125, 32, rsi); // Bar
		setBounds(31150, 90, 125, 33, rsi); // Bar

		setBounds(31152, 121, 125, 34, rsi); // Bar
		setBounds(31154, 121, 125, 35, rsi); // Bar

		setBounds(31156, 152, 125, 36, rsi); // Bar
		setBounds(31158, 152, 125, 37, rsi); // Bar

		setBounds(31160, 59, 173, 38, rsi); // Bar
		setBounds(31162, 59, 173, 39, rsi); // Bar

		setBounds(31164, 90, 173, 40, rsi); // Bar
		setBounds(31166, 90, 173, 41, rsi); // Bar

		setBounds(31168, 121, 173, 42, rsi); // Bar
		setBounds(31170, 121, 173, 43, rsi); // Bar

		setBounds(31172, 152, 173, 44, rsi); // Bar
		setBounds(31174, 152, 173, 45, rsi); // Bar

		rsi = addInterface(31060); // Chat Tab

		addHoverButton(31061, "Interfaces/WrenchTab/main tab/chat", 2, 40, 40,
				"Chat Test", -1, 31062, 1);
		addHoveredButton(31062, "Interfaces/WrenchTab/main tab/chat", 4, 40,
				40, 31063);

		addConfigHover(31064, 4, 31065, 2, 1,
				"Interfaces/WrenchTab/main tab/ceffects", 40, 40, 171, 1,
				"Toggle Chat Effects", 31066, 2, 1,
				"Interfaces/WrenchTab/main tab/ceffects", 31067, "", "", 12, 20);

		addConfigHover(31068, 4, 31069, 1, 2,
				"Interfaces/WrenchTab/main tab/pchat", 40, 40, 287, 1,
				"Toggle Split Chat", 31070, 1, 2,
				"Interfaces/WrenchTab/main tab/pchat", 31071, "", "", 12, 20);

		addConfigHover(31072, 4, 31073, 1, 2,
				"Interfaces/WrenchTab/main tab/profanity", 40, 40, 311, 1,
				"Toggle Profanity Filter", 31074, 1, 2,
				"Interfaces/WrenchTab/main tab/profanity", 31075, "", "", 12,
				20);

		addConfigHover(31076, 4, 31077, 1, 2,
				"Interfaces/WrenchTab/main tab/lnotification", 40, 40, 312, 1,
				"Toggle Login/Logout notification timeout", 31078, 1, 2,
				"Interfaces/WrenchTab/main tab/lnotification", 31079, "", "",
				12, 20);

		setChildren(24, rsi);
		setBounds(31001, 3, 45, 0, rsi);
		setBounds(31041, 6, 3, 1, rsi); // Display
		setBounds(31042, 6, 3, 2, rsi);
		setBounds(31005, 52, 3, 3, rsi); // Audio
		setBounds(31006, 52, 3, 4, rsi);
		setBounds(31061, 98, 3, 5, rsi); // Chat
		setBounds(31062, 98, 3, 6, rsi);
		setBounds(31011, 144, 3, 7, rsi); // Controls
		setBounds(31012, 144, 3, 8, rsi);
		setBounds(31014, 6, 221, 9, rsi); // Aid
		setBounds(31016, 6, 221, 10, rsi); // Aid
		setBounds(31018, 52, 221, 11, rsi); // Run
		setBounds(31020, 52, 221, 12, rsi); // Run
		setBounds(31022, 98, 221, 13, rsi); // House
		setBounds(31023, 144, 221, 14, rsi); // Membership Bonds
		setBounds(149, 57, 241, 15, rsi); // Run Text
		setBounds(31064, 39, 81, 16, rsi); // Toggle Chat
		setBounds(31066, 39, 81, 17, rsi); // Toggle Chat
		setBounds(31068, 111, 81, 18, rsi); // Split Chat
		setBounds(31070, 111, 81, 19, rsi); // Split Chat
		setBounds(31072, 39, 144, 20, rsi); // Profanity Filter
		setBounds(31074, 39, 144, 21, rsi); // Profanity Filter
		setBounds(31076, 111, 144, 22, rsi); // Logout notification
		setBounds(31078, 111, 144, 23, rsi); // Logout notification

		rsi = addInterface(31080); // Controls Tab

		addHoverButton(31081, "Interfaces/WrenchTab/main tab/controls", 2, 40,
				40, "Controls Test", -1, 31082, 1);
		addHoveredButton(31082, "Interfaces/WrenchTab/main tab/controls", 4,
				40, 40, 31083);

		addConfigHover(31084, 4, 31085, 1, 2,
				"Interfaces/WrenchTab/main tab/mouse", 40, 40, 313, 1,
				"Toggle number of mouse buttons", 31086, 1, 2,
				"Interfaces/WrenchTab/main tab/mouse", 31087, "Toggle xp 2",
				"Toggle xp 3", 12, 20);

		addConfigHover(31088, 4, 31089, 1, 2,
				"Interfaces/WrenchTab/main tab/camera", 40, 40, 314, 1,
				"Toggle Mouse Camera", 31090, 1, 2,
				"Interfaces/WrenchTab/main tab/camera", 31091, "Toggle xp 2",
				"Toggle xp 3", 12, 20);

		addText(31092, "Attack option priority:", 0xFF981F, false, true, 52,
				tda, 1);

		addConfigHover(31093, 4, 31094, 2, 1,
				"Interfaces/WrenchTab/main tab/check", 15, 15, 315, 1, "Auto",
				31095, 2, 1, "Interfaces/WrenchTab/main tab/check", 31096, "",
				"", 12, 20);

		addConfigHover(31097, 4, 31098, 1, 2,
				"Interfaces/WrenchTab/main tab/check", 15, 15, 316, 1,
				"Left-click ", 31099, 1, 2,
				"Interfaces/WrenchTab/main tab/check", 31100, "", "", 12, 20);

		addConfigHover(31101, 4, 31102, 1, 2,
				"Interfaces/WrenchTab/main tab/check", 15, 15, 317, 1,
				"Right-click ", 31103, 1, 2,
				"Interfaces/WrenchTab/main tab/check", 31104, "", "", 12, 20);

		// addText(31105, "Attack option priority:", 0xFF981F, false, true, 52,
		// tda, 1);

		addHoverText(31105, "Depends on combat levels.",
				"Depends on combat levels.", tda, 0, 0xFF981F, false, true, 150);

		addHoverText(31106, "Left-click where available.",
				"Left-click where available.", tda, 0, 0xFF981F, false, true,
				150);

		addHoverText(31107, "Always right-click.", "Always right-click.", tda,
				0, 0xFF981F, false, true, 150);

		setChildren(30, rsi);
		setBounds(31001, 3, 45, 0, rsi);
		setBounds(31041, 6, 3, 1, rsi); // Display
		setBounds(31042, 6, 3, 2, rsi);
		setBounds(31005, 52, 3, 3, rsi); // Audio
		setBounds(31006, 52, 3, 4, rsi);
		setBounds(31008, 98, 3, 5, rsi); // Chat
		setBounds(31009, 98, 3, 6, rsi);
		setBounds(31081, 144, 3, 7, rsi); // Controls
		setBounds(31082, 144, 3, 8, rsi);
		setBounds(31014, 6, 221, 9, rsi); // Aid
		setBounds(31016, 6, 221, 10, rsi); // Aid
		setBounds(31018, 52, 221, 11, rsi); // Run
		setBounds(31020, 52, 221, 12, rsi); // Run
		setBounds(31022, 98, 221, 13, rsi); // House
		setBounds(31023, 144, 221, 14, rsi); // Membership Bonds
		setBounds(149, 57, 241, 15, rsi); // Run Text
		setBounds(31084, 40, 71, 16, rsi); // Mouse
		setBounds(31086, 40, 71, 17, rsi); // Mouse
		setBounds(31088, 110, 71, 18, rsi); // Camera
		setBounds(31090, 110, 71, 19, rsi); // Camera
		setBounds(31092, 9, 126, 20, rsi); // Text
		setBounds(31093, 10, 145, 21, rsi); // Check
		setBounds(31095, 10, 145, 22, rsi); // Check
		setBounds(31097, 10, 162, 23, rsi); // Check
		setBounds(31099, 10, 162, 24, rsi); // Check
		setBounds(31101, 10, 179, 25, rsi); // Check
		setBounds(31103, 10, 179, 26, rsi); // Check
		setBounds(31105, 26, 145, 27, rsi); // Text
		setBounds(31106, 26, 162, 28, rsi); // Text
		setBounds(31107, 26, 179, 29, rsi); // Text

	}
	
	public static void itemsOnDeathDATA(TextDrawingArea[] tda) {
		RSInterface RSinterface = addInterface(17115);
		addText(17109, "", 0xff981f, false, false, 0, tda, 0);
		addText(17110, "The normal amount of", 0xff981f, false, false, 0, tda,
				0);
		addText(17111, "items kept is three.", 0xff981f, false, false, 0, tda,
				0);
		addText(17112, "", 0xff981f, false, false, 0, tda, 0);
		addText(17113, "If you are skulled,", 0xff981f, false, false, 0, tda, 0);
		addText(17114, "you will lose all your", 0xff981f, false, false, 0,
				tda, 0);
		addText(17117, "items, unless an item", 0xff981f, false, false, 0, tda,
				0);
		addText(17118, "protecting prayer is", 0xff981f, false, false, 0, tda,
				0);
		addText(17119, "used.", 0xff981f, false, false, 0, tda, 0);
		addText(17120, "", 0xff981f, false, false, 0, tda, 0);
		addText(17121, "Item protecting prayers", 0xff981f, false, false, 0,
				tda, 0);
		addText(17122, "will allow you to keep", 0xff981f, false, false, 0,
				tda, 0);
		addText(17123, "one extra item.", 0xff981f, false, false, 0, tda, 0);
		addText(17124, "", 0xff981f, false, false, 0, tda, 0);
		addText(17125, "The items kept are", 0xff981f, false, false, 0, tda, 0);
		addText(17126, "selected by the server", 0xff981f, false, false, 0,
				tda, 0);
		addText(17127, "and include the most", 0xff981f, false, false, 0, tda,
				0);
		addText(17128, "expensive items you're", 0xff981f, false, false, 0,
				tda, 0);
		addText(17129, "carrying.", 0xff981f, false, false, 0, tda, 0);
		addText(17130, "", 0xff981f, false, false, 0, tda, 0);
		RSinterface.parentID = 17115;
		RSinterface.id = 17115;
		RSinterface.type = 0;
		RSinterface.atActionType = 0;
		RSinterface.contentType = 0;
		RSinterface.width = 130;
		RSinterface.height = 197;
		RSinterface.opacity = 0;
		RSinterface.hoverType = -1;
		RSinterface.scrollMax = 280;
		RSinterface.children = new int[20];
		RSinterface.childX = new int[20];
		RSinterface.childY = new int[20];
		RSinterface.children[0] = 17109;
		RSinterface.childX[0] = 0;
		RSinterface.childY[0] = 0;
		RSinterface.children[1] = 17110;
		RSinterface.childX[1] = 0;
		RSinterface.childY[1] = 12;
		RSinterface.children[2] = 17111;
		RSinterface.childX[2] = 0;
		RSinterface.childY[2] = 24;
		RSinterface.children[3] = 17112;
		RSinterface.childX[3] = 0;
		RSinterface.childY[3] = 36;
		RSinterface.children[4] = 17113;
		RSinterface.childX[4] = 0;
		RSinterface.childY[4] = 48;
		RSinterface.children[5] = 17114;
		RSinterface.childX[5] = 0;
		RSinterface.childY[5] = 60;
		RSinterface.children[6] = 17117;
		RSinterface.childX[6] = 0;
		RSinterface.childY[6] = 72;
		RSinterface.children[7] = 17118;
		RSinterface.childX[7] = 0;
		RSinterface.childY[7] = 84;
		RSinterface.children[8] = 17119;
		RSinterface.childX[8] = 0;
		RSinterface.childY[8] = 96;
		RSinterface.children[9] = 17120;
		RSinterface.childX[9] = 0;
		RSinterface.childY[9] = 108;
		RSinterface.children[10] = 17121;
		RSinterface.childX[10] = 0;
		RSinterface.childY[10] = 120;
		RSinterface.children[11] = 17122;
		RSinterface.childX[11] = 0;
		RSinterface.childY[11] = 132;
		RSinterface.children[12] = 17123;
		RSinterface.childX[12] = 0;
		RSinterface.childY[12] = 144;
		RSinterface.children[13] = 17124;
		RSinterface.childX[13] = 0;
		RSinterface.childY[13] = 156;
		RSinterface.children[14] = 17125;
		RSinterface.childX[14] = 0;
		RSinterface.childY[14] = 168;
		RSinterface.children[15] = 17126;
		RSinterface.childX[15] = 0;
		RSinterface.childY[15] = 180;
		RSinterface.children[16] = 17127;
		RSinterface.childX[16] = 0;
		RSinterface.childY[16] = 192;
		RSinterface.children[17] = 17128;
		RSinterface.childX[17] = 0;
		RSinterface.childY[17] = 204;
		RSinterface.children[18] = 17129;
		RSinterface.childX[18] = 0;
		RSinterface.childY[18] = 216;
		RSinterface.children[19] = 17130;
		RSinterface.childX[19] = 0;
		RSinterface.childY[19] = 228;
	}
	
	public static void itemsKeptOnDeath(TextDrawingArea[] tda) {
		RSInterface Interface = addInterface(22030);
		addSprite(22031, 1, "Interfaces/Death/SPRITE");
		addHoverButton(22032, "Interfaces/Death/SPRITE", 2, 17, 17, "Close",
				250, 22033, 3);
		addHoveredButton(22033, "Interfaces/Death/SPRITE", 3, 17, 17, 22034);
		addText(22035, "", tda, 0, 0xff981f, false, true);
		addText(22036, "", tda, 0, 0xff981f, false, true);
		addText(22037, "", tda, 0, 0xff981f, false, true);
		addText(22038, "", tda, 0, 0xff981f, false, true);
		addText(22039, "", tda, 0, 0xff981f, false, true);
		addText(22040, "", tda, 1, 0xffcc33, false, true);
		setChildren(9, Interface);
		setBounds(22031, 7, 8, 0, Interface);
		setBounds(22032, 480, 18, 1, Interface);
		setBounds(22033, 480, 18, 2, Interface);
		setBounds(22035, 348, 98, 3, Interface);
		setBounds(22036, 348, 110, 4, Interface);
		setBounds(22037, 348, 122, 5, Interface);
		setBounds(22038, 348, 134, 6, Interface);
		setBounds(22039, 348, 146, 7, Interface);
		setBounds(22040, 398, 297, 8, Interface);
	}	
	
	

	
	public static void itemsOnDeath(TextDrawingArea[] wid) {
		RSInterface rsinterface = addInterface(17100);
		addSprite(17101, 2, 2);
		// addHover(17102,"Items Kept On Death/SPRITE", 1, 17, 17, "Close", 0,
		// 10602, 1);
		// addHovered(10602,"Items Kept On Death/SPRITE", 3, 17, 17, 10603);
		addText(17103, "Items kept on death", wid, 2, 0xff981f);
		addText(17104, "Items I will keep...", wid, 1, 0xff981f);
		addText(17105, "Items I will lose...", wid, 1, 0xff981f);
		addText(17106, "Info", wid, 1, 0xff981f);
		addText(17107, "", wid, 1, 0xffcc33);
		addText(17108, "", wid, 1, 0xffcc33);
		// rsinterface.scrollMax = 50;
		rsinterface.interfaceShown = false;
		rsinterface.children = new int[12];
		rsinterface.childX = new int[12];
		rsinterface.childY = new int[12];

		rsinterface.children[0] = 17101;
		rsinterface.childX[0] = 7;
		rsinterface.childY[0] = 8;
		rsinterface.children[1] = 15210;
		rsinterface.childX[1] = 478;
		rsinterface.childY[1] = 17;
		rsinterface.children[2] = 17103;
		rsinterface.childX[2] = 185;
		rsinterface.childY[2] = 18;
		rsinterface.children[3] = 17104;
		rsinterface.childX[3] = 22;
		rsinterface.childY[3] = 49;
		rsinterface.children[4] = 17105;
		rsinterface.childX[4] = 22;
		rsinterface.childY[4] = 109;
		rsinterface.children[5] = 17106;
		rsinterface.childX[5] = 347;
		rsinterface.childY[5] = 49;
		rsinterface.children[6] = 17107;
		rsinterface.childX[6] = 348;
		rsinterface.childY[6] = 270;
		rsinterface.children[7] = 17108;
		rsinterface.childX[7] = 401;
		rsinterface.childY[7] = 293;
		rsinterface.children[8] = 17115;
		rsinterface.childX[8] = 348;
		rsinterface.childY[8] = 64;
		rsinterface.children[9] = 10494;
		rsinterface.childX[9] = 26;
		rsinterface.childY[9] = 71;
		rsinterface.children[10] = 10600;
		rsinterface.childX[10] = 26;
		rsinterface.childY[10] = 129;
		rsinterface.children[11] = 15211;
		rsinterface.childX[11] = 478;
		rsinterface.childY[11] = 17;
		rsinterface = interfaceCache[10494];
		rsinterface.invSpritePadX = 6;
		rsinterface.invSpritePadY = 5;
		rsinterface = interfaceCache[10600];
		rsinterface.invSpritePadX = 6;
		rsinterface.invSpritePadY = 5;
	}
	
	public static void quickPrayers(TextDrawingArea[] TDA) {
		int frame = 0;
		RSInterface tab = addTabInterface(17200);

		setChildren(55, tab);
		setBounds(5632, 5, 8 + 3, frame++, tab);
		setBounds(5633, 44, 8 + 3, frame++, tab);
		setBounds(5634, 79, 11 + 3, frame++, tab);
		setBounds(19813, 116, 10 + 3, frame++, tab);
		setBounds(19815, 153, 9 + 3, frame++, tab);
		setBounds(5635, 5, 48 + 3, frame++, tab);
		setBounds(5636, 44, 47 + 3, frame++, tab);
		setBounds(5637, 79, 49 + 3, frame++, tab);
		setBounds(5638, 116, 50 + 3, frame++, tab);
		setBounds(5639, 154, 50 + 3, frame++, tab);
		setBounds(5640, 4, 84 + 3, frame++, tab);
		setBounds(19817, 44, 87 + 3, frame++, tab);
		setBounds(19820, 81, 85 + 3, frame++, tab);
		setBounds(5641, 117, 85 + 3, frame++, tab);
		setBounds(5642, 156, 87 + 3, frame++, tab);
		setBounds(5643, 5, 125 + 3, frame++, tab);
		setBounds(5644, 43, 124 + 3, frame++, tab);
		setBounds(13984, 83, 124 + 3, frame++, tab);
		setBounds(5645, 115, 121 + 3, frame++, tab);
		setBounds(19822, 154, 124 + 3, frame++, tab);
		setBounds(19824, 5, 160 + 3, frame++, tab);
		setBounds(5649, 41, 158 + 3, frame++, tab);
		setBounds(5647, 79, 163 + 3, frame++, tab);
		setBounds(5648, 116, 158 + 3, frame++, tab);
		setBounds(19826, 161, 160 + 3, frame++, tab);
		setBounds(19828, 4, 202, frame++, tab);

		setBounds(17229, 0, 7, frame++, tab);// Faded backing
		// setBounds(17201, 0, 22, frame++, tab);// Split
		// setBounds(17201, 0, 237, frame++, tab);// Split

		setBounds(17202, 5 - 3, 8, frame++, tab);
		setBounds(17203, 44 - 3, 8, frame++, tab);
		setBounds(17204, 79 - 3, 8, frame++, tab);
		setBounds(17205, 116 - 3, 8, frame++, tab);
		setBounds(17206, 153 - 3, 8, frame++, tab);
		setBounds(17207, 5 - 3, 48, frame++, tab);
		setBounds(17208, 44 - 3, 48, frame++, tab);
		setBounds(17209, 79 - 3, 48, frame++, tab);
		setBounds(17210, 116 - 3, 48, frame++, tab);
		setBounds(17211, 153 - 3, 48, frame++, tab);
		setBounds(17212, 5 - 3, 85, frame++, tab);
		setBounds(17213, 44 - 3, 85, frame++, tab);
		setBounds(17214, 79 - 3, 85, frame++, tab);
		setBounds(17215, 116 - 3, 85, frame++, tab);
		setBounds(17216, 153 - 3, 85, frame++, tab);
		setBounds(17217, 5 - 3, 124, frame++, tab);
		setBounds(17218, 44 - 3, 124, frame++, tab);
		setBounds(17219, 79 - 3, 124, frame++, tab);
		setBounds(17220, 116 - 3, 124, frame++, tab);
		setBounds(17221, 153 - 3, 124, frame++, tab);
		setBounds(17222, 5 - 3, 160, frame++, tab);
		setBounds(17223, 44 - 3, 160, frame++, tab);
		setBounds(17224, 79 - 3, 160, frame++, tab);
		setBounds(17225, 116 - 3, 160, frame++, tab);
		setBounds(17226, 153 - 3, 160, frame++, tab);
		setBounds(17227, 4 - 3, 194, frame++, tab);

		// setBounds(17230, 5, 5, frame++, tab);// text
		setBounds(17231, 54, 233, frame++, tab);// Confirm
		setBounds(17232, 54, 233, frame++, tab);// Confirm hover
	}
	
	public static void questTab(TextDrawingArea[] TDA) {
		RSInterface Interface = addInterface(638);
		setChildren(5, Interface);
		addText(29155, "Quest Tab", 0xFF981F, false, true, 52, TDA, 2);
		addButton(29156, 1, "Interfaces/QuestTab/QUEST", 18, 18,
				"Swap to Achievements", 1);
		addButton(29270, 3, "Interfaces/QuestTab/QUEST", 18, 18,
				"Swap to Minigames", 1);
		addSprite(29157, 0, "Interfaces/QuestTab/QUEST");
		setBounds(29155, 10, 5, 0, Interface);
		setBounds(29156, 154, 4, 1, Interface);
		setBounds(29157, 3, 24, 2, Interface);
		setBounds(29160, 5, 29, 3, Interface);
		setBounds(29270, 172, 4, 4, Interface);
		Interface = addInterface(29160);
		Interface.height = 214;
		Interface.width = 165;
		Interface.scrollMax = 1700;
		Interface.newScroller = false;
		setChildren(104, Interface);
		addText(29161, " Meralin", 0xFF981F, false, true, 22, TDA, 1);
		addHoverText(29162, "", "View Progress", TDA, 0, 0xff0000, false, true,
				150);
		addHoverText(29163, "Players Online", "View Progress", TDA, 0, 0xff0000, false, true,
				150);
		addHoverText(29164, "Server Information", "View Progress", TDA, 0, 0xff0000, false, true,
				150);
		addHoverText(29165, "", "View Progress", TDA, 0, 0xff0000, false, true,
				150);
		addHoverText(29166, "", "View Progress", TDA, 0, 0xff0000, false, true,
				150);
		setBounds(29161, 4, 4, 0, Interface);
		setBounds(29162, 8, 22, 1, Interface);
		setBounds(29163, 8, 35, 2, Interface);
		setBounds(29164, 8, 48, 3, Interface);
		setBounds(29165, 8, 61, 4, Interface);
		setBounds(29166, 8, 74, 5, Interface);
		int Ypos = 87;
		int frameID = 6;
		for (int iD = 29167; iD <= 29264; iD++) {
			addHoverText(iD, "", "View" + iD/* "View Quest Journal, "+iD */,
					TDA, 0, 0xff0000, false, true, 150);
			setBounds(iD, 8, Ypos, frameID, Interface);
			frameID++;
			Ypos += 13;
			Ypos++;
		}
		Interface = addInterface(29265);
		setChildren(5, Interface);
		addText(29266, "        Achievements", 0xFF981F, false, true, -1, TDA,
				2);
		addButton(29267, 2, "Interfaces/QuestTab/QUEST", 18, 18,
				"Swap to Quests", 1);
		addButton(29271, 3, "Interfaces/QuestTab/QUEST", 18, 18,
				"Swap to Minigames", 1);
		addSprite(29269, 0, "Interfaces/QuestTab/QUEST");
		setBounds(29266, 10, 5, 0, Interface);
		setBounds(29267, 154, 4, 1, Interface);
		setBounds(29269, 3, 24, 2, Interface);
		setBounds(29268, 5, 29, 3, Interface);
		setBounds(29271, 172, 4, 4, Interface);
		Interface = addInterface(29268);
		Interface.height = 214;
		Interface.width = 165;
		Interface.scrollMax = 215;
		Interface.newScroller = false;
		setChildren(14, Interface);
		setBounds(29295, 8, 6, 0, Interface);
		setBounds(29287, 8, 21, 1, Interface);
		setBounds(29305, 8, 36, 2, Interface);
		setBounds(29306, 8, 51, 3, Interface);
		setBounds(29307, 8, 66, 4, Interface);
		setBounds(29308, 8, 81, 5, Interface);
		setBounds(29309, 8, 96, 6, Interface);
		setBounds(29310, 8, 110, 7, Interface);
		setBounds(29311, 8, 125, 8, Interface);
		setBounds(29312, 8, 155, 9, Interface);
		setBounds(29313, 8, 170, 10, Interface);
		setBounds(29314, 8, 140, 11, Interface);
		setBounds(29315, 8, 185, 12, Interface);
		setBounds(29316, 8, 200, 13, Interface);
		addHoverText(29295, "Tasks Completed: 0", "View Achievements", TDA, 0,
				65280, false, true, 150);
		addHoverText(29287, "Easy Tasks", "View Achievements", TDA, 0,
				0xFF981F, false, true, 150);
		addHoverText(29305, "        Task", "View Achievements",
				TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29306, "        Task", "View Achievements",
				TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29307, "        Task",
				"View Achievements", TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29308, "        Task",
				"View Achievements", TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29309, "        Task",
				"View Achievements", TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29310, "Medium Tasks", "View Achievements", TDA, 0,
				0xFF981F, false, true, 150);
		addHoverText(29311, "        Task",
				"View Achievements", TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29314, "        Task", "View Achievements",
				TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29312, "Hard Tasks", "View Achievements", TDA, 0,
				0xFF981F, false, true, 150);
		addHoverText(29313, "        Task", "View Achievements",
				TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29315, "        Task", "View Achievements",
				TDA, 0, 0xff0000, false, true, 150);
		addHoverText(29316, "        Task",
				"View Achievements", TDA, 0, 0xff0000, false, true, 150);

		Interface = addInterface(29300);

		addText(29301, "        MiniGames", 0xFF981F, false, true, -1, TDA, 2);
		addButton(29302, 2, "Interfaces/QuestTab/QUEST", 18, 18,
				"Swap to Quests", 1);
		addButton(29303, 1, "Interfaces/QuestTab/QUEST", 18, 18,
				"Swap to Achievements", 1);
		addSprite(29304, 0, "Interfaces/QuestTab/QUEST");

		setChildren(5, Interface);
		setBounds(29301, 10, 5, 0, Interface);
		setBounds(29302, 154, 4, 1, Interface);
		setBounds(29304, 3, 24, 2, Interface);
		setBounds(29350, 5, 29, 3, Interface);
		setBounds(29303, 172, 4, 4, Interface);
		Interface = addInterface(29350);

		addHoverText(29352, "Barrows", "Teleport to Barrows", TDA, 0, 0xFF981F,
				false, true, 150);
		addHoverText(29353, "Castle Wars", "Teleport to Castle Wars", TDA, 0,
				0xFF981F, false, true, 150);
		addHoverText(29351, "Duel Arena", "Teleport to Duel Arena", TDA, 0,
				0xFF981F, false, true, 150);
		addHoverText(29354, "Fight Caves", "Teleport to Fight Caves", TDA, 0,
				0xFF981F, false, true, 150);
		addHoverText(29355, "Fight Pits", "Teleport to Fight Pits", TDA, 0,
				0xFF981F, false, true, 150);
		addHoverText(29356, "Pest Control ", "Teleport to Pest Control", TDA,
				0, 0xFF981F, false, true, 150);
		addHoverText(29357, "Nightmarezone ", "Teleport to Nightmarezone", TDA,
				0, 0xFF981F, false, true, 150);
		addHoverText(29358, "Rouges Den", "Teleport to Rouges Den", TDA, 0,
				0xFF981F, false, true, 150);
		addHoverText(29359, "Warriors Guild", "Teleport to Warriors Guild",
				TDA, 0, 0xFF981F, false, true, 150);
		addHoverText(29360, "", "", TDA, 0, 0xFF981F, false, true, 150);
		addHoverText(29361, "", "", TDA, 0, 0xFF981F, false, true, 150);
		addHoverText(29362, "", "", TDA, 0, 0xFF981F, false, true, 150);

		Interface.height = 214;
		Interface.width = 165;
		Interface.scrollMax = 215;
		Interface.newScroller = false;
		setChildren(12, Interface);
		setBounds(29352, 8, 6, 0, Interface);
		setBounds(29353, 8, 21, 1, Interface);
		setBounds(29351, 8, 36, 2, Interface);
		setBounds(29354, 8, 51, 3, Interface);
		setBounds(29355, 8, 66, 4, Interface);
		setBounds(29356, 8, 81, 5, Interface);
		setBounds(29357, 8, 96, 6, Interface);
		setBounds(29358, 8, 110, 7, Interface);
		setBounds(29359, 8, 125, 8, Interface);
		setBounds(29360, 8, 140, 9, Interface);
		setBounds(29361, 8, 155, 10, Interface);
		setBounds(29362, 8, 170, 11, Interface);
	}
	
	public static void addHoverText(int id, String text, String tooltip,
			TextDrawingArea tda[], int idx, int color, boolean centerText,
			boolean textShadowed, int width) {
		RSInterface rsinterface = addInterface(id);
		rsinterface.id = id;
		rsinterface.parentID = id;
		rsinterface.type = 4;
		rsinterface.atActionType = 1;
		rsinterface.width = width;
		rsinterface.height = 11;
		rsinterface.contentType = 0;
		rsinterface.opacity = 0;
		rsinterface.mOverInterToTrigger = -1;
		rsinterface.centerText = centerText;
		rsinterface.textShadow = textShadowed;
		rsinterface.textDrawingAreas = tda[idx];
		rsinterface.message = text;
		rsinterface.aString228 = "";
		rsinterface.textColor = color;
		rsinterface.anInt219 = 0;
		rsinterface.anInt216 = 0xffffff;
		rsinterface.anInt239 = 0;
		rsinterface.tooltip = tooltip;
	}
	
	public static void priceChecker(TextDrawingArea[] wid) {
		RSInterface rsi = addInterface(22000);

		addSprite(22001, 1, "Interfaces/PriceChecker/checker");
		// Close
		addHoverButton(22002, "Interfaces/PriceChecker/close", 1, 16, 21,
				"Close", -1, 22003, 1);
		addHoveredButton(22003, "Interfaces/PriceChecker/close", 2, 21, 21,
				22004);
		// Deposit all
		addHoverButton(22005, "Interfaces/PriceChecker/deposit", 1, 36, 36,
				"Add all", -1, 22006, 1);
		addHoveredButton(22006, "Interfaces/PriceChecker/deposit", 2, 36, 36,
				22007);
		// Search
		addHoverButton(22008, "Interfaces/PriceChecker/search", 1, 36, 36,
				"Search for item", -1, 22009, 1);
		addHoveredButton(22009, "Interfaces/PriceChecker/search", 2, 36, 36,
				22010);

		// Text
		addText(22011, "Grand Exchange guide prices", wid, 2, 0xFF981F, false,
				true);
		addText(22012, "Total guide price:", wid, 1, 0xFF981F, false, true);
		addText(22013, "0", wid, 1, 0xffffff, false, true);

		rsi.totalChildren(11);
		rsi.child(0, 22001, 15, 15);
		rsi.child(1, 22002, 467, 22);
		rsi.child(2, 22003, 467, 22);
		rsi.child(3, 22005, 451, 285);
		rsi.child(4, 22006, 451, 285);
		rsi.child(5, 22008, 25, 285);
		rsi.child(6, 22009, 25, 285);
		rsi.child(7, 22011, 160, 22);
		rsi.child(8, 22012, 211, 287);
		rsi.child(9, 22013, 255, 302);
		rsi.child(10, 22014, 19, 55);

		RSInterface scroll = addInterface(22014);

		// Item Slots
		addText(22015, "", wid, 0, 0xFFFFFF, true, true);
		addText(22016, "", wid, 0, 0xFFFFFF, true, true);

		addText(22018, "", wid, 0, 0xFFFFFF, true, true);
		addText(22019, "", wid, 0, 0xFFFFFF, true, true);

		addText(22021, "", wid, 0, 0xFFFFFF, true, true);
		addText(22022, "", wid, 0, 0xFFFFFF, true, true);

		addText(22024, "", wid, 0, 0xFFFFFF, true, true);
		addText(22025, "", wid, 0, 0xFFFFFF, true, true);

		addText(22027, "", wid, 0, 0xFFFFFF, true, true); // Soul rune showing
															// someone above
		addText(22028, "", wid, 0, 0xFFFFFF, true, true);

		addText(22030, "", wid, 0, 0xFFFFFF, true, true);
		addText(22031, "", wid, 0, 0xFFFFFF, true, true);

		addText(22033, "", wid, 0, 0xFFFFFF, true, true);
		addText(22034, "", wid, 0, 0xFFFFFF, true, true);

		addText(22036, "", wid, 0, 0xFFFFFF, true, true);
		addText(22037, "", wid, 0, 0xFFFFFF, true, true);

		addText(22039, "", wid, 0, 0xFFFFFF, true, true);
		addText(22040, "", wid, 0, 0xFFFFFF, true, true);

		addText(22042, "", wid, 0, 0xFFFFFF, true, true);
		addText(22043, "", wid, 0, 0xFFFFFF, true, true);

		addText(22045, "", wid, 0, 0xFFFFFF, true, true);
		addText(22046, "", wid, 0, 0xFFFFFF, true, true);

		addText(22048, "", wid, 0, 0xFFFFFF, true, true);
		addText(22049, "", wid, 0, 0xFFFFFF, true, true);

		addText(22051, "", wid, 0, 0xFFFFFF, true, true);
		addText(22052, "", wid, 0, 0xFFFFFF, true, true);

		addText(22054, "", wid, 0, 0xFFFFFF, true, true);
		addText(22055, "", wid, 0, 0xFFFFFF, true, true);

		addText(22057, "", wid, 0, 0xFFFFFF, true, true);
		addText(22058, "", wid, 0, 0xFFFFFF, true, true);

		addText(22060, "", wid, 0, 0xFFFFFF, true, true);
		addText(22061, "", wid, 0, 0xFFFFFF, true, true);

		addText(22063, "", wid, 0, 0xFFFFFF, true, true);
		addText(22064, "", wid, 0, 0xFFFFFF, true, true);

		addText(22066, "", wid, 0, 0xFFFFFF, true, true);
		addText(22067, "", wid, 0, 0xFFFFFF, true, true);

		addText(22069, "", wid, 0, 0xFFFFFF, true, true);
		addText(22070, "", wid, 0, 0xFFFFFF, true, true);

		addText(22072, "", wid, 0, 0xFFFFFF, true, true);
		addText(22073, "", wid, 0, 0xFFFFFF, true, true);

		addText(22075, "", wid, 0, 0xFFFFFF, true, true);
		addText(22076, "", wid, 0, 0xFFFFFF, true, true);

		addText(22078, "", wid, 0, 0xFFFFFF, true, true);
		addText(22079, "", wid, 0, 0xFFFFFF, true, true);

		addText(22081, "", wid, 0, 0xFFFFFF, true, true);
		addText(22082, "", wid, 0, 0xFFFFFF, true, true);

		addText(22084, "", wid, 0, 0xFFFFFF, true, true);
		addText(22085, "", wid, 0, 0xFFFFFF, true, true);

		addText(22087, "", wid, 0, 0xFFFFFF, true, true);
		addText(22088, "", wid, 0, 0xFFFFFF, true, true);

		addText(22090, "", wid, 0, 0xFFFFFF, true, true);
		addText(22091, "", wid, 0, 0xFFFFFF, true, true);

		addText(22093, "", wid, 0, 0xFFFFFF, true, true);
		addText(22094, "", wid, 0, 0xFFFFFF, true, true);

		addText(22096, "", wid, 0, 0xFFFFFF, true, true);
		addText(22097, "", wid, 0, 0xFFFFFF, true, true);

		addPriceChecker(22099); // Price Checker

		scroll.totalChildren(57);
		scroll.child(0, 22015, 49, 32);
		scroll.child(1, 22016, 49, 42);
		// scroll.child(2, 22017, 49, 52);
		scroll.child(2, 22018, 138, 32);
		scroll.child(3, 22019, 138, 42);
		// scroll.child(5, 22020, 138, 52);
		scroll.child(4, 22021, 227, 32);
		scroll.child(5, 22022, 227, 42);
		// scroll.child(8, 22023, 227, 52);
		scroll.child(6, 22024, 316, 32);
		scroll.child(7, 22025, 316, 42);
		// scroll.child(11, 22026, 316, 52);
		scroll.child(8, 22027, 405, 32);
		scroll.child(9, 22028, 405, 42);
		// scroll.child(14, 22029, 405, 52); //First Row
		scroll.child(10, 22030, 49, 92);
		scroll.child(11, 22031, 49, 102);
		// scroll.child(17, 22032, 49, 112);
		scroll.child(12, 22033, 138, 92);
		scroll.child(13, 22034, 138, 102);
		// scroll.child(20, 22035, 138, 112);
		scroll.child(14, 22036, 227, 92);
		scroll.child(15, 22037, 227, 102);
		// scroll.child(23, 22038, 227, 112);
		scroll.child(16, 22039, 316, 92);
		scroll.child(17, 22040, 316, 102);
		// scroll.child(26, 22041, 316, 112);
		scroll.child(18, 22042, 405, 92);
		scroll.child(19, 22043, 405, 102);
		// scroll.child(29, 22044, 405, 112); //Second Row
		scroll.child(20, 22045, 49, 152);
		scroll.child(21, 22046, 49, 162);
		// scroll.child(32, 22047, 49, 172);
		scroll.child(22, 22048, 138, 152);
		scroll.child(23, 22049, 138, 162);
		// scroll.child(35, 22050, 138, 172);
		scroll.child(24, 22051, 227, 152);
		scroll.child(25, 22052, 227, 162);
		// scroll.child(38, 22053, 227, 172);
		scroll.child(26, 22054, 316, 152);
		scroll.child(27, 22055, 316, 162);
		// scroll.child(41, 22056, 316, 172);
		scroll.child(28, 22057, 405, 152);
		scroll.child(29, 22058, 405, 162);
		// scroll.child(44, 22059, 405, 172); //Third Row
		scroll.child(30, 22060, 49, 212);
		scroll.child(31, 22061, 49, 222);
		// scroll.child(47, 22062, 49, 232);
		scroll.child(32, 22063, 138, 212);
		scroll.child(33, 22064, 138, 222);
		// scroll.child(50, 22065, 138, 232);
		scroll.child(34, 22066, 227, 212);
		scroll.child(35, 22067, 227, 222);
		// scroll.child(53, 22068, 227, 232);
		scroll.child(36, 22069, 316, 212);
		scroll.child(37, 22070, 316, 222);
		// scroll.child(56, 22071, 316, 232);
		scroll.child(38, 22072, 405, 212); // Forth Row
		scroll.child(39, 22073, 405, 222);
		// scroll.child(59, 22074, 405, 232);
		scroll.child(40, 22075, 49, 272);
		scroll.child(41, 22076, 49, 282);
		// roll.child(62, 22077, 49, 292);
		scroll.child(42, 22078, 138, 272);
		scroll.child(43, 22079, 138, 282);
		// scroll.child(65, 22080, 138, 292);
		scroll.child(44, 22081, 227, 272);
		scroll.child(45, 22082, 227, 282);
		// scroll.child(68, 22083, 227, 292);
		scroll.child(46, 22084, 316, 272);
		scroll.child(47, 22085, 316, 282);
		// scroll.child(71, 22086, 316, 292);
		scroll.child(48, 22087, 405, 272); // Fifth Row
		scroll.child(49, 22088, 405, 282);
		// scroll.child(74, 22089, 405, 292);
		scroll.child(50, 22090, 49, 332);
		scroll.child(51, 22091, 49, 342);
		// scroll.child(77, 22092, 49, 352);
		scroll.child(52, 22093, 138, 332);
		scroll.child(53, 22094, 138, 342);
		// scroll.child(80, 22095, 138, 352);
		scroll.child(54, 22096, 227, 332);
		scroll.child(55, 22097, 227, 342);
		// scroll.child(83, 22098, 227, 352);
		scroll.child(56, 22099, 32, 0); // PriceChecker method
		scroll.width = 480 - 30;
		scroll.height = 217;
		scroll.scrollMax = 500;
	}
	
	public static void addPriceChecker(int index) {
		RSInterface rsi = interfaceCache[index] = new RSInterface();
		rsi.actions = new String[10];
		rsi.spritesX = new int[20];
		rsi.invStackSizes = new int[25];
		rsi.inv = new int[30];
		rsi.spritesY = new int[20];
		rsi.children = new int[0];
		rsi.childX = new int[0];
		rsi.childY = new int[0];
		rsi.actions[0] = "Take 1";
		rsi.actions[1] = "Take 5";
		rsi.actions[2] = "Take 10";
		rsi.actions[3] = "Take All";
		rsi.actions[4] = "Take X";
		rsi.centerText = true;
		rsi.aBoolean227 = false;
		rsi.aBoolean235 = false;
		rsi.usableItemInterface = false;
		rsi.isInventoryInterface = false;
		rsi.aBoolean259 = true;
		rsi.textShadow = false;
		rsi.invSpritePadX = 57;
		rsi.invSpritePadY = 28;
		rsi.height = 5;
		rsi.width = 5;
		rsi.parentID = 22099;
		rsi.id = 4393;
		rsi.type = 2;
	}
	
	public static void equipmentTab(TextDrawingArea[] wid) {
		RSInterface Interface = interfaceCache[1644];
		addSprite(15101, 0, "Interfaces/Equipment/bl");// cheap hax
		addSprite(15102, 1, "Interfaces/Equipment/bl");// cheap hax
		addSprite(15109, 2, "Interfaces/Equipment/bl");// cheap hax
		removeConfig(21338);
		removeConfig(21344);
		removeConfig(21342);
		removeConfig(21341);
		removeConfig(21340);
		removeConfig(15103);
		removeConfig(15104);
		// Interface.children[23] = 15101;
		// Interface.childX[23] = 40;
		// Interface.childY[23] = 205;
		Interface.children[24] = 15102;
		Interface.childX[24] = 110;
		Interface.childY[24] = 205;
		Interface.children[25] = 15109;
		Interface.childX[25] = 39;
		Interface.childY[25] = 240;
		Interface.children[26] = 27650;
		Interface.childX[26] = 0;
		Interface.childY[26] = 0;
		Interface = addInterface(27650);

		addHoverButton(27651, "Interfaces/Equipment/BOX", 2, 40, 40,
				"Price-checker", -1, 27652, 1);
		addHoveredButton(27652, "Interfaces/Equipment/HOVER", 2, 40, 40, 27658);

		addHoverButton(27653, "Interfaces/Equipment/BOX", 1, 40, 40,
				"Show Equipment Stats", -1, 27655, 1);
		addHoveredButton(27655, "Interfaces/Equipment/HOVER", 1, 40, 40, 27665);

		addHoverButton(27654, "Interfaces/Equipment/BOX", 3, 40, 40,
				"Show items kept on death", -1, 27657, 1);
		addHoveredButton(27657, "Interfaces/Equipment/HOVER", 3, 40, 40, 27666);

		setChildren(6, Interface);
		setBounds(27651, 75, 205, 0, Interface);
		setBounds(27652, 75, 205, 1, Interface);
		setBounds(27653, 23, 205, 2, Interface);
		setBounds(27654, 127, 205, 3, Interface);
		setBounds(27655, 23, 205, 4, Interface);
		setBounds(27657, 127, 205, 5, Interface);
	}	
	
	public static void equipmentScreen(TextDrawingArea[] wid) {
		RSInterface Interface = RSInterface.interfaceCache[1644];
		addButton(19144, 6, "Interfaces/Equipment/CUSTOM",
				"Show Equipment Stats");
		removeSomething(19145);
		removeSomething(19146);
		removeSomething(19147);
		// setBounds(19144, 21, 210, 23, Interface);
		setBounds(19145, 40, 210, 24, Interface);
		setBounds(19146, 40, 210, 25, Interface);
		setBounds(19147, 40, 210, 26, Interface);
		RSInterface tab = addTabInterface(15106);
		addSprite(15107, 7, "Interfaces/Equipment/CUSTOM");
		addHoverButton(15210, "Interfaces/Equipment/CUSTOM", 8, 21, 21,
				"Close", 250, 15211, 3);
		addHoveredButton(15211, "Interfaces/Equipment/CUSTOM", 9, 21, 21, 15212);
		addText(15111, "Equip Your Character...", wid, 2, 0xe4a146, false, true);
		addText(15112, "Attack bonus", wid, 2, 0xe4a146, false, true);
		addText(15113, "Defence bonus", wid, 2, 0xe4a146, false, true);
		addText(15114, "Other bonuses", wid, 2, 0xe4a146, false, true);
		for (int i = 1675; i <= 1684; i++) {
			textSize(i, wid, 1);
		}
		textSize(1686, wid, 1);
		textSize(1687, wid, 1);
		addChar(15125);
		tab.totalChildren(44);
		tab.child(0, 15107, 4, 20);
		tab.child(1, 15210, 476, 29);
		tab.child(2, 15211, 476, 29);
		tab.child(3, 15111, 14, 30);
		int Child = 4;
		int Y = 69;
		for (int i = 1675; i <= 1679; i++) {
			tab.child(Child, i, 20, Y);
			Child++;
			Y += 14;
		}
		tab.child(9, 1680, 20, 161);
		tab.child(10, 1681, 20, 177);
		tab.child(11, 1682, 20, 192);
		tab.child(12, 1683, 20, 207);
		tab.child(13, 1684, 20, 221);
		tab.child(14, 1686, 20, 262);
		tab.child(15, 15125, 170, 200);
		tab.child(16, 15112, 16, 55);
		tab.child(17, 1687, 20, 276);
		tab.child(18, 15113, 16, 147);
		tab.child(19, 15114, 16, 248);
		tab.child(20, 1645, 104 + 295, 149 - 52);
		tab.child(21, 1646, 399, 163);
		tab.child(22, 1647, 399, 163);
		tab.child(23, 1648, 399, 58 + 146);
		tab.child(24, 1649, 26 + 22 + 297 - 2, 110 - 44 + 118 - 13 + 5);
		tab.child(25, 1650, 321 + 22, 58 + 154);
		tab.child(26, 1651, 321 + 134, 58 + 118);
		tab.child(27, 1652, 321 + 134, 58 + 154);
		tab.child(28, 1653, 321 + 48, 58 + 81);
		tab.child(29, 1654, 321 + 107, 58 + 81);
		tab.child(30, 1655, 321 + 58, 58 + 42);
		tab.child(31, 1656, 321 + 112, 58 + 41);
		tab.child(32, 1657, 321 + 78, 58 + 4);
		tab.child(33, 1658, 321 + 37, 58 + 43);
		tab.child(34, 1659, 321 + 78, 58 + 43);
		tab.child(35, 1660, 321 + 119, 58 + 43);
		tab.child(36, 1661, 321 + 22, 58 + 82);
		tab.child(37, 1662, 321 + 78, 58 + 82);
		tab.child(38, 1663, 321 + 134, 58 + 82);
		tab.child(39, 1664, 321 + 78, 58 + 122);
		tab.child(40, 1665, 321 + 78, 58 + 162);
		tab.child(41, 1666, 321 + 22, 58 + 162);
		tab.child(42, 1667, 321 + 134, 58 + 162);
		tab.child(43, 1688, 50 + 297 - 2, 110 - 13 + 5);
		for (int i = 1675; i <= 1684; i++) {
			RSInterface rsi = interfaceCache[i];
			rsi.textColor = 0xe4a146;
			rsi.centerText = false;
		}
		for (int i = 1686; i <= 1687; i++) {
			RSInterface rsi = interfaceCache[i];
			rsi.textColor = 0xe4a146;
			rsi.centerText = false;
		}
	}
	
	public static void addChar(int ID) {
		RSInterface t = interfaceCache[ID] = new RSInterface();
		t.id = ID;
		t.parentID = ID;
		t.type = 6;
		t.atActionType = 0;
		t.contentType = 328;
		t.width = 136;
		t.height = 168;
		t.opacity = 0;
		t.mOverInterToTrigger = 0;
		t.modelZoom = 560;
		t.modelRotation1 = 150;
		t.modelRotation2 = 0;
		t.anInt257 = -1;
		t.anInt258 = -1;
	}
	
	public static void clanChatTab(TextDrawingArea[] tda) {
		RSInterface tab = addTabInterface(18128);
		addHoverButton(18129, "/Clan Chat/SPRITE", 6, 72, 32, "Join Clan", 550,
				18130, 5);
		addHoveredButton(18130, "/Clan Chat/SPRITE", 7, 72, 32, 18131);
		addHoverButton(18132, "/Clan Chat/SPRITE", 6, 72, 32, "Clan Setup", -1,
				18133, 5);
		addConfigHover(18250, 4, 18251, 1, 2, "/Clan Chat/Lootshare", 40, 40,
				308, 1, "Toggle Lootshare", 18252, 1, 2,
				"/Clan Chat/Lootshare", 18253, "Toggle xp 2", "Toggle xp 3",
				12, 20);
		addHoveredButton(18133, "/Clan Chat/SPRITE", 7, 72, 32, 18134);
		addText(18135, "Join Clan", tda, 0, 0xff9b00, true, true);
		addText(18136, "Clan Setup", tda, 0, 0xff9b00, true, true);
		addSprite(18137, 38, "/Clan Chat/SPRITE");
		addText(18138, "Clan Chat", tda, 2, 0xff9b00, true, true);
		addText(18139, "Talking in: Not in clan", tda, 0, 0xff9b00, false, true);
		addText(18140, "Owner: None", tda, 0, 0xff9b00, false, true);
		addSprite(16126, 4, "/Clan Chat/SPRITE");
		addText(18141, "(1/100)", tda, 2, 0xff9b00, true, true);
		tab.totalChildren(14);
		tab.child(0, 18137, 0, 53);
		tab.child(1, 18143, 6, 62);
		tab.child(2, 18129, 15, 226);
		tab.child(3, 18130, 15, 226);
		tab.child(4, 18132, 103, 226);
		tab.child(5, 18133, 103, 226);
		tab.child(6, 18135, 51, 237);
		tab.child(7, 18136, 139, 237);
		tab.child(8, 18138, 95, 1);
		tab.child(9, 18139, 10, 23);
		tab.child(10, 18140, 25, 38);
		tab.child(11, 18250, 153, 22);
		tab.child(12, 18252, 153, 22);
		tab.child(13, 18141, 163, 1);

		/* Text area */
		RSInterface list = addTabInterface(18143);
		list.totalChildren(100);
		for (int i = 18144; i <= 18244; i++) {
			addText(i, "", tda, 0, 0xffffff, false, true);
		}
		for (int id = 18144, i = 0; id <= 18243 && i <= 99; id++, i++) {
			interfaceCache[id].actions = new String[] { "Edit Rank", "Kick",
					"Ban" };
			list.children[i] = id;
			list.childX[i] = 5;
			for (int id2 = 18144, i2 = 1; id2 <= 18243 && i2 <= 99; id2++, i2++) {
				list.childY[0] = 2;
				list.childY[i2] = list.childY[i2 - 1] + 14;
			}
		}
		list.height = 158;
		list.width = 162;
		list.scrollMax = 1405;
	}
	
	public static void clanChatSetup(TextDrawingArea[] tda) {
		RSInterface tab = addTabInterface(15456);
		addSprite(15251, 0, "Clan Chat/sprite");
		addHoverButton(15252, "Clan Chat/button", 2, 150, 35, "Set prefix", -1,
				15253, 1);
		addHoveredButton(15253, "Clan Chat/button", 3, 150, 35, 15254);
		addHoverButton(15255, "Clan Chat/button", 2, 150, 35, "Anyone", -1,
				15256, 1);
		addHoveredButton(15256, "Clan Chat/button", 3, 150, 35, 15257);

		addHoverButton(16000, "b", 1, 150, 35, "Only me", -1, 15999, 1);
		addHoverButton(16001, "b", 1, 150, 35, "General+", -1, 15999, 1);
		addHoverButton(16002, "b", 1, 150, 35, "Captain+", -1, 15999, 1);
		addHoverButton(16003, "b", 1, 150, 35, "Lieutenant+", -1, 15999, 1);
		addHoverButton(16004, "b", 1, 150, 35, "Sergeant+", -1, 15999, 1);
		addHoverButton(16005, "b", 1, 150, 35, "Corporal+", -1, 15999, 1);
		addHoverButton(16006, "b", 1, 150, 35, "Recruit+", -1, 15999, 1);
		addHoverButton(16007, "b", 1, 150, 35, "Any friends", -1, 15999, 1);

		addHoverButton(15258, "Clan Chat/button", 2, 150, 35, "Anyone", -1,
				15259, 1);
		addHoveredButton(15259, "Clan Chat/button", 3, 150, 35, 15260);

		addHoverButton(16010, "b", 1, 150, 35, "Only me", -1, 15999, 1);
		addHoverButton(16011, "b", 1, 150, 35, "General+", -1, 15999, 1);
		addHoverButton(16012, "b", 1, 150, 35, "Captain+", -1, 15999, 1);
		addHoverButton(16013, "b", 1, 150, 35, "Lieutenant+", -1, 15999, 1);
		addHoverButton(16014, "b", 1, 150, 35, "Sergeant+", -1, 15999, 1);
		addHoverButton(16015, "b", 1, 150, 35, "Corporal+", -1, 15999, 1);
		addHoverButton(16016, "b", 1, 150, 35, "Recruit+", -1, 15999, 1);
		addHoverButton(16017, "b", 1, 150, 35, "Any friends", -1, 15999, 1);

		addHoverButton(15261, "Clan Chat/button", 2, 150, 35, "Only me", -1,
				15262, 1);
		addHoveredButton(15262, "Clan Chat/button", 3, 150, 35, 15263);

		// addHoverButton(48020, "b", 1, 150, 35, "Only me", -1, 47999, 1);
		addHoverButton(16221, "b", 1, 150, 35, "General+", -1, 15999, 1);
		addHoverButton(16222, "b", 1, 150, 35, "Captain+", -1, 15999, 1);
		addHoverButton(16223, "b", 1, 150, 35, "Lieutenant+", -1, 15999, 1);
		addHoverButton(16224, "b", 1, 150, 35, "Sergeant+", -1, 15999, 1);
		addHoverButton(16225, "b", 1, 150, 35, "Corporal+", -1, 15999, 1);
		addHoverButton(16226, "b", 1, 150, 35, "Recruit+", -1, 15999, 1);

		addHoverButton(15267, "Clan Chat/close", 0, 16, 16, "Close", -1, 15268,
				1);
		addHoveredButton(15268, "Clan Chat/close", 1, 16, 16, 15269);

		addText(15800, "Clan name:", tda, 0, 0xff981f, false, true);
		addText(15801, "Who can enter chat?", tda, 0, 0xff981f, false, true);
		addText(15812, "Who can talk on chat?", tda, 0, 0xff981f, false, true);
		addText(15813, "Who can kick on chat?", tda, 0, 0xff981f, false, true);
		addText(15814, "None", tda, 2, 0xffffff, true, true);
		addText(15815, "Anyone", tda, 2, 0xffffff, true, true);
		addText(15816, "Anyone", tda, 2, 0xffffff, true, true);
		addText(15817, "Only me", tda, 2, 0xffffff, true, true);

		/* Table */
		addSprite(18319, 10, "/Interfaces/Clan Chat/sprite");

		/* Label */
		addText(18320, "Name:", tda, 2, 0xFF981F, false, true);
		addText(18321, "Rank:", tda, 2, 0xFF981F, false, true);

		/* Ranked members list */

		/* Table info text */
		addText(18322, "Friends List", tda, 2, 0xFF981F, true, true);

		addText(18323, "Right click on a name to edit the member.", tda, 0,
				0xFF981F, true, true);

		/* Add ranked member button */
		addButton(18324, 0, "/Interfaces/Clan Chat/plus", "Add ranked member");
		interfaceCache[18323].atActionType = 5;

		/* Add banned member button */
		addButton(18325, 0, "/Interfaces/Clan Chat/plus", "Add banned member");
		interfaceCache[18325].atActionType = 5;

		/* Clan Setup title */
		addText(18303, "Clan Chat Setup", tda, 2, 0xFF981F, true, true);

		tab.totalChildren(48);
		tab.child(0, 15251, 15, 15);
		tab.child(1, 15252, 25, 50);
		tab.child(2, 15253, 25, 50);
		tab.child(3, 15267, 476, 23);
		tab.child(4, 15268, 476, 23);
		tab.child(5, 16000, 25, 87 + 10);
		tab.child(6, 16001, 25, 87 + 10);
		tab.child(7, 16002, 25, 87 + 10);
		tab.child(8, 16003, 25, 87 + 10);
		tab.child(9, 16004, 25, 87 + 10);
		tab.child(10, 16005, 25, 87 + 10);
		tab.child(11, 16006, 25, 87 + 10);
		tab.child(12, 16007, 25, 87 + 10);
		tab.child(13, 15255, 25, 87 + 10);
		tab.child(14, 15256, 25, 87 + 10);
		tab.child(15, 16010, 25, 128 + 16);
		tab.child(16, 16011, 25, 128 + 16);
		tab.child(17, 16012, 25, 128 + 16);
		tab.child(18, 16013, 25, 128 + 16);
		tab.child(19, 16014, 25, 128 + 16);
		tab.child(20, 16015, 25, 128 + 16);
		tab.child(21, 16016, 25, 128 + 16);
		tab.child(22, 16017, 25, 128 + 16);
		tab.child(23, 15258, 25, 128 + 16);
		tab.child(24, 15259, 25, 128 + 16);
		// tab.child(25, 48020, 25, 168+35);
		tab.child(25, 16221, 25, 168 + 23);
		tab.child(26, 16222, 25, 168 + 23);
		tab.child(27, 16223, 25, 168 + 23);
		tab.child(28, 16224, 25, 168 + 23);
		tab.child(29, 16225, 25, 168 + 23);
		tab.child(30, 16226, 25, 168 + 23);
		tab.child(31, 15261, 25, 168 + 23);
		tab.child(32, 15262, 25, 168 + 23);
		tab.child(33, 15800, 73, 54);
		tab.child(34, 15801, 53, 95 + 5);
		tab.child(35, 15812, 53, 136 + 10);
		tab.child(36, 15813, 53, 177 + 15);
		tab.child(37, 15814, 100, 54 + 12);
		tab.child(38, 15815, 100, 95 + 5 + 12);
		tab.child(39, 15816, 100, 136 + 10 + 12);
		tab.child(40, 15817, 100, 177 + 15 + 12);
		tab.child(41, 14100, 0 - 8, 94);
		tab.child(42, 18319, 197, 70);
		tab.child(43, 18320, 202 - 4, 74);
		tab.child(44, 18321, 339 - 4, 74);
		tab.child(45, 18322, 337, 47);
		tab.child(46, 18323, 337, 47 + 11);
		// tab.child(47, 18324, 319, 75);
		// tab.child(48, 18325, 459, 75);
		tab.child(47, 18303, 256, 26 - 5);

		tab = addTabInterface(14100);
		tab.width = 474;
		tab.height = 213;
		tab.scrollMax = 2030;
		for (int i = 14101; i <= 14300; i++)
			addText2(i, "", tda, 1, 0xffff64, false, true);

		for (int i = 17551; i <= 17750; i++) {
			addHoverText2(i, "", new String[] { "Unban", "General", "Captain",
					"Lieutenant", "Sergeant", "Corporal", "Recruit", "Guest" },
					tda, 1, 0xffffff, false, false, 150);
		}

		tab.totalChildren(400);
		int Child = 0;
		int Y = 3;
		for (int i = 14101; i <= 14300; i++) {
			tab.child(Child, i, 204, Y);
			Child++;
			Y += 13;
		}
		Y = 3;
		for (int i = 17551; i <= 17750; i++) {
			tab.child(Child, i, 343, Y);
			Child++;
			Y += 13;
		}
	}
	
	public static void addHoverText2(int id, String text, String[] tooltips,
			TextDrawingArea tda[], int idx, int color, boolean center,
			boolean textShadowed, int width) {
		RSInterface rsinterface = addInterface(id);
		rsinterface.id = id;
		rsinterface.parentID = id;
		rsinterface.type = 4;
		rsinterface.atActionType = 1;
		rsinterface.width = width;
		rsinterface.height = 11;
		rsinterface.contentType = 0;
		rsinterface.opacity = 0;
		rsinterface.hoverType = -1;
		rsinterface.centerText = center;
		rsinterface.textShadow = textShadowed;
		rsinterface.textDrawingAreas = tda[idx];
		rsinterface.message = text;
		rsinterface.aString228 = "";
		rsinterface.textColor = color;
		rsinterface.anInt219 = 0;
		rsinterface.anInt216 = 0xffffff;
		rsinterface.anInt239 = 0;
		rsinterface.tooltips = tooltips;
	}
	
	public static void addText2(int id, String text, TextDrawingArea tda[],
			int idx, int color, boolean center, boolean shadow) {
		RSInterface tab = addTabInterface(id);
		tab.parentID = id;
		tab.id = id;
		tab.type = 4;
		tab.atActionType = 0;
		tab.width = 0;
		tab.height = 11;
		tab.contentType = 0;
		tab.opacity = 0;
		tab.hoverType = -1;
		tab.centerText = center;
		tab.textShadow = shadow;
		tab.textDrawingAreas = tda[idx];
		tab.message = text;
		tab.aString228 = "";
		tab.textColor = color;
		tab.anInt219 = 0;
		tab.anInt216 = 0;
		tab.anInt239 = 0;
	}
	
	// addBankHover
	public static void addConfigHover(int interfaceID, int actionType,
			int hoverid, int spriteId, int spriteId2, String NAME, int Width,
			int Height, int configFrame, int configId, String Tooltip,
			int hoverId2, int hoverSpriteId, int hoverSpriteId2,
			String hoverSpriteName, int hoverId3, String hoverDisabledText,
			String hoverEnabledText, int X, int Y) {
		RSInterface hover = addTabInterface(interfaceID);
		hover.id = interfaceID;
		hover.parentID = interfaceID;
		hover.type = 5;
		hover.atActionType = actionType;
		hover.contentType = 0;
		hover.opacity = 0;
		hover.hoverType = hoverid;
		hover.disabledSprite = imageLoader(spriteId, NAME);
		hover.enabledSprite = imageLoader(spriteId2, NAME);
		hover.width = Width;
		hover.tooltip = Tooltip;
		hover.height = Height;
		hover.anIntArray245 = new int[1];
		hover.anIntArray212 = new int[1];
		hover.anIntArray245[0] = 1;
		hover.anIntArray212[0] = configId;
		hover.valueIndexArray = new int[1][3];
		hover.valueIndexArray[0][0] = 5;
		hover.valueIndexArray[0][1] = configFrame;
		hover.valueIndexArray[0][2] = 0;
		hover = addTabInterface(hoverid);
		hover.parentID = hoverid;
		hover.id = hoverid;
		hover.type = 0;
		hover.atActionType = 0;
		hover.width = 550;
		hover.height = 334;
		hover.isMouseoverTriggered = true;
		hover.hoverType = -1;
		addSprites(hoverId2, hoverSpriteId, hoverSpriteId2, hoverSpriteName,
				configId, configFrame);
		addHoverBox(hoverId3, interfaceID, hoverDisabledText, hoverEnabledText,
				configId, configFrame);
		setChildren(2, hover);
		setBounds(hoverId2, 15, 60, 0, hover);
		setBounds(hoverId3, X, Y, 1, hover);
	}
	
	 public static void addSprites(int ID, int i, int i2, String name,
             int configId, int configFrame)
     {
             RSInterface Tab = addTabInterface(ID);
             Tab.id = ID;
             Tab.parentID = ID;
             Tab.type = 5;
             Tab.atActionType = 0;
             Tab.contentType = 0;
             Tab.width = 512;
             Tab.height = 334;
             Tab.opacity = (byte) 0;
             Tab.hoverType = -1;
             Tab.anIntArray245 = new int[1];
             Tab.anIntArray212 = new int[1];
             Tab.anIntArray245[0] = 1;
             Tab.anIntArray212[0] = configId;
             Tab.valueIndexArray = new int[1][3];
             Tab.valueIndexArray[0][0] = 5;
             Tab.valueIndexArray[0][1] = configFrame;
             Tab.valueIndexArray[0][2] = 0;
             Tab.disabledSprite = imageLoader(i, name);
             Tab.enabledSprite = imageLoader(i2, name);
     }
	
	 public static void addBankHover(int interfaceID, int actionType,
             int hoverid, int spriteId, int spriteId2, String NAME, int Width,
             int Height, int configFrame, int configId, String Tooltip,
             int hoverId2, int hoverSpriteId, int hoverSpriteId2,
             String hoverSpriteName, int hoverId3, String hoverDisabledText,
             String hoverEnabledText, int X, int Y)
     {
             RSInterface hover = addTabInterface(interfaceID);
             hover.id = interfaceID;
             hover.parentID = interfaceID;
             hover.type = 5;
             hover.atActionType = actionType;
             hover.contentType = 0;
             hover.opacity = 0;
             hover.hoverType = hoverid;
             hover.disabledSprite = imageLoader(spriteId, NAME);
             hover.enabledSprite = imageLoader(spriteId2, NAME);
             hover.width = Width;
             hover.tooltip = Tooltip;
             hover.height = Height;
             hover.anIntArray245 = new int[1];
             hover.anIntArray212 = new int[1];
             hover.anIntArray245[0] = 1;
             hover.anIntArray212[0] = configId;
             hover.valueIndexArray = new int[1][3];
             hover.valueIndexArray[0][0] = 5;
             hover.valueIndexArray[0][1] = configFrame;
             hover.valueIndexArray[0][2] = 0;
             hover = addTabInterface(hoverid);
             hover.parentID = hoverid;
             hover.id = hoverid;
             hover.type = 0;
             hover.atActionType = 0;
             hover.width = 550;
             hover.height = 334;
             hover.isMouseoverTriggered = true;
             hover.hoverType = -1;
             addSprites(hoverId2, hoverSpriteId, hoverSpriteId2, hoverSpriteName,
                     configId, configFrame);
             addHoverBox(hoverId3, interfaceID, hoverDisabledText, hoverEnabledText,
                     configId, configFrame);
             setChildren(2, hover);
             setBounds(hoverId2, 15, 60, 0, hover);
             setBounds(hoverId3, X, Y, 1, hover);
     }

     public static void addBankHover1(int interfaceID, int actionType,
             int hoverid, int spriteId, String NAME, int Width, int Height,
             String Tooltip, int hoverId2, int hoverSpriteId,
             String hoverSpriteName, int hoverId3, String hoverDisabledText,
             int X, int Y)
     {
             RSInterface hover = addTabInterface(interfaceID);
             hover.id = interfaceID;
             hover.parentID = interfaceID;
             hover.type = 5;
             hover.atActionType = actionType;
             hover.contentType = 0;
             hover.opacity = 0;
             hover.hoverType = hoverid;
             hover.disabledSprite = imageLoader(spriteId, NAME);
             hover.width = Width;
             hover.tooltip = Tooltip;
             hover.height = Height;
             hover = addTabInterface(hoverid);
             hover.parentID = hoverid;
             hover.id = hoverid;
             hover.type = 0;
             hover.atActionType = 0;
             hover.width = 550;
             hover.height = 334;
             hover.isMouseoverTriggered = true;
             hover.hoverType = -1;
             addSprites(hoverId2, hoverSpriteId, hoverSpriteId, hoverSpriteName, 0,
                     0);
             addHoverBox(hoverId3, interfaceID, hoverDisabledText,
                     hoverDisabledText, 0, 0);
             setChildren(2, hover);
             setBounds(hoverId2, 15, 60, 0, hover);
             setBounds(hoverId3, X, Y, 1, hover);
     }
     
     public static void addHover(int i, int aT, int cT, int hoverid, int sId,
             String NAME, int W, int H, String tip)
     {
             RSInterface rsinterfaceHover = addInterface(i);
             rsinterfaceHover.id = i;
             rsinterfaceHover.parentID = i;
             rsinterfaceHover.type = 5;
             rsinterfaceHover.atActionType = aT;
             rsinterfaceHover.contentType = cT;
             rsinterfaceHover.isMouseoverTriggereds = hoverid;
             rsinterfaceHover.disabledSprite = imageLoader(sId, NAME);
             rsinterfaceHover.enabledSprite = imageLoader(sId, NAME);
             rsinterfaceHover.width = W;
             rsinterfaceHover.height = H;
             rsinterfaceHover.tooltip = tip;
     }

     public static void addHovered(int i, int j, String imageName, int w, int h,
             int IMAGEID)
     {
             RSInterface rsinterfaceHover = addInterface(i);
             rsinterfaceHover.parentID = i;
             rsinterfaceHover.id = i;
             rsinterfaceHover.type = 0;
             rsinterfaceHover.atActionType = 0;
             rsinterfaceHover.width = w;
             rsinterfaceHover.height = h;
             rsinterfaceHover.isMouseoverTriggered = true;
             rsinterfaceHover.isMouseoverTriggereds = -1;
             addSprite(IMAGEID, j, imageName);
             setChildren(1, rsinterfaceHover);
             setBounds(IMAGEID, 0, 0, 0, rsinterfaceHover);
     }
	
 	public static void bank(TextDrawingArea[] wid) {
		RSInterface Interface = addTabInterface(5292);
		setChildren(44, Interface);
		addSprite(5293, 0, "Interfaces/Bank/BANK");
		setBounds(5293, 13, 1, 0, Interface);
		addHover(5384, 3, 0, 5380, 1, "Interfaces/Bank/BANK", 25, 25,
				"Close Window");
		addHovered(5380, 2, "Interfaces/Bank/BANK", 25, 25, 5379);
		setBounds(5384, 472, 8, 3, Interface);
		setBounds(5380, 472, 8, 4, Interface);
		// addHover(5294, 4, 0, 5295, 3, "Interfaces/Bank/BANK", 83, 25,
		// "Insert");
		// addHovered(5295, 4, "Interfaces/Bank/BANK", 114, 25, 5296);

		addBankHover(5294, 4, 5295, 1, 2, "Interfaces/Bank/INSERT", 83, 25,
				304, 1, "Insert", 5296, 1, 2, "Interfaces/Bank/INSERT", 5297,
				"Switch to insert items \nmode",
				"Switch to swap items \nmode.", 12, 20);

		setBounds(5294, 104, 306, 5, Interface);
		setBounds(5295, 104, 306, 6, Interface);
		addBankHover(23100, 4, 23101, 3, 1, "Interfaces/Bank/SWAP", 83, 25,
				304, 1, "Swap", 23102, 1, 3, "Bank/SWAP", 23103,
				"Switch to insert items \nmode",
				"Switch to swap items \nmode.", 12, 20);
		setBounds(23100, 19, 306, 7, Interface);
		setBounds(23101, 19, 306, 8, Interface);
		addBankHover(22104, 4, 22105, 13, 15, "Interfaces/Bank/BANK", 83, 25,
				115, 1, "Item", 22106, 14, 16, "Bank/BANK", 22107,
				"Click here to search your \nbank",
				"Click here to search your \nbank", 12, 20);
		setBounds(22104, 199, 306, 9, Interface);
		setBounds(22105, 199, 306, 10, Interface);
		addBankHover(22118, 4, 22119, 9, 11, "Interfaces/Bank/BANK", 83, 25,
				115, 1, "Note", 22110, 10, 12, "Bank/BANK", 22111,
				"Switch to note withdrawal \nmode",
				"Switch to item withdrawal \nmode", 12, 20);
		setBounds(22118, 284, 306, 11, Interface);
		setBounds(22119, 284, 306, 12, Interface);
		addBankHover(22112, 5, 22113, 1, 2, "Interfaces/Bank/SEARCH", 36, 36,
				0, 1, "Search", 22114, 1, 22015, "Bank/SEARCH", 22116,
				"Empty your backpack into\nyour bank", "", 0, 20);
		setBounds(22112, 377, 290, 13, Interface);
		setBounds(22113, 362, 290, 14, Interface);
		setBounds(22114, 362, 290, 43, Interface);
		addBankHover1(23116, 5, 23117, 19, "Interfaces/Bank/BANK", 36, 36,
				"Deposit inventory", 23118, 20, "Bank/BANK", 23119,
				"Empty the items your are\nwearing into your bank", 0, 20);
		setBounds(23116, 423, 290, 15, Interface);
		setBounds(23117, 423, 290, 16, Interface);
		addBankHover1(23120, 5, 23121, 21, "Interfaces/Bank/BANK", 36, 36,
				"Deposit worn items", 23122, 22, "Bank/BANK", 23123,
				"Empty your BoB's inventory\ninto your bank", 0, 20);
		setBounds(23120, 459, 290, 17, Interface);
		setBounds(23121, 459, 290, 18, Interface);
		setBounds(5383, 195, 11, 1, Interface);
		setBounds(5385, 27, 78, 2, Interface);
		addButton(23124, 0, "Interfaces/BANK/TAB", "View all items");
		setBounds(23124, 57, 36, 19, Interface);
		addButton(23125, 4, "Interfaces/BANK/TAB", "New tab");
		setBounds(23125, 97, 37, 20, Interface);
		addButton(23126, 4, "Interfaces/BANK/TAB", "New tab");
		setBounds(23126, 137, 37, 21, Interface);
		addButton(23127, 4, "Interfaces/BANK/TAB", "New tab");
		setBounds(23127, 177, 37, 22, Interface);
		addButton(23128, 4, "Interfaces/BANK/TAB", "New tab");
		setBounds(23128, 217, 37, 23, Interface);
		addButton(23129, 4, "Interfaces/BANK/TAB", "New tab");
		setBounds(23129, 257, 37, 24, Interface);
		addButton(23130, 4, "Interfaces/BANK/TAB", "New tab");
		setBounds(23130, 297, 37, 25, Interface);
		addButton(23131, 4, "Interfaces/BANK/TAB", "New tab");
		setBounds(23131, 337, 37, 26, Interface);
		addButton(23132, 4, "Interfaces/BANK/TAB", "New tab");
		setBounds(23132, 377, 37, 27, Interface);
		addText(22133, "N/A", wid, 0, 16750623, true, true);
		setBounds(22133, 30, 7, 28, Interface);
		addText(22134, "496", wid, 0, 16750623, true, true);
		setBounds(22134, 30, 19, 29, Interface);
		addBankItem(22135, false);
		setBounds(22135, 102, 40, 30, Interface);
		addBankItem(22136, false);
		setBounds(22136, 142, 40, 31, Interface);
		addBankItem(22137, false);
		setBounds(22137, 182, 40, 32, Interface);
		addBankItem(22138, false);
		setBounds(22138, 222, 40, 33, Interface);
		addBankItem(22139, false);
		setBounds(22139, 262, 40, 34, Interface);
		addBankItem(22140, false);
		setBounds(22140, 302, 40, 35, Interface);
		addBankItem(22141, false);
		setBounds(22141, 342, 40, 36, Interface);
		addBankItem(22142, false);
		setBounds(22142, 382, 40, 37, Interface);

		addText(27100, "0", 16750623, false, true, 52, wid, 1);
		addText(27101, "0", 16750623, false, true, 52, wid, 1);
		addText(27102, "0", 16750623, false, true, 52, wid, 1);
		addText(22143, "__", wid, 0, 16750623, true, true);
		setBounds(22143, 30, 10, 38, Interface);

		addBankHover1(22144, 1, 22147, 1, "Interfaces/Bank/MENU", 27, 27,
				"Show menu", 22148, 1, "Bank/MENU", 22149,
				"Empty your backpack into\nyour bank", 0, 20);
		setBounds(22144, 463, 42, 39, Interface);
		setBounds(22147, 463, 42, 42, Interface);
		// setBounds(22048, 463, 42, 43, Interface);
		// setBounds(22049, 463, 42, 44, Interface);

		addText(22145, "Withdraw as:", wid, 1, 16750623, true, true);
		setBounds(22145, 283, 290, 40, Interface);
		addText(22146, "Rearrange mode:", wid, 1, 16750623, true, true);
		setBounds(22146, 102, 290, 41, Interface);
		Interface = interfaceCache[5385];
		Interface.height = 206;
		Interface.width = 452;
		Interface = interfaceCache[5382];
		Interface.width = 8;
		Interface.invSpritePadX = 17;
		Interface.height = 35;
	}
	
	public boolean hasExamine = true;
	
	public static void addBankItem(int index, boolean hasOption)
	{
		RSInterface rsi = interfaceCache[index] = new RSInterface();
		rsi.actions = new String[5];
		rsi.spritesX = new int[20];
		rsi.invStackSizes = new int[30];
		rsi.inv = new int[30];
		rsi.spritesY = new int[20];
		rsi.children = new int[0];
		rsi.childX = new int[0];
		rsi.childY = new int[0];
		rsi.hasExamine = false;
		rsi.invSpritePadX = 24;
		rsi.invSpritePadY = 24;
		rsi.height = 5;
		rsi.width = 6;
		rsi.parentID = 5292;
		rsi.id = index;
		rsi.type = 2;
	}
	
	  public static void addTextButton(int i, String s, String tooltip, int k, boolean l, boolean m, TextDrawingArea[] TDA, int j, int w) {
	        RSInterface rsinterface = addInterface(i);
	        rsinterface.parentID = i;
	        rsinterface.id = i;
	        rsinterface.type = 4;
	        rsinterface.atActionType = 1;
	        rsinterface.width = w;
	        rsinterface.height = 16;
	        rsinterface.contentType = 0;
	        rsinterface.opacity = (byte)0xFF981F;
	        rsinterface.hoverType = -1;
	        rsinterface.centerText = l;
	        rsinterface.textShadow = m;
	        rsinterface.textDrawingAreas = TDA[j];
	        rsinterface.message = s;
	        rsinterface.aString228 = "";
	        rsinterface.anInt219 = 0xFF981F;
	        rsinterface.textColor = 0xFF981F;
	        rsinterface.tooltip = tooltip;
	    }
	  
	    public static void slayerInterface(TextDrawingArea[] tda) {
	        RSInterface rsInterface = addInterface(41000);
	        addSprite(41001, 1, "Slayer/SPRITE");
	        addHoverButton(41002, "Slayer/SPRITE", 4, 16, 16, "Close window", 0, 41003, 1);
	        addHoveredButton(41003, "Slayer/SPRITE", 5, 16, 16, 41004);
	        addHoverButton(41005, "", 0, 85, 20, "Buy", 0, 41006, 1);
	        addHoverButton(41007, "", 0, 85, 20, "Learn", 0, 41008, 1);
	        addHoverButton(41009, "", 0, 85, 20, "Assignment", 0, 41010, 1);
	        addText(41011, "Slayer points: ", tda, 3, 0xFF981F, true);
	        addTextButton(41012, "Slayer experience                           (50 points)", "Buy Slayer Experience", 0xFF981F, false, true, tda, 1, 400);
	        addTextButton(41013, "Slayer's respite                             (25 points)", "Buy Slayer's Respite", 0xFF981F, false, true, tda, 1, 401);
	        addTextButton(41014, "Slayer darts                                     (35 points)", "Buy Slayer Darts", 0xFF981F, false, true, tda, 1, 402);
	        addTextButton(41015, "Broad arrows                                    (25 points)", "Buy Broad Arrows", 0xFF981F, false, true, tda, 1, 403);
	        setChildren(11, rsInterface);
	        rsInterface.child(0, 41001, 12, 10);
	        rsInterface.child(1, 41002, 473, 20);
	        rsInterface.child(2, 41003, 473, 20);
	        rsInterface.child(3, 41005, 21, 23);
	        rsInterface.child(4, 41007, 107, 23);
	        rsInterface.child(5, 41009, 193, 23);
	        rsInterface.child(6, 41011, 170, 74);
	        rsInterface.child(7, 41012, 124, 128);
	        rsInterface.child(8, 41013, 125, 160);
	        rsInterface.child(9, 41014, 125, 190);
	        rsInterface.child(10, 41015, 124, 220);
	        
	    }
	    
	    public static void slayerInterfaceSub1(TextDrawingArea[] tda) {
	        RSInterface rsInterface = addInterface(41500);
	        addSprite(41501, 2, "Slayer/SPRITE");
	        addHoverButton(41502, "Slayer/SPRITE", 4, 16, 16, "Close window", 0, 41503, 1);
	        addHoveredButton(41503, "Slayer/SPRITE", 5, 16, 16, 41504);
	        addHoverButton(41505, "", 0, 85, 20, "Buy", 0, 41506, 1);
	        addHoverButton(41507, "", 0, 85, 20, "Learn", 0, 41508, 1);
	        addHoverButton(41509, "", 0, 85, 20, "Assignment", 0, 41510, 1);
	        addText(41511, "Slayer points: ", tda, 3, 0xFF981F, true);
	        setChildren(7, rsInterface);
	        rsInterface.child(0, 41501, 12, 10);
	        rsInterface.child(1, 41502, 473, 20);
	        rsInterface.child(2, 41503, 473, 20);
	        rsInterface.child(3, 41505, 21, 23);
	        rsInterface.child(4, 41507, 107, 23);
	        rsInterface.child(5, 41509, 193, 23);
	        rsInterface.child(6, 41511, 170, 74);
	    }
	    
	    public static void slayerInterfaceSub2(TextDrawingArea[] tda) {
	        RSInterface rsInterface = addInterface(42000);
	        addSprite(42001, 3, "Slayer/SPRITE");
	        addHoverButton(42002, "Slayer/SPRITE", 4, 16, 16, "Close window", 0, 42003, 1);
	        addHoveredButton(42003, "Slayer/SPRITE", 5, 16, 16, 42004);
	        addHoverButton(42005, "", 0, 85, 20, "Buy", 0, 42006, 1);
	        addHoverButton(42007, "", 0, 85, 20, "Learn", 0, 42008, 1);
	        addHoverButton(42009, "", 0, 85, 20, "Assignment", 0, 42010, 1);
	        addText(42011, "Slayer points: ", tda, 3, 0xFF981F, true);
	        addTextButton(42012, "Cancel task", "Temporarily cancel your current slayer task", 0xFF981F, false, true, tda, 1, 300);
	        addTextButton(42013, "Remove task permanently", "Permanently remove this monster as a task", 0xFF981F, false, true, tda, 1, 305);
	        addText(42014, "line 1", tda, 1, 0xFF981F, true);
	        addText(42015, "line 2", tda, 1, 0xFF981F, true);
	        addText(42016, "line 3", tda, 1, 0xFF981F, true);
	        addText(42017, "line 4", tda, 1, 0xFF981F, true);
	        addButton(42018, 6, "Slayer/SPRITE", "Delete removed slayer task");
	        addButton(42019, 6, "Slayer/SPRITE", "Delete removed slayer task");
	        addButton(42020, 6, "Slayer/SPRITE", "Delete removed slayer task");
	        addButton(42021, 6, "Slayer/SPRITE", "Delete removed slayer task");
	        setChildren(17, rsInterface);
	        rsInterface.child(0, 42001, 12, 10);
	        rsInterface.child(1, 42002, 473, 20);
	        rsInterface.child(2, 42003, 473, 20);
	        rsInterface.child(3, 42005, 21, 23);
	        rsInterface.child(4, 42007, 107, 23);
	        rsInterface.child(5, 42009, 193, 23);
	        rsInterface.child(6, 42011, 170, 74);
	        rsInterface.child(7, 42012, 71, 127);
	        rsInterface.child(8, 42013, 71, 146);
	        rsInterface.child(9, 42014, 108, 216);
	        rsInterface.child(10, 42015, 108, 234);
	        rsInterface.child(11, 42016, 108, 252);
	        rsInterface.child(12, 42017, 108, 270);
	        rsInterface.child(13, 42018, 303, 215);
	        rsInterface.child(14, 42019, 303, 233);
	        rsInterface.child(15, 42020, 303, 251);
	        rsInterface.child(16, 42021, 303, 269);
	    }
	
	public static void nightmareZone(TextDrawingArea[] tda) {
		 RSInterface nz = addInterface(920);
		 addSprite(921, 0, "Nightmare/SPRITE");
		 addText(922, "0", tda, 0, 0xFF981F, true, true);
		 addText(923, "Points:", tda, 0, 0xFF981F, false, true);
	     nz.totalChildren(3);
	     nz.child(0, 921, 460, 14);
	     nz.child(1, 922, 481, 29);
	     nz.child(2, 923, 465, 19);
	}
	
	 public static void addClickableText(int id, String text, String tooltip, TextDrawingArea tda[], int idx, int color, boolean center, boolean shadow, int width) {
	        RSInterface tab = addTabInterface(id);
	        tab.parentID = id;
	        tab.id = id;
	        tab.type = 4;
	        tab.atActionType = 1;
	        tab.width = width;
	        tab.height = 11;
	        tab.contentType = 0;
	        tab.opacity = 0;
	        tab.hoverType = -1;
	        tab.centerText = center;
	        tab.textShadow = shadow;
	        tab.textDrawingAreas = tda[idx];
	        tab.message = text;
	        tab.aString228 = "";
	        tab.textColor = color;
	        tab.anInt219 = 0;
	        tab.anInt216 = 0xffffff;
	        tab.anInt239 = 0;
	        tab.tooltip = tooltip;
	    }

	public static void optionTab1(TextDrawingArea[] tda) {
        RSInterface tab = addTabInterface(904);
        RSInterface energy = interfaceCache[149];
        energy.textColor = 0xff9933;
        addSprite(949, 35, "Options/SPRITE");
        addSprite(950, 36, "Options/SPRITE");
        addSprite(952, 37, "Options/SPRITE");
        addSprite(954, 38, "Options/SPRITE");
        addSprite(956, 29, "Options/SPRITE");
        addSprite(958, 39, "Options/SPRITE");
        addSprite(960, 40, "Options/SPRITE");
        addSprite(961, 41, "Options/SPRITE");
        addSprite(962, 9, "Options/SPRITE");
        addSprite(963, 17, "Options/SPRITE");
        addSprite(964, 12, "Options/SPRITE");
        addSprite(965, 11, "Options/SPRITE");
        addSprite(966, 10, "Options/SPRITE");
        addSprite(968, 42, "Options/SPRITE");
        addSprite(971, 43, "Options/SPRITE");
        addSprite(972, 44, "Options/SPRITE");
        addConfigButton(152, 904, 30, 31, "Options/SPRITE", 40, 40, "Toggle Run", 1, 5, 173);
        addConfigButton(12464, 904, 31, 30, "Options/SPRITE", 40, 40, "Toggle Accept Aid", 0, 5, 427);
        addConfigButton(951, 904, 31, 30, "Options/SPRITE", 40, 40, "Open House Options", 0, 5, 427);
        addConfigButton(953, 904, 30, 31, "Options/SPRITE", 40, 40, "Display", 0, 5, 427);
        addConfigButton(955, 904, 31, 30, "Options/SPRITE", 40, 40, "Audio", 0, 5, 427);
        addConfigButton(957, 904, 31, 30, "Options/SPRITE", 40, 40, "Chat", 0, 5, 427);
        addConfigButton(959, 904, 31, 30, "Options/SPRITE", 40, 40, "Controls", 0, 5, 427);
        addConfigButton(967, 904, 30, 31, "Options/SPRITE", 40, 40, "Toggle Data Orbs", 0, 5, 427);
        addConfigButton(969, 904, 30, 31, "Options/SPRITE", 40, 40, "Toggle roof-removal", 0, 5, 427);
        addConfigButton(970, 904, 31, 30, "Options/SPRITE", 40, 40, "Toggle 'Remaining XP'", 0, 5, 427);
        tab.totalChildren(27);
        tab.child(0, 12464, 19, 220);
        tab.child(1, 949, 23, 225);
        tab.child(2, 152, 75, 220);
        tab.child(3, 149, 80, 243);
        tab.child(4, 950, 86, 224);
        tab.child(5, 951, 131, 220);
        tab.child(6, 952, 135, 224);
        tab.child(7, 953, 6, 1);
        tab.child(8, 954, 10, 5);
        tab.child(9, 955, 52, 1);
        tab.child(10, 956, 57, 5);
        tab.child(11, 957, 98, 1);
        tab.child(12, 958, 102, 5);
        tab.child(13, 959, 144, 1);
        tab.child(14, 960, 149, 5);
        tab.child(15, 961, 0, 42);
        tab.child(16, 962, 9, 70);
        tab.child(17, 963, 149, 80);
        tab.child(18, 964, 117, 80);
        tab.child(19, 965, 85, 80);
        tab.child(20, 966, 53, 80);
        tab.child(21, 967, 19, 121);
        tab.child(22, 968, 23, 125);
        tab.child(23, 969, 75, 121);
        tab.child(24, 970, 131, 121);
        tab.child(25, 971, 79, 125);
        tab.child(26, 972, 135, 125);
    }

	public static void edgevilleHomeTeleport(TextDrawingArea[] TDA) {
		RSInterface rsi = interfaceCache[21741];
		rsi.atActionType = 1;
		rsi.tooltip = "Cast @gre@Edgeville Home Teleport";
	}

	public static void addButton(int id, int sid, String spriteName, String tooltip) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 1;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;
		tab.disabledSprite = imageLoader(sid, spriteName);
		tab.enabledSprite = imageLoader(sid, spriteName);
		tab.width = tab.disabledSprite.myWidth;
		tab.height = tab.enabledSprite.myHeight;
		tab.tooltip = tooltip;
	}

	public String popupString;

	public static void addTooltipBox(int id, String text) {
		RSInterface rsi = addInterface(id);
		rsi.id = id;
		rsi.parentID = id;
		rsi.type = 8;
		rsi.popupString = text;
	}

	public static void addTooltip(int id, String text) {
		RSInterface rsi = addInterface(id);
		rsi.id = id;
		rsi.type = 0;
		rsi.isMouseoverTriggered = true;
		rsi.hoverType = -1;
		addTooltipBox(id + 1, text);
		rsi.totalChildren(1);
		rsi.child(0, id + 1, 0, 0);
	}

	private static Sprite CustomSpriteLoader(int id, String s) {
		long l = (TextClass.method585(s) << 8) + (long) id;
		Sprite sprite = (Sprite) aMRUNodes_238.insertFromCache(l);
		if (sprite != null) {
			return sprite;
		}
		try {
			sprite = new Sprite("/Attack/" + id + s);
			aMRUNodes_238.removeFromCache(sprite, l);
		} catch (Exception exception) {
			return null;
		}
		return sprite;
	}

	public static RSInterface addInterface(int id) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		rsi.id = id;
		rsi.parentID = id;
		rsi.width = 512;
		rsi.height = 334;
		return rsi;
	}

	public static void addText(int id, String text, TextDrawingArea tda[], int idx, int color, boolean centered) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		if (centered)
			rsi.centerText = true;
		rsi.textShadow = true;
		rsi.textDrawingAreas = tda[idx];
		rsi.message = text;
		rsi.textColor = color;
		rsi.id = id;
		rsi.type = 4;
	}

	public static void textColor(int id, int color) {
		RSInterface rsi = interfaceCache[id];
		rsi.textColor = color;
	}

	public static void textSize(int id, TextDrawingArea tda[], int idx) {
		RSInterface rsi = interfaceCache[id];
		rsi.textDrawingAreas = tda[idx];
	}

	public static void addCacheSprite(int id, int sprite1, int sprite2, String sprites) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		rsi.disabledSprite = method207(sprite1, aClass44, sprites);
		rsi.enabledSprite = method207(sprite2, aClass44, sprites);
		rsi.parentID = id;
		rsi.id = id;
		rsi.type = 5;
	}

	public static void sprite1(int id, int sprite) {
		RSInterface class9 = interfaceCache[id];
		class9.disabledSprite = CustomSpriteLoader(sprite, "");
	}

	public static void addActionButton(int id, int sprite, int sprite2, int width, int height, String s) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		rsi.disabledSprite = CustomSpriteLoader(sprite, "");
		if (sprite2 == sprite)
			rsi.enabledSprite = CustomSpriteLoader(sprite, "a");
		else
			rsi.enabledSprite = CustomSpriteLoader(sprite2, "");
		rsi.tooltip = s;
		rsi.contentType = 0;
		rsi.atActionType = 1;
		rsi.width = width;
		rsi.hoverType = 52;
		rsi.parentID = id;
		rsi.id = id;
		rsi.type = 5;
		rsi.height = height;
	}

	public static void addToggleButton(int id, int sprite, int setconfig, int width, int height, String s) {
		RSInterface rsi = addInterface(id);
		rsi.disabledSprite = CustomSpriteLoader(sprite, "");
		rsi.enabledSprite = CustomSpriteLoader(sprite, "a");
		rsi.anIntArray212 = new int[1];
		rsi.anIntArray212[0] = 1;
		rsi.anIntArray245 = new int[1];
		rsi.anIntArray245[0] = 1;
		rsi.valueIndexArray = new int[1][3];
		rsi.valueIndexArray[0][0] = 5;
		rsi.valueIndexArray[0][1] = setconfig;
		rsi.valueIndexArray[0][2] = 0;
		rsi.atActionType = 4;
		rsi.width = width;
		rsi.hoverType = -1;
		rsi.parentID = id;
		rsi.id = id;
		rsi.type = 5;
		rsi.height = height;
		rsi.tooltip = s;
	}

	public void totalChildren(int id, int x, int y) {
		children = new int[id];
		childX = new int[x];
		childY = new int[y];
	}

	public static void removeSomething(int id) {
		@SuppressWarnings("unused")
		RSInterface rsi = interfaceCache[id] = new RSInterface();
	}

	public void specialBar(int id) // 7599
	{
		/*
		 * addActionButton(ID, SpriteOFF, SpriteON, Width, Height,
		 * "SpriteText");
		 */
		addActionButton(id - 12, 7587, -1, 150, 26, "Use @gre@Special Attack");
		/* removeSomething(ID); */
		for (int i = id - 11; i < id; i++)
			removeSomething(i);

		RSInterface rsi = interfaceCache[id - 12];
		rsi.width = 150;
		rsi.height = 26;

		rsi = interfaceCache[id];
		rsi.width = 150;
		rsi.height = 26;

		rsi.child(0, id - 12, 0, 0);

		rsi.child(12, id + 1, 3, 7);

		rsi.child(23, id + 12, 16, 8);

		for (int i = 13; i < 23; i++) {
			rsi.childY[i] -= 1;
		}

		rsi = interfaceCache[id + 1];
		rsi.type = 5;
		rsi.disabledSprite = CustomSpriteLoader(7600, "");

		for (int i = id + 2; i < id + 12; i++) {
			rsi = interfaceCache[i];
			rsi.type = 5;
		}

		sprite1(id + 2, 7601);
		sprite1(id + 3, 7602);
		sprite1(id + 4, 7603);
		sprite1(id + 5, 7604);
		sprite1(id + 6, 7605);
		sprite1(id + 7, 7606);
		sprite1(id + 8, 7607);
		sprite1(id + 9, 7608);
		sprite1(id + 10, 7609);
		sprite1(id + 11, 7610);
	}

	public static void Sidebar0(TextDrawingArea[] tda) {
		/*
		 * Sidebar0a(id, id2, id3, "text1", "text2", "text3", "text4", str1x,
		 * str1y, str2x, str2y, str3x, str3y, str4x, str4y, img1x, img1y, img2x,
		 * img2y, img3x, img3y, img4x, img4y, tda);
		 */
		Sidebar0a(1698, 1701, 7499, "Chop", "Hack", "Smash", "Block", 42, 75, 127, 75, 39, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
		Sidebar0a(2276, 2279, 7574, "Stab", "Lunge", "Slash", "Block", 43, 75, 124, 75, 41, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
		Sidebar0a(2423, 2426, 7599, "Chop", "Slash", "Lunge", "Block", 42, 75, 125, 75, 40, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
		Sidebar0a(3796, 3799, 7624, "Pound", "Pummel", "Spike", "Block", 39, 75, 121, 75, 41, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
		Sidebar0a(4679, 4682, 7674, "Lunge", "Swipe", "Pound", "Block", 40, 75, 124, 75, 39, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
		Sidebar0a(4705, 4708, 7699, "Chop", "Slash", "Smash", "Block", 42, 75, 125, 75, 39, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
		Sidebar0a(5570, 5573, 7724, "Spike", "Impale", "Smash", "Block", 41, 75, 123, 75, 39, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
		Sidebar0a(7762, 7765, 7800, "Chop", "Slash", "Lunge", "Block", 42, 75, 125, 75, 40, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
		/*
		 * Sidebar0b(id, id2, "text1", "text2", "text3", "text4", str1x, str1y,
		 * str2x, str2y, str3x, str3y, str4x, str4y, img1x, img1y, img2x, img2y,
		 * img3x, img3y, img4x, img4y, tda);
		 */
		Sidebar0b(776, 779, "Reap", "Chop", "Jab", "Block", 42, 75, 126, 75, 46, 128, 125, 128, 122, 103, 122, 50, 40, 103, 40, 50, tda);
		/*
		 * Sidebar0c(id, id2, id3, "text1", "text2", "text3", str1x, str1y,
		 * str2x, str2y, str3x, str3y, img1x, img1y, img2x, img2y, img3x, img3y,
		 * tda);
		 */
		Sidebar0c(425, 428, 7474, "Pound", "Pummel", "Block", 39, 75, 121, 75, 42, 128, 40, 103, 40, 50, 122, 50, tda);
		Sidebar0c(1749, 1752, 7524, "Accurate", "Rapid", "Longrange", 33, 75, 125, 75, 29, 128, 40, 103, 40, 50, 122, 50, tda);
		Sidebar0c(1764, 1767, 7549, "Accurate", "Rapid", "Longrange", 33, 75, 125, 75, 29, 128, 40, 103, 40, 50, 122, 50, tda);
		Sidebar0c(4446, 4449, 7649, "Accurate", "Rapid", "Longrange", 33, 75, 125, 75, 29, 128, 40, 103, 40, 50, 122, 50, tda);
		Sidebar0c(5855, 5857, 7749, "Punch", "Kick", "Block", 40, 75, 129, 75, 42, 128, 40, 50, 122, 50, 40, 103, tda);
		Sidebar0c(6103, 6132, 6117, "Bash", "Pound", "Block", 43, 75, 124, 75, 42, 128, 40, 103, 40, 50, 122, 50, tda);
		Sidebar0c(8460, 8463, 8493, "Jab", "Swipe", "Fend", 46, 75, 124, 75, 43, 128, 40, 103, 40, 50, 122, 50, tda);
		Sidebar0c(12290, 12293, 12323, "Flick", "Lash", "Deflect", 44, 75, 127, 75, 36, 128, 40, 50, 40, 103, 122, 50, tda);
		/*
		 * Sidebar0d(id, id2, "text1", "text2", "text3", str1x, str1y, str2x,
		 * str2y, str3x, str3y, img1x, img1y, img2x, img2y, img3x, img3y, tda);
		 */
		Sidebar0d(328, 331, "Bash", "Pound", "Focus", 42, 66, 39, 101, 41, 136, 40, 120, 40, 50, 40, 85, tda);

		RSInterface rsi = addInterface(19300);
		/* textSize(ID, wid, Size); */
		textSize(3983, tda, 0);
		/* addToggleButton(id, sprite, config, width, height, wid); */
		addToggleButton(150, 150, 172, 150, 44, "Auto Retaliate");

		rsi.totalChildren(2, 2, 2);
		rsi.child(0, 3983, 52, 25); // combat level
		rsi.child(1, 150, 21, 153); // auto retaliate

		rsi = interfaceCache[3983];
		rsi.centerText = true;
		rsi.textColor = 0xff981f;
	}

	public static void Sidebar0a(int id, int id2, int id3, String text1, String text2, String text3, String text4, int str1x, int str1y, int str2x, int str2y, int str3x, int str3y, int str4x, int str4y, int img1x, int img1y, int img2x, int img2y, int img3x, int img3y, int img4x, int img4y, TextDrawingArea[] tda) // 4button
																																																																															// spec
	{
		RSInterface rsi = addInterface(id); // 2423
		/* addText(ID, "Text", tda, Size, Colour, Centered); */
		addText(id2, "-2", tda, 3, 0xff981f, true); // 2426
		addText(id2 + 11, text1, tda, 0, 0xff981f, false);
		addText(id2 + 12, text2, tda, 0, 0xff981f, false);
		addText(id2 + 13, text3, tda, 0, 0xff981f, false);
		addText(id2 + 14, text4, tda, 0, 0xff981f, false);
		/* specialBar(ID); */
		rsi.specialBar(id3); // 7599

		rsi.width = 190;
		rsi.height = 261;

		int last = 15;
		int frame = 0;
		rsi.totalChildren(last, last, last);

		rsi.child(frame, id2 + 3, 21, 46);
		frame++; // 2429
		rsi.child(frame, id2 + 4, 104, 99);
		frame++; // 2430
		rsi.child(frame, id2 + 5, 21, 99);
		frame++; // 2431
		rsi.child(frame, id2 + 6, 105, 46);
		frame++; // 2432

		rsi.child(frame, id2 + 7, img1x, img1y);
		frame++; // bottomright 2433
		rsi.child(frame, id2 + 8, img2x, img2y);
		frame++; // topleft 2434
		rsi.child(frame, id2 + 9, img3x, img3y);
		frame++; // bottomleft 2435
		rsi.child(frame, id2 + 10, img4x, img4y);
		frame++; // topright 2436

		rsi.child(frame, id2 + 11, str1x, str1y);
		frame++; // chop 2437
		rsi.child(frame, id2 + 12, str2x, str2y);
		frame++; // slash 2438
		rsi.child(frame, id2 + 13, str3x, str3y);
		frame++; // lunge 2439
		rsi.child(frame, id2 + 14, str4x, str4y);
		frame++; // block 2440

		rsi.child(frame, 19300, 0, 0);
		frame++; // stuffs
		rsi.child(frame, id2, 94, 4);
		frame++; // weapon 2426
		rsi.child(frame, id3, 21, 205);
		frame++; // special attack 7599

		for (int i = id2 + 3; i < id2 + 7; i++) { // 2429 - 2433
			rsi = interfaceCache[i];
			rsi.disabledSprite = CustomSpriteLoader(19301, "");
			rsi.enabledSprite = CustomSpriteLoader(19301, "a");
			rsi.width = 68;
			rsi.height = 44;
		}
	}

	public static void Sidebar0b(int id, int id2, String text1, String text2, String text3, String text4, int str1x, int str1y, int str2x, int str2y, int str3x, int str3y, int str4x, int str4y, int img1x, int img1y, int img2x, int img2y, int img3x, int img3y, int img4x, int img4y, TextDrawingArea[] tda) // 4button
																																																																													// nospec
	{
		RSInterface rsi = addInterface(id); // 2423
		/* addText(ID, "Text", tda, Size, Colour, Centered); */
		addText(id2, "-2", tda, 3, 0xff981f, true); // 2426
		addText(id2 + 11, text1, tda, 0, 0xff981f, false);
		addText(id2 + 12, text2, tda, 0, 0xff981f, false);
		addText(id2 + 13, text3, tda, 0, 0xff981f, false);
		addText(id2 + 14, text4, tda, 0, 0xff981f, false);

		rsi.width = 190;
		rsi.height = 261;

		int last = 14;
		int frame = 0;
		rsi.totalChildren(last, last, last);

		rsi.child(frame, id2 + 3, 21, 46);
		frame++; // 2429
		rsi.child(frame, id2 + 4, 104, 99);
		frame++; // 2430
		rsi.child(frame, id2 + 5, 21, 99);
		frame++; // 2431
		rsi.child(frame, id2 + 6, 105, 46);
		frame++; // 2432

		rsi.child(frame, id2 + 7, img1x, img1y);
		frame++; // bottomright 2433
		rsi.child(frame, id2 + 8, img2x, img2y);
		frame++; // topleft 2434
		rsi.child(frame, id2 + 9, img3x, img3y);
		frame++; // bottomleft 2435
		rsi.child(frame, id2 + 10, img4x, img4y);
		frame++; // topright 2436

		rsi.child(frame, id2 + 11, str1x, str1y);
		frame++; // chop 2437
		rsi.child(frame, id2 + 12, str2x, str2y);
		frame++; // slash 2438
		rsi.child(frame, id2 + 13, str3x, str3y);
		frame++; // lunge 2439
		rsi.child(frame, id2 + 14, str4x, str4y);
		frame++; // block 2440

		rsi.child(frame, 19300, 0, 0);
		frame++; // stuffs
		rsi.child(frame, id2, 94, 4);
		frame++; // weapon 2426

		for (int i = id2 + 3; i < id2 + 7; i++) { // 2429 - 2433
			rsi = interfaceCache[i];
			rsi.disabledSprite = CustomSpriteLoader(19301, "");
			rsi.enabledSprite = CustomSpriteLoader(19301, "a");
			rsi.width = 68;
			rsi.height = 44;
		}
	}

	public static void Sidebar0c(int id, int id2, int id3, String text1, String text2, String text3, int str1x, int str1y, int str2x, int str2y, int str3x, int str3y, int img1x, int img1y, int img2x, int img2y, int img3x, int img3y, TextDrawingArea[] tda) // 3button
																																																																// spec
	{
		RSInterface rsi = addInterface(id); // 2423
		/* addText(ID, "Text", tda, Size, Colour, Centered); */
		addText(id2, "-2", tda, 3, 0xff981f, true); // 2426
		addText(id2 + 9, text1, tda, 0, 0xff981f, false);
		addText(id2 + 10, text2, tda, 0, 0xff981f, false);
		addText(id2 + 11, text3, tda, 0, 0xff981f, false);
		/* specialBar(ID); */
		rsi.specialBar(id3); // 7599

		rsi.width = 190;
		rsi.height = 261;

		int last = 12;
		int frame = 0;
		rsi.totalChildren(last, last, last);

		rsi.child(frame, id2 + 3, 21, 99);
		frame++;
		rsi.child(frame, id2 + 4, 105, 46);
		frame++;
		rsi.child(frame, id2 + 5, 21, 46);
		frame++;

		rsi.child(frame, id2 + 6, img1x, img1y);
		frame++; // topleft
		rsi.child(frame, id2 + 7, img2x, img2y);
		frame++; // bottomleft
		rsi.child(frame, id2 + 8, img3x, img3y);
		frame++; // topright

		rsi.child(frame, id2 + 9, str1x, str1y);
		frame++; // chop
		rsi.child(frame, id2 + 10, str2x, str2y);
		frame++; // slash
		rsi.child(frame, id2 + 11, str3x, str3y);
		frame++; // lunge

		rsi.child(frame, 19300, 0, 0);
		frame++; // stuffs
		rsi.child(frame, id2, 94, 4);
		frame++; // weapon
		rsi.child(frame, id3, 21, 205);
		frame++; // special attack 7599

		for (int i = id2 + 3; i < id2 + 6; i++) {
			rsi = interfaceCache[i];
			rsi.disabledSprite = CustomSpriteLoader(19301, "");
			rsi.enabledSprite = CustomSpriteLoader(19301, "a");
			rsi.width = 68;
			rsi.height = 44;
		}
	}

	public static void Sidebar0d(int id, int id2, String text1, String text2, String text3, int str1x, int str1y, int str2x, int str2y, int str3x, int str3y, int img1x, int img1y, int img2x, int img2y, int img3x, int img3y, TextDrawingArea[] tda) // 3button
																																																														// nospec
																																																														// (magic
																																																														// intf)
	{
		RSInterface rsi = addInterface(id); // 2423
		/* addText(ID, "Text", tda, Size, Colour, Centered); */
		addText(id2, "-2", tda, 3, 0xff981f, true); // 2426
		addText(id2 + 9, text1, tda, 0, 0xff981f, false);
		addText(id2 + 10, text2, tda, 0, 0xff981f, false);
		addText(id2 + 11, text3, tda, 0, 0xff981f, false);

		// addText(353, "Spell", tda, 0, 0xff981f, false);
		removeSomething(353);
		addText(354, "Spell", tda, 0, 0xff981f, false);

		addCacheSprite(337, 19, 0, "combaticons");
		addCacheSprite(338, 13, 0, "combaticons2");
		addCacheSprite(339, 14, 0, "combaticons2");

		/* addToggleButton(id, sprite, config, width, height, tooltip); */
		// addToggleButton(349, 349, 108, 68, 44, "Select");
		removeSomething(349);
		addToggleButton(350, 350, 108, 68, 44, "Select");

		rsi.width = 190;
		rsi.height = 261;

		int last = 15;
		int frame = 0;
		rsi.totalChildren(last, last, last);

		rsi.child(frame, id2 + 3, 20, 115);
		frame++;
		rsi.child(frame, id2 + 4, 20, 80);
		frame++;
		rsi.child(frame, id2 + 5, 20, 45);
		frame++;

		rsi.child(frame, id2 + 6, img1x, img1y);
		frame++; // topleft
		rsi.child(frame, id2 + 7, img2x, img2y);
		frame++; // bottomleft
		rsi.child(frame, id2 + 8, img3x, img3y);
		frame++; // topright

		rsi.child(frame, id2 + 9, str1x, str1y);
		frame++; // bash
		rsi.child(frame, id2 + 10, str2x, str2y);
		frame++; // pound
		rsi.child(frame, id2 + 11, str3x, str3y);
		frame++; // focus

		rsi.child(frame, 349, 105, 46);
		frame++; // spell1
		rsi.child(frame, 350, 104, 106);
		frame++; // spell2

		rsi.child(frame, 353, 125, 74);
		frame++; // spell
		rsi.child(frame, 354, 125, 134);
		frame++; // spell

		rsi.child(frame, 19300, 0, 0);
		frame++; // stuffs
		rsi.child(frame, id2, 94, 4);
		frame++; // weapon
	}

	public static void emoteTab() {
		RSInterface tab = addTabInterface(147);
		RSInterface scroll = addTabInterface(148);
		tab.totalChildren(1);
		tab.child(0, 148, 0, 1);
		addButton(168, 0, "/Emotes/EMOTE", "Yes", 41, 47);
		addButton(169, 1, "/Emotes/EMOTE", "No", 41, 47);
		addButton(164, 2, "/Emotes/EMOTE", "Bow", 41, 47);
		addButton(165, 3, "/Emotes/EMOTE", "Angry", 41, 47);
		addButton(162, 4, "/Emotes/EMOTE", "Think", 41, 47);
		addButton(163, 5, "/Emotes/EMOTE", "Wave", 41, 47);
		addButton(13370, 6, "/Emotes/EMOTE", "Shrug", 41, 47);
		addButton(171, 7, "/Emotes/EMOTE", "Cheer", 41, 47);
		addButton(167, 8, "/Emotes/EMOTE", "Beckon", 41, 47);
		addButton(170, 9, "/Emotes/EMOTE", "Laugh", 41, 47);
		addButton(13366, 10, "/Emotes/EMOTE", "Jump for Joy", 41, 47);
		addButton(13368, 11, "/Emotes/EMOTE", "Yawn", 41, 47);
		addButton(166, 12, "/Emotes/EMOTE", "Dance", 41, 47);
		addButton(13363, 13, "/Emotes/EMOTE", "Jig", 41, 47);
		addButton(13364, 14, "/Emotes/EMOTE", "Spin", 41, 47);
		addButton(13365, 15, "/Emotes/EMOTE", "Headbang", 41, 47);
		addButton(161, 16, "/Emotes/EMOTE", "Cry", 41, 47);
		addButton(11100, 17, "/Emotes/EMOTE", "Blow kiss", 41, 47);
		addButton(13362, 18, "/Emotes/EMOTE", "Panic", 41, 47);
		addButton(13367, 19, "/Emotes/EMOTE", "Raspberry", 41, 47);
		addButton(172, 20, "/Emotes/EMOTE", "Clap", 41, 47);
		addButton(13369, 21, "/Emotes/EMOTE", "Salute", 41, 47);
		addButton(13383, 22, "/Emotes/EMOTE", "Goblin Bow", 41, 47);
		addButton(13384, 23, "/Emotes/EMOTE", "Goblin Salute", 41, 47);
		addButton(667, 24, "/Emotes/EMOTE", "Glass Box", 41, 47);
		addButton(6503, 25, "/Emotes/EMOTE", "Climb Rope", 41, 47);
		addButton(6506, 26, "/Emotes/EMOTE", "Lean On Air", 41, 47);
		addButton(666, 27, "/Emotes/EMOTE", "Glass Wall", 41, 47);
		addButton(18464, 28, "/Emotes/EMOTE", "Zombie Walk", 41, 47);
		addButton(18465, 29, "/Emotes/EMOTE", "Zombie Dance", 41, 47);
		addButton(15166, 30, "/Emotes/EMOTE", "Scared", 41, 47);
		addButton(18686, 31, "/Emotes/EMOTE", "Rabbit Hop", 41, 47);
		addConfigButton(154, 147, 32, 33, "EMOTE", 41, 47, "Skillcape Emote", 0, 1, 700);
		scroll.totalChildren(33);
		scroll.child(0, 168, 10, 7);
		scroll.child(1, 169, 54, 7);
		scroll.child(2, 164, 98, 14);
		scroll.child(3, 165, 137, 7);
		scroll.child(4, 162, 9, 56);
		scroll.child(5, 163, 48, 56);
		scroll.child(6, 13370, 95, 56);
		scroll.child(7, 171, 137, 56);
		scroll.child(8, 167, 7, 105);
		scroll.child(9, 170, 51, 105);
		scroll.child(10, 13366, 95, 104);
		scroll.child(11, 13368, 139, 105);
		scroll.child(12, 166, 6, 154);
		scroll.child(13, 13363, 50, 154);
		scroll.child(14, 13364, 90, 154);
		scroll.child(15, 13365, 135, 154);
		scroll.child(16, 161, 8, 204);
		scroll.child(17, 11100, 51, 203);
		scroll.child(18, 13362, 99, 204);
		scroll.child(19, 13367, 137, 203);
		scroll.child(20, 172, 10, 253);
		scroll.child(21, 13369, 53, 253);
		scroll.child(22, 13383, 88, 258);
		scroll.child(23, 13384, 138, 252);
		scroll.child(24, 667, 2, 303);
		scroll.child(25, 6503, 49, 302);
		scroll.child(26, 6506, 93, 302);
		scroll.child(27, 666, 137, 302);
		scroll.child(28, 18464, 9, 352);
		scroll.child(29, 18465, 50, 352);
		scroll.child(30, 15166, 94, 356);
		scroll.child(31, 18686, 141, 353);
		scroll.child(32, 154, 5, 401);
		scroll.width = 173;
		scroll.height = 258;
		scroll.scrollMax = 403;
	}

	public static void quickCurses(TextDrawingArea[] TDA) {
		RSInterface tab = addTabInterface(17234);
		addTransparentSprite(17229, 0, "Quicks/Quickprayers", 50);
		addSprite(17201, 3, "Quicks/Quickprayers");
		addText(17230, "Select your quick prayers:", TDA, 0, 0xFF981F, false, true);
		for (int i = 17202, j = 630; i <= 17228 || j <= 656; i++, j++) {
			addConfigButton(i, 17200, 2, 1, "Quicks/Quickprayers", 14, 15, "Select", 0, 1, j);
		}
		addHoverButton(17231, "Quicks/Quickprayers", 4, 190, 24, "Confirm Selection", -1, 17232, 1);
		addHoveredButton(17232, "Quicks/Quickprayers", 5, 190, 24, 17233);
		int frame = 0;
		setChildren(46, tab);
		setBounds(21358, 11, 8 + 20, frame++, tab);
		setBounds(21360, 50, 11 + 20, frame++, tab);
		setBounds(21362, 87, 11 + 20, frame++, tab);
		setBounds(21364, 122, 10 + 20, frame++, tab);
		setBounds(21366, 159, 11 + 20, frame++, tab);
		setBounds(21368, 12, 45 + 20, frame++, tab);
		setBounds(21370, 46, 45 + 20, frame++, tab);
		setBounds(21372, 83, 46 + 20, frame++, tab);
		setBounds(21374, 119, 45 + 20, frame++, tab);
		setBounds(21376, 157, 45 + 20, frame++, tab);
		setBounds(21378, 11, 83 + 20, frame++, tab);
		setBounds(21380, 49, 84 + 20, frame++, tab);
		setBounds(21382, 84, 83 + 20, frame++, tab);
		setBounds(21384, 123, 84 + 20, frame++, tab);
		setBounds(21386, 159, 83 + 20, frame++, tab);
		setBounds(21388, 12, 119 + 20, frame++, tab);
		setBounds(21390, 49, 119 + 20, frame++, tab);
		setBounds(21392, 88, 119 + 20, frame++, tab);
		setBounds(21394, 122, 121 + 20, frame++, tab);
		setBounds(21396, 155, 122 + 20, frame++, tab);
		setBounds(17229, 0, 25, frame++, tab);// Faded backing
		setBounds(17201, 0, 22, frame++, tab);// Split
		setBounds(17201, 0, 237, frame++, tab);// Split
		setBounds(17202, 13 - 3, 8 + 17, frame++, tab);
		setBounds(17203, 52 - 3, 8 + 17, frame++, tab);
		setBounds(17204, 90 - 3, 8 + 17, frame++, tab);
		setBounds(17205, 126 - 3, 8 + 17, frame++, tab);
		setBounds(17206, 162 - 3, 8 + 17, frame++, tab);
		setBounds(17207, 13 - 3, 45 + 17, frame++, tab);
		setBounds(17208, 52 - 3, 45 + 17, frame++, tab);
		setBounds(17209, 90 - 3, 45 + 17, frame++, tab);
		setBounds(17210, 126 - 3, 45 + 17, frame++, tab);
		setBounds(17211, 162 - 3, 45 + 17, frame++, tab);
		setBounds(17212, 13 - 3, 80 + 17, frame++, tab);
		setBounds(17213, 52 - 3, 80 + 17, frame++, tab);
		setBounds(17214, 90 - 3, 80 + 17, frame++, tab);
		setBounds(17215, 126 - 3, 80 + 17, frame++, tab);
		setBounds(17216, 162 - 3, 80 + 17, frame++, tab);
		setBounds(17217, 13 - 3, 119 + 17, frame++, tab);
		setBounds(17218, 52 - 3, 119 + 17, frame++, tab);
		setBounds(17219, 90 - 3, 119 + 17, frame++, tab);
		setBounds(17220, 126 - 3, 119 + 17, frame++, tab);
		setBounds(17221, 162 - 3, 119 + 17, frame++, tab);
		setBounds(17230, 5, 5, frame++, tab);// text
		setBounds(17231, 0, 237, frame++, tab);// confirm
		setBounds(17232, 0, 237, frame++, tab);// Confirm hover
	}


	public int transparency;

	private static void addTransparentSprite(int id, int spriteId, String spriteName, int transparency) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.transparency = (byte) transparency;
		tab.hoverType = 52;
		tab.disabledSprite = imageLoader(spriteId, spriteName);
		tab.enabledSprite = imageLoader(spriteId, spriteName);
		tab.width = 512;
		tab.height = 334;
		tab.drawsTransparent = true;
	}

	public static void Pestpanel(TextDrawingArea[] tda) {
		RSInterface RSinterface = addInterface(21119);
		addText(21120, "What", 0x999999, false, true, 52, tda, 1);
		addText(21121, "What", 0x33cc00, false, true, 52, tda, 1);
		addText(21122, "(Need 5 to 25 players)", 0xFFcc33, false, true, 52, tda, 1);
		addText(21123, "Points", 0x33ccff, false, true, 52, tda, 1);
		int last = 4;
		RSinterface.children = new int[last];
		RSinterface.childX = new int[last];
		RSinterface.childY = new int[last];
		setBounds(21120, 15, 12, 0, RSinterface);
		setBounds(21121, 15, 30, 1, RSinterface);
		setBounds(21122, 15, 48, 2, RSinterface);
		setBounds(21123, 15, 66, 3, RSinterface);
	}

	public static void Pestpanel2(TextDrawingArea[] tda) {
		RSInterface RSinterface = addInterface(21100);
		addSprite(21101, 0, "Pest Control/PEST1");
		addSprite(21102, 1, "Pest Control/PEST1");
		addSprite(21103, 2, "Pest Control/PEST1");
		addSprite(21104, 3, "Pest Control/PEST1");
		addSprite(21105, 4, "Pest Control/PEST1");
		addSprite(21106, 5, "Pest Control/PEST1");
		addText(21107, "", 0xCC00CC, false, true, 52, tda, 1);
		addText(21108, "", 0x0000FF, false, true, 52, tda, 1);
		addText(21109, "", 0xFFFF44, false, true, 52, tda, 1);
		addText(21110, "", 0xCC0000, false, true, 52, tda, 1);
		addText(21111, "250", 0x99FF33, false, true, 52, tda, 1);// w purp
		addText(21112, "250", 0x99FF33, false, true, 52, tda, 1);// e blue
		addText(21113, "250", 0x99FF33, false, true, 52, tda, 1);// se yel
		addText(21114, "250", 0x99FF33, false, true, 52, tda, 1);// sw red
		addText(21115, "200", 0x99FF33, false, true, 52, tda, 1);// attacks
		addText(21116, "0", 0x99FF33, false, true, 52, tda, 1);// knights hp
		addText(21117, "Time Remaining:", 0xFFFFFF, false, true, 52, tda, 0);
		addText(21118, "", 0xFFFFFF, false, true, 52, tda, 0);
		int last = 18;
		RSinterface.children = new int[last];
		RSinterface.childX = new int[last];
		RSinterface.childY = new int[last];
		setBounds(21101, 361, 26, 0, RSinterface);
		setBounds(21102, 396, 26, 1, RSinterface);
		setBounds(21103, 436, 26, 2, RSinterface);
		setBounds(21104, 474, 26, 3, RSinterface);
		setBounds(21105, 3, 21, 4, RSinterface);
		setBounds(21106, 3, 50, 5, RSinterface);
		setBounds(21107, 371, 60, 6, RSinterface);
		setBounds(21108, 409, 60, 7, RSinterface);
		setBounds(21109, 443, 60, 8, RSinterface);
		setBounds(21110, 479, 60, 9, RSinterface);
		setBounds(21111, 362, 10, 10, RSinterface);
		setBounds(21112, 398, 10, 11, RSinterface);
		setBounds(21113, 436, 10, 12, RSinterface);
		setBounds(21114, 475, 10, 13, RSinterface);
		setBounds(21115, 32, 32, 14, RSinterface);
		setBounds(21116, 32, 62, 15, RSinterface);
		setBounds(21117, 8, 88, 16, RSinterface);
		setBounds(21118, 87, 88, 17, RSinterface);
	}

	public String hoverText;

	public static void addHoverBox(int id, int ParentID, String text, String text2, int configId, int configFrame) {
		RSInterface rsi = addTabInterface(id);
		rsi.id = id;
		rsi.parentID = ParentID;
		rsi.type = 8;
		rsi.aString228 = text;
		rsi.message = text2;
		rsi.anIntArray245 = new int[1];
		rsi.anIntArray212 = new int[1];
		rsi.anIntArray245[0] = 1;
		rsi.anIntArray212[0] = configId;
		rsi.valueIndexArray = new int[1][3];
		rsi.valueIndexArray[0][0] = 5;
		rsi.valueIndexArray[0][1] = configFrame;
		rsi.valueIndexArray[0][2] = 0;
	}

	public static void addText(int id, String text, TextDrawingArea tda[], int idx, int color, boolean center, boolean shadow) {
		RSInterface tab = addTabInterface(id);
		tab.parentID = id;
		tab.id = id;
		tab.type = 4;
		tab.atActionType = 0;
		tab.width = 0;
		tab.height = 11;
		tab.contentType = 0;
		tab.opacity = 0;
		tab.hoverType = -1;
		tab.centerText = center;
		tab.textShadow = shadow;
		tab.textDrawingAreas = tda[idx];
		tab.message = text;
		tab.aString228 = "";
		tab.textColor = color;
		tab.anInt219 = 0;
		tab.anInt216 = 0;
		tab.anInt239 = 0;
	}

	public static void addText(int i, String s, int k, boolean l, boolean m, int a, TextDrawingArea[] TDA, int j) {
		RSInterface RSInterface = addInterface(i);
		RSInterface.parentID = i;
		RSInterface.id = i;
		RSInterface.type = 4;
		RSInterface.atActionType = 0;
		RSInterface.width = 0;
		RSInterface.height = 0;
		RSInterface.contentType = 0;
		RSInterface.opacity = 0;
		RSInterface.hoverType = a;
		RSInterface.centerText = l;
		RSInterface.textShadow = m;
		RSInterface.textDrawingAreas = TDA[j];
		RSInterface.message = s;
		RSInterface.aString228 = "";
		RSInterface.textColor = k;
	}

	public static void addButton(int id, int sid, String spriteName, String tooltip, int w, int h) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 1;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;
		tab.disabledSprite = imageLoader(sid, spriteName);
		tab.enabledSprite = imageLoader(sid, spriteName);
		tab.width = w;
		tab.height = h;
		tab.tooltip = tooltip;
	}

	public static void addConfigButton(int ID, int pID, int bID, int bID2, String bName, int width, int height, String tT, int configID, int aT, int configFrame) {
		RSInterface Tab = addTabInterface(ID);
		Tab.parentID = pID;
		Tab.id = ID;
		Tab.type = 5;
		Tab.atActionType = aT;
		Tab.contentType = 0;
		Tab.width = width;
		Tab.height = height;
		Tab.opacity = 0;
		Tab.hoverType = -1;
		Tab.anIntArray245 = new int[1];
		Tab.anIntArray212 = new int[1];
		Tab.anIntArray245[0] = 1;
		Tab.anIntArray212[0] = configID;
		Tab.valueIndexArray = new int[1][3];
		Tab.valueIndexArray[0][0] = 5;
		Tab.valueIndexArray[0][1] = configFrame;
		Tab.valueIndexArray[0][2] = 0;
		Tab.disabledSprite = imageLoader(bID, bName);
		Tab.enabledSprite = imageLoader(bID2, bName);
		Tab.tooltip = tT;
	}

	public static void addSprite(int id, int spriteId, String spriteName) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;
		tab.disabledSprite = imageLoader(spriteId, spriteName);
		tab.enabledSprite = imageLoader(spriteId, spriteName);
		tab.width = 512;
		tab.height = 334;
	}

	public static void addHoverButton(int i, String imageName, int j, int width, int height, String text, int contentType, int hoverOver, int aT) {// hoverable
																																					// button
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = aT;
		tab.contentType = contentType;
		tab.opacity = 0;
		tab.hoverType = hoverOver;
		tab.disabledSprite = imageLoader(j, imageName);
		tab.enabledSprite = imageLoader(j, imageName);
		tab.width = width;
		tab.height = height;
		tab.tooltip = text;
	}

	public static void addHoveredButton(int i, String imageName, int j, int w, int h, int IMAGEID) {// hoverable
																									// button
		RSInterface tab = addTabInterface(i);
		tab.parentID = i;
		tab.id = i;
		tab.type = 0;
		tab.atActionType = 0;
		tab.width = w;
		tab.height = h;
		tab.isMouseoverTriggered = true;
		tab.opacity = 0;
		tab.hoverType = -1;
		tab.scrollMax = 0;
		addHoverImage(IMAGEID, j, j, imageName);
		tab.totalChildren(1);
		tab.child(0, IMAGEID, 0, 0);
	}

	public static void addHoverImage(int i, int j, int k, String name) {
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.width = 512;
		tab.height = 334;
		tab.opacity = 0;
		tab.hoverType = 52;
		tab.disabledSprite = imageLoader(j, name);
		tab.enabledSprite = imageLoader(k, name);
	}

	public static void addTransparentSprite(int id, int spriteId, String spriteName) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;
		tab.disabledSprite = imageLoader(spriteId, spriteName);
		tab.enabledSprite = imageLoader(spriteId, spriteName);
		tab.width = 512;
		tab.height = 334;
		tab.drawsTransparent = true;
	}

	public static RSInterface addScreenInterface(int id) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 0;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.width = 512;
		tab.height = 334;
		tab.opacity = (byte) 0;
		tab.hoverType = 0;
		return tab;
	}

	public static RSInterface addTabInterface(int id) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;// 250
		tab.parentID = id;// 236
		tab.type = 0;// 262
		tab.atActionType = 0;// 217
		tab.contentType = 0;
		tab.width = 512;// 220
		tab.height = 700;// 267
		tab.opacity = (byte) 0;
		tab.hoverType = -1;// Int 230
		return tab;
	}

	private static Sprite imageLoader(int i, String s) {
		long l = (TextClass.method585(s) << 8) + (long) i;
		Sprite sprite = (Sprite) aMRUNodes_238.insertFromCache(l);
		if (sprite != null)
			return sprite;
		try {
			sprite = new Sprite(s + " " + i);
			aMRUNodes_238.removeFromCache(sprite, l);
		} catch (Exception exception) {
			return null;
		}
		return sprite;
	}

	public void child(int id, int interID, int x, int y) {
		children[id] = interID;
		childX[id] = x;
		childY[id] = y;
	}

	public void totalChildren(int t) {
		children = new int[t];
		childX = new int[t];
		childY = new int[t];
	}

	private Model method206(int i, int j) {
		Model model = (Model) aMRUNodes_264.insertFromCache((i << 16) + j);
		if (model != null)
			return model;
		if (i == 1)
			model = Model.method462(j);
		if (i == 2)
			model = EntityDef.forID(j).method160();
		if (i == 3)
			model = Client.myPlayer.method453();
		if (i == 4)
			model = ItemDef.forID(j).method202(50);
		if (i == 5)
			model = null;
		if (model != null)
			aMRUNodes_264.removeFromCache(model, (i << 16) + j);
		return model;
	}

	private static Sprite method207(int i, StreamLoader streamLoader, String s) {
		long l = (TextClass.method585(s) << 8) + (long) i;
		Sprite sprite = (Sprite) aMRUNodes_238.insertFromCache(l);
		if (sprite != null)
			return sprite;
		try {
			sprite = new Sprite(streamLoader, s, i);
			aMRUNodes_238.removeFromCache(sprite, l);
		} catch (Exception _ex) {
			return null;
		}
		return sprite;
	}

	public static void method208(boolean flag, Model model) {
		int i = 0;// was parameter
		int j = 5;// was parameter
		if (flag)
			return;
		aMRUNodes_264.unlinkAll();
		if (model != null && j != 4)
			aMRUNodes_264.removeFromCache(model, (j << 16) + i);
	}

	public Model method209(int j, int k, boolean flag) {
		Model model;
		if (flag)
			model = method206(anInt255, anInt256);
		else
			model = method206(anInt233, mediaID);
		if (model == null)
			return null;
		if (k == -1 && j == -1 && model.anIntArray1640 == null)
			return model;
		Model model_1 = new Model(true, Class36.method532(k) & Class36.method532(j), false, model);
		if (k != -1 || j != -1)
			model_1.method469();
		if (k != -1)
			model_1.method470(k);
		if (j != -1)
			model_1.method470(j);
		model_1.method479(64, 768, -50, -10, -50, true);
		return model_1;
	}

	public RSInterface() {
	}

	public static StreamLoader aClass44;
	public boolean drawsTransparent;
	public int anInt208;
	public Sprite sprites[];
	public static RSInterface interfaceCache[];
	public int anIntArray212[];
	public int contentType;// anInt214
	public int spritesX[];
	public int anInt216;
	public int atActionType;
	public String spellName;
	public int anInt219;
	public int width;
	public String tooltip;
	public String selectedActionName;
	public boolean centerText;
	public int scrollPosition;
	public String actions[];
	public int valueIndexArray[][];
	public boolean aBoolean227;
	public String aString228;
	public int hoverType;
	public int invSpritePadX;
	public int textColor;
	public int anInt233;
	public int mediaID;
	public boolean aBoolean235;
	public int parentID;
	public int spellUsableOn;
	private static MRUNodes aMRUNodes_238;
	public int anInt239;
	public int children[];
	public int childX[];
	public boolean usableItemInterface;
	public TextDrawingArea textDrawingAreas;
	public int isMouseoverTriggereds;
	public int invSpritePadY;
	public int anIntArray245[];
	public int anInt246;
	public int spritesY[];
	public String message;
	public boolean isInventoryInterface;
	public int id;
	public int invStackSizes[];
	public int inv[];
	public byte opacity;
	private int anInt255;
	private int anInt256;
	public int anInt257;
	public int anInt258;
	public boolean aBoolean259;
	public int scrollMax;
	public int type;
	public int anInt263;
	private static final MRUNodes aMRUNodes_264 = new MRUNodes(30);
	public int anInt265;
	public boolean isMouseoverTriggered;
	public int height;
	public boolean textShadow;
	public int modelZoom;
	public int modelRotation1;
	public int modelRotation2;
	public int childY[];

	public static void addLunarSprite(int i, int j, String name) {
		RSInterface RSInterface = addInterface(i);
		RSInterface.id = i;
		RSInterface.parentID = i;
		RSInterface.type = 5;
		RSInterface.atActionType = 0;
		RSInterface.contentType = 0;
		RSInterface.opacity = 0;
		RSInterface.hoverType = 52;
		RSInterface.disabledSprite = imageLoader(j, name);
		RSInterface.width = 500;
		RSInterface.height = 500;
		RSInterface.tooltip = "";
	}

	public static void drawRune(int i, int id, String runeName) {
		RSInterface RSInterface = addInterface(i);
		RSInterface.type = 5;
		RSInterface.atActionType = 0;
		RSInterface.contentType = 0;
		RSInterface.opacity = 0;
		RSInterface.hoverType = 52;
		RSInterface.disabledSprite = imageLoader(id, "Lunar/RUNE");
		RSInterface.width = 500;
		RSInterface.height = 500;
	}

	public static void addRuneText(int ID, int runeAmount, int RuneID, TextDrawingArea[] font) {
		RSInterface rsInterface = addInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 4;
		rsInterface.atActionType = 0;
		rsInterface.contentType = 0;
		rsInterface.width = 0;
		rsInterface.height = 14;
		rsInterface.opacity = 0;
		rsInterface.hoverType = -1;
		rsInterface.anIntArray245 = new int[1];
		rsInterface.anIntArray212 = new int[1];
		rsInterface.anIntArray245[0] = 3;
		rsInterface.anIntArray212[0] = runeAmount;
		rsInterface.valueIndexArray = new int[1][4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = RuneID;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.centerText = true;
		rsInterface.textDrawingAreas = font[0];
		rsInterface.textShadow = true;
		rsInterface.message = "%1/" + runeAmount + "";
		rsInterface.aString228 = "";
		rsInterface.textColor = 12582912;
		rsInterface.anInt219 = 49152;
	}

	public static void homeTeleport() {
		RSInterface RSInterface = addInterface(30000);
		RSInterface.tooltip = "Cast @gre@Lunar Home Teleport";
		RSInterface.id = 30000;
		RSInterface.parentID = 30000;
		RSInterface.type = 5;
		RSInterface.atActionType = 5;
		RSInterface.contentType = 0;
		RSInterface.opacity = 0;
		RSInterface.hoverType = 30001;
		RSInterface.disabledSprite = imageLoader(1, "Lunar/SPRITE");
		RSInterface.width = 20;
		RSInterface.height = 20;
		RSInterface Int = addInterface(30001);
		Int.isMouseoverTriggered = true;
		Int.hoverType = -1;
		setChildren(1, Int);
		addLunarSprite(30002, 0, "SPRITE");
		setBounds(30002, 0, 0, 0, Int);
	}

	public static void addLunar2RunesSmallBox(int ID, int r1, int r2, int ra1, int ra2, int rune1, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
		RSInterface rsInterface = addInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 5;
		rsInterface.atActionType = type;
		rsInterface.contentType = 0;
		rsInterface.hoverType = ID + 1;
		rsInterface.spellUsableOn = suo;
		rsInterface.selectedActionName = "Cast On";
		rsInterface.width = 20;
		rsInterface.height = 20;
		rsInterface.tooltip = "Cast @gre@" + name;
		rsInterface.spellName = name;
		rsInterface.anIntArray245 = new int[3];
		rsInterface.anIntArray212 = new int[3];
		rsInterface.anIntArray245[0] = 3;
		rsInterface.anIntArray212[0] = ra1;
		rsInterface.anIntArray245[1] = 3;
		rsInterface.anIntArray212[1] = ra2;
		rsInterface.anIntArray245[2] = 3;
		rsInterface.anIntArray212[2] = lvl;
		rsInterface.valueIndexArray = new int[3][];
		rsInterface.valueIndexArray[0] = new int[4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = r1;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.valueIndexArray[1] = new int[4];
		rsInterface.valueIndexArray[1][0] = 4;
		rsInterface.valueIndexArray[1][1] = 3214;
		rsInterface.valueIndexArray[1][2] = r2;
		rsInterface.valueIndexArray[1][3] = 0;
		rsInterface.valueIndexArray[2] = new int[3];
		rsInterface.valueIndexArray[2][0] = 1;
		rsInterface.valueIndexArray[2][1] = 6;
		rsInterface.valueIndexArray[2][2] = 0;
		rsInterface.enabledSprite = imageLoader(sid, "Lunar/LUNARON");
		rsInterface.disabledSprite = imageLoader(sid, "Lunar/LUNAROFF");
		RSInterface INT = addInterface(ID + 1);
		INT.isMouseoverTriggered = true;
		INT.hoverType = -1;
		setChildren(7, INT);
		addLunarSprite(ID + 2, 0, "Lunar/BOX");
		setBounds(ID + 2, 0, 0, 0, INT);
		addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, TDA, 1);
		setBounds(ID + 3, 90, 4, 1, INT);
		addText(ID + 4, descr, 0xAF6A1A, true, true, 52, TDA, 0);
		setBounds(ID + 4, 90, 19, 2, INT);
		setBounds(30016, 37, 35, 3, INT);// Rune
		setBounds(rune1, 112, 35, 4, INT);// Rune
		addRuneText(ID + 5, ra1 + 1, r1, TDA);
		setBounds(ID + 5, 50, 66, 5, INT);
		addRuneText(ID + 6, ra2 + 1, r2, TDA);
		setBounds(ID + 6, 123, 66, 6, INT);
	}

	public static void addLunar3RunesSmallBox(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3, int rune1, int rune2, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
		RSInterface rsInterface = addInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 5;
		rsInterface.atActionType = type;
		rsInterface.contentType = 0;
		rsInterface.hoverType = ID + 1;
		rsInterface.spellUsableOn = suo;
		rsInterface.selectedActionName = "Cast on";
		rsInterface.width = 20;
		rsInterface.height = 20;
		rsInterface.tooltip = "Cast @gre@" + name;
		rsInterface.spellName = name;
		rsInterface.anIntArray245 = new int[4];
		rsInterface.anIntArray212 = new int[4];
		rsInterface.anIntArray245[0] = 3;
		rsInterface.anIntArray212[0] = ra1;
		rsInterface.anIntArray245[1] = 3;
		rsInterface.anIntArray212[1] = ra2;
		rsInterface.anIntArray245[2] = 3;
		rsInterface.anIntArray212[2] = ra3;
		rsInterface.anIntArray245[3] = 3;
		rsInterface.anIntArray212[3] = lvl;
		rsInterface.valueIndexArray = new int[4][];
		rsInterface.valueIndexArray[0] = new int[4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = r1;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.valueIndexArray[1] = new int[4];
		rsInterface.valueIndexArray[1][0] = 4;
		rsInterface.valueIndexArray[1][1] = 3214;
		rsInterface.valueIndexArray[1][2] = r2;
		rsInterface.valueIndexArray[1][3] = 0;
		rsInterface.valueIndexArray[2] = new int[4];
		rsInterface.valueIndexArray[2][0] = 4;
		rsInterface.valueIndexArray[2][1] = 3214;
		rsInterface.valueIndexArray[2][2] = r3;
		rsInterface.valueIndexArray[2][3] = 0;
		rsInterface.valueIndexArray[3] = new int[3];
		rsInterface.valueIndexArray[3][0] = 1;
		rsInterface.valueIndexArray[3][1] = 6;
		rsInterface.valueIndexArray[3][2] = 0;
		rsInterface.enabledSprite = imageLoader(sid, "Lunar/LUNARON");
		rsInterface.disabledSprite = imageLoader(sid, "Lunar/LUNAROFF");
		RSInterface INT = addInterface(ID + 1);
		INT.isMouseoverTriggered = true;
		INT.hoverType = -1;
		setChildren(9, INT);
		addLunarSprite(ID + 2, 0, "Lunar/BOX");
		setBounds(ID + 2, 0, 0, 0, INT);
		addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, TDA, 1);
		setBounds(ID + 3, 90, 4, 1, INT);
		addText(ID + 4, descr, 0xAF6A1A, true, true, 52, TDA, 0);
		setBounds(ID + 4, 90, 19, 2, INT);
		setBounds(30016, 14, 35, 3, INT);
		setBounds(rune1, 74, 35, 4, INT);
		setBounds(rune2, 130, 35, 5, INT);
		addRuneText(ID + 5, ra1 + 1, r1, TDA);
		setBounds(ID + 5, 26, 66, 6, INT);
		addRuneText(ID + 6, ra2 + 1, r2, TDA);
		setBounds(ID + 6, 87, 66, 7, INT);
		addRuneText(ID + 7, ra3 + 1, r3, TDA);
		setBounds(ID + 7, 142, 66, 8, INT);
	}

	public static void addLunar3RunesBigBox(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3, int rune1, int rune2, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
		RSInterface rsInterface = addInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 5;
		rsInterface.atActionType = type;
		rsInterface.contentType = 0;
		rsInterface.hoverType = ID + 1;
		rsInterface.spellUsableOn = suo;
		rsInterface.selectedActionName = "Cast on";
		rsInterface.width = 20;
		rsInterface.height = 20;
		rsInterface.tooltip = "Cast @gre@" + name;
		rsInterface.spellName = name;
		rsInterface.anIntArray245 = new int[4];
		rsInterface.anIntArray212 = new int[4];
		rsInterface.anIntArray245[0] = 3;
		rsInterface.anIntArray212[0] = ra1;
		rsInterface.anIntArray245[1] = 3;
		rsInterface.anIntArray212[1] = ra2;
		rsInterface.anIntArray245[2] = 3;
		rsInterface.anIntArray212[2] = ra3;
		rsInterface.anIntArray245[3] = 3;
		rsInterface.anIntArray212[3] = lvl;
		rsInterface.valueIndexArray = new int[4][];
		rsInterface.valueIndexArray[0] = new int[4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = r1;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.valueIndexArray[1] = new int[4];
		rsInterface.valueIndexArray[1][0] = 4;
		rsInterface.valueIndexArray[1][1] = 3214;
		rsInterface.valueIndexArray[1][2] = r2;
		rsInterface.valueIndexArray[1][3] = 0;
		rsInterface.valueIndexArray[2] = new int[4];
		rsInterface.valueIndexArray[2][0] = 4;
		rsInterface.valueIndexArray[2][1] = 3214;
		rsInterface.valueIndexArray[2][2] = r3;
		rsInterface.valueIndexArray[2][3] = 0;
		rsInterface.valueIndexArray[3] = new int[3];
		rsInterface.valueIndexArray[3][0] = 1;
		rsInterface.valueIndexArray[3][1] = 6;
		rsInterface.valueIndexArray[3][2] = 0;
		rsInterface.enabledSprite = imageLoader(sid, "Lunar/LUNARON");
		rsInterface.disabledSprite = imageLoader(sid, "Lunar/LUNAROFF");
		RSInterface INT = addInterface(ID + 1);
		INT.isMouseoverTriggered = true;
		INT.hoverType = -1;
		setChildren(9, INT);
		addLunarSprite(ID + 2, 1, "Lunar/BOX");
		setBounds(ID + 2, 0, 0, 0, INT);
		addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, TDA, 1);
		setBounds(ID + 3, 90, 4, 1, INT);
		addText(ID + 4, descr, 0xAF6A1A, true, true, 52, TDA, 0);
		setBounds(ID + 4, 90, 21, 2, INT);
		setBounds(30016, 14, 48, 3, INT);
		setBounds(rune1, 74, 48, 4, INT);
		setBounds(rune2, 130, 48, 5, INT);
		addRuneText(ID + 5, ra1 + 1, r1, TDA);
		setBounds(ID + 5, 26, 79, 6, INT);
		addRuneText(ID + 6, ra2 + 1, r2, TDA);
		setBounds(ID + 6, 87, 79, 7, INT);
		addRuneText(ID + 7, ra3 + 1, r3, TDA);
		setBounds(ID + 7, 142, 79, 8, INT);
	}

	public static void addLunar3RunesLargeBox(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3, int rune1, int rune2, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
		RSInterface rsInterface = addInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 5;
		rsInterface.atActionType = type;
		rsInterface.contentType = 0;
		rsInterface.hoverType = ID + 1;
		rsInterface.spellUsableOn = suo;
		rsInterface.selectedActionName = "Cast on";
		rsInterface.width = 20;
		rsInterface.height = 20;
		rsInterface.tooltip = "Cast @gre@" + name;
		rsInterface.spellName = name;
		rsInterface.anIntArray245 = new int[4];
		rsInterface.anIntArray212 = new int[4];
		rsInterface.anIntArray245[0] = 3;
		rsInterface.anIntArray212[0] = ra1;
		rsInterface.anIntArray245[1] = 3;
		rsInterface.anIntArray212[1] = ra2;
		rsInterface.anIntArray245[2] = 3;
		rsInterface.anIntArray212[2] = ra3;
		rsInterface.anIntArray245[3] = 3;
		rsInterface.anIntArray212[3] = lvl;
		rsInterface.valueIndexArray = new int[4][];
		rsInterface.valueIndexArray[0] = new int[4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = r1;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.valueIndexArray[1] = new int[4];
		rsInterface.valueIndexArray[1][0] = 4;
		rsInterface.valueIndexArray[1][1] = 3214;
		rsInterface.valueIndexArray[1][2] = r2;
		rsInterface.valueIndexArray[1][3] = 0;
		rsInterface.valueIndexArray[2] = new int[4];
		rsInterface.valueIndexArray[2][0] = 4;
		rsInterface.valueIndexArray[2][1] = 3214;
		rsInterface.valueIndexArray[2][2] = r3;
		rsInterface.valueIndexArray[2][3] = 0;
		rsInterface.valueIndexArray[3] = new int[3];
		rsInterface.valueIndexArray[3][0] = 1;
		rsInterface.valueIndexArray[3][1] = 6;
		rsInterface.valueIndexArray[3][2] = 0;
		rsInterface.enabledSprite = imageLoader(sid, "Lunar/LUNARON");
		rsInterface.disabledSprite = imageLoader(sid, "Lunar/LUNAROFF");
		RSInterface INT = addInterface(ID + 1);
		INT.isMouseoverTriggered = true;
		INT.hoverType = -1;
		setChildren(9, INT);
		addLunarSprite(ID + 2, 2, "Lunar/BOX");
		setBounds(ID + 2, 0, 0, 0, INT);
		addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, TDA, 1);
		setBounds(ID + 3, 90, 4, 1, INT);
		addText(ID + 4, descr, 0xAF6A1A, true, true, 52, TDA, 0);
		setBounds(ID + 4, 90, 34, 2, INT);
		setBounds(30016, 14, 61, 3, INT);
		setBounds(rune1, 74, 61, 4, INT);
		setBounds(rune2, 130, 61, 5, INT);
		addRuneText(ID + 5, ra1 + 1, r1, TDA);
		setBounds(ID + 5, 26, 92, 6, INT);
		addRuneText(ID + 6, ra2 + 1, r2, TDA);
		setBounds(ID + 6, 87, 92, 7, INT);
		addRuneText(ID + 7, ra3 + 1, r3, TDA);
		setBounds(ID + 7, 142, 92, 8, INT);
	}

	public static void setChildren(int total, RSInterface i) {
		i.children = new int[total];
		i.childX = new int[total];
		i.childY = new int[total];
	}

	public static void configureLunar(TextDrawingArea[] TDA) {
		homeTeleport();
		constructLunar();
		drawRune(30003, 1, "Fire");
		drawRune(30004, 2, "Water");
		drawRune(30005, 3, "Air");
		drawRune(30006, 4, "Earth");
		drawRune(30007, 5, "Mind");
		drawRune(30008, 6, "Body");
		drawRune(30009, 7, "Death");
		drawRune(30010, 8, "Nature");
		drawRune(30011, 9, "Chaos");
		drawRune(30012, 10, "Law");
		drawRune(30013, 11, "Cosmic");
		drawRune(30014, 12, "Blood");
		drawRune(30015, 13, "Soul");
		drawRune(30016, 14, "Astral");
		addLunar3RunesSmallBox(30017, 9075, 554, 555, 0, 4, 3, 30003, 30004, 64, "Bake Pie", "Bake pies without a stove", TDA, 0, 16, 2);
		addLunar2RunesSmallBox(30025, 9075, 557, 0, 7, 30006, 65, "Cure Plant", "Cure disease on farming patch", TDA, 1, 4, 2);
		addLunar3RunesBigBox(30032, 9075, 564, 558, 0, 0, 0, 30013, 30007, 65, "Monster Examine", "Detect the combat statistics of a\\nmonster", TDA, 2, 2, 2);
		addLunar3RunesSmallBox(30040, 9075, 564, 556, 0, 0, 1, 30013, 30005, 66, "NPC Contact", "Speak with varied NPCs", TDA, 3, 0, 2);
		addLunar3RunesSmallBox(30048, 9075, 563, 557, 0, 0, 9, 30012, 30006, 67, "Cure Other", "Cure poisoned players", TDA, 4, 8, 2);
		addLunar3RunesSmallBox(30056, 9075, 555, 554, 0, 2, 0, 30004, 30003, 67, "Humidify", "Fills certain vessels with water", TDA, 5, 0, 5);
		addLunar3RunesSmallBox(30064, 9075, 563, 557, 1, 0, 1, 30012, 30006, 68, "Moonclan Teleport", "Teleports you to moonclan island", TDA, 6, 0, 5);
		addLunar3RunesBigBox(30075, 9075, 563, 557, 1, 0, 3, 30012, 30006, 69, "Tele Group Moonclan", "Teleports players to Moonclan\\nisland", TDA, 7, 0, 5);
		addLunar3RunesSmallBox(30083, 9075, 563, 557, 1, 0, 5, 30012, 30006, 70, "Ourania Teleport", "Teleports you to ourania rune altar", TDA, 8, 0, 5);
		addLunar3RunesSmallBox(30091, 9075, 564, 563, 1, 1, 0, 30013, 30012, 70, "Cure Me", "Cures Poison", TDA, 9, 0, 5);
		addLunar2RunesSmallBox(30099, 9075, 557, 1, 1, 30006, 70, "Hunter Kit", "Get a kit of hunting gear", TDA, 10, 0, 5);
		addLunar3RunesSmallBox(30106, 9075, 563, 555, 1, 0, 0, 30012, 30004, 71, "Waterbirth Teleport", "Teleports you to Waterbirth island", TDA, 11, 0, 5);
		addLunar3RunesBigBox(30114, 9075, 563, 555, 1, 0, 4, 30012, 30004, 72, "Tele Group Waterbirth", "Teleports players to Waterbirth\\nisland", TDA, 12, 0, 5);
		addLunar3RunesSmallBox(30122, 9075, 564, 563, 1, 1, 1, 30013, 30012, 73, "Cure Group", "Cures Poison on players", TDA, 13, 0, 5);
		addLunar3RunesBigBox(30130, 9075, 564, 559, 1, 1, 4, 30013, 30008, 74, "Stat Spy", "Cast on another player to see their\\nskill levels", TDA, 14, 8, 2);
		addLunar3RunesBigBox(30138, 9075, 563, 554, 1, 1, 2, 30012, 30003, 74, "Barbarian Teleport", "Teleports you to the Barbarian\\noutpost", TDA, 15, 0, 5);
		addLunar3RunesBigBox(30146, 9075, 563, 554, 1, 1, 5, 30012, 30003, 75, "Tele Group Barbarian", "Teleports players to the Barbarian\\noutpost", TDA, 16, 0, 5);
		addLunar3RunesSmallBox(30154, 9075, 554, 556, 1, 5, 9, 30003, 30005, 76, "Superglass Make", "Make glass without a furnace", TDA, 17, 16, 2);
		addLunar3RunesSmallBox(30162, 9075, 563, 555, 1, 1, 3, 30012, 30004, 77, "Khazard Teleport", "Teleports you to Port khazard", TDA, 18, 0, 5);
		addLunar3RunesSmallBox(30170, 9075, 563, 555, 1, 1, 7, 30012, 30004, 78, "Tele Group Khazard", "Teleports players to Port khazard", TDA, 19, 0, 5);
		addLunar3RunesBigBox(30178, 9075, 564, 559, 1, 0, 4, 30013, 30008, 78, "Dream", "Take a rest and restore hitpoints 3\\n times faster", TDA, 20, 0, 5);
		addLunar3RunesSmallBox(30186, 9075, 557, 555, 1, 9, 4, 30006, 30004, 79, "String Jewellery", "String amulets without wool", TDA, 21, 0, 5);
		addLunar3RunesLargeBox(30194, 9075, 557, 555, 1, 9, 9, 30006, 30004, 80, "Stat Restore Pot\\nShare", "Share a potion with up to 4 nearby\\nplayers", TDA, 22, 0, 5);
		addLunar3RunesSmallBox(30202, 9075, 554, 555, 1, 6, 6, 30003, 30004, 81, "Magic Imbue", "Combine runes without a talisman", TDA, 23, 0, 5);
		addLunar3RunesBigBox(30210, 9075, 561, 557, 2, 1, 14, 30010, 30006, 82, "Fertile Soil", "Fertilise a farming patch with super\\ncompost", TDA, 24, 4, 2);
		addLunar3RunesBigBox(30218, 9075, 557, 555, 2, 11, 9, 30006, 30004, 83, "Boost Potion Share", "Shares a potion with up to 4 nearby\\nplayers", TDA, 25, 0, 5);
		addLunar3RunesSmallBox(30226, 9075, 563, 555, 2, 2, 9, 30012, 30004, 84, "Fishing Guild Teleport", "Teleports you to the fishing guild", TDA, 26, 0, 5);
		addLunar3RunesLargeBox(30234, 9075, 563, 555, 1, 2, 13, 30012, 30004, 85, "Tele Group Fishing Guild", "Teleports players to the Fishing\\nGuild", TDA, 27, 0, 5);
		addLunar3RunesSmallBox(30242, 9075, 557, 561, 2, 14, 0, 30006, 30010, 85, "Plank Make", "Turn Logs into planks", TDA, 28, 16, 5);
		addLunar3RunesSmallBox(30250, 9075, 563, 555, 2, 2, 9, 30012, 30004, 86, "Catherby Teleport", "Teleports you to Catherby", TDA, 29, 0, 5);
		addLunar3RunesSmallBox(30258, 9075, 563, 555, 2, 2, 14, 30012, 30004, 87, "Tele Group Catherby", "Teleports players to Catherby", TDA, 30, 0, 5);
		addLunar3RunesSmallBox(30266, 9075, 563, 555, 2, 2, 7, 30012, 30004, 88, "Ice Plateau Teleport", "Teleports you to Ice Plateau", TDA, 31, 0, 5);
		addLunar3RunesLargeBox(30274, 9075, 563, 555, 2, 2, 15, 30012, 30004, 89, "Tele Group Ice Plateau", "Teleports players to Ice Plateau", TDA, 32, 0, 5);
		addLunar3RunesBigBox(30282, 9075, 563, 561, 2, 1, 0, 30012, 30010, 90, "Energy Transfer", "Spend HP and SA energy to\\n give another SA and run energy", TDA, 33, 8, 2);
		addLunar3RunesBigBox(30290, 9075, 563, 565, 2, 2, 0, 30012, 30014, 91, "Heal Other", "Transfer up to 75% of hitpoints\\n to another player", TDA, 34, 8, 2);
		addLunar3RunesBigBox(30298, 9075, 560, 557, 2, 1, 9, 30009, 30006, 92, "Vengeance Other", "Allows another player to rebound\\ndamage to an opponent", TDA, 35, 8, 2);
		addLunar3RunesSmallBox(30306, 9075, 560, 557, 3, 1, 9, 30009, 30006, 93, "Vengeance", "Rebound damage to an opponent", TDA, 36, 0, 5);
		addLunar3RunesBigBox(30314, 9075, 565, 563, 3, 2, 5, 30014, 30012, 94, "Heal Group", "Transfer up to 75% of hitpoints\\n to a group", TDA, 37, 0, 5);
		addLunar3RunesBigBox(30322, 9075, 564, 563, 2, 1, 0, 30013, 30012, 95, "Spellbook Swap", "Change to another spellbook for 1\\nspell cast", TDA, 38, 0, 5);
	}

	public static void constructLunar() {
		RSInterface Interface = addTabInterface(29999);
		setChildren(80, Interface);
		setBounds(30000, 11, 10, 0, Interface);
		setBounds(30017, 40, 9, 1, Interface);
		setBounds(30025, 71, 12, 2, Interface);
		setBounds(30032, 103, 10, 3, Interface);
		setBounds(30040, 135, 12, 4, Interface);
		setBounds(30048, 165, 10, 5, Interface);
		setBounds(30056, 8, 38, 6, Interface);
		setBounds(30064, 39, 39, 7, Interface);
		setBounds(30075, 71, 39, 8, Interface);
		setBounds(30083, 103, 39, 9, Interface);
		setBounds(30091, 135, 39, 10, Interface);
		setBounds(30099, 165, 37, 11, Interface);
		setBounds(30106, 12, 68, 12, Interface);
		setBounds(30114, 42, 68, 13, Interface);
		setBounds(30122, 71, 68, 14, Interface);
		setBounds(30130, 103, 68, 15, Interface);
		setBounds(30138, 135, 68, 16, Interface);
		setBounds(30146, 165, 68, 17, Interface);
		setBounds(30154, 14, 97, 18, Interface);
		setBounds(30162, 42, 97, 19, Interface);
		setBounds(30170, 71, 97, 20, Interface);
		setBounds(30178, 101, 97, 21, Interface);
		setBounds(30186, 135, 98, 22, Interface);
		setBounds(30194, 168, 98, 23, Interface);
		setBounds(30202, 11, 125, 24, Interface);
		setBounds(30210, 42, 124, 25, Interface);
		setBounds(30218, 74, 125, 26, Interface);
		setBounds(30226, 103, 125, 27, Interface);
		setBounds(30234, 135, 125, 28, Interface);
		setBounds(30242, 164, 126, 29, Interface);
		setBounds(30250, 10, 155, 30, Interface);
		setBounds(30258, 42, 155, 31, Interface);
		setBounds(30266, 71, 155, 32, Interface);
		setBounds(30274, 103, 155, 33, Interface);
		setBounds(30282, 136, 155, 34, Interface);
		setBounds(30290, 165, 155, 35, Interface);
		setBounds(30298, 13, 185, 36, Interface);
		setBounds(30306, 42, 185, 37, Interface);
		setBounds(30314, 71, 184, 38, Interface);
		setBounds(30322, 104, 184, 39, Interface);
		setBounds(30001, 6, 184, 40, Interface);// hover
		setBounds(30018, 5, 176, 41, Interface);// hover
		setBounds(30026, 5, 176, 42, Interface);// hover
		setBounds(30033, 5, 163, 43, Interface);// hover
		setBounds(30041, 5, 176, 44, Interface);// hover
		setBounds(30049, 5, 176, 45, Interface);// hover
		setBounds(30057, 5, 176, 46, Interface);// hover
		setBounds(30065, 5, 176, 47, Interface);// hover
		setBounds(30076, 5, 163, 48, Interface);// hover
		setBounds(30084, 5, 176, 49, Interface);// hover
		setBounds(30092, 5, 176, 50, Interface);// hover
		setBounds(30100, 5, 176, 51, Interface);// hover
		setBounds(30107, 5, 176, 52, Interface);// hover
		setBounds(30115, 5, 163, 53, Interface);// hover
		setBounds(30123, 5, 176, 54, Interface);// hover
		setBounds(30131, 5, 163, 55, Interface);// hover
		setBounds(30139, 5, 163, 56, Interface);// hover
		setBounds(30147, 5, 163, 57, Interface);// hover
		setBounds(30155, 5, 176, 58, Interface);// hover
		setBounds(30163, 5, 176, 59, Interface);// hover
		setBounds(30171, 5, 176, 60, Interface);// hover
		setBounds(30179, 5, 163, 61, Interface);// hover
		setBounds(30187, 5, 176, 62, Interface);// hover
		setBounds(30195, 5, 149, 63, Interface);// hover
		setBounds(30203, 5, 176, 64, Interface);// hover
		setBounds(30211, 5, 163, 65, Interface);// hover
		setBounds(30219, 5, 163, 66, Interface);// hover
		setBounds(30227, 5, 176, 67, Interface);// hover
		setBounds(30235, 5, 149, 68, Interface);// hover
		setBounds(30243, 5, 176, 69, Interface);// hover
		setBounds(30251, 5, 5, 70, Interface);// hover
		setBounds(30259, 5, 5, 71, Interface);// hover
		setBounds(30267, 5, 5, 72, Interface);// hover
		setBounds(30275, 5, 5, 73, Interface);// hover
		setBounds(30283, 5, 5, 74, Interface);// hover
		setBounds(30291, 5, 5, 75, Interface);// hover
		setBounds(30299, 5, 5, 76, Interface);// hover
		setBounds(30307, 5, 5, 77, Interface);// hover
		setBounds(30323, 5, 5, 78, Interface);// hover
		setBounds(30315, 5, 5, 79, Interface);// hover
	}

	public static void setBounds(int ID, int X, int Y, int frame, RSInterface RSinterface) {
		RSinterface.children[frame] = ID;
		RSinterface.childX[frame] = X;
		RSinterface.childY[frame] = Y;
	}

	public static void addButton(int i, int j, String name, int W, int H, String S, int AT) {
		RSInterface RSInterface = addInterface(i);
		RSInterface.id = i;
		RSInterface.parentID = i;
		RSInterface.type = 5;
		RSInterface.atActionType = AT;
		RSInterface.contentType = 0;
		RSInterface.opacity = 0;
		RSInterface.hoverType = 52;
		RSInterface.disabledSprite = imageLoader(j, name);
		RSInterface.enabledSprite = imageLoader(j, name);
		RSInterface.width = W;
		RSInterface.height = H;
		RSInterface.tooltip = S;
	}
	
	public static void addText(int id, String text, TextDrawingArea wid[],
			int idx, int color) {
		RSInterface rsinterface = addTabInterface(id);
		rsinterface.id = id;
		rsinterface.parentID = id;
		rsinterface.type = 4;
		rsinterface.atActionType = 0;
		rsinterface.width = 174;
		rsinterface.height = 11;
		rsinterface.contentType = 0;
		rsinterface.opacity = 0;
		rsinterface.mOverInterToTrigger = -1;
		rsinterface.centerText = false;
		rsinterface.textShadow = true;
		rsinterface.textDrawingAreas = wid[idx];
		rsinterface.message = text;
		rsinterface.aString228 = "";
		rsinterface.textColor = color;
		rsinterface.anInt219 = 0;
		rsinterface.anInt216 = 0;
		rsinterface.anInt239 = 0;
	}
	
	public static void addSprite(int i, int j, int k) {
		RSInterface rsinterface = interfaceCache[i] = new RSInterface();
		rsinterface.id = i;
		rsinterface.parentID = i;
		rsinterface.type = 5;
		rsinterface.atActionType = 1;
		rsinterface.contentType = 0;
		rsinterface.width = 20;
		rsinterface.height = 20;
		rsinterface.opacity = 0;
		rsinterface.mOverInterToTrigger = 52;
		rsinterface.disabledSprite = imageLoader(j,
				"Interfaces/Equipment/SPRITE");
		rsinterface.enabledSprite = imageLoader(k,
				"Interfaces/Equipment/SPRITE");
	}
	
	private boolean interfaceShown;
	public boolean newScroller;
	private int mOverInterToTrigger;
	public Sprite disabledSprite;
	public Sprite enabledSprite;
	public String[] tooltips;
}
