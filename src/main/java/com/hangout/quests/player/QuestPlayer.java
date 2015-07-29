package com.hangout.quests.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;

import com.hangout.core.HangoutAPI;
import com.hangout.core.player.HangoutPlayer;
import com.hangout.quests.events.ObjectiveCompleteEvent;
import com.hangout.quests.quest.Quest;
import com.hangout.quests.quest.QuestAction;
import com.hangout.quests.quest.QuestManager;
import com.hangout.quests.quest.QuestObjective;

public class QuestPlayer {
	
	private HangoutPlayer hp;
	private List<Quest> activeQuests = new ArrayList<Quest>();
	private List<Quest> completedQuests = new ArrayList<Quest>();
	private HashMap<QuestObjective, Integer> objectiveProgress = new HashMap<QuestObjective, Integer>();

	public QuestPlayer(HangoutPlayer hp) {
		this.hp = hp;
	}
	
	public HangoutPlayer getHangoutPlayer(){
		return hp;
	}
	
	public List<Quest> getActiveQuests(){
		return activeQuests;
	}
	
	public List<Quest> getCompletedQuests(){
		return completedQuests;
	}
	
	public void addCompletedQuest(Quest q){
		if(!completedQuests.contains(q)){
			completedQuests.add(q);
		}
	}
	
	public void addActiveQuest(Quest q){
		if(activeQuests.contains(q)) return;
		
		activeQuests.add(q);
		
		for(QuestObjective o : q.getObjectives()){
			objectiveProgress.put(o, 0);
		}
		
		QuestManager.commitDatabaseAction(this, q.getObjectives().get(0), QuestAction.ADD);
	}
	
	public void removeActiveQuest(Quest q){
		if(activeQuests.contains(q)){
			activeQuests.remove(q);
			QuestManager.commitDatabaseAction(this, q.getObjectives().get(0), QuestAction.REMOVE);
		}
	}
	
	public void incrementQuestProgress(QuestObjective o, boolean commitToDatabase){
		int progress = 0;
		if(objectiveProgress.containsKey(o)){
			progress = objectiveProgress.get(o);
		}
		progress++;
		setQuestProgress(o, progress);
		
		if(commitToDatabase){
			QuestManager.commitDatabaseAction(this, o, QuestAction.INCREMENT);
			
			if(progress >= o.getGoalAmount()){
				Bukkit.getPluginManager().callEvent(new ObjectiveCompleteEvent(o, this));
			}
		}
	}
	
	public void setQuestProgress(QuestObjective o, int value){
		objectiveProgress.put(o, value);
		HangoutAPI.sendDebugMessage(this.getHangoutPlayer().getName() + " has set progress of " + o.getTag() + " to " + value);
	}
	
	public Integer getQuestProgress(QuestObjective o){
		if(objectiveProgress.containsKey(o)){
			return objectiveProgress.get(o);
		}
		return 0;
	}
}
