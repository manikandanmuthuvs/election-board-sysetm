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
		
		//When citizen rate one of the idea of manifesto

	}
//	@Test
//	public void contenderSendsMailToFollowersWhenAddingNewIdeaInManifesto() {
////		Citizen citizen = eboardTestUtils.createCitizen("manikandan","muthuvel",18,"test@domain.com");
////		Integer WITH_NUMBER_OF_IDEAS = 2;
////		Manifesto manifesto = eboardTestUtils.createManifesto(WITH_NUMBER_OF_IDEAS);
////		Contender contender = eboardTestUtils.createContender(citizen, manifesto, null, null, null);
////		
////		citizenUsecase.citizenNominatesAsContender(contender);
////		contenderUsecase.contenderPostManifesto(contender.getContenderId().toString(), manifesto);
////		
//		Contender contender = eboardTestUtils.CitizenNominatesAsContenderWhoPostManifestoAndCitizenRateIdea();
//
//		Idea newIdea = eboardTestUtils.createIdea("Openness");
//		contenderUsecase.contenderAddsIdeaInManifesto(contender.getContenderId().toString(), newIdea);
//		
//		String contenderId = contender.getContenderId().toString();
//		String contenderFirstName = contender.getContender().getFirstName();
//		String contenderLastName = contender.getContender().getLastName().toUpperCase();
//		String mailFrom = contender.getContender().getEmail();
//		String mailTitle = "the contender, " + contenderFirstName + "" + contenderLastName + " you are following has posted new idea";
//		String mailBody = newIdea.getIdea().toString();
//	
//		ArrayList<String> mailTofollowersEmailIds = contenderUsecase.contenderGetsAllOfFollowersEmail(contenderId);
//		
//		Mail mail = eboardTestUtils.createMail(mailFrom, mailTofollowersEmailIds, mailTitle, mailBody);
//		
//		contenderUsecase.contenderSendsEmailToFollowers(mail);
//				
//		assertThat(mail,is(not(nullValue())));
//	}
	
}
