package com.hangout.quests;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import com.hangout.core.utils.lang.MessageManager;
import com.hangout.quests.listener.BlockListener;
import com.hangout.quests.listener.MenuListener;
import com.hangout.quests.listener.PlayerListener;
import com.hangout.quests.listener.QuestListener;
import com.mysql.jdbc.StringUtils;

public class Plugin extends JavaPlugin {
	
	private static Plugin instance;
	private MessageManager messageBundle;
	
	public void onEnable(){
		
		instance = this;
		messageBundle = new MessageManager(getConfig().getString("locale", "en"));
		
		this.saveDefaultConfig();
		Config.loadQuests();
		
		this.getServer().getPluginManager().registerEvents(new MenuListener(), this);
		this.getServer().getPluginManager().registerEvents(new BlockListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		this.getServer().getPluginManager().registerEvents(new QuestListener(), this);
		
		//MenuUtils.createMenuItem(HangoutAPI.getMenu("mainmenu"), Material.BOOK_AND_QUILL, "Quest list", Arrays.asList("Check out your quests!"), 2 + 9, "quest_item");
	}
	
	public void onDisable(){
		
	}
	
	public static Plugin getInstance(){
		return instance;
	}
	
	public static String getString(String s){
        if (Plugin.instance.messageBundle == null) {
            return s;
        }
        String message = Plugin.instance.messageBundle.getString(s);
        if (StringUtils.isNullOrEmpty(message)) {
            return s;
        } else {
            return ChatColor.translateAlternateColorCodes('&', message.replace("%&", " "));
        }
	}
}
