package net.krglok.realms.core;

import java.io.Serializable;

/**
 * <pre>
 * Der Trader ist kein Gebaeude, sondern ein Manager der Handel abwickelt. 
 * Das entsprechnde Gebaeude ist unter buildungs zu finden.
 * Der Trader wickelt die Handelsaktionen ab.
 * Hierzu benutzt er Karawanen. Jede Karawane hat einen Esel zum Transport der Warenkiste
 * Jeder Trader hat 5 Handelskisten. Fuer jede Handelskiste kann eine Handelstransaktion ausgeführt werden . 
 * Dies begrenzt auch gleichzeitig den Umfang der Transaktion (max 1762 Items). 
 * Die tatsaechliche Anzahl der Items ist von der Stacksize abhaengig. 
 * tradeOrders , sind die Transporte mit den Karawanen
 * buyOrders , sind die Einkaufsaktionen des Settlement
 * sellOrders, sind die Verkaufsaktionen des Settlements
 * </pre>
 * @author Windu
 *
 */

public class Trader  
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1502593484415028676L;
	private int caravanMax;
	private int caravanCount;
	private int buildingId;
	private boolean isEnabled;
	private boolean isActive;
	private TradeOrderList buyOrders;
	private int orderMax;
	private int orderCount;
	
	
	
	public Trader()
	{
		caravanMax = 5;
		caravanCount = 0;
		buildingId = 0;
		isEnabled = false;
		isActive  = false;
		orderMax = 20;
		buyOrders = new TradeOrderList();
//		sellOrders = new TradeOrderList();
		
	}



	public Trader(int caravanMax, int caravanCount,
			TradeOrderList tradeOrders, int buildingId,
			boolean isEnabled, boolean isActive, int orderMax)
	{
		super();
		this.caravanMax = caravanMax;
		this.caravanCount = caravanCount;
		this.buildingId = buildingId;
		this.isEnabled = isEnabled;
		this.isActive = isActive;
		this.orderMax = orderMax;
		buyOrders = new TradeOrderList();
//		sellOrders = new TradeOrderList();
	}

	


	public Trader(int caravanMax, int caravanCount,
			TradeOrderList tradeOrders, int buildingId,
			boolean isActive, TradeOrderList buyOrder,
			TradeOrderList sellOrder, int orderMax)
	{
		super();
		this.caravanMax = caravanMax;
		this.caravanCount = caravanCount;
		this.buildingId = buildingId;
		this.isActive = isActive;
		this.buyOrders = buyOrder;
//		this.sellOrders = sellOrder;
		this.orderMax = orderMax;
	}

	//public 

	public int getCaravanMax()
	{
		return caravanMax;
	}



	public void setCaravanMax(int caravanMax)
	{
		this.caravanMax = caravanMax;
	}



	public int getCaravanCount()
	{
		return caravanCount;
	}



	public void setCaravanCount(int caravanCount)
	{
		this.caravanCount = caravanCount;
	}


	public boolean isFreeSellOrder()
	{
		return (5 >= orderCount);
	}
	
	public boolean isFreeBuyOrder()
	{
//		if (orderMax >= buyOrders.size())
//		{
//			return true;
//		}
		return true;
	}

	public int getBuildingId()
	{
		return buildingId;
	}



	public void setBuildingId(int buildingId)
	{
		this.buildingId = buildingId;
		this.isEnabled = true;
		this.isActive = true;
	}



	public boolean isEnabled()
	{
		return isEnabled;
	}



	public void setEnabled(boolean isEnabled)
	{
		this.isEnabled = isEnabled;
	}



	public boolean isActive()
	{
		return isActive;
	}



	public void setActive(boolean isActive)
	{
		this.isActive = isActive;
	}



	public TradeOrderList getBuyOrders()
	{
		return buyOrders;
	}



	public void setBuyOrders(TradeOrderList buyOrder)
	{
		this.buyOrders = buyOrder;
	}


	public int getOrderMax()
	{
		return orderMax;
	}


	public void setOrderMax(int orderMax)
	{
		this.orderMax = orderMax;
	}
	
	public int getOrderCount()
	{
		return orderCount;
	}



	public void setOrderCount(int orderCount)
	{
		this.orderCount = orderCount;
	}



	public long  getTransportDelay(double distance)
	{
		
		if (distance > ConfigBasis.DISTANCE_1_DAY )
		{
			long way = (long) (distance / ConfigBasis.DISTANCE_1_DAY * ConfigBasis.GameDay);
			return way; 
		}
		return ConfigBasis.GameDay;
	}
	
	/**
	 * Erzeugt eine TransportOrder mit gekaufter Menge und Preis
	 * berechne Menge und buche sie vom MarketOrder ab
	 * berechnet den Kaufpreis und bucht ihn vom kaeufer ab
	 * SettleId , ist derjenige, der das Geld erhaelt
	 * TargetId , ist derjenige der die Ware erhaelt
	 * errorHandling, wenn der Kaeufer nicht genug Geld hat passiert nix
	 * @param tmo
	 * @param foundOrder
	 * @param transport
	 * @param SettleId
	 */
	public void makeTransportOrder(TradeMarketOrder tmo, TradeOrder foundOrder, TradeTransport transport , Settlement settle, double distance)
	{
		int amount = 0;
		double cost = 0.0 ;
		if (tmo.value() >= foundOrder.value())
		{
			amount = foundOrder.value();
			cost = amount * tmo.getBasePrice();
			tmo.setValue(tmo.value() - amount);
			long travelTime = getTransportDelay(distance);
			if (tmo.getWorld().equalsIgnoreCase(settle.getPosition().getWorld()) == false)
			{
				travelTime = travelTime + (10 * ConfigBasis.GameDay);
			}
			if (settle.getBank().getKonto() >= cost)
			{
				if (caravanCount < caravanMax)
				{
					TradeMarketOrder tto = new TradeMarketOrder(
							tmo.getSettleID(),			// ID des Absenders
							tmo.getId(),				// Id der sellOrder
							TradeType.TRANSPORT, 
							foundOrder.ItemRef(), 		// Ware
							amount,						// gepkaufte Menge 
							tmo.getBasePrice(),  		// Kaufpreis
							travelTime, // Laufzeit des Transports
							0, 							// abgelaufene Transportzeit
							TradeStatus.STARTED, 		// automatischer Start des Transport
							tmo.getWorld(),				// ZielWelt
							settle.getId()					// ID des Ziel Settlement
							);			
					transport.addOrder(tto);
					settle.getTrader().setCaravanCount(settle.getTrader().getCaravanCount() +1);
					settle.getBank().withdrawKonto(cost, "Trader "+settle.getId());
					tmo.setStatus(TradeStatus.WAIT);
					foundOrder.setStatus(TradeStatus.NONE);
				}
			}	
		}
	}
	
	
	private TradeOrder checkBuyOrder(String itemRef, int offerValue, double offerPrice)
	{
//		System.out.println("checkBuyOrder "+itemRef+"/");
		for (TradeOrder to : buyOrders.values())
		{
//			System.out.println("checkBuyOrder "+itemRef+"/"+to.ItemRef());
			if (to.ItemRef().equalsIgnoreCase(itemRef))
			{
//				System.out.println("checkBuyOrder "+offerPrice+"<="+to.getBasePrice());
				if (offerPrice <= to.getBasePrice())
				{
//					System.out.println("return "+to.ItemRef());
					return to;
				}
			}
		}
		return null;
	}
	
	/**
	 * Versucht eine sellOrder zu finden fuer seine buyOrder
	 * prueft Anzahl der Caravan
	 * prueft Verkaufpreis <= Kaufpreis
	 * prueft VerkaufMenge >= Kaufmenge 
	 * @param tradeMarket
	 * @param tradeTransport
	 * @param settle
	 */
	public void checkMarket(TradeMarket tradeMarket, TradeTransport tradeTransport, Settlement settle,SettlementList settlements )
	{
		double distance = 0.0;
		TradeOrder foundOrder = null;
		if (tradeMarket.isEmpty())
		{
			return;
		}
		for (TradeMarketOrder tmo : tradeMarket.values())
		{
			foundOrder = checkBuyOrder(tmo.ItemRef(), tmo.value(), tmo.getBasePrice());
			if ( foundOrder != null)
			{
				if (caravanCount < caravanMax)
				{
					distance = settle.getPosition().distance2D(settlements.getSettlement(tmo.getSettleID()).getPosition());
					makeTransportOrder(tmo, foundOrder, tradeTransport, settle, distance );
				} else
				{
					return;
				}
			}
		}
		
	}
	
	public void makeSellOrder(TradeMarket tradeMarket, Settlement settle, TradeOrder sellOrder)
	{
		if (settle.getWarehouse().getItemList().getValue(sellOrder.ItemRef())>= sellOrder.value())
		{
			settle.getWarehouse().withdrawItemValue(sellOrder.ItemRef(), sellOrder.value());
			sellOrder.setStatus(TradeStatus.STARTED);
			TradeMarketOrder tmo = new TradeMarketOrder(settle.getId(), sellOrder);
			tradeMarket.addOrder(tmo);
			orderCount++;
		}
	}

	public void makeBuyOrder(TradeOrder buyOrder)
	{
		buyOrders.addTradeOrder(buyOrder);
	}
}
