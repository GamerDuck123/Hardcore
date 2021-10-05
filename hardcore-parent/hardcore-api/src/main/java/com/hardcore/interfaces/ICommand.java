package com.hardcore.interfaces;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public interface ICommand {
	public boolean run(Player p, String[] args);
	public default String color(String s) {return ChatColor.translateAlternateColorCodes('&', s);}
}
