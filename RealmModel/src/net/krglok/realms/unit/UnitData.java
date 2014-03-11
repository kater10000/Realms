package net.krglok.realms.unit;

import net.krglok.realms.core.ItemList;

/**
 * Die Unit ist eine abstrakte Class von der die realen Units abgeleitet werden.
 * 
 * @author Windu
 *
 */
public abstract class UnitData implements iUnitData
{
	protected int speed;
	protected int offense;
	protected int defense;
	protected int offenseRange;
	protected int maxStorage;
	protected int armor;
	
	// required
	protected ItemList requiredItems;
	protected double requiredCost;
	protected long requiredTime;
	protected UnitTypeList requiredUnits;
	
	//consum
	protected ItemList consumItems;
	protected double consumCost;
	protected long consumTime;
	
	public UnitData()
	{
		this.speed = 0;
		this.offense = 1;
		this.defense = 1;
		this.offenseRange = 1;
		this.maxStorage = 9;
		this.armor  = 0;
		// required
		requiredItems = new ItemList();
		requiredCost = 0.0;
		requiredTime = 10;
		requiredUnits = new UnitTypeList();
		
		//consum
		consumItems = new ItemList();
		consumCost  = 0.0;
		consumTime  = 10;
		
	}
	
	

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#getSpeed()
	 */
	@Override
	public int getSpeed()
	{
		return speed;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#setSpeed(int)
	 */
	@Override
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#getOffense()
	 */
	@Override
	public int getOffense()
	{
		return offense;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#setOffense(int)
	 */
	@Override
	public void setOffense(int offense)
	{
		this.offense = offense;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#getDefense()
	 */
	@Override
	public int getDefense()
	{
		return defense;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#setDefense(int)
	 */
	@Override
	public void setDefense(int defense)
	{
		this.defense = defense;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#getOffenseRange()
	 */
	@Override
	public int getOffenseRange()
	{
		return offenseRange;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#setOffenseRange(int)
	 */
	@Override
	public void setOffenseRange(int offenseRange)
	{
		this.offenseRange = offenseRange;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#getMaxStorage()
	 */
	@Override
	public int getMaxStorage()
	{
		return maxStorage;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#setMaxStorage(int)
	 */
	@Override
	public void setMaxStorage(int maxStorage)
	{
		this.maxStorage = maxStorage;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#getArmor()
	 */
	@Override
	public int getArmor()
	{
		return armor;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#setArmor(int)
	 */
	@Override
	public void setArmor(int armor)
	{
		this.armor = armor;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#getRequiredCost()
	 */
	@Override
	public double getRequiredCost()
	{
		return requiredCost;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#setRequiredCost(double)
	 */
	@Override
	public void setRequiredCost(double requiredCost)
	{
		this.requiredCost = requiredCost;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#getConsumCost()
	 */
	@Override
	public double getConsumCost()
	{
		return consumCost;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#setConsumCost(double)
	 */
	@Override
	public void setConsumCost(double consumCost)
	{
		this.consumCost = consumCost;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#getConsumTime()
	 */
	@Override
	public long getConsumTime()
	{
		return consumTime;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#setConsumTime(long)
	 */
	@Override
	public void setConsumTime(long consumTime)
	{
		this.consumTime = consumTime;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#getRequiredItems()
	 */
	@Override
	public ItemList getRequiredItems()
	{
		return requiredItems;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#getRequiredUnits()
	 */
	@Override
	public UnitTypeList getRequiredUnits()
	{
		return requiredUnits;
	}

	public long getRequiredTime()
	{
		return requiredTime;
	}



	public void setRequiredTime(long requiredTime)
	{
		this.requiredTime = requiredTime;
	}



	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#getConsumItems()
	 */
	@Override
	public ItemList getConsumItems()
	{
		return consumItems;
	}

	
	
}