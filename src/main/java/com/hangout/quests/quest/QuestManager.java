package com.hangout.quests.quest;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;

import com.hangout.core.Config;
import com.hangout.core.utils.database.Database;
import com.hangout.core.utils.mc.DebugUtils;
import com.hangout.quests.Plugin;
import com.hangout.quests.player.QuestPlayer;

public class QuestManager {
	
	private static HashMap<String, Quest> registeredQuests = new HashMap<String, Quest>();
	private static HashMap<QuestObjectiveTypes, List<QuestObjective>> registeredObjectives = new HashMap<QuestObjectiveTypes, List<QuestObjective>>();
	
	public static List<Quest> getQuests(){
		return new ArrayList<Quest>(registeredQuests.values());
	}
	
	public static Quest getQuest(String tag){
		if(registeredQuests.containsKey(tag)){
			return registeredQuests.get(tag);
		}
		return null;
	}
	
	public static List<QuestObjective> getObjectives(QuestObjectiveTypes type){
		if(!registeredObjectives.containsKey(type)){
			return new ArrayList<QuestObjective>();
		}else{
			return registeredObjectives.get(type);
		}
	}
	
	public static void registerQuest(Quest q){
		registeredQuests.put(q.getTag(), q);
		
		for(QuestObjective o : q.getObjectives()){
			registerObjective(o);
		}
		
		DebugUtils.sendDebugMessage("Successfully registered quest: " + q.getDisplayName());
	}
	
	public static void registerObjective(QuestObjective objective){
		List<QuestObjective> list;
		if(!registeredObjectives.containsKey(objective.getType())){
			list = new ArrayList<QuestObjective>();
		}else{
			list = registeredObjectives.get(objective.getType());
		}
		list.add(objective);
		registeredObjectives.put(objective.getType(), list);
	}
	
	public static void commitDatabaseAction(final QuestPlayer player, final QuestObjective objective, final QuestAction action){
		Bukkit.getScheduler().runTaskAsynchronously(Plugin.getInstance(), new Runnable(){

			@Override
			public void run() {
				try(PreparedStatement pst = Database.getConnection().prepareStatement(
		    			"INSERT INTO " + Config.databaseName + ".quest_action (quest_id, objective_id, action, player_id) VALUES (?, ?, ?, ?)")){
					pst.setString(1, objective.getParent().getTag());
					pst.setString(2, objective.getTag());
					pst.setString(3, action.toString());
					pst.setString(4, player.getHangoutPlayer().getUUID().toString());
		    		pst.execute();
		    		pst.close();
		    	} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
