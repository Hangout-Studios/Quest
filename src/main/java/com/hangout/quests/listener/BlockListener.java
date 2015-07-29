package com.hangout.quests.listener;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.hangout.quests.events.ObjectiveProgressEvent;
import com.hangout.quests.player.QuestPlayer;
import com.hangout.quests.player.QuestPlayerManager;
import com.hangout.quests.quest.QuestManager;
import com.hangout.quests.quest.QuestObjective;
import com.hangout.quests.quest.QuestObjectiveTypes;

public class BlockListener implements Listener {
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		QuestPlayer player = QuestPlayerManager.getPlayer(e.getPlayer().getUniqueId());
		List<QuestObjective> objectives = QuestManager.getObjectives(QuestObjectiveTypes.BLOCK_DESTROY);
		for(QuestObjective o : objectives){
			if(o.getGoalType().equals(e.getBlock().getType())){
				Bukkit.getPluginManager().callEvent(new ObjectiveProgressEvent(o, player));
			}
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e){
		QuestPlayer player = QuestPlayerManager.getPlayer(e.getPlayer().getUniqueId());
		List<QuestObjective> objectives = QuestManager.getObjectives(QuestObjectiveTypes.BLOCK_PLACE);
		for(QuestObjective o : objectives){
			if(o.getGoalType().equals(e.getBlock().getType())){
				Bukkit.getPluginManager().callEvent(new ObjectiveProgressEvent(o, player));
			}
		}
	}
}
