package com.hangout.quests.quest;

import java.util.ArrayList;
import java.util.List;

import com.hangout.core.HangoutAPI;
import com.hangout.core.player.CommonPlayerBundle;
import com.hangout.quests.player.QuestPlayer;

public class QuestRequirements {
	
	private int level = 0;
	private List<String> races = new ArrayList<String>();
	private List<String> occupations = new ArrayList<String>();
	private List<Quest> quests = new ArrayList<Quest>();
	
	public QuestRequirements(){}
	
	public boolean isPassed(QuestPlayer p){
		CommonPlayerBundle bundle = HangoutAPI.getPlayerBundle(p.getHangoutPlayer().getUUID());
		if(bundle.getRPGLevel() >= level ||
				(!races.isEmpty() && races.contains(bundle.getRPGRace())) ||
				(!occupations.isEmpty() && occupations.contains(bundle.getRPGOccupation())) ||
				(!quests.isEmpty() && p.getCompletedQuests().containsAll(quests))){
			return true;
		}
		return false;
	}
	
	public void setMinimumLevel(int level){
		this.level = level;
	}
	
	public void addRace(String race){
		races.add(race);
	}
	
	public void addOccupation(String occupation){
		occupations.add(occupation);
	}
	
	public void addQuest(Quest quest){
		quests.add(quest);
	}
}
