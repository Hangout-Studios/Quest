package com.hangout.quests.quest;

import java.util.List;

import org.bukkit.Material;

import com.hangout.quests.player.QuestPlayer;

public class Quest {
	
	private String tag;
	private String displayName;
	private List<String> description;
	private List<QuestObjective> objectiveList;
	private Material iconMaterial;
	private QuestRequirements requirements;
	private QuestRewards rewards;
	
	public Quest(String tag, String displayName, List<String> description, Material iconMaterial){
		this.tag = tag;
		this.displayName = displayName;
		this.description = description;
		this.iconMaterial = iconMaterial;
	}
	
	public String getTag(){
		return tag;
	}
	
	public String getDisplayName(){
		return displayName;
	}
	
	public List<String> getDescription(){
		return description;
	}
	
	public List<QuestObjective> getObjectives(){
		return objectiveList;
	}
	
	public void setObjectives(List<QuestObjective> objectives){
		this.objectiveList = objectives;
	}
	
	public QuestObjective getObjective(String tag){
		for(QuestObjective o : getObjectives()){
			if(o.getTag().equals(tag)) return o;
		}
		return null;
	}
	
	public Material getIconMaterial(){
		return iconMaterial;
	}
	
	public QuestRequirements getRequirements(){
		return requirements;
	}
	
	public void setQuestRequirements(QuestRequirements requirements){
		this.requirements = requirements;
	}
	
	public QuestRewards getRewards(){
		return rewards;
	}
	
	public void setRewards(QuestRewards rewards){
		this.rewards = rewards;
	}
	
	public boolean hasCompleted(QuestPlayer p){
		for(QuestObjective o : objectiveList){
			if(!o.hasCompleted(p)){
				return false;
			}
		}
		return true;
	}
}
