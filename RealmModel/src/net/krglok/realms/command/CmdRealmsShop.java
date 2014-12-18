package net.krglok.realms.command;

import java.util.ArrayList;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.Realms;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Settlement;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdRealmsShop extends RealmsCommand
{
	private int page; 
	
	public CmdRealmsShop( )
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.SHOP);
		description = new String[] {
				ChatColor.YELLOW+"/realms SHOP    ",
		    	" fill Shop with items  ",
		    	"  ",
		    	"  ",
		    	" "
			};
			requiredArgs = 0;
			page = 0;
	}

	@Override
	public void setPara(int index, String value)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setPara(int index, int value)
	{
		switch (index)
		{
		case 0 :
			page = value;
			break;
		default:
			break;
		}

	}

	@Override
	public void setPara(int index, boolean value)
	{

	}

	@Override
	public void setPara(int index, double value)
	{

	}

	@Override
	public String[] getParaTypes()
	{
		return new String[] {int.class.getName()  };
	}

	private void checkRegionChest(Realms plugin, ArrayList<String> msg)
	{
		for (Region region : plugin.stronghold.getRegionManager().getRegions().values())
		{
			Block bs = region.getLocation().getBlock();
			if (bs.getType() != Material.CHEST)
			{
				String x = ConfigBasis.setStrformat2(region.getLocation().getX(),7);
				String y = ConfigBasis.setStrformat2(region.getLocation().getY(),7);
				String z = ConfigBasis.setStrformat2(region.getLocation().getZ(),7);
				String type = ConfigBasis.setStrleft(region.getType(), 12);
				msg.add(region.getID()+":"+ type +":"+ x + ":"+ y +":"+ z);
			}
		}
	}
	
	private SuperRegion findSuperRegionAtPosition(Realms plugin, Location position)
	{
	    for (SuperRegion sRegion : plugin.stronghold.getRegionManager().getContainingSuperRegions(position))
	    {
	    	if (sRegion != null)
	    	{
	    		return sRegion;
	    	}
	    }
		return null;
	}

    private void cmdSignShop(Realms plugin, Block b, Player player)
    {
    	Location pos = b.getLocation();
		SuperRegion sRegion = findSuperRegionAtPosition(plugin, b.getLocation());
		if (sRegion != null)
		{
			Settlement settle = plugin.getRealmModel().getSettlements().findName(sRegion.getName());
			if (settle != null)
			{
    	    	System.out.println("Realms setShop");
    	    	plugin.setShopPrice(b.getLocation());
//    	    	plugin.setShop(player,b.getLocation(), settle);
//    	    	plugin.setShopPrice(pos);
    	    }
		}
    }

	
    
	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		System.out.println("Look for Signs ");
		int radius = 5;
		int edge = radius * 2 -1;
		Player player = (Player) sender;
		Location pos = new Location(player.getLocation().getWorld(), player.getLocation().getX()-radius, player.getLocation().getY(), player.getLocation().getZ()-radius);
		Block lookAt =  player.getTargetBlock(null, 6);   
		if (lookAt.getType()!= Material.AIR)
		{
			System.out.println("pos "+(int) lookAt.getX()+":"+(int) lookAt.getY()+"."+lookAt.getType());
		}
		switch (lookAt.getType())
		{
		case SIGN_POST:  // sign standing on ground
			Sign sBlock =	((Sign) lookAt.getState());
    		String l0 = sBlock.getLine(0);
			if (l0.contains("Shop"))
			{
				cmdSignShop(plugin, lookAt, player );
			}
			break;
		default:
		}
		page = 0;
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		return true;
//		if (sender instanceof Player)
//		{
//			return true;
//		}
//		errorMsg.add("Not a console command !");
//		errorMsg.add("The command must send by a Player !");
//		return false;
	}

}