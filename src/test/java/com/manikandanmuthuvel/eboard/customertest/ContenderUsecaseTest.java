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
import com.manikandanmuthuvel.eboard.model.Mail;
import com.manikandanmuthuvel.eboard.model.Manifesto;
import com.manikandanmuthuvel.eboard.model.Rate;
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
	public void contenderPostManifestoWithMiniumNumberOfIdeas() {
		Citizen citizen = eboardTestUtils.createCitizen("Rajni", "superstar", 70, "rajni@superstar.com");
		Contender contender = eboardTestUtils.createContender(citizen);			
		citizenUsecase.citizenNominatesAsContender(contender);
		int numberOfIdeas = 1;
		Manifesto manifesto = eboardTestUtils.createManifesto(numberOfIdeas);
		
		contenderUsecase.contenderPostManifesto(contender.getContenderId(), manifesto);
		Contender actualContender = citizenUsecase.citizenFindContenderById(contender.getContenderId());
		ArrayList<Idea> ideas = contenderUsecase.contenderGetsIdeasOfManifesto(actualContender.getContenderId());
		assertThat(ideas.size(),is(numberOfIdeas));
	}
	@Test
	public void contenderPostManifestoWithMaximumNumberOfIdeas() {
		Citizen citizen = eboardTestUtils.createCitizen("Rajni", "superstar", 70, "rajni@superstar.com");
		Contender contender = eboardTestUtils.createContender(citizen);			
		citizenUsecase.citizenNominatesAsContender(contender);
		int numberOfIdeas = 3;
		Manifesto manifesto = eboardTestUtils.createManifesto(numberOfIdeas);
		
		contenderUsecase.contenderPostManifesto(contender.getContenderId(), manifesto);
		Contender actualContender = citizenUsecase.citizenFindContenderById(contender.getContenderId());
		ArrayList<Idea> ideas = contenderUsecase.contenderGetsIdeasOfManifesto(contender.getContenderId());
		assertThat(ideas.size(),is(numberOfIdeas));
	}
	@Test
	public void contenderFailsToPostManifestoWithMoreThanMaximumNumberOfIdeas() {
		Citizen citizen = eboardTestUtils.createCitizen("Rajni", "superstar", 70, "rajni@superstar.com");
		Contender contender = eboardTestUtils.createContender(citizen);			
		citizenUsecase.citizenNominatesAsContender(contender);
		int numberOfIdeas = 4;
		Manifesto manifesto = eboardTestUtils.createManifesto(numberOfIdeas);
		
		contenderUsecase.contenderPostManifesto(contender.getContenderId(), manifesto);
		Contender actualContender = citizenUsecase.citizenFindContenderById(contender.getContenderId());
		ArrayList<Idea> ideas = contenderUsecase.contenderGetsIdeasOfManifesto(contender.getContenderId());
		assertThat(ideas.size(),is(0));
	}
	@Test
	public void contenderSHOULDNOTbeAbleToPostManifestoWithMoreThanMaximumNumberOfIdeas() {
		Citizen citizen = eboardTestUtils.createCitizen("Rajni", "superstar", 70, "rajni@superstar.com");
		Contender contender = eboardTestUtils.createContender(citizen);			
		citizenUsecase.citizenNominatesAsContender(contender);
		int numberOfIdeas = 4;
		Manifesto manifesto = eboardTestUtils.createManifesto(numberOfIdeas);
		
		contenderUsecase.contenderPostManifesto(contender.getContenderId(), manifesto);
		Contender actualContender = citizenUsecase.citizenFindContenderById(contender.getContenderId());
		ArrayList<Idea> ideas = contenderUsecase.contenderGetsIdeasOfManifesto(contender.getContenderId());
		assertThat(ideas.size(),is(0));
	}
	@Test
	public void contenderSendsMailToFollowerWhenAddingNewIdeaInManifesto() {
		Citizen citizen = eboardTestUtils.createCitizen("Rajni", "superstar", 70, "rajni@superstar.com");
		Contender contender = eboardTestUtils.createContender(citizen);
		String contenderId = contender.getContenderId();
		citizenUsecase.citizenNominatesAsContender(contender);		
		int numberOfIdeas = 2;
		Manifesto manifesto = eboardTestUtils.createManifesto(numberOfIdeas);		
		contenderUsecase.contenderPostManifesto(contender.getContenderId(), manifesto);	
		ArrayList<Idea> ideas = contenderUsecase.contenderGetsIdeasOfManifesto(contenderId);
		Idea idea1 = ideas.get(0);
		int setRating1 = 8;
		Rate rate1 = eboardTestUtils.createRate(setRating1);	
		citizenUsecase.CitizenRateAnIdeaOfContenderManifesto(citizen,contenderId,idea1.getId(),rate1);	
		Idea idea2 = ideas.get(1);
		int setRating2 = 6;
		Rate rate2 = eboardTestUtils.createRate(setRating2);	
		citizenUsecase.CitizenRateAnIdeaOfContenderManifesto(citizen,contenderId,idea2.getId(),rate2);
		
		
		Idea newIdea = eboardTestUtils.createIdea("Equality");
		
		contenderUsecase.contenderAddsIdeaInManifesto(contender.getContenderId(), newIdea);		
		String contenderFirstName = contender.getContender().getFirstName();
		String contenderLastName = contender.getContender().getLastName().toUpperCase();
		String mailFrom = contender.getContender().getEmail();
		String mailTitle = "the contender, " + contenderFirstName + "" + contenderLastName + " you are following has posted new idea";
		String mailBody = newIdea.getIdea().toString();
	
		ArrayList<String> mailTofollowersEmailIds = contenderUsecase.contenderGetsAllOfFollowersEmail(contenderId);
		Mail mail = eboardTestUtils.createMail(mailFrom, mailTofollowersEmailIds, mailTitle, mailBody);
		contenderUsecase.contenderSendsEmailToFollowers(mail);				
		assertThat(mail,is(not(nullValue())));
	}
	@Test
	public void contenderGetsRemovedFromContestingElectionWhenMoreThan3VotersRateAnIdeaLessThan5Rating() {
		Citizen citizen = eboardTestUtils.createCitizen("Rajni", "superstar", 70, "rajni@superstar.com");
		Contender contender = eboardTestUtils.createContender(citizen);
		String contenderId = contender.getContenderId();
		citizenUsecase.citizenNominatesAsContender(contender);		
		int numberOfIdeas = 3;
		Manifesto manifesto = eboardTestUtils.createManifesto(numberOfIdeas);		
		contenderUsecase.contenderPostManifesto(contender.getContenderId(), manifesto);	
		ArrayList<Idea> actualIdeas = contenderUsecase.contenderGetsIdeasOfManifesto(contenderId);
		Idea idea1 = actualIdeas.get(0);		
		
		Rate  rate = eboardTestUtils.createRate(9, citizen);
		citizenUsecase.CitizenRateAnIdeaOfContenderManifesto(citizen, contenderId, idea1.getId(), rate);
		
		
		Citizen voter1 = eboardTestUtils.createCitizen("voter1","muthuvel",18,"voter1@domain.com");
		Rate  rate1 = eboardTestUtils.createRate(4, voter1);
		citizenUsecase.CitizenRateAnIdeaOfContenderManifesto(voter1, contenderId, idea1.getId(), rate1);
		
		Citizen voter2 = eboardTestUtils.createCitizen("voter2","muthuvel",18,"voter2@domain.com");
		Rate rate2 = eboardTestUtils.createRate(3, voter2);		
		citizenUsecase.CitizenRateAnIdeaOfContenderManifesto(voter2, contenderId, idea1.getId(), rate2);
		
		Citizen voter3 = eboardTestUtils.createCitizen("voter3","muthuvel",18,"voter3@domain.com");
		Rate rate3 = eboardTestUtils.createRate(1, voter3);		
		citizenUsecase.CitizenRateAnIdeaOfContenderManifesto(voter3, contenderId, idea1.getId(), rate3);

		Citizen voter4 = eboardTestUtils.createCitizen("voter4","muthuvel",18,"voter4@domain.com");
		Rate rate4 = eboardTestUtils.createRate(2, voter4);		
		citizenUsecase.CitizenRateAnIdeaOfContenderManifesto(voter4, contenderId, idea1.getId(), rate4);
	
		Contender actualContenderAfterRating = citizenUsecase.citizenFindContenderById(contenderId);
		assertThat(actualContenderAfterRating,is(nullValue()));
	}
	
}
