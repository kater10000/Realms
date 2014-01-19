package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import java.util.Set;

import net.krglok.realms.CmdRealmsHelp;
import net.krglok.realms.CmdRealmsVersion;
import net.krglok.realms.CommandParser;
import net.krglok.realms.RealmsCommand;
import net.krglok.realms.cmdRealmNone;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.junit.Test;

public class CommandParserTest
{

	private RealmsCommand[] makeCommandList()
	{
		RealmsCommand[] commandList = new RealmsCommand[] {
			new cmdRealmNone(),
			new CmdRealmsVersion(),
			new CmdRealmsHelp()
			
		};
		return commandList;
	}
	
	
	
	@Test
	public void testGetRealmsCommand()
	{
		class TestSender implements CommandSender
		{

			@Override
			public PermissionAttachment addAttachment(Plugin arg0)
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public PermissionAttachment addAttachment(Plugin arg0, int arg1)
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public PermissionAttachment addAttachment(Plugin arg0, String arg1,
					boolean arg2)
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public PermissionAttachment addAttachment(Plugin arg0, String arg1,
					boolean arg2, int arg3)
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Set<PermissionAttachmentInfo> getEffectivePermissions()
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean hasPermission(String arg0)
			{
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public boolean hasPermission(Permission arg0)
			{
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public boolean isPermissionSet(String arg0)
			{
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public boolean isPermissionSet(Permission arg0)
			{
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public void recalculatePermissions()
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void removeAttachment(PermissionAttachment arg0)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean isOp()
			{
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public void setOp(boolean arg0)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public String getName()
			{
				// TODO Auto-generated method stub
				return "Test User";
			}

			@Override
			public Server getServer()
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void sendMessage(String arg0)
			{
				// TODO Auto-generated method stub
				System.out.println(arg0);
			}

			@Override
			public void sendMessage(String[] arg0)
			{
				// TODO Auto-generated method stub
				for (int i = 0; i < arg0.length; i++)
				{
					System.out.println(arg0[i]);
				}
			}
			
		}
		
		
		RealmsCommand[] cmdList = makeCommandList();
		CommandParser parser = new CommandParser(cmdList);
		String[] args = new String[] {
			"help",	
			"2"	,
			"2"
		};
		String s = "INPUT: realms";
		for (int i = 0; i < args.length; i++)
		{
			s = s + " "+args[i];
		}
		System.out.println(s);
		System.out.println("OUTPUT:");
		RealmsCommand cmd = parser.getRealmsCommand(args);
		CommandSender sender = new TestSender();
		if (cmd != null)
		{
//			System.out.println("Command found "+cmd.command()+" "+cmd.subCommand());
			String [] msg = cmd.getDescription();
			for (int i = 0; i < msg.length; i++)
			{
				System.out.println(msg[i]);
			}
			if (cmd.canExecute(null, sender))
			{
				System.out.println("");
				System.out.println("Command will be Executed !");
			}
		}else
		{
			System.out.println("NO Command found ");
		}
		
		fail("Not yet implemented");
	}

}
