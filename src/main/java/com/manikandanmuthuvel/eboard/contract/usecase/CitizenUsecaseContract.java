package com.manikandanmuthuvel.eboard.contract.usecase;

import java.util.ArrayList;
import java.util.Map;

import com.manikandanmuthuvel.eboard.model.Contender;

public interface CitizenUsecaseContract {
	public void citizenNominatesAsContender(Contender contender);
	public Contender citizenFindContenderById(String contenderId);
	public ArrayList<Contender> citizenFindAllContenders();

}
