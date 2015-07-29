package com.hangout.quests.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.hangout.quests.player.QuestPlayer;
import com.hangout.quests.quest.Quest;

public class QuestCompleteEvent extends Event {
	
	private Quest q;
	private QuestPlayer p;
	public QuestCompleteEvent(Quest q, QuestPlayer p){
		this.q = q;
		this.p = p;
	}
	
	public Quest getQuest(){
		return q;
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
