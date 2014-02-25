package net.krglok.realms.core;

import java.util.HashMap;

/**
 * <pre>
 * Hier werden zentral die Angebote der Trader gespeichert und verwaltet.
 * Die Instanz ist zentral angeordnet und wird an die entsprechenden Nutzer uebergeben.
 * Es wird von den Nutzern ver�ndert und verwaltet.
 * </pre>
 * @author oduda
 *
 */
public class TradeMarket extends HashMap<Integer,TradeMarketOrder>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5194184061229844880L;
	private  int lastNumber;


	public TradeMarket()
	{
		super();
		lastNumber = 0;
	}

	public  int getLastNumber()
	{
		return lastNumber;
	}

	public  int nextLastNumber()
	{
		lastNumber++;
		return lastNumber;
	}

	public  void setLastNumber(int lastNumber)
	{
		this.lastNumber = lastNumber;
	}

	
	public void runTick()
	{
		for (TradeMarketOrder to : this.values())
		{
			to.runTick();
		}
	}
	
	public void checkLastNumber()
	{
		int max = 0;
		for (TradeMarketOrder to : this.values())
		{
			if (max < to.getId())
			{
				max = to.getId();
			}
		}
	}

	public void removeOrder(int orderId)
	{
		this.remove(orderId);
	}
	
	public void cancelOrder(int orderId)
	{
		this.get(orderId).setStatus(TradeStatus.READY);
	}
	
	
	private boolean checkPrice(TradeMarketOrder sellOrder, TradeMarketOrder buyOrder)
	{
		if (sellOrder.getBasePrice() <= buyOrder.getBasePrice())
		{
			return true;
		}
		
		return false;
	}
	
	public void setOrderFullfill(TradeMarketOrder sellOrder)
	{
		sellOrder.setStatus(TradeStatus.FULFILL);
	}
	
	public TradeMarket find (TradeMarketOrder buyOrder)
	{
		TradeMarket subList = new TradeMarket();
		for (TradeMarketOrder sellOrder : this.values())
		{
			if ((sellOrder.getSettleID() != buyOrder.getSettleID())
				&& (sellOrder.getStatus() == TradeStatus.STARTED)
				&& (sellOrder.getWorld().equalsIgnoreCase(buyOrder.getWorld()))
				)
			{
				if (sellOrder.ItemRef().equalsIgnoreCase(buyOrder.ItemRef()))
				{
					if (checkPrice(sellOrder, buyOrder))
					{
//						this.lastNumber++;
						subList.put(sellOrder.getId(),sellOrder);
					}
				}
			}
		}
		return subList;
	}
	
	
	public TradeMarketOrder putOrder(TradeMarketOrder tmo)
	{
		if (this.containsKey(tmo.getId()))
		{
			this.put(tmo.getId(), tmo);
			return tmo;
		}

		return addOrder(tmo);
	}
	
	public TradeMarketOrder getOrder(int orderId)
	{
		return this.get(orderId);
	}
	
	public TradeMarketOrder addOrder(TradeMarketOrder tmo)
	{
		if (tmo.getId() < 1) 
		{
			lastNumber++;
			tmo.setId(lastNumber);
		}
		tmo.setId(lastNumber);
		this.put(lastNumber, tmo);
		return tmo;
	}
	
	public TradeMarket getSettleOrders(int settleId)
	{
		TradeMarket subList = new TradeMarket();
		for (TradeMarketOrder tmo : this.values())
		{
			if (tmo.getSettleID() == settleId)
			{
//				this.lastNumber++;
				subList.put(tmo.getId(),tmo);
			}
		}
		
		return subList;
	}
	
	public TradeMarket getWorldOrders(String world)
	{
		TradeMarket subList = new TradeMarket();
		for (TradeMarketOrder tmo : this.values())
		{
			if (tmo.getWorld().equalsIgnoreCase(world))
			{
//				this.lastNumber++;
				subList.put(tmo.getId(),tmo);
			}
		}
		
		return subList;
	}

	public TradeMarket getOtherWorldOrders(String world)
	{
		TradeMarket subList = new TradeMarket();
		for (TradeMarketOrder tmo : this.values())
		{
			if (!tmo.getWorld().equalsIgnoreCase(world))
			{
//				this.lastNumber++;
				subList.put(tmo.getId(),tmo);
			}
		}
		
		return subList;
	}
	
	
}
