package com.manikandanmuthuvel.eboard.contract.repository;

import com.manikandanmuthuvel.eboard.model.Contender;

public interface EboardRepositoryContract {
	public void create(Contender contender);
	public Contender findBy(String contenderId);
}
