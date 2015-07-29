package com.hangout.quests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import com.hangout.quests.quest.Quest;
import com.hangout.quests.quest.QuestManager;
import com.hangout.quests.quest.QuestObjective;
import com.hangout.quests.quest.QuestObjectiveTypes;
import com.hangout.quests.quest.QuestRequirements;
import com.hangout.quests.quest.QuestRewards;

public class Config {
	
	public static void loadQuests(){
		FileConfiguration config = Plugin.getInstance().getConfig();
		
		HashMap<Quest, List<String>> requiredQuestsMap = new HashMap<Quest, List<String>>();
		
		for(String questTag : config.getConfigurationSection("Quests").getKeys(false)){
			ConfigurationSection questSection = config.getConfigurationSection("Quests." + questTag);
			String questName = questSection.getString("Display_Name");
			List<String> questDescription = questSection.getStringList("Description");
			Material questIconMaterial = Material.valueOf(questSection.getString("Icon_Material"));
			
			Quest q = new Quest(questTag, questName, questDescription, questIconMaterial);
			
			/*
			 * Objectives
			 */
			List<QuestObjective> objectives = new ArrayList<QuestObjective>();
			for(String objectiveTag : questSection.getConfigurationSection("Objectives").getKeys(false)){
				ConfigurationSection objectiveSection = questSection.getConfigurationSection("Objectives." + objectiveTag);
				
				String objectiveName = objectiveSection.getString("Display_Name");
				QuestObjectiveTypes type = QuestObjectiveTypes.valueOf(objectiveSection.getString("Type"));
				Object goalType = null;
				switch(type){
				case BLOCK_DESTROY:
				case BLOCK_PLACE:
					goalType = Material.valueOf(objectiveSection.getString("Goal.Type"));
					break;
				case KILL_MOB:
					goalType = EntityType.valueOf(objectiveSection.getString("Goal.Type"));
					break;
				case KILL_PLAYER:
					goalType = objectiveSection.getString("Goal.Type");
					break;
				default:
					goalType = "Unknown";
					break;
				
				}
				Integer goalAmount = objectiveSection.getInt("Goal.Amount");
				
				QuestObjective objective = new QuestObjective(q, objectiveTag, objectiveName, type, goalType, goalAmount);
				objectives.add(objective);
			}
			q.setObjectives(objectives);
			
			/*
			 * Requirements
			 */
			ConfigurationSection requirementSection = questSection.getConfigurationSection("Requirements");
			QuestRequirements requirements = new QuestRequirements();
			
			int requirementLevel = requirementSection.getInt("RPG.Minimal_Level", 0);
			List<String> requirementRaces = requirementSection.getStringList("RPG.Races");
			List<String> requirementOccupations = requirementSection.getStringList("RPG.Occupations");
			List<String> requirementQuests = requirementSection.getStringList("Completed_Quests");
			
			requirements.setMinimumLevel(requirementLevel);
			if(!requirementRaces.isEmpty() && !requirementRaces.get(0).equals("-none-")){
				for(String s : requirementRaces){
					requirements.addRace(s);
				}
			}
			if(!requirementOccupations.isEmpty() && !requirementOccupations.get(0).equals("-none-")){
				for(String s : requirementOccupations){
					requirements.addOccupation(s);
				}
			}
			if(!requirementQuests.isEmpty() && !requirementQuests.get(0).equals("-none-")){
				requirementQuests = new ArrayList<String>();
			}
			
			q.setQuestRequirements(requirements);
			
			requiredQuestsMap.put(q, requirementQuests);
			
			/*
			 * Rewards
			 */
			ConfigurationSection rewardSection = questSection.getConfigurationSection("Rewards");
			QuestRewards rewards = new QuestRewards();
			
			int rewardExperience = rewardSection.getInt("RPG.Experience", 0);
			String rewardOccupation = rewardSection.getString("RPG.Occupation", "-none-");
			
			rewards.setExperience(rewardExperience);
			rewards.setOccupation(rewardOccupation);
			
			q.setRewards(rewards);
			
			QuestManager.registerQuest(q);
		}
		
		for(Quest q : requiredQuestsMap.keySet()){
			for(String qString : requiredQuestsMap.get(q)){
				Quest reqQ = QuestManager.getQuest(qString);
				if(reqQ == null) continue;
				q.getRequirements().addQuest(reqQ);
			}
		}
	}
}
