package com.hangout.quests.quest;

import org.bukkit.Material;

public enum QuestObjectiveTypes {
	KILL_MOB("Kill mob", "Kill <type> <progress>", "ZOMBIE", Material.IRON_SWORD),
	KILL_PLAYER("Kill player", "Kill <progress> players", "", Material.SKULL_ITEM),
	BLOCK_DESTROY("Destroy block", "Destroy <progress> <type> blocks", Material.DIRT, Material.IRON_PICKAXE),
	BLOCK_PLACE("Place block", "Place <progress> <type> blocks", Material.DIRT, Material.IRON_BLOCK);
	
	private String displayName;
	private String formatting;
	private Object defaultGoalType;
	private Material mat;
	
	QuestObjectiveTypes(String displayName, String formatting, Object goalType, Material mat){
		this.displayName = displayName;
		this.formatting = formatting;
		this.defaultGoalType = goalType;
		this.mat = mat;
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
	
	public Material getMaterial(){
		return mat;
	}
}
