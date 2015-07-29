package com.hangout.quests.quest;

import com.hangout.quests.player.QuestPlayer;

public class QuestObjective {
	
	private Quest parent;
	private String tag;
	private String displayName;
	private QuestObjectiveTypes type;
	private Object goalType;
	private int goalAmount;
	
	public QuestObjective(Quest parent, String tag, String displayName, QuestObjectiveTypes type, Object goalType, int goalAmount){
		this.parent = parent;
		this.tag = tag;
		this.displayName = displayName;
		this.type = type;
		this.goalType = goalType;
		this.goalAmount = goalAmount;
	}
	
	public Quest getParent(){
		return parent;
	}
	
	public String getTag(){
		return tag;
	}
	
	public String getDisplayName(){
		return displayName;
	}
	
	public QuestObjectiveTypes getType(){
		return type;
	}
	
	public Object getGoalType(){
		return goalType;
	}
	
	public int getGoalAmount(){
		return goalAmount;
	}
	
	public boolean hasCompleted(QuestPlayer p){
		return p.getQuestProgress(this) >= getGoalAmount();
	}
}
