package com.hardcore.handlers;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

import com.hardcore.annotations.Command;
import com.hardcore.interfaces.ICommand;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import net.md_5.bungee.api.ChatColor;

public class CommandHandler implements Listener {
	private CommandHandler() { }
    static CommandHandler instance = new CommandHandler();
    public static CommandHandler a() {return instance;}
	
	HashMap<String, ICommand> cmds;
	HashMap<String, ICommand> aliases;
	
	Plugin plug;	
	
	public boolean setup(Plugin plug, String pkg) {
		long startTime = System.currentTimeMillis();
		this.plug = plug;
		cmds = new HashMap<String, ICommand>();
		aliases = new HashMap<String, ICommand>();
		
		String routeAnnotation = "com.hardcore.annotations.Command";
		try (ScanResult scanResult =
		        new ClassGraph()
//	            	.verbose()               // Log to stderr
		            .enableAllInfo()         // Scan classes, methods, fields, annotations
		            .acceptPackages(pkg)     // Scan com.xyz and subpackages (omit to scan all packages)
		            .scan()) {               // Start the scan
		    for (ClassInfo routeClassInfo : scanResult.getClassesWithAnnotation(routeAnnotation)) {
		    	Class<?> clazz = routeClassInfo.loadClass();
		    	if (ICommand.class.isAssignableFrom(clazz)) {
		    		Annotation[] annons = clazz.getAnnotations();
		    		for (Annotation annon : annons) {
		    			if (annon instanceof Command) {
		    				Command myannon = (Command) annon;
				    		try {
				    			ICommand cmdclass = (ICommand) clazz.getConstructor().newInstance();
								cmds.put(myannon.command(), cmdclass);
								for (String alias : myannon.aliases()) aliases.put(alias, cmdclass);
								Bukkit.getLogger().info(aliases.keySet().toString());
							} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
									| InvocationTargetException | NoSuchMethodException | SecurityException e) {
								e.printStackTrace();
							}
		    			}
		    		}
		    	}
		    }
		}
		plug.getServer().getPluginManager().registerEvents(this, plug);
		long elapsedTime = System.currentTimeMillis() - startTime;
		Bukkit.getLogger().info("Commands have been loaded and it took " + elapsedTime + " ms");
		return true;
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		String[] args = e.getMessage().split(" ");
		String cmd = args[0].replaceFirst("/", "");
		long startTime = System.currentTimeMillis();
		if (cmds.containsKey(cmd)) {
			e.setCancelled(true);
			ICommand cmdclass = cmds.get(cmd);
    		Command annon = cmdclass.getClass().getAnnotation(Command.class);
    			if (annon instanceof Command) {
    				Command myannon = (Command) annon;
    				if (!e.getPlayer().hasPermission(myannon.permission())) {
    					e.getPlayer().sendMessage(color(myannon.noperms()));
    				} else {
    					cmdclass.run(e.getPlayer(), args);
    				}
    			}
		} else if (aliases.containsKey(cmd)) {
			e.setCancelled(true);
			ICommand cmdclass = aliases.get(cmd);
    		Command annon = cmdclass.getClass().getAnnotation(Command.class);
    			if (annon instanceof Command) {
    				Command myannon = (Command) annon;
    				if (!e.getPlayer().hasPermission(myannon.permission())) {
    					e.getPlayer().sendMessage(color(myannon.noperms()));
    				} else {
    					cmdclass.run(e.getPlayer(), args);
    				}
    			}
    		}
		long elapsedTime = System.currentTimeMillis() - startTime;
		Bukkit.getLogger().info(e.getPlayer() + " ran " + cmd + " and it took " + elapsedTime + " ms");
	}
	
	
	private String color(String s) {return ChatColor.translateAlternateColorCodes('&', s);}
	
}
