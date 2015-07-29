package com.hangout.quests.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.hangout.quests.player.QuestPlayer;
import com.hangout.quests.quest.QuestObjective;

public class ObjectiveCompleteEvent extends Event {
	
	private QuestObjective o;
	private QuestPlayer p;
	public ObjectiveCompleteEvent(QuestObjective o, QuestPlayer p){
		this.o = o;
		this.p = p;
	}
	
	public QuestObjective getObjective(){
		return o;
	}
	
	public QuestPlayer getPlayer(){
		return p;
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
