package com.manikandanmuthuvel.eboard.test.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.manikandanmuthuvel.eboard.contract.repository.EboardRepositoryContract;
import com.manikandanmuthuvel.eboard.contract.usecase.CitizenUsecaseContract;
import com.manikandanmuthuvel.eboard.model.Citizen;
import com.manikandanmuthuvel.eboard.model.Contender;
import com.manikandanmuthuvel.eboard.persistance.EboardRepositoryImpl;
import com.manikandanmuthuvel.eboard.usecase.CitizenUsecaseImpl;


public class EboardTestUtils {
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	EboardRepositoryContract eboardRepository = new EboardRepositoryImpl();
	int MIN_VOTER_AGE = 18;
	
	public CitizenUsecaseContract createCitizenUsecase() {
		CitizenUsecaseContract citizenUsecase = CitizenUsecaseImpl.builder()
				.eboardRepository(eboardRepository)
				.MIN_VOTER_AGE(MIN_VOTER_AGE)
				.build();
		return citizenUsecase;
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
}
