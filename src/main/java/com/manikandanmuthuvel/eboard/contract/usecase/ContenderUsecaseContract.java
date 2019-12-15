package com.manikandanmuthuvel.eboard.contract.usecase;

import java.util.ArrayList;

import com.manikandanmuthuvel.eboard.model.Idea;
import com.manikandanmuthuvel.eboard.model.Manifesto;

public interface ContenderUsecaseContract {
	public void contenderPostManifesto(String contenderId, Manifesto manifesto);
	public Manifesto contenderGetsManifesto(String contenderId);
	public ArrayList<Idea> contenderGetsIdeasOfManifesto(String contenderId);

}
