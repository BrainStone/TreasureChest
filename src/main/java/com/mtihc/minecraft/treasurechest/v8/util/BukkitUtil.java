package com.mtihc.minecraft.treasurechest.v8.util;

import java.util.UUID;
import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import com.mtihc.minecraft.treasurechest.v8.util.commands.CommandException;

public final class BukkitUtil {

	@Nonnull
	public static OfflinePlayer findOfflinePlayer(@Nonnull String playerName)
			throws CommandException {
		OfflinePlayer p = null;
		OfflinePlayer[] offlinePlayers = Bukkit.getOfflinePlayers();

		for (OfflinePlayer offlinePlayer : offlinePlayers) {
			if (playerName.equalsIgnoreCase(offlinePlayer.getName())) {
				if (p != null) {
					throw new CommandException(
							"Found multiple players named \"" + playerName + "\". Try using a UUID instead.");
				}
				p = offlinePlayer;
			}
		}

		if ((p == null) || !(p.isOnline() || p.hasPlayedBefore())) {
			try {
				UUID uuid = UUID.fromString(playerName);
				p = Bukkit.getOfflinePlayer(uuid);
			} catch (IllegalArgumentException e) {
				throw new CommandException("Invalid UUID format \"" + playerName + "\"");
			}
		}

		if (!(p.isOnline() || p.hasPlayedBefore())) {
			throw new CommandException("Player \"" + playerName + "\" does not exist.");
		}

		return p;
	}
}
