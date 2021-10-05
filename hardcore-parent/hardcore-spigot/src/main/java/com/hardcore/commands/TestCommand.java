package com.hardcore.commands;

import org.bukkit.entity.Player;

import com.hardcore.annotations.Command;
import com.hardcore.interfaces.ICommand;


@Command(command = "test",
		permission = "test.plugin",
		aliases = {"testplugin", "ts"},
		description = "Tests the server's plugin")
public class TestCommand implements ICommand {

	@Override
	public boolean run(Player p, String[] args) {
		p.sendMessage("TEST");
		return false;
	}
	

}
