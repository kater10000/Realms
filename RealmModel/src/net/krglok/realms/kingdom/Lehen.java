package net.krglok.realms.kingdom;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.AbstractSettle;
import net.krglok.realms.core.Bank;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.BuildingList;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.core.TradeTransport;
import net.krglok.realms.core.Trader;
import net.krglok.realms.data.DataInterface;
import net.krglok.realms.data.ServerInterface;
import net.krglok.realms.manager.BuildManager;
import net.krglok.realms.model.RealmModel;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.unit.UnitArcher;
import net.krglok.realms.unit.UnitFactory;
import net.krglok.realms.unit.UnitHeavyInfantry;
import net.krglok.realms.unit.UnitKnight;
import net.krglok.realms.unit.UnitLightInfantry;
import net.krglok.realms.unit.UnitMilitia;
import net.krglok.realms.unit.UnitType;


/**
 * <pre>
 * the Lehen is the Basic object of of a feudal system.
 * The id= 0 is not allowed , because no should have id=0 !
 * The lehen is a settlement with NOBLE citizen, servant and military units.
 * 
 * 
 * @author Windu
 * </pre>
 */

public class Lehen  extends AbstractSettle
{

	private final int  SELL_DELAY   = 1200;
	private static int lfdID = 0;
	
	private NobleLevel nobleLevel;
	private Owner owner;
	private int KingdomId;
	private int parentId;
	private double sales;
	private LocationData position;
	private int supportId;
	private Trader trader;
	private BuildManager buildManager;
	
	private int delayRoutes = 0;

	
	public Lehen()
	{
		super();
		this.id = 0;
		this.name = "Lehen";
		this.nobleLevel = NobleLevel.COMMONER;
		this.settleType = SettleType.NONE;
		this.ownerId = 0;
		this.owner = null;
		this.KingdomId = 0;
		this.parentId = 0;
		this.supportId = 0;
		this.sales = 0;
		this.age = 0;
		this.position = new LocationData("", 0.0, 0.0, 0.0);
		trader = new Trader();
	}

	/**
	 * will be used to create new Lehen in existing kingdom
	 * the id = 0 and will be set at adding to List
	 * hint : the lehen_4 of the kingdom must exist before !!
	 * hint: use Kingdom.checkLehen for verify the Lehen and kingdom settings 
	 *  
	 * @param KingdomId
	 * @param name
	 * @param nobleLevel
	 * @param settleType
	 * @param owner
	 * @param parentId
	 */
	public Lehen(int KingdomId, String name,NobleLevel nobleLevel,SettleType settleType, Owner owner, int parentId, LocationData position)
	{
		this.id = 0;
		this.name = name;
		this.nobleLevel = nobleLevel;;
		this.settleType = settleType;
		this.ownerId = 0;
		this.owner = owner;
		this.KingdomId = KingdomId;
		this.parentId = parentId;
		this.supportId = 0;
		this.sales = 0;
		this.age = 0;
		this.position = position;
	}

	public static int getLfdID()
	{
		return lfdID;
	}

	public static void initLfdID(int iD)
	{
		lfdID = iD;
	}

	public static int nextID()
	{
		lfdID++;
		return lfdID; 
	}
	
	public String getName()
	{
		return this.getName();
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public NobleLevel getNobleLevel()
	{
		return nobleLevel;
	}

	public void setNobleLevel(NobleLevel nobleLevel)
	{
		this.nobleLevel = nobleLevel;
	}

	public Owner getOwner()
	{
		return owner;
	}

	public void setOwner(Owner owner)
	{
		this.owner = owner;
		this.ownerId = owner.getId();
	}

	/**
	 * @return the parentId
	 */
	public int getParentId()
	{
		return parentId;
	}


	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(int parentId)
	{
		this.parentId = parentId;
	}


	/**
	 * @return the kingdomId
	 */
	public int getKingdomId()
	{
		return KingdomId;
	}


	/**
	 * @param kingdomId the kingdomId to set
	 */
	public void setKingdomId(int kingdomId)
	{
		KingdomId = kingdomId;
	}

	/**
	 * @return the ownerId
	 */
	public int getOwnerId()
	{
		return ownerId;
	}

	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(int ownerId)
	{
		this.ownerId = ownerId;
	}


	/**
	 * @return the position
	 */
	public LocationData getPosition()
	{
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(LocationData position)
	{
		this.position = position;
	}

	/**
	 * @return the sales
	 */
	public double getSales()
	{
		return sales;
	}

	/**
	 * @param sales the sales to set
	 */
	public void setSales(double sales)
	{
		this.sales = sales;
	}

	/**
	 * add value to sales 
	 * hint: dont use negative values , better use withdraw
	 * @param value
	 */
	public void depositSales(double value)
	{
		this.sales = this.sales + value;
	}
	
	/**
	 * subtract value from sales.
	 * hint: dont use negative values !
	 * @param value
	 */
	public void withdrawSales(double value)
	{
		this.sales = this.sales - value;
	}


	public int getSupportId()
	{
		return supportId;
	}

	public void setSupportId(int supportId)
	{
		this.supportId = supportId;
	}
	
	public boolean isFreeNPC()
	{
		return true;
	}

	public void doProduce(ServerInterface server, DataInterface data)
	{
		System.out.println("[REALMS] unit consum");
		age++;

		for (NpcData unit :barrack.getUnitList())
		{
			doConsumUnit(unit, data);
		}
		resident.getNpcList().clear();
		resident.setNpcList(barrack.getUnitList().asNpcList());
		resident.doSettlerCalculation(this.buildingList, data);
		// unit production
		for (Building building : buildingList.values())
		{
			if (BuildPlanType.getBuildGroup(building.getBuildingType())== ConfigBasis.BUILDPLAN_GROUP_MILITARY)
			{
				System.out.println("Train check : "+building.getMaxTrain());
	
				if (building.isEnabled())
				{
					doTrainStart(data, building);
				} else
				{
					System.out.println("Train not enaled : "+building.getBuildingType()+" in "+this.id+" "+this.name);
				}
			}
		}
		
	}
	
	public void doUnitTrain(UnitFactory unitFactory)
	{
		for (Building building : buildingList.values())
		{
			// unit production
			if (BuildPlanType.getBuildGroup(building.getBuildingType())== 500)
			{
				if (building.isEnabled())
				{
					switch(building.getBuildingType())
					{
					case GUARDHOUSE:
						if (building.isTrainReady())
						{
//						System.out.println("GUARD " +item.ItemRef()+":"+item.value()+"*"+prodFactor);
							NpcData recrute = barrack.getUnitList().getBuildingRecrute(building.getId());
							if (recrute != null)
							{
								recrute.setWorkBuilding(0);
								recrute.setUnitType(UnitType.MILITIA);
								UnitMilitia.initData(recrute.getUnit());
								building.addMaxTrain(-1);
								building.setIsEnabled(false);
								building.setTrainCounter(0);
							} else
							{
								System.out.println("[REALMS] Guardhouse Train Recrute not found !");
							}
						} else
						{
						}
						break;
					case ARCHERY:
						if (building.isTrainReady())
						{
//						System.out.println("GUARD " +item.ItemRef()+":"+item.value()+"*"+prodFactor);
							NpcData recrute = barrack.getUnitList().getBuildingRecrute(building.getId());
							recrute.setWorkBuilding(0);
							recrute.setUnitType(UnitType.ARCHER);
							UnitArcher.initData(recrute.getUnit());
							building.addMaxTrain(-1);
							building.setIsEnabled(false);
							building.setTrainCounter(0);
						} else
						{
						}
						break;
					case BARRACK:
						if (building.isTrainReady())
						{
//						System.out.println("GUARD " +item.ItemRef()+":"+item.value()+"*"+prodFactor);
							NpcData recrute = barrack.getUnitList().getBuildingRecrute(building.getId());
							recrute.setWorkBuilding(0);
							recrute.setUnitType(UnitType.LIGHT_INFANTRY);
							UnitLightInfantry.initData(recrute.getUnit());
							building.addMaxTrain(-1);
							building.setIsEnabled(false);
							building.setTrainCounter(0);
						} else
						{
						}
						break;
					case CASERN:
						if (building.isTrainReady())
						{
//						System.out.println("GUARD " +item.ItemRef()+":"+item.value()+"*"+prodFactor);
							NpcData recrute = barrack.getUnitList().getBuildingRecrute(building.getId());
							recrute.setWorkBuilding(0);
							recrute.setUnitType(UnitType.HEAVY_INFANTRY);
							UnitHeavyInfantry.initData(recrute.getUnit());
							building.addMaxTrain(-1);
							building.setIsEnabled(false);
							building.setTrainCounter(0);
						} else
						{
						}
						break;
					case TOWER:
						if (building.isTrainReady())
						{
//						System.out.println("GUARD " +item.ItemRef()+":"+item.value()+"*"+prodFactor);
							NpcData recrute = barrack.getUnitList().getBuildingRecrute(building.getId());
							recrute.setWorkBuilding(0);
							recrute.setUnitType(UnitType.KNIGHT);
							UnitKnight.initData(recrute.getUnit());
							building.addMaxTrain(-1);
							building.setIsEnabled(false);
							building.setTrainCounter(0);
						} else
						{
						}
						break;
					default:
						break;
					}
				}
			}
		}
	}

	/*
	 * <pre>
	 * active tick for the Manager 
	 * the manager is is a finite state machine
	 * The Lehen Manager realize the functions of a TradeManager but not in the same way 
	 * Here are the basic supply chain for the lehen
	 * </pre>
	 */
	public void run(RealmModel rModel)
	{
		// check for RouteOrders
		if (delayRoutes > (SELL_DELAY / 20))
		{
			checksupport(rModel, rModel.getTradeTransport(), rModel.getSettlements());
			delayRoutes = 0;
		} else
		{
			delayRoutes++;
		}
		
	}
	
	/**
	 * add Wheat  to Requestlist
	 * @param rModel
	 * @param transport
	 * @param settlements
	 */
	private void checksupport(RealmModel rModel,TradeTransport transport, SettlementList settlements)
	{
		SettlementList subList = settlements.getSubList(owner);
		if (this.supportId > 0)
		{
			Settlement settle = settlements.getSettlement(supportId);	
			subList.addSettlement(settle);
		}
		int requiredWhet = this.getSupported();
		Item item = new Item("WHEAT",requiredWhet);
		this.requiredProduction.addItem(item);
	}
	
	/**
	 * count for residents, units in barrack and units in buildings
	 *  
	 * @return  count of resident and units
	 */
	private int getSupported()
	{
		int value = 2;
		value = value + resident.getSettlerCount();
		value = value + barrack.getUnitList().size();
		for (Building building : buildingList.values())
		{
			value = value + building.getSettler();
		}
		return  value;
	}

	public ItemList getrequiredItems()
	{
		return this.requiredProduction;
	}

	/**
	 * @return the trader
	 */
	public Trader getTrader()
	{
		return trader;
	}
	
}
