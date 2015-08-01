package com.hangout.quests.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.hangout.core.utils.mc.DebugUtils;
import com.hangout.core.utils.mc.DebugUtils.DebugMode;
import com.hangout.quests.events.ObjectiveCompleteEvent;
import com.hangout.quests.events.ObjectiveProgressEvent;
import com.hangout.quests.events.QuestCompleteEvent;
import com.hangout.quests.quest.Quest;
import com.hangout.quests.quest.QuestAction;
import com.hangout.quests.quest.QuestManager;

public class QuestListener implements Listener {
	
	@EventHandler
	public void onObjectiveProgress(ObjectiveProgressEvent e){
		if(!e.getPlayer().getActiveQuests().contains(e.getObjective().getParent()) ||
				e.getObjective().hasCompleted(e.getPlayer())) return;
		
		DebugUtils.sendDebugMessage("Player " + e.getPlayer().getHangoutPlayer().getName() + " has progressed quest " + e.getObjective().getParent().getDisplayName(), DebugMode.DEBUG);
		e.getPlayer().incrementQuestProgress(e.getObjective(), true);
		
		e.getObjective().getParent().getRewards().reward(e.getPlayer());
	}
	
	@EventHandler
	public void onObjectiveComplete(ObjectiveCompleteEvent e){
		Quest q = e.getObjective().getParent();
		
		QuestManager.commitDatabaseAction(e.getPlayer(), e.getObjective(), QuestAction.OBJECTIVE_COMPLETE);
		
		DebugUtils.sendDebugMessage("Player " + e.getPlayer().getHangoutPlayer().getName() + " has completed objective " + e.getObjective().getDisplayName(), DebugMode.DEBUG);
		
		if(q.hasCompleted(e.getPlayer())){
			Bukkit.getPluginManager().callEvent(new QuestCompleteEvent(q, e.getPlayer()));
		}
	}
	
	@EventHandler
	public void onQuestComplete(QuestCompleteEvent e){
		
		QuestManager.commitDatabaseAction(e.getPlayer(), e.getQuest().getObjectives().get(0), QuestAction.COMPLETE);
		e.getQuest().getRewards().reward(e.getPlayer());
		
		DebugUtils.sendDebugMessage("Player " + e.getPlayer().getHangoutPlayer().getName() + " has completed quest " + e.getQuest().getDisplayName(), DebugMode.DEBUG);
	}
}
