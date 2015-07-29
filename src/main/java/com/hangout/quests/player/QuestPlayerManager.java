package com.hangout.quests.player;

import java.util.HashMap;
import java.util.UUID;

public class QuestPlayerManager {
	
	private static HashMap<UUID, QuestPlayer> questPlayers = new HashMap<UUID, QuestPlayer>();
	
	public static void addPlayer(QuestPlayer p){
		questPlayers.put(p.getHangoutPlayer().getUUID(), p);
	}
	
	public static QuestPlayer getPlayer(UUID id){
		if(questPlayers.containsKey(id)){
			return questPlayers.get(id);
		}
		return null;
	}
	
	public static void removePlayer(UUID id){
		if(questPlayers.containsKey(id)){
			
			/*
			 * Unregister player from objectives here
			 */
			
			questPlayers.remove(id);
		}
	}
}
