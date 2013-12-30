package net.krglok.realms.data;

import java.util.HashMap;

import net.krglok.realms.core.Bank;
import net.krglok.realms.core.Barrack;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.BuildingList;
import net.krglok.realms.core.BuildingType;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.MemberLevel;
import net.krglok.realms.core.MemberList;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.Position;
import net.krglok.realms.core.Realm;
import net.krglok.realms.core.RealmList;
import net.krglok.realms.core.RealmModel;
import net.krglok.realms.core.Resident;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.core.Townhall;
import net.krglok.realms.core.Warehouse;

/**
 * Simuliert die eingelesenen des Realms
 * im der live umgebung werden die daten aus einer Datenbank (YML) gelesen
 * @author Windu
 *
 */
public class DataTest implements DataInterface
{
	private static final String NPC_0 = "NPC0";
	private static final String NPC_1 = "NPC1";
	private static final String NPC_2 = "NPC2";
	private static final String NPC_4 = "NPC4";
	private static final String PC_3 = "NPC3";
	private static final String PC_4 = "NPC4";
	private static final String PC_5 = "NPC5";
	private static final String Realm_1_NPC = "Realm 1 NPC";
	
	private OwnerList testOwners ;
	private RealmList testRealms ;
	private SettlementList testSettlements;
	private BuildingList testBuildings; 
	
	public DataTest()
	{
		initTestData();
	}

	public void initTestData()
	{
		initOwnerList();
		initRealmList();
		initBuildingList();
		initSettlementList ();
		
	}
	
	/**
	 * erzeugt testdaten mit 6 Owner,  3 NPC und 3 PC
	 * @return
	 */
	private void initOwnerList()
	{
		testOwners = new OwnerList();
		testOwners.addOwner(new Owner(0, MemberLevel.MEMBER_NONE, 0, NPC_0, 1, true));
		testOwners.addOwner(new Owner(1, MemberLevel.MEMBER_NONE, 0, NPC_1, 0, true));
		testOwners.addOwner(new Owner(2, MemberLevel.MEMBER_NONE, 0, NPC_2, 0, true));
		testOwners.addOwner(new Owner(3, MemberLevel.MEMBER_NONE, 0, PC_3, 0, false));
		testOwners.addOwner(new Owner(4, MemberLevel.MEMBER_NONE, 0, PC_4, 0, false));
		testOwners.addOwner(new Owner(5, MemberLevel.MEMBER_NONE, 0, PC_5, 0, false));
		
	}
	

	/**
	 * Erzeugt testdaten fuer eine Realm List mit
	 * - NPC Realm, NPC Owner , nur ein Member
	 * @return
	 */
	private void initRealmList()
	{
		testRealms = new RealmList();
		Owner owner ;
		if (testOwners == null)
		{
			owner = new Owner(6, MemberLevel.MEMBER_NONE, 0, "NPC4", 0, true);
		} else
		{
			owner = testOwners.getOwner(NPC_0);
		}
		testRealms.addRealm(new Realm(1, "Realm 1 NPC", owner, new MemberList(), true));
	}
	
	/**
	 * erzeugt testdaten fuer eine SettlementList mit 
	 * - 1 Settlement 
	 * - buildingList fuer Settlement   
	 */
	private void initSettlementList ()
	{
		testSettlements = new SettlementList(0);
		testSettlements.addSettlement(createSettlement(1));
	}

	private Settlement createSettlement(int id)
	{
		
		Position position = new Position(0.0, 0.0, 0.0);
		Owner owner = testOwners.getOwner(NPC_0);
		Barrack barrack = new Barrack(5);
		Warehouse warehouse = new Warehouse(1000);
		warehouse.setItemList(defaultWarehouseItems());
		BuildingList buildingList = new BuildingList(); 
		Townhall townhall = new Townhall(true);
		Bank bank = new Bank();
		Resident resident = new Resident();

		Settlement settle =  new Settlement(
				id, 
				SettleType.SETTLE_HAMLET, 
				"TestSiedlung", 
				position, 
				owner,
				true, 
				barrack, 
				warehouse,
				buildingList, 
				townhall, 
				bank,
				resident);
		
		for (Building b : testBuildings.getBuildingList().values())
		{
			Settlement.addBuilding(b, settle);
		}
		
		return settle;
	}
	
	/**
	 * create a test Home, id and settler are configurable
	 * @param id
	 * @param residentHome
	 * @return configured building
	 */
	public Building createBuildingHome(int id, int settler, int regionId)
	{
		return  new	Building(
						id, 
						BuildingType.BUILDING_HOME, 
						settler, 
						0, 
						0, 
						true, 
						regionId,
						"haus_einfach",
						"", 
						true
						);
		
	}
	
	/**
	 * create a test Production, id and workerNeeded are configurable 
	 * @param id
	 * @param workerNeeded
	 * @return configured building
	 */
	public Building createBuildingKornfeld(int id, int workerNeeded, int regionId)
	{
		return  new	Building(
						id, 
						BuildingType.BUILDING_PROD, 
						0,
						workerNeeded,
						0, 
						true, 
						regionId, 
						"kornfeld",
						"", 
						true
						);
		
	}

	public Building createBuildingHolzfaeller(int id, int workerNeeded, int regionId)
	{
		return  new	Building(
						id, 
						BuildingType.BUILDING_PROD, 
						0,
						workerNeeded,
						0, 
						true, 
						regionId, 
						"holzfaeller",
						"", 
						true
						);
		
	}
	
	/**
	 * create a test townhall, id and settler are configurable
	 * @param id
	 * @param residentHome
	 * @return configured building
	 */
	
	public Building createBuildingHall(int id, int settler, int regionId)
	{
		return  new	Building(
						id, 
						BuildingType.BUILDING_HALL, 
						settler,
						0,
						0, 
						true, 
						regionId, 
						"haupthaus",
						"", 
						true
						);
		
	}

	public Building createBuildingTaverne(int id, int workerNeeded, int regionId)
	{
		return  new	Building(
						id, 
						BuildingType.BUILDING_ENTERTAIN, 
						0,
						workerNeeded,
						0, 
						true, 
						regionId, 
						"taverne",
						"", 
						true
						);
		
	}
	
	/**
	 * create a test millitary building , id and unitSpace are configurable
	 * 
	 * @param id
	 * @param unitSpace
	 * @return configured building
	 */
	public Building createBuildingMilitary(int id, int unitSpace, int regionId)
	{
		return  new	Building(
						id, 
						BuildingType.BUILDING_MILITARY, 
						unitSpace,
						0,
						0, 
						true, 
						regionId, 
						"stadtwache",
						"", 
						true
						);
		
	}

	/**
	 * create a test warehouse building , id and workerNeeded are configurable
	 * the itemMax are calculated = workerNeeded * factor
	 * @param id
	 * @param workerNeeded
	 * @return configured building
	 */
	public Building createBuildingWarehouse(int id, int workerNeeded, int regionId)
	{
		return  new	Building(
						id, 
						BuildingType.BUILDING_WAREHOUSE, 
						0,
						workerNeeded,
						0, 
						true, 
						regionId, 
						"markt",
						"", 
						true
						);
		
	}

	public Building createBuildingBauernhof(int id, int settler, int workerNeeded, int regionId)
	{
		return  new	Building(
						id, 
						BuildingType.BUILDING_BAUERNHOF, 
						settler,
						workerNeeded,
						0, 
						true, 
						regionId, 
						"",
						"", 
						true
						);
		
	}

	public Building createBuildingWerkstatt(int id, int settler, int workerNeeded, int regionId, 
			String slot1, String slot2, String slot3, String slot4, String slot5)
	{
		return  new	Building(
						id, 
						BuildingType.BUILDING_WERKSTATT, 
						settler,
						workerNeeded,
						0, 
						true, 
						regionId, 
						"",
						"", 
						true,
						slot1,
						slot2,
						slot3,
						slot4,
						slot5
						);
		
	}
	
	public Building createBuildingBaecker(int id, int settler, int workerNeeded, int regionId, 
			String slot1, String slot2, String slot3, String slot4, String slot5)
	{
		return  new	Building(
						id, 
						BuildingType.BUILDING_BAECKER, 
						settler,
						workerNeeded,
						0, 
						true, 
						regionId, 
						"",
						"", 
						true,
						slot1,
						slot2,
						slot3,
						slot4,
						slot5
						);
		
	}
	
	/**
	 * create a test trade building , id , settler and workerNeeded are configurable
	 * the itemMax are calculated = workerNeeded * factor
	 * the tradelineMax are calculated = settler * factor 
	 * @param id
	 * @param workerNeeded
	 * @return configured building
	 */
	public Building createBuildingTrader(int id, int settler, int workerNeeded, int regionId)
	{
		return  new	Building(
						id, 
						BuildingType.BUILDING_TRADER, 
						settler,
						workerNeeded,
						0, 
						true, 
						regionId, 
						"handelshaus",
						"", 
						true
						);
		
	}
	
	
	private void initBuildingList()
	{
		testBuildings = new BuildingList();
		testBuildings.addBuilding(createBuildingHome(2, 2,2));
		testBuildings.addBuilding(createBuildingHome(6, 2,6));
		testBuildings.addBuilding(createBuildingHome(7, 2,7));
		testBuildings.addBuilding(createBuildingTaverne(8, 2,8));
		testBuildings.addBuilding(createBuildingWarehouse(9, 2, 9));
		testBuildings.addBuilding(createBuildingHome(10, 2,10));
		testBuildings.addBuilding(createBuildingHome(11, 2,11));
		testBuildings.addBuilding(createBuildingHome(12, 2,12));
		testBuildings.addBuilding(createBuildingHome(13, 2,13));
		testBuildings.addBuilding(createBuildingHome(14, 2,14));
		testBuildings.addBuilding(createBuildingKornfeld(16, 2, 16));
		testBuildings.addBuilding(createBuildingKornfeld(18, 2, 18));
		testBuildings.addBuilding(createBuildingHome(28, 2,28));
		testBuildings.addBuilding(createBuildingHome(29, 2,29));
		testBuildings.addBuilding(createBuildingHome(30, 2,20));
		testBuildings.addBuilding(createBuildingBauernhof(31, 6, 5, 31));
		testBuildings.addBuilding(createBuildingBauernhof(32, 6, 5, 32));
		testBuildings.addBuilding(createBuildingBauernhof(33, 6, 5, 33));
		testBuildings.addBuilding(createBuildingHall(34, 2, 34));
		testBuildings.addBuilding(createBuildingWerkstatt(41, 6, 5, 41, "FENCE", "STICK", "WOOD", "", ""));
		testBuildings.addBuilding(createBuildingWerkstatt(42, 6, 5, 42, "IRON_SWORD", "BOW", "ARROW", "ARROW", "ARROW"));
		testBuildings.addBuilding(createBuildingWerkstatt(43, 6, 5, 42, "IRON_HELMET", "IRON_CHESTPLATE", "IRON_LEGGINGS", "IRON_BOOTS", ""));
		testBuildings.addBuilding(createBuildingWerkstatt(51, 6, 5, 51, "BREAD", "BREAD", "BREAD", "BREAD", "BREAD"));
		testBuildings.addBuilding(createBuildingWerkstatt(52, 6, 5, 52, "BREAD", "BREAD", "BREAD", "BREAD", "BREAD"));
		testBuildings.addBuilding(createBuildingWerkstatt(53, 6, 5, 53, "BREAD", "BREAD", "BREAD", "BREAD", "BREAD"));
	}
	
	

	/**
	 * 
	 * @return default list of items
	 */
	public ItemList defaultWarehouseItems()
	{
		ItemList subList = new ItemList();
		subList.addItem("BUCKET",0);
		subList.addItem("COAL",0);
		subList.addItem("COBBLESTONE",0);
		subList.addItem("COBBLESTONE_STAIRS",0);
		subList.addItem("FEATHER",0);
		subList.addItem("FENCE",0);
		subList.addItem("FENCE_GATE",0);
		subList.addItem("FLINT",0);
		subList.addItem("FURNACE",0);
		subList.addItem("GRAVEL",0);
		subList.addItem("LADDER",0);
		subList.addItem("LEATHER",0);
		subList.addItem("LOG",0);
		subList.addItem("SADDLE",0);
		subList.addItem("SAND",0);
		subList.addItem("SANDSTONE",0);
		subList.addItem("SAPLING",0);
		subList.addItem("STICK",0);
		subList.addItem("STONE",0);
		subList.addItem("STRING",0);
		subList.addItem("TORCH",0);
		subList.addItem("WALL_SIGN",0);
		subList.addItem("WATER_BUCKET",0);
		subList.addItem("WOOD",0);
		subList.addItem("WOODEN_DOOR",0); // (Block only)
		subList.addItem("WOOL",0);
		subList.addItem("WORKBENCH",0);

		return subList;
	}
	
	/**
	 * 
	 * @return list of default Food Items
	 */
	public ItemList defaultFoodItems()
	{
		ItemList subList = new ItemList();
		subList.addItem("BREAD",0);
		subList.addItem("CAKE",0);
		subList.addItem("COOKED_BEEF",0);
		subList.addItem("COOKED_CHICKEN",0);
		subList.addItem("COOKED_FISH",0);
		subList.addItem("COOKIE",0);
		subList.addItem("CROPS",0);
		subList.addItem("EGG",0);
		subList.addItem("GRILLED_PORK",0);
		subList.addItem("MELON",0);
		subList.addItem("MELON_SEEDS",0);
		subList.addItem("MILK_BUCKET",0);
		subList.addItem("PORK",0);
		subList.addItem("PUMPKIN",0);
		subList.addItem("PUMPKIN_SEEDS",0);
		subList.addItem("RAW_BEEF",0);
		subList.addItem("RAW_CHICKEN",0);
		subList.addItem("RAW_FISH",0);
		subList.addItem("SEEDS",0);
		subList.addItem("SUGAR",0);
		subList.addItem("SUGAR_CANE",0);
		subList.addItem("WHEAT",0);

		return subList;
	}

	/**
	 * prerun initOwnerList, initRealmList, defaultFoodList, defaultWarehouseItems, 
	 *  	  initTool, initArmor, initWeapon
	 * default Plot 
	 * - with out buildings
	 * - with NPC owner
	 * - with default warehouse
	 * @return a configured Settlement of a default Plot 
	 */
	public Settlement defaultPlot()
	{
		Settlement plot = new Settlement();
		
		plot.setOwner(testOwners.getOwner(NPC_0));
		plot.getWarehouse().setItemList(defaultWarehouseItems());
		return plot;
	}

	
//	public HashMap<String,String> setSiedlungAtPoition(Position pos)
//	{
//		HashMap<String,String> testData = new HashMap<String,String>();
//		HashMap<String,String> regionTypes = new HashMap<String,String>();
//		regionTypes.put("1","haupthaus");
//		regionTypes.put("2","haus_einfach");
//		regionTypes.put("3","haus_einfach");
//		regionTypes.put("4","haus_einfach");
//		regionTypes.put("5","taverne");
//		regionTypes.put("NewHaven","Siedlung");
//				
//		//this.strongholdAPI.getRegionAtPotion(pos, testData);
//		return testData;
//	}
	
	public HashMap<String,String> defaultRegionList()
	{
		HashMap<String,String> regionTypes = new HashMap<String,String>();
		regionTypes.put("1","haupthaus");
		regionTypes.put("2","haus_einfach");
		regionTypes.put("6","haus_einfach");
		regionTypes.put("7","haus_einfach");
		regionTypes.put("16","kornfeld");
		regionTypes.put("9","markt");
				
		//this.strongholdAPI.getRegionAtPotion(pos, testData);
		return regionTypes;
	}
	
	public HashMap<String,String> defaultSuperregionList()
	{
		HashMap<String,String> regionTypes = new HashMap<String,String>();
		regionTypes.put("NewHaven","Siedlung");
				
		//this.strongholdAPI.getRegionAtPotion(pos, testData);
		return regionTypes;
	}

	@Override
	public void initOwners(RealmModel realmModel)
	{
	
		realmModel.setOwners(testOwners);
	}

	@Override
	public void initRealms(RealmModel realmModel)
	{
		realmModel.setRealms(testRealms);
		
	}

	@Override
	public void initSettlements(RealmModel realmModel)
	{
		realmModel.setSettlements(testSettlements);
	}

	public OwnerList getTestOwners()
	{
		return testOwners;
	}

	public RealmList getTestRealms()
	{
		return testRealms;
	}

	public SettlementList getTestSettlements()
	{
		return testSettlements;
	}
	
}
