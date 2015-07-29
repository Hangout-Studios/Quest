package com.hangout.quests.quest;

import com.hangout.core.HangoutAPI;
import com.hangout.core.player.CommonPlayerBundle;
import com.hangout.quests.player.QuestPlayer;

public class QuestRewards {
	
	private String occupation = "-none-";
	private int experience = 0;
	
	public QuestRewards(){
		
	}
	
	public void reward(QuestPlayer p){
		CommonPlayerBundle bundle = HangoutAPI.getPlayerBundle(p.getHangoutPlayer().getUUID());
		if(!occupation.equals("-none-")){
			bundle.unlockRPGOccupation(occupation);
		}
		if(experience != 0){
			bundle.addRPGExperience(experience, "QUEST");
		}
	}
	
	public void setOccupation(String occupation){
		this.occupation = occupation;
	}
	
	public void setExperience(int experience){
		this.experience = experience;
	}
}
