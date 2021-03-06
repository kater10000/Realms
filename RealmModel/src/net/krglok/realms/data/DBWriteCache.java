package net.krglok.realms.data;

import java.util.HashMap;
import java.util.Iterator;


/**
 * <pre>
 * Hold a list of tablename and referenzId to store data with a separate task
 * This is a delayed write of data to the database (SQLite) 
 * because the normal write time is 250-450 ms  mouch more then a server cycle  
 * 
 * could be used in the tickTask , so 1 per second are stored
 * 
 * @author Windu
 * </pre>
 */
public class DBWriteCache extends HashMap<Integer,DBCachRef>
{

	private static final long serialVersionUID = 3694326172720473717L;

	private static int ID = 0;

	private DataStorage dataStorage;
	
	
	public DBWriteCache(DataStorage dataStorage)
	{
		
		this.dataStorage = dataStorage;
	}
	
	public void addCache(DBCachType ref, int id)
	{
		DBCachRef cachRef = new DBCachRef(ref,id);
		ID++;
		this.put(ID, cachRef);
	}
	
	
	public void run()
	{
		Iterator<Integer> cache = this.keySet().iterator();
		
		int id = cache.next();
		
		DBCachRef ref = this.get(id);

		dataStorage.writeCache(ref);
		
		this.remove(id);
	}
}
