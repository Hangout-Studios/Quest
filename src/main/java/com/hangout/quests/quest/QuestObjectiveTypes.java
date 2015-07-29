package com.hangout.quests.quest;

import org.bukkit.Material;

public enum QuestObjectiveTypes {
	KILL_MOB("Kill mob", "Kill mob <type> <progress>", "ZOMBIE"),
	KILL_PLAYER("Kill player", "Kill <progress> players", ""),
	BLOCK_DESTROY("Destroy block", "Destroy <progress> <type> blocks", Material.DIRT),
	BLOCK_PLACE("Place block", "Place <progress> <type> blocks", Material.DIRT);
	
	private String displayName;
	private String formatting;
	private Object defaultGoalType;
	
	QuestObjectiveTypes(String displayName, String formatting, Object goalType){
		this.displayName = displayName;
		this.formatting = formatting;
		this.defaultGoalType = goalType;
	}
	
	public String getDisplayName(){
		return displayName;
	}
	
	public String getFormatting(){
		return formatting;
	}
	
	public Object getDefaultGoalType(){
		return defaultGoalType;
	}
}
