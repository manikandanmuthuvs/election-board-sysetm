package com.manikandanmuthuvel.eboard.test.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.manikandanmuthuvel.eboard.contract.repository.EboardRepositoryContract;
import com.manikandanmuthuvel.eboard.contract.usecase.CitizenUsecaseContract;
import com.manikandanmuthuvel.eboard.model.Citizen;
import com.manikandanmuthuvel.eboard.model.Contender;
import com.manikandanmuthuvel.eboard.model.Idea;
import com.manikandanmuthuvel.eboard.model.Manifesto;
import com.manikandanmuthuvel.eboard.persistance.EboardRepositoryImpl;
import com.manikandanmuthuvel.eboard.usecase.CitizenUsecaseImpl;
import com.manikandanmuthuvel.eboard.usecase.ContenderUsecaseImpl;


public class EboardTestUtils {
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	EboardRepositoryContract eboardRepository = new EboardRepositoryImpl();
	int MIN_VOTER_AGE = 18;
	int MIN_NUMBEROF_IDEAS = 1;
	
	String[] ideas = new String[] {"Commitment","Empathy","Openness"};

	public CitizenUsecaseContract createCitizenUsecase() {
		CitizenUsecaseContract citizenUsecase = CitizenUsecaseImpl.builder()
				.eboardRepository(eboardRepository)
				.MIN_VOTER_AGE(MIN_VOTER_AGE)
				.build();
		return citizenUsecase;
	}
	public ContenderUsecaseImpl createContenderUsecase() {
		ContenderUsecaseImpl contenderUsecase = ContenderUsecaseImpl.builder()
				.eboardRepository(eboardRepository)
				.MIN_NUMBEROF_IDEAS(MIN_NUMBEROF_IDEAS)
				.build();
		return contenderUsecase;
	}
	public Citizen createCitizen(String firstName, String lastName,Integer age, String email) {
		Citizen citizen = Citizen.builder()
				.firstName(firstName)
				.lastName(lastName)
				.age(age)
				.email(email)
				.id(UUID.randomUUID().toString())
				.createdAt(dateFormat.format(new Date()).toString())
				.updatedAt(dateFormat.format(new Date()).toString())
				.build();
		return citizen;
	}
	public Contender createContender(Citizen citizen) {
		Contender contender = Contender.builder()
				.contenderId(UUID.randomUUID().toString())
				.contender(citizen)
				.build();
		return contender;
	}
	public Manifesto createManifesto(int numberOfIdea) {		
		Map<String,Idea> ideaOfMap = new HashMap<String, Idea>();
		for (int ideaIndex = 1; ideaIndex <= numberOfIdea; ideaIndex++) {
			Idea idea = createIdea(ideas[ideaIndex-1]);
			String ideaId = idea.getId().toString();
			ideaOfMap.put(ideaId, idea);
		}
		Manifesto manifesto = Manifesto.builder()
				.ideas(ideaOfMap)
				.build();
		return manifesto;
	}
	public Idea createIdea(String idea) {
		Idea ideaBuilder = Idea.builder()
				.id(UUID.randomUUID().toString())
				.idea(idea)	
				.build();
		return ideaBuilder;
	}
}
