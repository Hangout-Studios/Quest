package com.hangout.quests.utils;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;

import com.hangout.core.menu.MenuInventory;
import com.hangout.core.menu.MenuUtils;
import com.hangout.core.player.HangoutPlayer;
import com.hangout.quests.player.QuestPlayer;
import com.hangout.quests.player.QuestPlayerManager;
import com.hangout.quests.quest.Quest;
import com.hangout.quests.quest.QuestObjective;

public class QuestMenuUtils {

	public static MenuInventory createQuestListMenu(HangoutPlayer p, List<Quest> quests, int pageNumber, boolean normalQuests){
		
		String type = "completed";
		String menuTitle = "Completed story quests - Page " + (pageNumber + 1);
		if(normalQuests){
			type = "menu";
			menuTitle = "Quest list - Page " + (pageNumber + 1);
		}
		
		MenuInventory questMenu = MenuUtils.createMenu(menuTitle, "quest_menu", p);
		questMenu.setTemporary(true);
		
		int count = 0;
		for(int q = pageNumber * 18; q < (pageNumber + 1) * 18 && q < quests.size(); q++){
			Quest quest = quests.get(q);
			MenuUtils.createMenuItem(questMenu, quest.getIconMaterial(), quest.getDisplayName(), quest.getDescription(), count, "quest_info_" + quest.getTag());
			count++;
		}
		
		if(count == 18 && quests.size() >= (pageNumber + 1) * 18){
			MenuUtils.createMenuItem(questMenu, Material.ARROW, "Next page", Arrays.asList("Click to look at", "more quests."), 26, "quest_"+type+"_page_" + (pageNumber + 1));
		}
		
		if(pageNumber >= 1){
			MenuUtils.createMenuItem(questMenu, Material.ARROW, "Previous page", Arrays.asList("Click to look at", "previous quests."), 18, "quest_"+type+"_page_" + (pageNumber - 1));
		}
		
		if(normalQuests){
			MenuUtils.createMenuItem(questMenu, Material.NETHER_STAR, "Completed Story Quests", Arrays.asList("Check your legacy!"), 24, "quest_completed_page_0");
		}else{
			MenuUtils.createMenuItem(questMenu, Material.BOOK_AND_QUILL, "Active Quests", Arrays.asList("Check your active quests!"), 24, "quest_menu_page_0");
		}
		return questMenu;
	}
	
	public static MenuInventory createQuestMenu(HangoutPlayer p, Quest q){
		MenuInventory questMenu = MenuUtils.createMenu("Quest " + q.getDisplayName(), "quest_" + q.getTag(), p);
		questMenu.setTemporary(true);
		
		QuestPlayer pQ = QuestPlayerManager.getPlayer(p.getUUID());
		
		int count = 0;
		for(QuestObjective o : q.getObjectives()){
			MenuUtils.createMenuItem(questMenu, o.getType().getMaterial(), o.getDisplayName(), Arrays.asList(pQ.getQuestProgressString(o)), count, "quest_objective_" + o.getTag());
			count++;
		}
		
		return questMenu;
	}
}
