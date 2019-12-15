package com.manikandanmuthuvel.eboard.customertest;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import com.manikandanmuthuvel.eboard.contract.usecase.CitizenUsecaseContract;
import com.manikandanmuthuvel.eboard.model.Citizen;
import com.manikandanmuthuvel.eboard.model.Contender;
import com.manikandanmuthuvel.eboard.test.utils.EboardTestUtils;

public class CitizenUsecaseTest {
	EboardTestUtils eboardTestUtils;
	CitizenUsecaseContract citizenUsecase;
	
	public CitizenUsecaseTest() {
		eboardTestUtils = new EboardTestUtils();
		citizenUsecase = eboardTestUtils.createCitizenUsecase();
	}
	@Test
	public void citizenWithAgeGreatherThanOrEqualToVotingAgeShouldBeAbleToNominateAsContender() {
		Citizen citizen = eboardTestUtils.createCitizen("Rajni", "superstar", 70, "rajni@superstar.com");
		Contender contender = eboardTestUtils.createContender(citizen);
			
		citizenUsecase.citizenNominatesAsContender(contender);		
		Contender actualContender = citizenUsecase.citizenFindContenderById(contender.getContenderId());
		
		assertThat(actualContender,is(not(nullValue())));
	}
	@Test
	public void citizenWithAgeLessThanVotingAgeShouldNOTBeAbleToNominateAsContender() {
		Citizen citizen = eboardTestUtils.createCitizen("Rajni", "superstar", 17, "rajni@superstar.com");
		Contender contender = eboardTestUtils.createContender(citizen);
		
		citizenUsecase.citizenNominatesAsContender(contender);		
		Contender actualContender = citizenUsecase.citizenFindContenderById(contender.getContenderId());
		
		assertThat(actualContender,is(nullValue()));
	}
	
}
