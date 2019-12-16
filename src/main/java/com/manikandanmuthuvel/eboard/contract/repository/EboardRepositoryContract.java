package com.manikandanmuthuvel.eboard.contract.repository;

import java.util.ArrayList;
import java.util.Map;

import com.manikandanmuthuvel.eboard.model.Citizen;
import com.manikandanmuthuvel.eboard.model.Contender;
import com.manikandanmuthuvel.eboard.model.Idea;
import com.manikandanmuthuvel.eboard.model.Manifesto;
import com.manikandanmuthuvel.eboard.model.Rate;

public interface EboardRepositoryContract {
	public void createContender(Contender contender);
	public void postManifesto(String contenderId, Manifesto manifesto);
	public Contender findContenderBy(String contenderId);
	public Manifesto findManifestoBy(String contenderId);
	public ArrayList<Idea> findIdeasBy(String contenderId);
	public ArrayList<Contender> findAllContenders();
	public Idea getIdea(String contenderId,String ideaId);
	public Contender getContender(String contenderId);
	public void setFollwers(Citizen citizen, String contenderId, Idea actualIdea, Rate rate);
	public void setRating(Citizen citizen,String contenderId,String ideaId, Rate rate);

}
