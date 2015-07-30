package com.hangout.quests.listener;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.hangout.core.events.MenuCreateEvent;
import com.hangout.core.events.MenuItemClickEvent;
import com.hangout.core.menu.MenuInventory;
import com.hangout.core.menu.MenuUtils;
import com.hangout.quests.player.QuestPlayerManager;
import com.hangout.quests.quest.Quest;
import com.hangout.quests.quest.QuestManager;
import com.hangout.quests.utils.QuestMenuUtils;

public class MenuListener implements Listener {
	
	@EventHandler
	public void onMenuCreate(MenuCreateEvent e){
		MenuInventory menu = e.getMenu();
		if(e.getMenu().getTag().equals("main_menu")){
			MenuUtils.createMenuItem(menu, Material.BOOK_AND_QUILL, "Quest list", Arrays.asList("Check out your quests!"), 3 + 9, "quest_item");
		}
	}
	
	@EventHandler
	public void onMenuClick(MenuItemClickEvent e){
		if(e.getItem().getTag().equals("quest_item")){
			QuestMenuUtils.createQuestListMenu(e.getPlayer(), QuestPlayerManager.getPlayer(e.getPlayer().getUUID()).getActiveQuests(), 0, true).openMenu(e.getPlayer());
		}
		
		if(e.getItem().getTag().startsWith("quest_menu_page_")){
			int pageNumber = Integer.parseInt(e.getItem().getTag().split("_")[3]);
			QuestMenuUtils.createQuestListMenu(e.getPlayer(), QuestPlayerManager.getPlayer(e.getPlayer().getUUID()).getActiveQuests(), pageNumber, true).openMenu(e.getPlayer());
		}
		
		if(e.getItem().getTag().startsWith("quest_info_")){
			Quest q = QuestManager.getQuest(e.getItem().getTag().replace("quest_info_", ""));
			
			if(q == null){
				System.out.print("Quest not found: " + e.getItem().getTag().split("_")[2]);
				return;
			}
			QuestMenuUtils.createQuestMenu(e.getPlayer(), q).openMenu(e.getPlayer());
		}
		
		if(e.getItem().getTag().startsWith("quest_completed_page_")){
			int pageNumber = Integer.parseInt(e.getItem().getTag().split("_")[3]);
			QuestMenuUtils.createQuestListMenu(e.getPlayer(), QuestPlayerManager.getPlayer(e.getPlayer().getUUID()).getCompletedQuests(), pageNumber, false).openMenu(e.getPlayer());
		}
	}
}
