package com.manikandanmuthuvel.eboard.contract.repository;

import java.util.ArrayList;

import com.manikandanmuthuvel.eboard.model.Contender;
import com.manikandanmuthuvel.eboard.model.Idea;
import com.manikandanmuthuvel.eboard.model.Manifesto;

public interface EboardRepositoryContract {
	public void createContender(Contender contender);
	public void postManifesto(String contenderId, Manifesto manifesto);
	public Contender findContenderBy(String contenderId);
	public Manifesto findManifestoBy(String contenderId);
	public ArrayList<Idea> findIdeasBy(String contenderId);

}
