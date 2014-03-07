package com.massivecraft.factions.integration;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import com.massivecraft.factions.Conf;
import com.earth2me.essentials.IEssentials;
import com.earth2me.essentials.Teleport;
import com.earth2me.essentials.Trade;


/*
 * This Essentials integration handler is for newer 3.x.x versions of Essentials which don't have "IEssentialsChatListener"
 * If an older version is detected in the setup() method below, handling is passed off to EssentialsOldVersionFeatures
 */

// silence deprecation warnings with this old interface
@SuppressWarnings("deprecation")
public class EssentialsFeatures
{
	private static IEssentials essentials;

	public static void setup()
	{
		// integrate main essentials plugin
		// TODO: this is the old Essentials method not supported in 3.0... probably needs to eventually be moved to EssentialsOldVersionFeatures and new method implemented
		if (essentials == null)
		{
			Plugin ess = Bukkit.getPluginManager().getPlugin("Essentials");
			if (ess != null && ess.isEnabled())
				essentials = (IEssentials)ess;
		}
	}

	// return false if feature is disabled or Essentials isn't available
	public static boolean handleTeleport(Player player, Location loc)
	{
		if ( ! Conf.homesTeleportCommandEssentialsIntegration || essentials == null) return false;

		Teleport teleport = (Teleport) essentials.getUser(player).getTeleport();
		Trade trade = new Trade(Conf.econCostHome, essentials);
		try
		{
			teleport.teleport(loc, trade);
		}
		catch (Exception e)
		{
			player.sendMessage(ChatColor.RED.toString()+e.getMessage());
		}
		return true;
	}
}
