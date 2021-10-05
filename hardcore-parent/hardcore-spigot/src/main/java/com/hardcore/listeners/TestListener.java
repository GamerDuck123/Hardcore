package com.hardcore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import com.hardcore.annotations.Listener;
import com.hardcore.interfaces.IListener;

@Listener
public class TestListener implements IListener {
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.getPlayer().sendMessage(color("&ctest"));
	}
}
