package com.manikandanmuthuvel.eboard.contract.usecase;

import com.manikandanmuthuvel.eboard.model.Contender;

public interface CitizenUsecaseContract {
	public void citizenNominatesAsContender(Contender contender);
	public Contender citizenFindContenderById(String contenderId);

}
