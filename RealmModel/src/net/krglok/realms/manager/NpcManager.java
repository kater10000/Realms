package net.krglok.realms.manager;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;


import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.Equipment.EquipmentSlot;
import net.citizensnpcs.api.trait.trait.Inventory;
import net.citizensnpcs.api.trait.trait.MobType;
import net.citizensnpcs.trait.Age;
import net.citizensnpcs.trait.Anchors;
import net.citizensnpcs.trait.LookClose;
import net.citizensnpcs.trait.VillagerProfession;
import net.citizensnpcs.trait.waypoint.Waypoints;
import net.krglok.realms.Realms;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.npc.GenderType;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.npc.NPCType;
import net.krglok.realms.npc.NpcList;
import net.krglok.realms.npc.SettlerTrait;
import net.krglok.realms.unit.Regiment;
import net.krglok.realms.unit.UnitType;

/**
 * <pre>
 * the npcManager must know the plugin diectly, so it cannot involved in RealmsModel
 * the npcManager manage and control the visible NPC in World
 * the manager store the NPC id of citizens in relation to the settlement (HashMap<NpcId, SettleId>)
 * a settlement id = 0 means this is a free/rogue npc
 * Based on the settlement the trait will be added to the npc created by citizens.
 * 
 * 
 * @author Windu
 * </pre>
 */

public class NpcManager
{
	private  Realms plugin;
	private boolean isEnabled = false;
//	private NpcList npcList = new NpcList();
	private boolean isSpawn = false;
	private ArrayList<Integer> spawnList = new ArrayList<Integer>();
	private boolean isNpcInit = false;

	private static int EQUIP_HAND = 0;
	private static int EQUIP_HELMET = 1;
	private static int EQUIP_CHEST = 2;
	private static int EQUIP_LEGGING = 3;
	private static int EQUIP_BOOT = 4;

	public NpcManager(Realms plugin) 
	{
		this.plugin = plugin;
	}

	/**
	 * @return the isEnabled
	 */
	public boolean isEnabled()
	{
		return isEnabled;
	}

	/**
	 * @param isEnabled the isEnabled to set
	 */
	public void setEnabled(boolean isEnabled)
	{
		this.isEnabled = isEnabled;
	}
	
	

	/**
	 * @return the isSpawn
	 */
	public boolean isSpawn()
	{
		return isSpawn;
	}

	/**
	 * @param isSpawn the isSpawn to set
	 */
	public void setSpawn(boolean isSpawn)
	{
		this.isSpawn = isSpawn;
	}

	/**
	 * @return the spawnList
	 */
	public ArrayList<Integer> getSpawnList()
	{
		return spawnList;
	}


	/**
	 * @return the isNpcInit
	 */
	public boolean isNpcInit()
	{
		return isNpcInit;
	}

	/**
	 * @param isNpcInit the isNpcInit to set
	 */
	public void setNpcInit(boolean isNpcInit)
	{
		this.isNpcInit = isNpcInit;
	}

	public void initNpc()
	{
		System.out.println("[REALMS] Init spawnList of NPC ");
		
		for (NpcData npcData : plugin.getData().getNpcs().values())
		{
			if (npcData.isSpawned == false)
			{
				spawnList.add(npcData.getId());
			}
		}
		isNpcInit = true;
	}
	
	/**
	 * create a NPC with citizens2 and the trait settler
	 * the NPC will be stored in the npcList
	 * 
	 * @param name
	 * @param npcType
	 * @param position
	 * @param settle
	 * @param buildingId
	 */
//	public void createNPC(String name, NPCType npcType,LocationData position, Settlement settle, int buildingId)
	public void createNPC(NpcData npc, LocationData position)
	{
		if (isEnabled == false)  
		{
			System.out.println("NPC Manager not enabled !");
			return; 
		}
		if (npc.isSpawned == false)
		{
//			System.out.println("NPC create : "+npc.getId()+":"+npc.getNpcType());
			int spawnId = doNPCSpawn( npc.getName(), npc.getNpcType(), position,npc.getSettleId(), npc.getHomeBuilding()) ;
			if (spawnId >= 0)
			{
				npc.isSpawned = true;
				npc.spawnId = spawnId;
			}
		}
//		npcList.put(npcId, new NpcData(npcId, npcType, UnitType.SETTLER, name, settleId, buildingId, GenderType.MAN, 20));
	}
	
	public void createUnitNPC(String name, UnitType npcType,LocationData position, Regiment regiment)
	{
		

	}
	

//	private void storeNPCinList(int npcId, NpcData npcData)
//	{
//		
//		npcList.put(npcId,npcData);
//	}
	
	/**
	 * create a colored helmet
	 * 
	 * @param npcType
	 * @param color
	 * @return
	 */
	private ItemStack makeNpcHelmet(NPCType npcType, Color color)
	{
		ItemStack item ;
		item = new ItemStack(Material.LEATHER_HELMET);
		LeatherArmorMeta armorMeta = (LeatherArmorMeta) item.getItemMeta();
		armorMeta.setColor(color);
		return item;
	}
	

	private ItemStack makeNPCTool(NPCType npcType)
	{
		ItemStack item = null ;
//		System.out.println("[REALMS] make NPC Tools ");
		switch(npcType)
		{
		case MANAGER:
			item = new ItemStack(Material.BOOK);
			break;
		case FARMER:
//			System.out.println("[REALMS] Trait makeNPCTool"+npcType.name());
			item = new ItemStack(Material.WOOD_HOE);
			break;
		case BUILDER:
			item = new ItemStack(Material.WOOD_SPADE);
			break;
		case TRADER:
			item = new ItemStack(Material.CHEST);
			break;
		case CRAFTSMAN:
			item = new ItemStack(Material.WOOD_AXE);
			break;
		case SETTLER:
			break;
		case CHILD:
			break;
		case BEGGAR:
//			System.out.println("[REALMS] Trait makeNPCTool"+npcType.name());
			item = new ItemStack(Material.ROTTEN_FLESH);
			break;
			
		default:
			item = null;
			break;
		}
		return item;
	}
	
	public void equipNpc(NPC npc, NPCType npcType)
	{
//		System.out.println("[REALMS] equip NPC  ");
//		Inventory inv   = npc.getTrait(Inventory.class);
		Color color = Color.WHITE;
		switch(npcType)
		{
		case MANAGER:
//			npc.getTrait(Equipment.class).set(EquipmentSlot.HAND, new ItemStack(Material.BOOK,1));
//			npc.getTrait(Equipment.class).set(EQUIP_HAND, makeNPCTool(npcType));
//			npc.getTrait(Equipment.class).set(EQUIP_HELMET, makeNpcHelmet(npcType, color));
			break;
		case SETTLER:
			npc.getTrait(Equipment.class).set(EQUIP_HELMET, makeNpcHelmet(npcType, color));
			break;
		case FARMER:
//			System.out.println("[REALMS] Trait equip "+npcType.name());
			npc.getTrait(Equipment.class).set(EQUIP_HELMET, makeNpcHelmet(npcType, color));
			npc.getTrait(Equipment.class).set(EQUIP_HAND, makeNPCTool(npcType));
			break;
		case BUILDER:
			npc.getTrait(Equipment.class).set(EQUIP_HELMET, makeNpcHelmet(npcType, color));
			npc.getTrait(Equipment.class).set(EQUIP_HAND, makeNPCTool(npcType));
			break;
		case TRADER:
			npc.getTrait(Equipment.class).set(EQUIP_HELMET, makeNpcHelmet(npcType, color));
			npc.getTrait(Equipment.class).set(EQUIP_HAND, makeNPCTool(npcType));
			break;
		case CRAFTSMAN:
			npc.getTrait(Equipment.class).set(EQUIP_HELMET, makeNpcHelmet(npcType, color));
			npc.getTrait(Equipment.class).set(EQUIP_HAND, makeNPCTool(npcType));
			break;
		case CHILD:
			npc.getTrait(Equipment.class).set(EQUIP_HELMET, makeNpcHelmet(npcType, color));
			break;
		case BEGGAR:
			npc.getTrait(Equipment.class).set(EQUIP_HAND, makeNPCTool(npcType));
			break;
			
		default:
			break;
		}
		
	}
	
	/**
	 * set the sttler trait to an citizen NPC
	 * 
	 * @param npc
	 * @param npcType
	 * @param settleId
	 * @param buildingId
	 * @return
	 */
	public SettlerTrait doAddTrait(NPC npc, NPCType npcType, int settleId, int buildingId)
	{
//		System.out.println("[REALMS] add trait  NPC ");
		SettlerTrait sTrait = new SettlerTrait();
		npc.getTrait(LookClose.class).lookClose(true);
		float newSpeed = (float) 0.8;
		npc.getNavigator().getDefaultParameters().speedModifier(newSpeed );
//		Waypoints wp = npc.getTrait(Waypoints.class);
//		Anchors anchors = npc.getTrait(Anchors.class);
		switch(npcType)
		{
		case MANAGER:
			npc.addTrait(sTrait);
			sTrait.setsNPCType(npcType);
			sTrait.setsUnitType(UnitType.SETTLER);
			sTrait.setSettleId(settleId);
			sTrait.setBuildingId(buildingId);
//			anchors.addAnchor("Home", location);
//			wp.getCurrentProvider().
			equipNpc(npc, npcType);
			break;
		case SETTLER:
			npc.addTrait(sTrait);
			sTrait.setsNPCType(npcType);
			sTrait.setsUnitType(UnitType.SETTLER);
			sTrait.setSettleId(settleId);
			sTrait.setBuildingId(buildingId);
//			npc.getTrait(Waypoints.class).setWaypointProvider("wander");
			equipNpc(npc, npcType);
			break;
		case CHILD:
			npc.addTrait(sTrait);
			sTrait.setsNPCType(npcType);
			sTrait.setsUnitType(UnitType.SETTLER);
			sTrait.setSettleId(settleId);
			sTrait.setBuildingId(buildingId);
//			npc.getTrait(Waypoints.class).setWaypointProvider("wander");
//			equipNpc(npc, npcType);
			break;
		case FARMER:
			npc.addTrait(sTrait);
			sTrait.setsNPCType(npcType);
			sTrait.setsUnitType(UnitType.SETTLER);
			sTrait.setSettleId(settleId);
			sTrait.setBuildingId(buildingId);
//			npc.getTrait(Waypoints.class).setWaypointProvider("wander");
//			equipNpc(npc, npcType);
			break;
		case BUILDER:
			npc.addTrait(sTrait);
			sTrait.setsNPCType(npcType);
			sTrait.setsUnitType(UnitType.SETTLER);
			sTrait.setSettleId(settleId);
			sTrait.setBuildingId(buildingId);
//			npc.getTrait(Waypoints.class).setWaypointProvider("wander");
//			equipNpc(npc, npcType);
			break;
		case TRADER:
			npc.addTrait(sTrait);
			sTrait.setsNPCType(npcType);
			sTrait.setsUnitType(UnitType.SETTLER);
			sTrait.setSettleId(settleId);
			sTrait.setBuildingId(buildingId);
//			npc.getTrait(Waypoints.class).setWaypointProvider("wander");
//			equipNpc(npc, npcType);
			break;
		case CRAFTSMAN:
			npc.addTrait(sTrait);
			sTrait.setsNPCType(npcType);
			sTrait.setsUnitType(UnitType.SETTLER);
			sTrait.setSettleId(settleId);
			sTrait.setBuildingId(buildingId);
//			npc.getTrait(Waypoints.class).setWaypointProvider("wander");
//			equipNpc(npc, npcType);
			break;
			
		default:
			npc.addTrait(sTrait);
			sTrait.setsNPCType(NPCType.BEGGAR);
			sTrait.setsUnitType(UnitType.SETTLER);
			sTrait.setSettleId(settleId);
			sTrait.setBuildingId(buildingId);
//			npc.getTrait(Waypoints.class).setWaypointProvider("wander");
//			equipNpc(npc, npcType);
			break;
		}
		return sTrait;
		
	}
	
	public void removeNPC(NpcData realmNpc)
	{
		try
		{
			NPC npc = CitizensAPI.getNPCRegistry().getById(realmNpc.spawnId);
			if (npc != null)
			{
//				System.out.println("Deregister "+npc.getId());
				CitizensAPI.getNPCRegistry().deregister(npc );
			}
		} catch (Exception e)
		{
			System.out.println("Exception Deregister "+realmNpc.getId());
		}
	}

	
	public int doNPCSpawn(String name, NPCType npcType, LocationData position, int settleId, int buildingId)
	{
		NPC npc = null;
		SettlerTrait sTrait;
		switch(npcType)
		{
		case MANAGER:
			npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Settler");
			npc.setProtected(true);
			npc.setName(name);
			sTrait = doAddTrait(npc, npcType, settleId, buildingId);
			break;
		case SETTLER:
			npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Settler");
			npc.setProtected(true);
			npc.setName(name);
			sTrait = doAddTrait(npc, npcType, settleId, buildingId);
			break;
		case FARMER:
			npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Settler");
			npc.setProtected(true);
			npc.setName(name);
			sTrait = doAddTrait(npc, npcType, settleId, buildingId);
			break;
		case BUILDER:
			npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Settler");
			npc.setProtected(true);
			npc.setName(name);
			sTrait = doAddTrait(npc, npcType, settleId, buildingId);
			break;
		case TRADER:
			npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Settler");
			npc.setProtected(true);
			npc.setName(name);
			sTrait = doAddTrait(npc, npcType, settleId, buildingId);
			break;
		case CRAFTSMAN:
//			npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Settler");
//			npc.setProtected(true);
//			npc.setName(name);
//			sTrait = doAddTrait(npc, npcType, settleId, buildingId);
			break;
		case CHILD:
			npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.VILLAGER, "Child");
			npc.setProtected(true);
			npc.getTrait(VillagerProfession.class).setProfession(Profession.FARMER);
			int age = -24000;
			npc.getTrait(Age.class).setAge(age);
			npc.setName(ChatColor.YELLOW+name);
			sTrait = doAddTrait(npc, npcType, settleId, buildingId);
			break;
			
		default:
			npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Beggar");
			npc.setProtected(false);
			npc.setName(name);
			sTrait = doAddTrait(npc, npcType, settleId, buildingId);
			break;
		}
		if (npc != null)
		{
			Location pos = new Location(plugin.getServer().getWorld(position.getWorld()), position.getX(), position.getY(), position.getZ());
			npc.spawn(pos);
			return npc.getId();
		} else
		{
			return -1;
		}
	}

	
	public void run()
	{
		if (isSpawn == false)
		{
			spawnList.get(0);
		}
	}
	
}
