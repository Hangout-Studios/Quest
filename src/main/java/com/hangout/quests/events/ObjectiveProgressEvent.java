package com.hangout.quests.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.hangout.quests.player.QuestPlayer;
import com.hangout.quests.quest.QuestObjective;

public class ObjectiveProgressEvent extends Event {
	
	private QuestObjective objective;
	private QuestPlayer player;
	public ObjectiveProgressEvent(QuestObjective objective, QuestPlayer player){
		this.objective = objective;
		this.player = player;
	}
	
	public QuestPlayer getPlayer(){
		return player;
	}
	
	public QuestObjective getObjective(){
		return objective;
	}
	
	
	/*
	 * Handlers
	 */
	private static final HandlerList handlers = new HandlerList();
	 
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}
}
