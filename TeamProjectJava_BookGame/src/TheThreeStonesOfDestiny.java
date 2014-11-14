import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TheThreeStonesOfDestiny {
	//globals
	public static int currentEpisode = 0;
	public static int nextEpisode = 0;
	public static int currentEpisode_Saved = 0;
	public static int choiceInt = 0;
	public static int loadInt = 1; //flag to load game if you lose
	//globals - inventory
	public static boolean playerAlive = true; //if false - the game is lost
	public static Map<String, String> animals = new HashMap<String, String>();
	public static int magic = 0;
	public static Map<String, String> items = new HashMap<String, String>();
	public static int money = 0;
	//globals - inventory saved
	public static Map<String, String> animals_Saved = new HashMap<String, String>();
	public static int magic_Saved = 0;
	public static Map<String, String> items_Saved = new HashMap<String, String>();
	public static int money_Saved = 0;
	//globals - episode
	public static boolean endGame = false;
	//game flags - to be saved / loaded;
	public static boolean altozarBool = false;
	public static boolean altozarBool_Saved = false;
	
	public static String delimiterStr = new String("--------------------");
	
	public static void main(String[] args) { //MAIN//
		boolean continueBool = true;
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("ТИМ ДЕНИЪЛС");
		System.out.println();
		System.out.println("ТРИТЕ КАМЪКА НА СЪДБАТА");
		System.out.println();
		System.out.println("дигитализирана конзолна книга-игра");
		System.out.println();
		
		do { //GAME START
			System.out.println("Изберете опция и натиснете Enter:");
			System.out.println("1 - Нова Игра");
			System.out.println("2 - Изход");
			choiceInt = input.nextInt();
		} while (choiceInt < 1 || choiceInt > 2);
		
		if (choiceInt == 1) {
			System.out.println(delimiterStr);
			System.out.println();
			showHowToPlay();
			showEpisode(500); // 500 - за тези, които не са чели...
			showEpisode(501); //501 - правила на играта = свитък на съдбата, за магическата сила
			magic = 10;
			showEpisode(502); //502 - за животните
			chooseAnimals();
			currentEpisode = 1;
		}
		else {
			return; //exit game
		}
		
		do { //ENGINE
			switch (currentEpisode) { //auto-save when getting Stone of Destiny
			case 1:
			case 12:
			case 43:
			case 61:
			case 80:
			case 125:
			case 127:
			case 130:
			case 138:
			case 159:
			case 194: autoSave(); break;
			default: break;
			}
			
			showInventory();
			showEpisode(currentEpisode);
			checkStatus(currentEpisode);
			if (endGame) {
				if (playerAlive == true) {
					System.out.println("Поздравления, ти завърши успешно своето приключение!");
					return;
				}
				else {
					System.out.println("За съжаление, ти не успя да завършиш успешно своето приключение! Късмет следващият път!");
					System.out.println(delimiterStr);
					System.out.println();
					System.out.print("Искаш ли да заредиш последно запаметената игра? (1 = да)");
					loadInt = input.nextInt();
					if (loadInt == 1) {
						loadGame();
						continueBool = true; //exit the loop
						continue; //resume loaded episode - exit do while
					}
					else {
						return;
					}
				}
			}
			else {
				do {
					System.out.print("Въведи следващ епизод: ");
					nextEpisode = input.nextInt();
					continueBool = validateNextEpisode(nextEpisode); //and also processEpisode
					System.out.println();
					if (nextEpisode == 1000) {
						return; //exit game
					}
					else if (nextEpisode == 2000) { //load game
						loadGame();
						continueBool = true; //exit the loop
						continue; //resume loaded episode - exit do while
					}
					else if (nextEpisode == 3000) { //show help
						showHowToPlay();
					}
					if (endGame) { //special case in episode 99, 179
						if (playerAlive == true) {
							System.out.println("Поздравления, ти завърши успешно своето приключение!");
							return;
						}
						else {
							System.out.println("За съжаление, ти не успя да завършиш успешно своето приключение! Късмет следващият път!");
							System.out.println(delimiterStr);
							System.out.println();
							System.out.print("Искаш ли да заредиш последно запаметената игра? (1 = да)");
							loadInt = input.nextInt();
							if (loadInt == 1) {
								loadGame();
								continueBool = true; //exit the loop
								continue; //resume loaded episode - exit do while
							}
							else {
								return;
							}
						}
					}
					if (!continueBool & nextEpisode != 3000) {
						System.out.println("Невалиден следващ епизод!");
						System.out.println();
					}
				} while (!continueBool);
				if (nextEpisode != 2000) {
					currentEpisode = nextEpisode; //n/a for loaded game
				}
			}
		} while (!endGame);
	}

	private static void autoSave() {
		System.out.println("Автоматично запаметяване на играта!");
		currentEpisode_Saved = currentEpisode;
		playerAlive = true;
		endGame = false;
		currentEpisode_Saved = currentEpisode;
		animals_Saved = animals;
		magic_Saved = magic;
		items_Saved = items;
		money_Saved = money;
		altozarBool_Saved = altozarBool;
		System.out.println(delimiterStr);
		System.out.println();
	}

	private static boolean validateNextEpisode(int nextEpisode) { //validate prerequisites to enter episode
		boolean validateBool = false;
		if ((nextEpisode == 1000) || (nextEpisode == 2000) || (nextEpisode == 3000)) {
			return false; //special actions
		}
		switch (currentEpisode) {
		case 1:
			if ((nextEpisode == 180) || (nextEpisode == 143)) {
				validateBool = true;
			}; break;
		case 2:
			if (nextEpisode == 88) {
					if ((magic > 0) && (animals.containsKey("mole") || animals.containsKey("mouse") || animals.containsKey("owl"))) {
						magic--;
						validateBool = true;
					}
			}
			else if (nextEpisode == 143) {
				validateBool = true;
			}; break;
		case 3:
			if ((nextEpisode == 228) || (nextEpisode == 58)) {
				validateBool = true;
			}; break;
		case 4:
			if ((nextEpisode == 205) || (nextEpisode == 42)) {
				validateBool = true;
			}
			else if (nextEpisode == 173) {
				if ((magic > 0) && (animals.containsKey("mole"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 156) {
				if (magic > 0) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 133) {
				if ((magic > 0) && (animals.containsKey("owl"))) {
					magic--;
					validateBool = true;
				}
			}; break;
		case 5:
			if ((nextEpisode == 67) || (nextEpisode == 182)) {
				validateBool = true;
			}; break;
		case 6:
			if (nextEpisode == 238) {
				validateBool = true;
			}; break;
		case 7:
			if ((nextEpisode == 208) || (nextEpisode == 153)) {
				validateBool = true;
			}; break;
		case 8:
			if (nextEpisode == 84) {
				validateBool = true;
			}; break;
		case 9:
			if (nextEpisode == 195) {
				money = money - 2;
				validateBool = true;
			}; break;
		case 10:
			if ((nextEpisode == 155) || (nextEpisode == 87)) {
				validateBool = true;
			}; break;
		case 11:
			if (nextEpisode == 144) {
				validateBool = true;
			}; break;
		case 12:
			if (nextEpisode == 215) {
				if ((!items.containsKey("rightStone")) && (!items.containsKey("leftStone"))) {
					items.put("middleStone", "Среден Камък");
					validateBool = true;
				}
			}
			else if (nextEpisode == 162) {
				if ((!items.containsKey("rightStone")) && (items.containsKey("leftStone"))) {
					items.put("middleStone", "Среден Камък");
					validateBool = true;
				}
			}; break;
		case 13:
			if (nextEpisode == 159) {
				money = money + 10;
				validateBool = true;
			}; break;
		case 14:
			if (nextEpisode == 127) {
				validateBool = true;
			}; break;
		case 15:
			if (nextEpisode == 147) {
				money--;
				validateBool = true;
				if (items.containsKey("northTalisman")) {
					items.remove("northTalisman");
				}
			}
			else if (nextEpisode == 252) {
				if (items.containsKey("northTalisman")) {
					money--;
					validateBool = true;
				}
			}; break;
		case 16:
			if (nextEpisode == 122) {
				validateBool = true;
			}; break;
		case 17:
			if (nextEpisode == 145) {
				validateBool = true;
			}; break;
		case 18:
			if (nextEpisode == 154) {
				validateBool = true;
			}; break;
		case 19:
			if (nextEpisode == 267) {
				if ((magic > 0) && (animals.containsKey("snake") || animals.containsKey("mouse"))) {
					money = money - 2;
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 106) {
				money = money - 2;
				validateBool = true;
			}; break;
		case 20:
			if (nextEpisode == 190) {
				if ((magic > 0) && (animals.containsKey("eagle"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 57) {
				money = money - 2;
				validateBool = true;
			}; break;
		case 21:
			if (nextEpisode == 5) {
				validateBool = true;
			}; break;
		case 22:
			if (nextEpisode == 138) {
				validateBool = true;
			}; break;
		case 23:
			if (nextEpisode == 98) {
				if (money >= 2) {
					money = money - 2;
					validateBool = true;
				}
			}
			else if (nextEpisode == 141) {
				validateBool = true;
			}; break;
		case 24:
			if (nextEpisode == 60) {
				if ((magic > 0) && (animals.containsKey("eagle"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 217) {
				if ((magic > 0) && (animals.containsKey("owl"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 163) {
				if ((magic > 0) && (animals.containsKey("otter"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 78) {
				if (magic > 0) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 49) {
				validateBool = true;
			}; break;
		case 26:
			if ((nextEpisode == 53) || (nextEpisode == 149)) {
				validateBool = true;
			}; break;
		case 27:
			if (nextEpisode == 35) {
				validateBool = true;
			}; break;
		case 28:
			if (nextEpisode == 68) {
				validateBool = true;
			}; break;
		case 30:
			if (nextEpisode == 134) {
				if ((magic > 0) && (animals.containsKey("mouse"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 161) {
				validateBool = true;
			}; break;
		case 33:
			if ((nextEpisode == 90) || (nextEpisode == 222)) {
				validateBool = true;
			}; break;
		case 34:
			if (nextEpisode == 153) {
				validateBool = true;
			}; break;
		case 35:
			if (nextEpisode == 137) {
				if ((magic > 0) && (animals.containsKey("wolf"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 93) {
				if ((magic > 0) && (animals.containsKey("owl"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 181) {
				if ((magic > 0) && (animals.containsKey("lion"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 69) {
				validateBool = true;
			}; break;
		case 36:
			if (nextEpisode == 71) {
				animals.put("eagle", "Орел");
				magic = magic - 4;
				validateBool = true;
			}; break;
		case 37:
			if (nextEpisode == 233) {
				if ((magic > 0) && (animals.containsKey("eagle"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 220) {
				if ((magic > 0) && (animals.containsKey("wolf"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 227) {
				if ((magic > 0) && (animals.containsKey("mouse"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 236) {
				validateBool = true;
			}; break;
		case 38:
			if (nextEpisode == 52) {
				validateBool = true;
			}; break;
		case 39:
			if (nextEpisode == 7) {
				if (money >= 3) {
					items.put("northTalisman", "Северен Талисман");
				}
				validateBool = true;
			}; break;
		case 40:
			if ((nextEpisode == 95) || (nextEpisode == 177) || (nextEpisode == 21)) {
				validateBool = true;
			}; break;
		case 41:
			if (nextEpisode == 145) {
				validateBool = true;
			}; break;
		case 42:
			if ((nextEpisode == 165) || (nextEpisode == 186)) {
				validateBool = true;
			}; break;
		case 43:
			if (nextEpisode == 172) {
				items.put("leftStone", "Ляв Камък");
				money = money + 7;
				validateBool = true;
			}; break;
		case 44:
			if ((nextEpisode == 207) || (nextEpisode == 12)) {
				validateBool = true;
			}; break;
		case 45:
			if (nextEpisode == 70) {
				validateBool = true;
			}; break;
		case 46:
			if (nextEpisode == 198) {
				validateBool = true;
			}; break;
		case 47:
			if (nextEpisode == 125) {
				validateBool = true;
			}; break;
		case 48:
			if (nextEpisode == 226) {
				if (money >= 2) {
					money = money - 2;
					validateBool = true;
				}
			}
			else if ((nextEpisode == 102) || (nextEpisode == 273)) {
				validateBool = true;
			}; break;
		case 50:
			if (nextEpisode == 129) {
				validateBool = true;
			}; break;
		case 51:
			if (nextEpisode == 160) {
				if ((magic > 0) && (animals.containsKey("otter"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 170) {
				validateBool = true;
			}; break;
		case 52:
			if (nextEpisode == 199) {
				if ((magic > 0) && (animals.containsKey("eagle"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 65) {
				validateBool = true;
			}; break;
		case 53:
			if (nextEpisode == 219) {
				if ((magic > 0) && (animals.containsKey("lion") || animals.containsKey("wolf"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 76) {
				validateBool = true;
			}; break;
		case 54:
			if (nextEpisode == 84) {
				validateBool = true;
			}; break;
		case 55:
			if ((nextEpisode == 243) || (nextEpisode == 206)) {
				validateBool = true;
			}; break;
		case 56:
			if (nextEpisode == 158) {
				validateBool = true;
			}; break;
		case 57:
			if (nextEpisode == 278) {
				validateBool = true;
			}; break;
		case 58:
			if (nextEpisode == 221) {
				magic--;
				validateBool = true;
			}; break;
		case 59:
			if (nextEpisode == 19) {
				validateBool = true;
			}; break;
		case 60:
			if (nextEpisode == 145) {
				validateBool = true;
			}; break;
		case 61:
			if (nextEpisode == 8) {
				items.put("rightStone", "Десен Камък");
				validateBool = true;
			}; break;
		case 62:
			if (nextEpisode == 200) {
				items.remove("northTalisman");
				validateBool = true;
			}
			else if (nextEpisode == 26) {
				validateBool = true;
			}; break;
		case 63:
			if (nextEpisode == 221) {
				validateBool = true;
			}; break;
		case 64:
			if (nextEpisode == 140) {
				validateBool = true;
			}; break;
		case 65:
			if ((nextEpisode == 84) || (nextEpisode == 129)) {
				validateBool = true;
			}; break;
		case 67:
			if (nextEpisode == 216) {
				validateBool = true;
			}; break;
		case 68:
			if (nextEpisode == 123) {
				if (!altozarBool) {
					validateBool = true;
				}
			}
			else if (nextEpisode == 46) {
				if (altozarBool) {
					validateBool = true;
				}
			}; break;
		case 69:
			if ((nextEpisode == 31) || (nextEpisode == 86)) {
				validateBool = true;
			}; break;
		case 71:
			if (nextEpisode == 202) {
				validateBool = true;
			}; break;
		case 72:
			if ((nextEpisode == 111) || (nextEpisode == 272)) {
				validateBool = true;
			}; break;
		case 73:
			if (nextEpisode == 84) {
				validateBool = true;
			}; break;
		case 74:
			if (nextEpisode == 185) {
				if ((magic > 0) && (animals.containsKey("eagle"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 50) {
				validateBool = true;
			}; break;
		case 75:
			if (nextEpisode == 197) {
				validateBool = true;
			}; break;
		case 76:
			if ((nextEpisode == 47) || (nextEpisode == 191)) {
				validateBool = true;
			}; break;
		case 77:
			if (nextEpisode == 192) {
				validateBool = true;
			}; break;
		case 78:
			if (nextEpisode == 187) {
				if ((animals.containsKey("mouse")) || (animals.containsKey("squirrel"))) {
					validateBool = true;
				}
			}
			else if (nextEpisode == 32) {
				if ((animals.containsKey("lion")) || (animals.containsKey("wolf"))) {
					validateBool = true;
				}
			}
			else if (nextEpisode == 260) {
				if ((animals.containsKey("snake")) || (animals.containsKey("mole"))) {
					validateBool = true;
				}
			}; break;
		case 79:
			if ((nextEpisode == 117) || (nextEpisode == 275)) {
				validateBool = true;
			}; break;
		case 80:
			if (nextEpisode == 92) {
				items.put("leftStone", "Ляв Камък");
				validateBool = true;
			}; break;
		case 81:
			if (nextEpisode == 56) {
				if (money >= 1) {
					money--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 152) {
				validateBool = true;
			}; break;
		case 82:
			if ((nextEpisode == 179) || (nextEpisode == 151)) {
				validateBool = true;
			}; break;
		case 83:
			if ((nextEpisode == 203) || (nextEpisode == 178)) {
				validateBool = true;
			}; break;
		case 84:
			if ((nextEpisode == 108) || (nextEpisode == 139)) {
				validateBool = true;
			}; break;
		case 85:
			if ((nextEpisode == 196) || (nextEpisode == 43)) {
				validateBool = true;
			}; break;
		case 86:
			if (nextEpisode == 77) {
				validateBool = true;
			}; break;
		case 87:
			if (nextEpisode == 99) {
				validateBool = true;
			}; break;
		case 88:
			if ((nextEpisode == 29) || (nextEpisode == 175)) {
				validateBool = true;
			}; break;
		case 89:
			if (nextEpisode == 9) {
				validateBool = true;
			}; break;
		case 90:
			if (nextEpisode == 221) {
				validateBool = true;
			}; break;
		case 91:
			if ((nextEpisode == 18) || (nextEpisode == 107)) {
				validateBool = true;
			}; break;
		case 92:
			if (nextEpisode == 84) {
				validateBool = true;
			}; break;
		case 94:
			if (nextEpisode == 145) {
				validateBool = true;
			}; break;
		case 95:
			if (nextEpisode == 216) {
				validateBool = true;
			}; break;
		case 96:
			if (nextEpisode == 37) {
				money--;
				validateBool = true;
			}; break;
		case 98:
			if (nextEpisode == 122) {
				money = money - 2;
				validateBool = true;
			}; break;
		case 99:
			if ((magic == 0) || (!((animals.containsKey("eagle")) && (animals.containsKey("lion")) && (animals.containsKey("wolf")) && (animals.containsKey("mole"))))) {
				endGame = true;
				playerAlive = false;
			}
			else if (nextEpisode == 41) {
				if ((magic > 0) && (animals.containsKey("eagle"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 17) {
				if ((magic > 0) && ((animals.containsKey("lion")) || (animals.containsKey("wolf")))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 210) {
				if ((magic > 0) && (animals.containsKey("mole"))) {
					magic--;
					validateBool = true;
				}
			}; break;
		case 100:
			if (nextEpisode == 218) {
				items.put("matGlass", "Кубче от Матово Стъкло");
				validateBool = true;
			}; break;
		case 101:
			if ((nextEpisode == 265) || (nextEpisode == 120)) {
				validateBool = true;
			}; break;
		case 102:
			if (nextEpisode == 266) {
				if (magic > 0) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 119) {
				validateBool = true;
			}; break;
		case 104:
			if (nextEpisode == 44) {
				items.remove("emptyBottle");
				items.put("fullBottle", "Пълна Бутилка");
				validateBool = true;
			}; break;
		case 106:
			if ((nextEpisode == 5) || (nextEpisode == 40)) {
				validateBool = true;
			}; break;
		case 107:
			if (nextEpisode == 3) {
				validateBool = true;
			}; break;
		case 108:
			if (nextEpisode == 193) {
				if (money > 0) {
					money--;
					items.put("emptyBottle", "Празна Бутилка");
				}
				validateBool = true;
			}; break;
		case 110:
			if (nextEpisode == 120) {
				validateBool = true;
			}; break;
		case 111:
			if (nextEpisode == 194) {
				validateBool = true;
			}; break;
		case 112:
			if (nextEpisode == 274) {
				if (items.containsKey("fullBottle")) {
					items.remove("fullBottle");
					validateBool = true;
				}
			}
			else if (nextEpisode == 229) {
				validateBool = true;
			}; break;
		case 113:
			if (nextEpisode == 98) {
				if (money >= 2) {
					money = money - 2;
					validateBool = true;
				}
			}
			else if (nextEpisode == 209) {
				validateBool = true;
			}; break;
		case 115:
			if (nextEpisode == 268) {
				if (money > 0) {
					money--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 271) {
				validateBool = true;
			}; break;
		case 117:
			if ((nextEpisode == 25) || (nextEpisode == 150)) {
				validateBool = true;
			}; break;
		case 119:
			if (nextEpisode == 226) {
				validateBool = true;
			}; break;
		case 120:
			if (nextEpisode == 278) {
				validateBool = true;
			}; break;
		case 121:
			if ((nextEpisode == 248) || (nextEpisode == 269)) {
				validateBool = true;
			}; break;
		case 122:
			if (nextEpisode == 4) {
				validateBool = true;
			}; break;
		case 123:
			if ((nextEpisode == 214) || (nextEpisode == 244)) {
				validateBool = true;
			}; break;
		case 124:
			if ((nextEpisode == 105) || (nextEpisode == 79)) {
				validateBool = true;
			}; break;
		case 125:
			if (nextEpisode == 167) {
				items.put("leftStone", "Ляв Камък");
				validateBool = true;
			}; break;
		case 126:
			if (nextEpisode == 85) {
				if (items.containsKey("northTalisman")) {
					validateBool = true;
				}
			}
			else if (nextEpisode == 43) {
				if (!items.containsKey("northTalisman")) {
					validateBool = true;
				}
			}; break;
		case 127:
			if (nextEpisode == 129) {
				items.put("middleStone", "Среден Камък");
				validateBool = true;
			}; break;
		case 128:
			if ((nextEpisode == 239) || (nextEpisode == 258) || (nextEpisode == 10)) {
				validateBool = true;
			}; break;
		case 129:
			if (nextEpisode == 124) {
				validateBool = true;
			}; break;
		case 130:
			if (nextEpisode == 92) {
				items.put("leftStone", "Ляв Камък");
				validateBool = true;
			}; break;
		case 131:
			if (nextEpisode == 72) {
				validateBool = true;
			}; break;
		case 132:
			if (nextEpisode == 140) {
				validateBool = true;
			}; break;
		case 133:
			if (nextEpisode == 27) {
				validateBool = true;
			}; break;
		case 134:
			if (nextEpisode == 34) {
				validateBool = true;
			}; break;
		case 135:
			if ((nextEpisode == 176) || (nextEpisode == 225)) {
				validateBool = true;
			}; break;
		case 137:
			if (nextEpisode == 77) {
				validateBool = true;
			}; break;
		case 138:
			if (nextEpisode == 92) {
				money = money + 4;
				items.put("leftStone", "Ляв Камък");
				validateBool = true;
			}; break;
		case 139:
			if (nextEpisode == 2) {
				validateBool = true;
			}; break;
		case 140:
			if ((nextEpisode == 256) || (nextEpisode == 28)) {
				validateBool = true;
			}; break;
		case 141:
			if (nextEpisode == 201) {
				money--;
				validateBool = true;
			}; break;
		case 142:
			if ((nextEpisode == 166) || (nextEpisode == 101)) {
				validateBool = true;
			}; break;
		case 143:
			if ((nextEpisode == 263) || (nextEpisode == 255) ||(nextEpisode == 246) || (nextEpisode == 237)) {
				validateBool = true;
			}; break;
		case 144:
			if (nextEpisode == 213) {
				validateBool = true;
			}; break;
		case 145:
			if ((nextEpisode == 259) || (nextEpisode == 169)) {
				validateBool = true;
			}; break;
		case 146:
			if (nextEpisode == 84) {
				validateBool = true;
			}; break;
		case 147:
			if (nextEpisode == 51) {
				validateBool = true;
			}; break;
		case 148:
			if (nextEpisode == 140) {
				validateBool = true;
			}; break;
		case 150:
			if (nextEpisode == 83) {
				validateBool = true;
			}; break;
		case 151:
			if ((nextEpisode == 261) || (nextEpisode == 55)) {
				validateBool = true;
			}; break;
		case 152:
			if ((nextEpisode == 23) || (nextEpisode == 141)) {
				validateBool = true;
			}
			else if (nextEpisode == 209) {
				if (magic > 0) {
					validateBool = true;
				}
			}; break;
		case 153:
			if (nextEpisode == 62) {
				if (items.containsKey("northTalisman")) {
					validateBool = true;
				}
			}
			else if (nextEpisode == 204) {
				if (!items.containsKey("northTalisman")) {
					validateBool = true;
				}
			}; break;
		case 154:
			if (nextEpisode == 63) {
				if ((magic > 0) && (animals.containsKey("lion"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 33) {
				if ((magic > 0) && (animals.containsKey("wolf"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 135) {
				validateBool = true;
			}; break;
		case 158:
			if (nextEpisode == 100) {
				if (magic > 0) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 218) {
				validateBool = true;
			}; break;
		case 159:
			if ((nextEpisode == 20) || (nextEpisode == 75) ||(nextEpisode == 54)) {
				validateBool = true;
			}; break;
		case 161:
			if (nextEpisode == 130) {
				if ((magic > 0) && (animals.containsKey("lion"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 80) {
				if ((magic > 0) && (animals.containsKey("wolf"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 22) {
				if ((magic > 0) && (animals.containsKey("eagle") || (animals.containsKey("owl")))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 212) {
				if ((magic > 0) && (animals.containsKey("snake"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 66) {
				validateBool = true;
			}; break;
		case 162:
			if (nextEpisode == 37) {
				if (items.containsKey("rightStone")) {
					validateBool = true;
				}
			}
			else if (nextEpisode == 211) {
				if (!items.containsKey("rightStone")) {
					validateBool = true;
				}
			}; break;
		case 163:
			if ((nextEpisode == 94) || (nextEpisode == 136)) {
				validateBool = true;
			}; break;
		case 164:
			if (nextEpisode == 70) {
				validateBool = true;
			}; break;
		case 166:
			if (nextEpisode == 126) {
				if (money >= 3) {
					items.put("northTalisman", "Северен Талисман");
				}
				validateBool = true;
			}; break;
		case 167:
			if (nextEpisode == 92) {
				validateBool = true;
			}; break;
		case 168:
			if (nextEpisode == 91) {
				if (money >= 5) {
					money = money - 5;
					validateBool = true;
				}
			}
			else if (nextEpisode == 48) {
				validateBool = true;
			}; break;
		case 169:
			if (nextEpisode == 48) {
				if ((magic > 0) && ((animals.containsKey("eagle") || animals.containsKey("wolf") || animals.containsKey("mouse") || animals.containsKey("squirrel") || animals.containsKey("mole")))) {
					validateBool = true;
				}
			}
			else if (nextEpisode == 48) {
				if ((magic == 0) || !((animals.containsKey("eagle") && animals.containsKey("wolf") && animals.containsKey("mouse") && animals.containsKey("squirrel") && animals.containsKey("mole")))) {
					validateBool = true;
				}
			}; break;
		case 170:
			if ((nextEpisode == 116) || (nextEpisode == 59)) {
				validateBool = true;
			}; break;
		case 171:
			if ((nextEpisode == 109) || (nextEpisode == 276)) {
				validateBool = true;
			}; break;
		case 172:
			if (nextEpisode == 92) {
				validateBool = true;
			}; break;
		case 173:
			if (nextEpisode == 27) {
				validateBool = true;
			}; break;
		case 174:
			if (nextEpisode == 245) {
				validateBool = true;
			}; break;
		case 175:
			if (nextEpisode == 104) {
				if (items.containsKey("emptyBottle")) {
					validateBool = true;
				}
			}
			else if (nextEpisode == 44) {
				if (!items.containsKey("emptyBottle")) {
					validateBool = true;
				}
			}; break;
		case 176:
			if (nextEpisode == 221) {
				validateBool = true;
			}; break;
		case 179:
			if ((magic == 0) || (!((animals.containsKey("eagle")) || (animals.containsKey("wolf")) || (animals.containsKey("squrrel"))))) {
				endGame = true;
				playerAlive = false;
			}
			else if (nextEpisode == 148) {
				if ((magic > 0) && (animals.containsKey("wolf"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 64) {
				if ((magic > 0) && (animals.containsKey("eagle"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 210) {
				if ((magic > 0) && (animals.containsKey("squirrel"))) {
					magic--;
					validateBool = true;
				}
			}; break;
		case 180:
			if ((nextEpisode == 143) || (nextEpisode == 241)) {
				validateBool = true;
			}; break;
		case 183:
			if (nextEpisode == 238) {
				validateBool = true;
			}; break;
		case 185:
			if ((nextEpisode == 14) || (nextEpisode == 171)) {
				validateBool = true;
			}; break;
		case 186:
			if (nextEpisode == 158) {
				validateBool = true;
			}; break;
		case 187:
			if (nextEpisode == 145) {
				validateBool = true;
			}; break;
		case 188:
			if (nextEpisode == 70) {
				validateBool = true;
			}; break;
		case 189:
			if (nextEpisode == 174) {
				if ((magic > 0) && (animals.containsKey("eagle") || animals.containsKey("owl"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 223) {
				if ((magic > 0) && (animals.containsKey("lion") || animals.containsKey("wolf"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 242) {
				if (magic > 0) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 97) {
				validateBool = true;
			}; break;
		case 190:
			if ((nextEpisode == 142) || (nextEpisode == 253)) {
				validateBool = true;
			}; break;
		case 192:
			if (nextEpisode == 61) {
				validateBool = true;
			}; break;
		case 194:
			if (nextEpisode == 146) {
				if ((!items.containsKey("middleStone")) && (!items.containsKey("leftStone"))) {
					items.put("rightStone", "Десен Камък");
					validateBool = true;
				}
			}
			else if (nextEpisode == 96) {
				if (items.containsKey("middleStone")) {
					items.put("rightStone", "Десен Камък");
					validateBool = true;
				}
			}; break;
		case 195:
			if (nextEpisode == 267) {
				if ((magic > 0) && (animals.containsKey("mouse") || animals.containsKey("snake"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 106) {
				validateBool = true;
			}; break;
		case 197:
			if ((nextEpisode == 152) || (nextEpisode == 81)) {
				validateBool = true;
			}; break;
		case 198:
			if ((nextEpisode == 184) || (nextEpisode == 13)) {
				validateBool = true;
			}; break;
		case 200:
			if (nextEpisode == 125) {
				money--;
				validateBool = true;
			}; break;
		case 201:
			if ((nextEpisode == 113) || (nextEpisode == 270)) {
				validateBool = true;
			}; break;
		case 202:
			if (nextEpisode == 68) {
				altozarBool = true;
				validateBool = true;
			}; break;
		case 203:
			if ((nextEpisode == 118) || (nextEpisode == 131)) {
				validateBool = true;
			}; break;
		case 204:
			if (nextEpisode == 208) {
				validateBool = true;
			}
			else if (nextEpisode == 89) {
				if ((magic > 0) && (animals.containsKey("eagle") || animals.containsKey("owl"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 15) {
				validateBool = true;
			}; break;
		case 206:
			if ((nextEpisode == 24) || (nextEpisode == 193) || (nextEpisode == 128)) {
				validateBool = true;
			}; break;
		case 207:
			if (nextEpisode == 12) {
				validateBool = true;
			}; break;
		case 208:
			if ((nextEpisode == 121) || (nextEpisode == 73)) {
				validateBool = true;
			}; break;
		case 209:
			if (nextEpisode == 206) {
				magic--;
				validateBool = true;
			}; break;
		case 210:
			if (nextEpisode == 145) {
				validateBool = true;
			}; break;
		case 211:
			if (nextEpisode == 168) {
				money--;
				validateBool = true;
			}; break;
		case 213:
			if ((nextEpisode == 38) || (nextEpisode == 199)) {
				validateBool = true;
			}; break;
		case 214:
			if (nextEpisode == 198) {
				validateBool = true;
			}; break;
		case 215:
			if (nextEpisode == 129) {
				validateBool = true;
			}; break;
		case 216:
			if (nextEpisode == 188) {
				if ((magic > 0) && (animals.containsKey("lion"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 164) {
				if ((magic > 0) && (animals.containsKey("snake"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 70) {
				if ((magic > 0) && (animals.containsKey("eagle"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 45) {
				if ((magic > 0) && (animals.containsKey("wolf"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 11) {
				validateBool = true;
			}; break;
		case 217:
			if (nextEpisode == 145) {
				validateBool = true;
			}; break;
		case 218:
			if ((nextEpisode == 8) || (nextEpisode == 74)) {
				validateBool = true;
			}; break;
		case 219:
			if (nextEpisode == 125) {
				validateBool = true;
			}; break;
		case 220:
			if (nextEpisode == 238) {
				validateBool = true;
			}; break;
		case 221:
			if (nextEpisode == 231) {
				validateBool = true;
			}; break;
		case 223:
			if (nextEpisode == 174) {
				validateBool = true;
			}; break;
		case 224:
			if ((nextEpisode == 234) || (nextEpisode == 103)) {
				validateBool = true;
			}; break;
		case 226:
			if (nextEpisode == 3) {
				validateBool = true;
			}; break;
		case 227:
			if (nextEpisode == 183) {
				validateBool = true;
			}; break;
		case 229:
			if ((nextEpisode == 157) || (nextEpisode == 235)) {
				validateBool = true;
			}; break;
		case 231:
			if (!(items.containsKey("leftStone") && items.containsKey("middleStone")) || !(items.containsKey("leftStone") && items.containsKey("rightStone")) || !(items.containsKey("middleStone") && items.containsKey("rightStone"))) {
				endGame = true;
				playerAlive = false;
			}
			else if (nextEpisode == 232) {
				if (items.containsKey("leftStone") && items.containsKey("middleStone") && items.containsKey("rightStone")) {
					validateBool = true;
				}
			}
			else if (nextEpisode == 37) {
				if ((items.containsKey("leftStone") && items.containsKey("middleStone")) || (items.containsKey("leftStone") && items.containsKey("rightStone")) || (items.containsKey("middleStone") && items.containsKey("rightStone"))) {
					validateBool = true;
				}
			}; break;
		case 232:
			if (nextEpisode == 277) {
				if (items.containsKey("fullBottle")) {
					items.remove("fullBottle");
					validateBool = true;
				}
			}
			else if (nextEpisode == 257) {
				validateBool = true;
			}; break;
		case 233:
			if ((nextEpisode == 6) || (nextEpisode == 238)) {
				validateBool = true;
			}; break;
		case 234:
			if ((nextEpisode == 250) || (nextEpisode == 112)) {
				validateBool = true;
			}; break;
		case 235:
			if (nextEpisode == 254) {
				validateBool = true;
			}; break;
		case 236:
			if (nextEpisode == 174) {
				if ((magic > 0) && (animals.containsKey("eagle") || animals.containsKey("owl"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 223) {
				if ((magic > 0) && (animals.containsKey("lion") || animals.containsKey("wolf"))) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 70) {
				if (magic > 0) {
					magic--;
					validateBool = true;
				}
			}
			else if (nextEpisode == 97) {
				validateBool = true;
			}
			else if (nextEpisode == 189) {
				validateBool = true;
			}; break;
		case 237:
			if ((nextEpisode == 263) || (nextEpisode == 255) || (nextEpisode == 246)) {
				validateBool = true;
			}; break;
		case 238:
			if (nextEpisode == 174) {
				magic--;
				validateBool = true;
			}; break;
		case 242:
			if (nextEpisode == 174) {
				validateBool = true;
			}; break;
		case 243:
			if (nextEpisode == 206) {
				magic--;
				validateBool = true;
			}; break;
		case 245:
			if ((nextEpisode == 230) || (nextEpisode == 224)) {
				validateBool = true;
			}; break;
		case 246:
			if ((nextEpisode == 179) || (nextEpisode == 82)) {
				validateBool = true;
			}; break;
		case 247:
			if ((nextEpisode == 240) || (nextEpisode == 251) || (nextEpisode == 262)) {
				validateBool = true;
			}; break;
		case 248:
			if (nextEpisode == 30) {
				validateBool = true;
			}; break;
		case 250:
			if (nextEpisode == 274) {
				if (items.containsKey("fullBottle")) {
					items.remove("fullBottle");
					validateBool = true;
				}
			}
			else if (nextEpisode == 229) {
				validateBool = true;
			}; break;
		case 252:
			if (nextEpisode == 147) {
				items.remove("northTalisman");
				money--;
				validateBool = true;
			}; break;
		case 253:
			if ((nextEpisode == 142) || (nextEpisode == 265) || (nextEpisode == 120)) {
				validateBool = true;
			}; break;
		case 254:
			if (nextEpisode == 232) {
				validateBool = true;
			}; break;
		case 255:
			if ((nextEpisode == 263) || (nextEpisode == 246) || (nextEpisode == 237)) {
				validateBool = true;
			}; break;
		case 257:
			if ((nextEpisode == 240) || (nextEpisode == 247) || (nextEpisode == 262) || (nextEpisode == 251)) {
				validateBool = true;
			}; break;
		case 258:
			if (nextEpisode == 239) {
				validateBool = true;
			}; break;
		case 261:
			if (nextEpisode == 55) {
				magic--;
				validateBool = true;
			}; break;
		case 262:
			if (nextEpisode == 264) {
				validateBool = true;
			}; break;
		case 263:
			if ((nextEpisode == 255) || (nextEpisode == 246) || (nextEpisode == 237)) {
				validateBool = true;
			}; break;
		case 264:
			if (nextEpisode == 280) {
				validateBool = true;
			}; break;
		case 265:
			if ((nextEpisode == 279) || (nextEpisode == 110)) {
				validateBool = true;
			}; break;
		case 266:
			if (nextEpisode == 114) {
				validateBool = true;
			}; break;
		case 267:
			if (nextEpisode == 216) {
				validateBool = true;
			}; break;
		case 268:
			if (nextEpisode == 126) {
				validateBool = true;
			}; break;
		case 269:
			if (nextEpisode == 84) {
				validateBool = true;
			}; break;
		case 271:
			if (nextEpisode == 120) {
				validateBool = true;
			}; break;
		case 272:
			if (nextEpisode == 194) {
				magic = magic - 2;
				validateBool = true;
			}; break;
		case 273:
			if (nextEpisode == 114) {
				validateBool = true;
			}; break;
		case 274:
			if ((nextEpisode == 157) || (nextEpisode == 235)) {
				validateBool = true;
			}; break;
		case 276:
			if (nextEpisode == 129) {
				validateBool = true;
			}; break;
		case 277:
			if (nextEpisode == 262) {
				validateBool = true;
			}; break;
		case 278:
			if ((nextEpisode == 7) || (nextEpisode == 39)) {
				validateBool = true;
			}; break;
		case 279:
			if ((nextEpisode == 271) || (nextEpisode == 115)) {
				validateBool = true;
			}; break;
		case 280:
			endGame = true;
		}
		return validateBool;
	}

	private static void checkStatus(int currentEpisode) { //check if you win or die
		switch (currentEpisode) {
		case 25:
		case 29:
		case 31:
		case 32:
		case 49:
		case 66:
		case 70:
		case 93:
		case 97:
		case 103:
		case 109:
		case 114:
		case 116:
		case 118:
		case 136:
		case 149:
		case 155:
		case 156:
		case 157:
		case 160:
		case 165:
		case 177:
		case 178:
		case 181:
		case 182:
		case 184:
		case 191:
		case 193:
		case 196:
		case 199:
		case 205:
		case 212:
		case 222:
		case 225:
		case 228:
		case 230:
		case 239:
		case 240:
		case 241:
		case 244:
		case 249:
		case 251:
		case 256:
		case 259:
		case 260:
		case 270:
		case 275: endGame = true; playerAlive = false; break;
		case 280: endGame = true;
		}
		//25, 29, 31, 32, 49, 66, 70, 93, 97, 103, 105, 109, 114, 116, 118, 136, 149, 155, 156, 157, 160, 165, 177, 178
		//181, 182, 184, 191, 193, 196, 199, 205, 212, 222, 225, 228, 230, 239, 240, 241, 244, 249, 251, 256, 259, 260, 270, 275
		//set endGame = true if episode ends the game, set playerAlive = false if episode ends with loss
	}

	private static void showInventory() {
		System.out.println("----- Инвентар -----");
		System.out.print("Форми: ");
		for (Map.Entry<String, String> entry : animals.entrySet()) {
			System.out.print(entry.getValue() + " ");
		}
		System.out.println();
		System.out.println("Магическа Сила: " + magic);
		System.out.println("Пари: " + money);
		System.out.print("Предмети: ");
		for (Map.Entry<String, String> entry : items.entrySet()) {
			System.out.print(entry.getValue() + " ");
		}
		System.out.println();
		System.out.println(delimiterStr);
		System.out.println();
	}

	private static void chooseAnimals() {
		System.out.println("Избери трите животни, в които ще можеш да се превръщаш!");
		System.out.println("Въведи цифрата срещу желаното животно: ");
		System.out.println("1 - Лъв");
		System.out.println("2 - Вълк");
		System.out.println("3 - Змия");
		System.out.println("4 - Катерица");
		System.out.println("5 - Видра");
		System.out.println("6 - Орел");
		System.out.println("7 - Бухал");
		System.out.println("8 - Мишка");
		System.out.println("9 - Къртица");
		System.out.println();
		
		int continueInt = 1;
		int animal1 = 0;
		int animal2 = 0;
		int animal3 = 0;
		Scanner inputAnimal = new Scanner(System.in);
		
		do {
			do {
				System.out.print("Първо Животно:");
				animal1 = inputAnimal.nextInt();
				System.out.println();
			} while (animal1 < 0 || animal1 > 9);
			switch (animal1) {
			case 1: animals.put("lion", "Лъв"); break;
			case 2: animals.put("wolf", "Вълк"); break;
			case 3: animals.put("snake", "Змия"); break;
			case 4: animals.put("squirrel", "Катерица"); break;
			case 5: animals.put("otter", "Видра"); break;
			case 6: animals.put("eagle", "Орел"); break;
			case 7: animals.put("owl", "Бухал"); break;
			case 8: animals.put("mouse", "Мишка"); break;
			case 9: animals.put("mole", "Къртица"); break;
			}
			do {
				System.out.print("Второ Животно: ");
				animal2 = inputAnimal.nextInt();
				System.out.println();
			} while ((animal2 < 0 || animal2 > 9) || animal2 == animal1);
			switch (animal2) {
			case 1: animals.put("lion", "Лъв"); break;
			case 2: animals.put("wolf", "Вълк"); break;
			case 3: animals.put("snake", "Змия"); break;
			case 4: animals.put("squirrel", "Катерица"); break;
			case 5: animals.put("otter", "Видра"); break;
			case 6: animals.put("eagle", "Орел"); break;
			case 7: animals.put("owl", "Бухал"); break;
			case 8: animals.put("mouse", "Мишка"); break;
			case 9: animals.put("mole", "Къртица"); break;
			}
			do {
				System.out.print("Трето Животно: ");
				animal3 = inputAnimal.nextInt();
				System.out.println();
			} while ((animal3 < 0 || animal3 > 9) || animal3 == animal1 || animal3 == animal2);
			switch (animal3) {
			case 1: animals.put("lion", "Лъв"); break;
			case 2: animals.put("wolf", "Вълк"); break;
			case 3: animals.put("snake", "Змия"); break;
			case 4: animals.put("squirrel", "Катерица"); break;
			case 5: animals.put("otter", "Видра"); break;
			case 6: animals.put("eagle", "Орел"); break;
			case 7: animals.put("owl", "Бухал"); break;
			case 8: animals.put("mouse", "Мишка"); break;
			case 9: animals.put("mole", "Къртица"); break;
			}
			
			System.out.println("Това ще бъдат животните, в които ще можеш да се превръщаш в хода на цялото приключение. Няма да можеш да ги промениш повече! Сигурен ли си, че искаш да владееш точно тези форми? (1 = да)");
			continueInt = inputAnimal.nextInt();
		} while (continueInt != 1);
		System.out.println(delimiterStr);
		System.out.println();
	}

	private static void showEpisode(int episode) {
		String fileName = episode + ".txt";
        String line = null; // This will reference one line at a time
		if (!(episode == 500 || episode == 501 || episode == 502)) {
		        System.out.println("*** " + episode + " ***");
				System.out.println();
		}
        try {
            FileReader fileReader = new FileReader(fileName); // FileReader reads text files in the default encoding

            BufferedReader bufferedReader = new BufferedReader(fileReader); // Always wrap FileReader in BufferedReader.

            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println(delimiterStr);
			System.out.println();
            bufferedReader.close(); // Always close files.
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + fileName + "'");				
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");					
            // Or we could just do this: 
            // ex.printStackTrace();
        }
	}

	private static void loadGame() {
		System.out.println("Зареждане на запаметената игра!");
		playerAlive = true;
		endGame = false;
		currentEpisode = currentEpisode_Saved;
		animals = animals_Saved;
		magic = magic_Saved;
		items = items_Saved;
		money = money_Saved;
		altozarBool = altozarBool_Saved;
	}

	private static void showHowToPlay() {
		System.out.println("Как се управлява играта?");
		System.out.println();
		System.out.println("Въведете следните команди и натиснете Enter, за да извършвате действия в играта:");
		System.out.println("Числата от 1 до 280 - прехвърля ви на избрания епизод, стига да са изпълнени условията нужни за това;");
		System.out.println("1000 = exit - спира играта;");
		//System.out.println("save - запазва играта: текущ епизод, форми, магия, пари и предмети;"); //only autosaving
		System.out.println("2000 = load - зарежда запазената игра;");
		System.out.println("3000 = help - показва тези команди;");
		System.out.println(delimiterStr);
		System.out.println();
	}
}