package com.hangout.quests.listener;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.hangout.core.Config;
import com.hangout.core.events.PlayerJoinCompleteEvent;
import com.hangout.core.events.PlayerPostLoadEvent;
import com.hangout.core.events.PlayerQuitCompleteEvent;
import com.hangout.core.player.CommonPlayerManager;
import com.hangout.core.utils.database.Database;
import com.hangout.core.utils.mc.DebugUtils;
import com.hangout.quests.Plugin;
import com.hangout.quests.player.QuestPlayer;
import com.hangout.quests.player.QuestPlayerManager;
import com.hangout.quests.quest.Quest;
import com.hangout.quests.quest.QuestManager;
import com.hangout.quests.quest.QuestObjective;

public class PlayerListener implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinCompleteEvent e){
		
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitCompleteEvent e){
		QuestPlayerManager.removePlayer(e.getUUID());
	}
	
	@EventHandler
	public void onPlayerPostLoad(final PlayerPostLoadEvent e){
		e.getPlayer().setLoadingState("quest", false);
		Bukkit.getScheduler().runTaskAsynchronously(Plugin.getInstance(), new Runnable(){

			@Override
			public void run() {
				
				QuestPlayer p = new QuestPlayer(e.getPlayer());
				QuestPlayerManager.addPlayer(p);
				CommonPlayerManager.addPlayer(e.getPlayer().getUUID(), "Quest", p);
				
				loadQuests(p);
				
				e.getPlayer().setLoadingState("quest", true);
				
				DebugUtils.sendDebugMessage("Loaded Quest player: " + e.getPlayer().getName());
			}
			
		});
	}
	
	private void loadQuests(QuestPlayer p){
		for(Quest q : QuestManager.getQuests()){
			String tag = q.getTag();
			
			try (PreparedStatement pst = Database.getConnection().prepareStatement(
	                "SELECT action, objective_id FROM " + Config.databaseName + ".quest_action WHERE player_id = ? AND quest_id = ? "
	                		+ "ORDER BY action_id DESC;")) {
				pst.setString(1, p.getHangoutPlayer().getUUID().toString());
				pst.setString(2, tag);
				ResultSet rs = pst.executeQuery();
				
				boolean searchForComplete = false;
				while(rs.next()){
					String action = rs.getString("action");
					String objective = rs.getString("objective_id");
					
					QuestObjective o = q.getObjective(objective);
					
					if(!searchForComplete){
						if(action.equals("INCREMENT")){
							p.incrementQuestProgress(o, false);
						}
						
						if(action.equals("ADD")){
							p.addActiveQuest(q, false);
							searchForComplete = true;
						}
					}else{
						if(action.equals("COMPLETE")){
							p.addCompletedQuest(q);
							break;
						}
					}
				}
				
				pst.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
