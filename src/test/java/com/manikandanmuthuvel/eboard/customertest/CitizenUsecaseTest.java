package com.manikandanmuthuvel.eboard.customertest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.manikandanmuthuvel.eboard.contract.usecase.CitizenUsecaseContract;
import com.manikandanmuthuvel.eboard.contract.usecase.ContenderUsecaseContract;
import com.manikandanmuthuvel.eboard.model.Citizen;
import com.manikandanmuthuvel.eboard.model.Contender;
import com.manikandanmuthuvel.eboard.model.Follower;
import com.manikandanmuthuvel.eboard.model.Idea;
import com.manikandanmuthuvel.eboard.model.Manifesto;
import com.manikandanmuthuvel.eboard.model.Rate;
import com.manikandanmuthuvel.eboard.test.utils.EboardTestUtils;

public class CitizenUsecaseTest {
	EboardTestUtils eboardTestUtils;
	CitizenUsecaseContract citizenUsecase;
	ContenderUsecaseContract contenderUsecase;

	public CitizenUsecaseTest() {
		eboardTestUtils = new EboardTestUtils();
		citizenUsecase = eboardTestUtils.createCitizenUsecase();
		contenderUsecase = eboardTestUtils.createContenderUsecase();
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
	@Test
	public void citizenGetsAllContendersAndTheirManifestos() {
		int numberOfContenders = 4;
		for (int index = 1; index <= numberOfContenders; index++) {
			Citizen citizen = eboardTestUtils.createCitizen("Rajni", "superstar", 70, "rajni@superstar.com");
			Contender contender = eboardTestUtils.createContender(citizen);
			citizenUsecase.citizenNominatesAsContender(contender);		
			int numberOfIdeas = 3;
			Manifesto manifesto = eboardTestUtils.createManifesto(numberOfIdeas);		
			contenderUsecase.contenderPostManifesto(contender.getContenderId(), manifesto);		
		}
		
		ArrayList<Contender> actualContenders = citizenUsecase.citizenFindAllContenders();
		
		assertThat(actualContenders.size(),is(numberOfContenders));
	}
	@Test
	public void citizenRateAnIdeaOfContenerManfesto() {
		// Given the citizen, contender and her/him manifesto of max number of ideas
		Citizen citizen = eboardTestUtils.createCitizen("Rajni", "superstar", 70, "rajni@superstar.com");
		Contender contender = eboardTestUtils.createContender(citizen);
		citizenUsecase.citizenNominatesAsContender(contender);		
		int numberOfIdeas = 3;
		Manifesto manifesto = eboardTestUtils.createManifesto(numberOfIdeas);		
		contenderUsecase.contenderPostManifesto(contender.getContenderId(), manifesto);	
		ArrayList<Idea> ideas = contenderUsecase.contenderGetsIdeasOfManifesto(contender.getContenderId());
		Idea idea = ideas.get(0);
		double setRating = 7;
		Rate rate = eboardTestUtils.createRate(setRating);	
		
		//When citizen rate one of the idea of manifesto
		citizenUsecase.CitizenRateAnIdeaOfContenderManifesto(citizen,contender.getContenderId(),idea.getId(),rate);
		
		//Then the rate should be updated in the rating ofidea
		ArrayList<Idea> actualIdeas = contenderUsecase.contenderGetsIdeasOfManifesto(contender.getContenderId());
		Idea actualIdea = actualIdeas.get(0);
		double actualRating = eboardTestUtils.getRating(actualIdea, rate);	
		assertThat(actualRating,is(setRating));
	}
	@Test
	public void citizenRateAnIdeaAboveAverageAndBecomeFollowerOfOfContener() {
		// Given the citizen, contender and her/him manifesto of max number of ideas
		Citizen citizen = eboardTestUtils.createCitizen("Rajni", "superstar", 70, "rajni@superstar.com");
		Contender contender = eboardTestUtils.createContender(citizen);
		citizenUsecase.citizenNominatesAsContender(contender);		
		int numberOfIdeas = 3;
		Manifesto manifesto = eboardTestUtils.createManifesto(numberOfIdeas);		
		contenderUsecase.contenderPostManifesto(contender.getContenderId(), manifesto);	
		ArrayList<Idea> ideas = contenderUsecase.contenderGetsIdeasOfManifesto(contender.getContenderId());
		Idea idea = ideas.get(0);
		int setRating = 6;
		Rate rate = eboardTestUtils.createRate(setRating);	
		
		//When citizen rate one of the idea of manifesto
		citizenUsecase.CitizenRateAnIdeaOfContenderManifesto(citizen,contender.getContenderId(),idea.getId(),rate);
		Follower follower = citizenUsecase.citizenFindContenderById(contender.getContenderId()).getFollower();
		
		Citizen followers = follower.getFollowers().get(citizen.getId());

		assertThat(citizen,is(followers));
	}
	@Test
	public void citizenRateAnIdeaBelowAverageAndSHOULDNOTBecomeFollowerOfOfContener() {
		// Given the citizen, contender and her/him manifesto of max number of ideas
		Citizen citizen = eboardTestUtils.createCitizen("Rajni", "superstar", 70, "rajni@superstar.com");
		Contender contender = eboardTestUtils.createContender(citizen);
		citizenUsecase.citizenNominatesAsContender(contender);		
		int numberOfIdeas = 3;
		Manifesto manifesto = eboardTestUtils.createManifesto(numberOfIdeas);		
		contenderUsecase.contenderPostManifesto(contender.getContenderId(), manifesto);	
		ArrayList<Idea> ideas = contenderUsecase.contenderGetsIdeasOfManifesto(contender.getContenderId());
		Idea idea = ideas.get(0);
		int setRating = 4;
		Rate rate = eboardTestUtils.createRate(setRating);	
		
		//When citizen rate one of the idea of manifesto
		citizenUsecase.CitizenRateAnIdeaOfContenderManifesto(citizen,contender.getContenderId(),idea.getId(),rate);
		Follower follower = citizenUsecase.citizenFindContenderById(contender.getContenderId()).getFollower();
		
		assertThat(follower,is(nullValue()));
	}
	@Test
	public void citizenDeleteRatingThatWasAboveAverageAndBecomeUnFollowerOfOfContener() {
		// Given the citizen, contender and her/him manifesto of max number of ideas
		Citizen citizen = eboardTestUtils.createCitizen("Rajni", "superstar", 70, "rajni@superstar.com");
		Contender contender = eboardTestUtils.createContender(citizen);
		citizenUsecase.citizenNominatesAsContender(contender);		
		int numberOfIdeas = 3;
		Manifesto manifesto = eboardTestUtils.createManifesto(numberOfIdeas);		
		contenderUsecase.contenderPostManifesto(contender.getContenderId(), manifesto);	
		ArrayList<Idea> ideas = contenderUsecase.contenderGetsIdeasOfManifesto(contender.getContenderId());
		Idea idea = ideas.get(0);
		int setRating = 8;
		Rate rate = eboardTestUtils.createRate(setRating);	
		
		citizenUsecase.CitizenRateAnIdeaOfContenderManifesto(citizen,contender.getContenderId(),idea.getId(),rate);
		ArrayList<Idea> newIdeas = contenderUsecase.contenderGetsIdeasOfManifesto(contender.getContenderId());
		Idea newIdea = newIdeas.get(0);
		int setNewRating = 0;
		Rate newRate = eboardTestUtils.createRate(setNewRating);	
		
		//When citizen rate one of the idea of manifesto
		citizenUsecase.CitizenRateAnIdeaOfContenderManifesto(citizen,contender.getContenderId(),newIdea.getId(),newRate);
		
		//Then the rate should be updated in the rating ofidea
		Citizen follower = citizenUsecase.citizenFindContenderById(contender.getContenderId()).getFollower().getFollowers().get(citizen.getId());
		assertThat(follower,is(nullValue()));

	}
	@Test
	public void citizenGetAverageRatingOfEachIdeaInManifesto() {
		Citizen citizen = eboardTestUtils.createCitizen("Rajni", "superstar", 70, "rajni@superstar.com");
		Contender contender = eboardTestUtils.createContender(citizen);
		String contenderId = contender.getContenderId();
		citizenUsecase.citizenNominatesAsContender(contender);		
		int numberOfIdeas = 3;
		Manifesto manifesto = eboardTestUtils.createManifesto(numberOfIdeas);		
		contenderUsecase.contenderPostManifesto(contender.getContenderId(), manifesto);	
		ArrayList<Idea> actualIdeas = contenderUsecase.contenderGetsIdeasOfManifesto(contenderId);
		Idea idea1 = actualIdeas.get(0);		

		Citizen voter1 = eboardTestUtils.createCitizen("voter1","muthuvel",18,"voter1@domain.com");
		Rate  rate1 = eboardTestUtils.createRate(7, voter1);
		citizenUsecase.CitizenRateAnIdeaOfContenderManifesto(citizen, contenderId, idea1.getId(), rate1);
		
		Citizen voter2 = eboardTestUtils.createCitizen("voter2","muthuvel",18,"voter2@domain.com");
		Rate rate2 = eboardTestUtils.createRate(4, voter2);		
		citizenUsecase.CitizenRateAnIdeaOfContenderManifesto(citizen, contenderId, idea1.getId(), rate2);

		Idea idea2 = actualIdeas.get(1);
		Citizen voter3 = eboardTestUtils.createCitizen("voter3","muthuvel",18,"voter3@domain.com");
		Rate  rate3 = eboardTestUtils.createRate(7, voter3);
		citizenUsecase.CitizenRateAnIdeaOfContenderManifesto(citizen, contenderId, idea2.getId(), rate1);
		
		Citizen voter4 = eboardTestUtils.createCitizen("voter4","muthuvel",18,"voter4@domain.com");
		Rate rate4 = eboardTestUtils.createRate(6, voter4);
		Idea idea3 = actualIdeas.get(2);
		citizenUsecase.CitizenRateAnIdeaOfContenderManifesto(citizen, contenderId, idea3.getId(), rate4);
		
		Idea actualIdeaAfterAfterOfRating = citizenUsecase.CitizenGetAnIdeaOfContenderManifesto(contenderId, idea1.getId());
		assertThat(actualIdeaAfterAfterOfRating.getAverageRating(),is(5.5));

	}
}
