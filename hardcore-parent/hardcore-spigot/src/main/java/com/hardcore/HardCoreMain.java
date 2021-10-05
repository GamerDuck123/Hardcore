package com.hardcore;

import org.bukkit.plugin.java.JavaPlugin;

import com.hardcore.handlers.CommandHandler;
import com.hardcore.handlers.ListenerHandler;

public class HardCoreMain extends JavaPlugin {
	@Override
	public void onEnable() {
		ListenerHandler.a().setup(this, "com.hardcore.listeners");
		CommandHandler.a().setup(this, "com.hardcore.commands");
	}
	
}
