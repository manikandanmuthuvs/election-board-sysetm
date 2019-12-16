package com.manikandanmuthuvel.eboard.contract.usecase;

import java.util.ArrayList;
import java.util.Map;

import com.manikandanmuthuvel.eboard.model.Citizen;
import com.manikandanmuthuvel.eboard.model.Contender;
import com.manikandanmuthuvel.eboard.model.Idea;
import com.manikandanmuthuvel.eboard.model.Rate;

public interface CitizenUsecaseContract {
	public void citizenNominatesAsContender(Contender contender);
	public Contender citizenFindContenderById(String contenderId);
	public ArrayList<Contender> citizenFindAllContenders();
	public void CitizenRateAnIdeaOfContenderManifesto(String contenderId,String ideaId, Rate rating);
	public Idea CitizenGetAnIdeaOfContenderManifesto(String contenderId,String ideaId);

}
