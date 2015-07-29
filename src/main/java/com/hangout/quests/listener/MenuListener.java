package com.hangout.quests.listener;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.hangout.core.HangoutAPI;
import com.hangout.core.events.MenuClickEvent;
import com.hangout.core.menu.MenuInventory;
import com.hangout.core.utils.mc.ItemUtils;
import com.hangout.quests.player.QuestPlayerManager;
import com.hangout.quests.quest.Quest;

public class MenuListener implements Listener {
	
	@EventHandler
	public void onMenuClick(MenuClickEvent e){
		if(!e.getItem().getTag().equals("quest_item")) return;
		
		MenuInventory questMenu = HangoutAPI.createMenu(
				ItemUtils.createItem(Material.DIRT, "quest_item", Arrays.asList("lel")), "Quest list", 4, "quest_menu");
		questMenu.setTemporary(true);
		
		int count = 0;
		for(Quest q : QuestPlayerManager.getPlayer(e.getPlayer().getUUID()).getActiveQuests()){
			HangoutAPI.createMenuItem(questMenu, q.getIconMaterial(), q.getDisplayName(), q.getDescription(), count, q.getTag());
		}
		
		questMenu.openMenu(e.getPlayer());
	}
}
