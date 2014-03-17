package net.krglok.realms.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import net.krglok.realms.core.LocationData;

/**
 * <pre>
 * log file for internal transactions.
 * the data are stored as CSV to make it possible to read external
 * all data stored as string.
 * timestamp, type user,type,data1, data2, ....datax
 * every TransaType has a special dataformat and a special ad  method
 * 
 * if new File false all writes are redirected to console
 * </pre>
 * @author Windu
 *
 */
public class LogList
{
	private ArrayList<String> prodList;
	private ArrayList<String> happyList;
	private ArrayList<String> orderList;
    FileWriter prodWrite;
    FileWriter happyWrite;
    FileWriter orderWrite;

    private int index;
    
	public LogList(String path )
	{
		index = 0;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy_HH");
		String formattedDate = sdf.format(date);
		prodList = new ArrayList<String>();
		happyList = new ArrayList<String>();
		orderList = new ArrayList<String>();
	    File prodFile = new File(path, "production_"+formattedDate+".csv");
	    if (prodFile.exists()== false) 
	    {
			System.out.println("NEW production.csv : "+path+":"+"production_"+formattedDate+".csv");
	    	try
			{
				prodFile.createNewFile();
			    prodWrite = new FileWriter(prodFile);
			} catch (IOException e)
			{
				System.out.println("Exeption LogList : "+path+":"+"production_"+formattedDate+".csv");
				e.printStackTrace();
				prodFile = null;
				prodWrite = null;
			}
	    } else
	    {
		    try
			{
				prodWrite = new FileWriter(prodFile,true);
			} catch (IOException e)
			{
				e.printStackTrace();
				prodWrite = null;
			}
	    }
	    
	    File happyFile = new File(path, "happy_"+formattedDate+".csv");
	    if (happyFile.exists()== false) 
	    {
			System.out.println("NEW happy.csv : "+path+":"+"happy_"+formattedDate+".csv");
	    	try
			{
	    		happyFile.createNewFile();
			    happyWrite = new FileWriter(happyFile);
			} catch (IOException e)
			{
				System.out.println("Exeption LogList : "+path+":"+"happy_"+formattedDate+".csv");
				e.printStackTrace();
				happyFile = null;
				happyWrite = null;
			}
	    } else
	    {
		    try
			{
		    	happyWrite = new FileWriter(happyFile,true);
			} catch (IOException e)
			{
				e.printStackTrace();
				happyWrite = null;
			}
	    }

	    File orderFile = new File(path, "order_"+formattedDate+".csv");
	    if (orderFile.exists()== false) 
	    {
			System.out.println("NEW order.csv : "+path+":"+"order_"+formattedDate+".csv");
	    	try
			{
	    		orderFile.createNewFile();
			    orderWrite = new FileWriter(orderFile);
			} catch (IOException e)
			{
				System.out.println("Exeption LogList : "+path+":"+"order_"+formattedDate+".csv");
				e.printStackTrace();
				orderFile = null;
				orderWrite = null;
			}
	    } else
	    {
		    try
			{
		    	orderWrite = new FileWriter(orderFile,true);
			} catch (IOException e)
			{
				e.printStackTrace();
				orderWrite = null;
			}
	    }
	    
	}

	public ArrayList<String> getLogList()
	{
		return prodList;
	}

	public void setLogList(ArrayList<String> logList)
	{
		this.prodList = logList;
	}

	/**
	 * Set a transaction text, there are automatic add datetime
	 * date / time / user / text
	 * @param text
	 */
	private void addText( String user, String text)
	{
		String DataType = "TEXT";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		String formattedDate = sdf.format(date);
		String transaction = formattedDate + ";"+DataType+ ";" + user + ";" + text;
//		this.prodList.add(transaction);
	}

	public void addBank(String user, String text, int SettleId,double value)
	{
		String DataType = "BANK";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		String formattedDate = sdf.format(date);
		String transaction = formattedDate 
				+ ";"+DataType
				+ ";" + user 
				+ ";" + text
				+";" + String.valueOf(SettleId) 
				+ ";" + String.valueOf(value);
//		this.prodList.add(transaction);
	}

	public void addProduction(String text,int SettleId, int buildingId, String itemRef, int value , String user, long age)
	{
		String DataType = "PRODUCTION";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		String formattedDate = sdf.format(date);
		String transaction = formattedDate  
				+";"+DataType 
				+";" + user  
				+";" + text  
				+";" + String.valueOf(SettleId) 
				+";" + String.valueOf(buildingId) 
				+";" + itemRef 
				+";" + String.valueOf(value)
				+";" + String.valueOf(age)
				;
		this.prodList.add(transaction);
	}

	private void addProductionSale(String text,int SettleId, int buildingId,  double value , String user, long age)
	{
		String DataType = "SALE";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		String formattedDate = sdf.format(date);
		String transaction = formattedDate  
				+";"+DataType 
				+";" + user  
				+";" + text  
				+";" + String.valueOf(SettleId) 
				+";" + String.valueOf(buildingId) 
				+";" + String.valueOf(value)
				+";" + String.valueOf(age)
				;
//		this.prodList.add(transaction);
	}

	public void addHappiness(String text,int SettleId, double happiness, double entertainFactor, double settlerFactor, double foodFactor , String user, long age)
	{
		String DataType = "HAPPINESS";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		String formattedDate = sdf.format(date);
		String transaction = formattedDate  
				+";"+DataType 
				+";" + user  
				+";" + text  
				+";" + String.valueOf(SettleId) 
				+";" + String.valueOf(happiness) 
				+";" + String.valueOf(entertainFactor)
				+";" + String.valueOf(settlerFactor)
				+";" + String.valueOf(foodFactor)
				+";" + String.valueOf(age)
				;
		this.happyList.add(transaction);
	}
	private void addSettler(String text,int SettleId, int settlerCount, int settlerBirthrate, int settlerDeathrate, String user, long age)
	{
//		settlerCount + settlerBirthrate - settlerDeathrate;

		String DataType = "SETTLER";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		String formattedDate = sdf.format(date);
		String transaction = formattedDate  
				+";"+DataType 
				+";" + user  
				+";" + text  
				+";" + String.valueOf(SettleId) 
				+";" + String.valueOf(settlerCount) 
				+";" + String.valueOf(settlerBirthrate)
				+";" + String.valueOf(settlerDeathrate)
				+";" + String.valueOf(age)
				;
//		this.prodList.add(transaction);
	}
	
	public void addOrder(String text,int SettleId, int TargetId, String itemRef, int value , double price, long timeOut ,String user)
	{
		String DataType = "ORDER";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		String formattedDate = sdf.format(date);
		String transaction = formattedDate  
				+";"+DataType
				+";" + user  
				+";" + text  
				+";" + String.valueOf(SettleId) 
				+";" + String.valueOf(TargetId) 
				+";" + itemRef 
				+";" + String.valueOf(value) 
				+";" + String.valueOf(price) 
				+";" + String.valueOf(timeOut)
				;
		this.orderList.add(transaction);
	}
	
	private void addLocation(String text,int SettleId, LocationData position , String user)
	{
		String DataType = "POSITION";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		String formattedDate = sdf.format(date);
		String transaction = formattedDate  
				+";"+DataType 
				+";" + user  
				+";" + text  
				+";" + String.valueOf(SettleId) 
				+";" + position.getWorld() 
				+";" + String.valueOf(position.getX()) 
				+";" + String.valueOf(position.getY()) 
				+";" + String.valueOf(position.getZ())
				;
		this.prodList.add(transaction);
	}
	
	
	public int size()
	{
		return prodList.size();
	}
	
	/**
	 * Write LogDat to File
	 * normally used by onTick;
	 */
	public void run ()
	{
		switch(index)
		{
		case 1:
			for (String line : prodList)
			{
				try
				{
					prodWrite.append(line);
					prodWrite.append("\n");
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			prodList.clear();
			try
			{
				prodWrite.flush();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			break;
		case 2:
			for (String line : happyList)
			{
				try
				{
					happyWrite.append(line);
					happyWrite.append("\n");
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			happyList.clear();
			try
			{
				happyWrite.flush();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			break;
		case 3:
			for (String line : orderList)
			{
				try
				{
					orderWrite.append(line);
					orderWrite.append("\n");
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			orderList.clear();
			try
			{
				orderWrite.flush();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			break;
		default:
			if (index > 3)
			{
				index = 0;
			}
		}
		index++;
	}
}
