package com.manikandanmuthuvel.eboard.usecase;

import java.util.ArrayList;
import java.util.Map;

import com.manikandanmuthuvel.eboard.contract.repository.EboardRepositoryContract;
import com.manikandanmuthuvel.eboard.contract.usecase.CitizenUsecaseContract;
import com.manikandanmuthuvel.eboard.model.Contender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CitizenUsecaseImpl implements CitizenUsecaseContract{

	private EboardRepositoryContract eboardRepository;
	private Integer MIN_VOTER_AGE = 18;
	
	@Override
	public void citizenNominatesAsContender(Contender contender) {
		if(contender.getContender().getAge() >= MIN_VOTER_AGE) {
			eboardRepository.createContender(contender);
		}		
	}
	@Override
	public Contender citizenFindContenderById(String contenderId) {
		return eboardRepository.findContenderBy(contenderId);
	}
	
	@Override
	public ArrayList<Contender> citizenFindAllContenders() {
		return eboardRepository.findAllContenders();
	}

}
