package net.krglok.realms.command;

import java.io.File;
import java.util.ArrayList;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.RegionType;
import net.krglok.realms.Realms;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.tool.StrongholdTools;
import net.krglok.realms.unittest.RegionConfig;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

public class CmdRealmsBuilding extends RealmsCommand
{

	private int page;
	private String search ;

	public CmdRealmsBuilding()
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.BUILDING);
		description = new String[] {
			ChatColor.YELLOW+"/realms BUILDING {BUILDINGTYPE} ",
			"Show construction details of BuildingType ",
			"If no BuildingType a List of Buildings shown",
			" "
		};
		requiredArgs = 0;
		page = 1;
		search = "";
	}

	@Override
	public void setPara(int index, String value)
	{
		switch (index)
		{
		case 0 :
				search = value;
			break;
		default:
			break;
		}

	}

	@Override
	public void setPara(int index, int value)
	{

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
		return new String[] { String.class.getName() };
	}

	private String toLine(String s, String value)
	{
		s = s + " "+ConfigBasis.setStrleft(value+"________", 12);
		return s;
	}

	private ArrayList<String> BuildingSection(int von, int bis, ChatColor color)
	{
    	ArrayList<String> msg = new ArrayList<String>();
    	int index = 0; 
    	String line = "";
    	for (BuildPlanType bType : BuildPlanType.values())
    	{
    		if ((bType.getValue() > von) && (bType.getValue() < bis) )
    		{
    			if (index >= 4)
    			{
    				msg.add(color+line);
    				index = 0;
    				line = "";
    			}
    			index++;
    			line = toLine(line,bType.name());
    		}
    	}
		if (line.length() > 0)
		{
			msg.add(color+line);
			index = 0;
			line = "";
		}

    	return msg;
	}
	
	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();
    	if (search == "")
    	{
//        	msg.add(ChatColor.GREEN+"BuildPlans available in Realms ");
        	msg.addAll(BuildingSection(50, 200,ChatColor.GOLD));
//        	msg.addAll(BuildingSection(100, 200,ChatColor.YELLOW));
        	msg.addAll(BuildingSection(200, 300,ChatColor.GREEN));
        	msg.addAll(BuildingSection(300, 400,ChatColor.LIGHT_PURPLE));
//        	msg.addAll(BuildingSection(400, 500,ChatColor.RED));
        	msg.addAll(BuildingSection(500, 1000,ChatColor.GOLD));
    		
    	} else
    	{
        	msg.add(ChatColor.GREEN+"BuildPlan minimum requirements for build a  "+ChatColor.YELLOW+search);
//        	String pathName = plugin.stronghold.getDataFolder().getPath();
//            File regionFolder = new File(pathName, "RegionConfig");
//        	RegionConfig region = StrongholdTools.getRegionConfig(regionFolder.getAbsolutePath(), search+".yml");
        	RegionType region = plugin.stronghold.getRegionManager().getRegionType(search);
        	if (region != null)
        	{
            	msg.add(ChatColor.GREEN+"Radius   : "+ChatColor.YELLOW+region.getRawRadius());
            	msg.add(ChatColor.GREEN+"Required Blocks :");
            	int index = 0; 
        		String line = "";
            	for (ItemStack item : region.getRequirements())
            	{
            		index++;
            		if (index <= 3)
            		{
            			line = line + item.getType().name()+" : "+item.getAmount()+" | ";
            		} else
            		{
            			msg.add(ChatColor.YELLOW+line);
            			line = item.getType().name()+" : "+item.getAmount()+" | ";
            			index = 1;
            		}
            	}
        		if (line.length() > 0)
        		{
        			msg.add(ChatColor.YELLOW+line);
        		}
            	msg.add(" ");
            	msg.add(ChatColor.GREEN+"Required Resources in center chest :");
            	index = 0; 
        		line = "";
            	for (ItemStack item : region.getReagents())
            	{
            		index++;
            		if (index <= 3)
            		{
            			line = line + item.getType().name()+" : "+item.getAmount()+" | ";
            		} else
            		{
            			msg.add(ChatColor.LIGHT_PURPLE+line);
            			line = item.getType().name()+" : "+item.getAmount()+" | ";
            			index = 1;
            		}
            	}
        		if (line.length() > 0)
        		{
        			msg.add(ChatColor.YELLOW+line);
        		}
            	msg.add(" ");
            	msg.add(ChatColor.GREEN+"needed for ACQUIRE in Realms:");
            	msg.add(ChatColor.GREEN+"MoneyCost: "+ChatColor.YELLOW+region.getMoneyRequirement());
        	} else
        	{
            	msg.add(ChatColor.RED+"Building not found");
            	msg.add(ChatColor.GREEN+"Try command /realms BUILDING (without parameter)");
        		
        	}
        			
//    		ItemList required = plugin.getServerData().getRegionUpkeep(search);
    	}
		plugin.getMessageData().printPage(sender, msg, page);
		helpPage = "";
		page = 1;
		msg.clear();
		search = "";
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		// anyone can use
		return true;
	}

}