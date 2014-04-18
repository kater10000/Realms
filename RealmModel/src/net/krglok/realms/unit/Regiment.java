package net.krglok.realms.unit;

import java.util.ArrayList;

import net.krglok.realms.colonist.Colony;
import net.krglok.realms.core.Bank;
import net.krglok.realms.core.Barrack;
import net.krglok.realms.core.BoardItemList;
import net.krglok.realms.core.BuildingList;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Resident;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Townhall;
import net.krglok.realms.core.Trader;
import net.krglok.realms.core.Warehouse;
import net.krglok.realms.data.LogList;
import net.krglok.realms.data.ServerInterface;
import net.krglok.realms.manager.BuildManager;
import net.krglok.realms.manager.MapManager;
import net.krglok.realms.manager.SettleManager;
import net.krglok.realms.manager.TradeManager;
import net.krglok.realms.model.RealmModel;

import org.bukkit.block.Biome;

/**
 * Das Privateer hat folgende Eigenschaften
  Einen Namen
  Eine aktuelle Position
  Einen Status
  Eine aktuelle Aktion, die es ausf�hrt
  Power, die Macht der Einheiten, die es umfa�t
  Liste der Einheiten nach Typen unterteilt.
o Militia
o Archer
o Settler
 * @author Windu
 *
 */
public class Regiment {
	private static final int REGIMENT_ITEM_MAX = 10 * ConfigBasis.CHEST_STORE;

	private static int lfdID = 0;
	
	private int id;
	private RegimentType regimentType = RegimentType.PRIVATEER;
	private RegimentStatus regStatus = RegimentStatus.NONE;
	private LocationData position;
	private String name;
	private String owner;
	private Barrack barrack ;
	private Warehouse warehouse ;
	private Bank bank;
	private ItemList requiredProduction;
	
	private Boolean isEnabled;
	private Boolean isActive;
	
	private double hungerCounter = 0.0;
	private double foodConsumCounter = 0.0;
	
	private BoardItemList battleOverview;

	private double FoodFactor = 0.0;

	private String world;
	private Biome biome;
	private long age;
	private Colony colonist;
	private BattleSetup battle;
	private Unit commander = null;
	
	private int settleId = -1;
	
	
	public Regiment(LogList logList) 
	{
		super();
		lfdID++;
		this.id			= lfdID;
		this.age         = 0;
		this.position 	= new LocationData("", 0.0, 0.0, 0.0);
		this.world 		= "";
		this.biome		= null;
		this.name		= "Regiment";
		this.owner		= "NPC";
		this.barrack	= new Barrack(60);
		this.warehouse		= new Warehouse(REGIMENT_ITEM_MAX);
		this.isEnabled  = true;
		this.isActive   = true;
		this.battleOverview = new BoardItemList();
		this.colonist =  Colony.newCamp(this.name, this.owner, logList);
		this.battle 	= new BattleSetup();

	}

	public static Regiment makeRaider(LogList logList)
	{
		Regiment regiment = new Regiment(logList);
		UnitList militiaList = makeMilitia(10);
		regiment.getBarrack().addUnitList(militiaList);
		UnitList settlerList = makeSettler(10);
		regiment.getBarrack().addUnitList(settlerList);
		regiment.name		= "Privateer";
		regiment.owner		= "Raider";
		regiment.commander 	= new Unit(UnitType.COMMANDER);
		
		return regiment;
	}
	
	private static UnitList makeMilitia(int amount)
	{
		UnitFactory unitFactory = new UnitFactory();
		UnitList unitList = new UnitList();
		for (int i=0; i < amount; i++)
		{
			unitList.add(unitFactory.erzeugeUnit(UnitType.MILITIA));
		}
		return unitList;
	}

	private static UnitList makeArcher(int amount)
	{
		UnitFactory unitFactory = new UnitFactory();
		UnitList unitList = new UnitList();
		for (int i=0; i < amount; i++)
		{
			unitList.add(unitFactory.erzeugeUnit(UnitType.ARCHER));
		}
		return unitList;
	}

	private static UnitList makeSettler(int amount)
	{
		UnitFactory unitFactory = new UnitFactory();
		UnitList unitList = new UnitList();
		for (int i=0; i < amount; i++)
		{
			unitList.add(unitFactory.erzeugeUnit(UnitType.SETTLER));
		}
		return unitList;
	}

	public static int getLfdID() {
		return lfdID;
	}



	public static void initLfdID(int lfdID) {
		Regiment.lfdID = lfdID;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public RegimentType getRegimentType() {
		return regimentType;
	}



	public void setRegimentType(RegimentType regimentType) {
		this.regimentType = regimentType;
	}



	public RegimentStatus getRegStatus() {
		return regStatus;
	}



	public void setRegStatus(RegimentStatus regStatus) {
		this.regStatus = regStatus;
	}



	public LocationData getPosition() {
		return position;
	}



	public void setPosition(LocationData position) {
		this.position = position;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
		this.colonist.setName(name);
	}



	public String getOwner() {
		return owner;
	}



	public void setOwner(String owner) {
		this.owner = owner;
		this.colonist.setOwner(owner);
	}



	public Barrack getBarrack() {
		return barrack;
	}



	public void setBarrack(Barrack barrack) {
		this.barrack = barrack;
	}



	public Warehouse getWarehouse() {
		return warehouse;
	}



	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}



	public Bank getBank() {
		return bank;
	}



	public void setBank(Bank bank) {
		this.bank = bank;
	}



	public ItemList getRequiredProduction() {
		return requiredProduction;
	}



	public void setRequiredProduction(ItemList requiredProduction) {
		this.requiredProduction = requiredProduction;
	}



	public Boolean getIsEnabled() {
		return isEnabled;
	}



	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}



	public Boolean getIsActive() {
		return isActive;
	}



	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}



	public double getHungerCounter() {
		return hungerCounter;
	}



	public void setHungerCounter(double hungerCounter) {
		this.hungerCounter = hungerCounter;
	}



	public double getFoodConsumCounter() {
		return foodConsumCounter;
	}



	public void setFoodConsumCounter(double foodConsumCounter) {
		this.foodConsumCounter = foodConsumCounter;
	}



	public BoardItemList getBattleOverview() {
		return battleOverview;
	}



	public void setBattleOverview(BoardItemList battleOverview) {
		this.battleOverview = battleOverview;
	}



	public double getFoodFactor() {
		return FoodFactor;
	}



	public void setFoodFactor(double foodFactor) {
		FoodFactor = foodFactor;
	}



	public String getWorld() {
		return world;
	}



	public void setWorld(String world) {
		this.world = world;
	}



	public Biome getBiome() {
		return biome;
	}



	public void setBiome(Biome biome) {
		this.biome = biome;
	}



	public long getAge() {
		return age;
	}



	public void setAge(long age) {
		this.age = age;
	}



	public Colony getColonist() {
		return colonist;
	}



	public BattleSetup getBattle() {
		return battle;
	}



	public void setBattle(BattleSetup battle) {
		this.battle = battle;
	}



	public static int getRegimentItemMax() {
		return REGIMENT_ITEM_MAX;
	}

	public void doProduce(RealmModel rModel)
	{
		
	}
	
	public void run(RealmModel rModel)
	{
		switch (regStatus)
		{
		case NONE :
			
			break;
		case CAMP :
			doCamp(rModel);
			break;
		case UNCAMP:
			colonist.run(rModel, warehouse);
			break;
		case MOVE :
			
			break;
		case BATTLE :
			
			break;
		case RAID :
			
			break;
		case HIDE :
			
			break;
		case WAIT :
			
			break;
		default :
			break;
		}
	}
	
	private void doCamp(RealmModel rModel)
	{
		colonist.run(rModel, warehouse);
	}
}
