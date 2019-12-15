package com.manikandanmuthuvel.eboard.customertest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.manikandanmuthuvel.eboard.contract.usecase.CitizenUsecaseContract;
import com.manikandanmuthuvel.eboard.contract.usecase.ContenderUsecaseContract;
import com.manikandanmuthuvel.eboard.model.Citizen;
import com.manikandanmuthuvel.eboard.model.Contender;
import com.manikandanmuthuvel.eboard.model.Idea;
import com.manikandanmuthuvel.eboard.model.Manifesto;
import com.manikandanmuthuvel.eboard.test.utils.EboardTestUtils;

public class ContenderUsecaseTest {
	EboardTestUtils eboardTestUtils;
	CitizenUsecaseContract citizenUsecase;
	ContenderUsecaseContract contenderUsecase;

	
	public ContenderUsecaseTest() {
		eboardTestUtils = new EboardTestUtils();
		citizenUsecase = eboardTestUtils.createCitizenUsecase();
		contenderUsecase = eboardTestUtils.createContenderUsecase();
	}
	@Test
	public void contenderPostManifestoWithMiniumNumberIdea() {
		Citizen citizen = eboardTestUtils.createCitizen("Rajni", "superstar", 70, "rajni@superstar.com");
		Contender contender = eboardTestUtils.createContender(citizen);			
		citizenUsecase.citizenNominatesAsContender(contender);
		int numberOfIdeas = 1;
		Manifesto manifesto = eboardTestUtils.createManifesto(numberOfIdeas);
		
		contenderUsecase.contenderPostManifesto(contender.getContenderId(), manifesto);
		Contender actualContender = citizenUsecase.citizenFindContenderById(contender.getContenderId());
		ArrayList<Idea> ideas = contenderUsecase.contenderGetsIdeasOfManifesto(contender.getContenderId());
		assertThat(ideas.size(),is(numberOfIdeas));
	}
	
}
