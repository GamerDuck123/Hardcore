package com.hardcore.interfaces;

import org.bukkit.event.Listener;

import net.md_5.bungee.api.ChatColor;

public interface IListener extends Listener {
	public default String color(String s) {return ChatColor.translateAlternateColorCodes('&', s);}
}
