package com.manikandanmuthuvel.eboard.usecase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.manikandanmuthuvel.eboard.contract.repository.EboardRepositoryContract;
import com.manikandanmuthuvel.eboard.contract.usecase.CitizenUsecaseContract;
import com.manikandanmuthuvel.eboard.model.Citizen;
import com.manikandanmuthuvel.eboard.model.Contender;
import com.manikandanmuthuvel.eboard.model.Idea;
import com.manikandanmuthuvel.eboard.model.Manifesto;
import com.manikandanmuthuvel.eboard.model.Rate;

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
	@Override
	public void CitizenRateAnIdeaOfContenderManifesto(String contenderId,String ideaId, Rate rate) {
		Map<String,Rate> rating = new HashMap<String, Rate>();
		Map<String,Rate> actualRating = new HashMap<String, Rate>();

		Idea actualIdea = eboardRepository.getIdea(contenderId, ideaId);
		actualRating = actualIdea.getRating();
		if(actualRating == null) {
			rating.put(rate.getId(),rate);
			actualIdea.setRating(rating);
		}
		else {
			Set<String> rateKeys = actualIdea.getRating().keySet();		
			for (String rateKey : rateKeys) {
				if(rateKey.equals(rate.getId())) {
					actualIdea.getRating().put(rate.getId(), rate);
				}else {
					rating.put(rate.getId(),rate);
					actualIdea.getRating().put(rate.getId(), rate);
				}
			}
		}	
	}
	@Override
	public Idea CitizenGetAnIdeaOfContenderManifesto(String contenderId,String ideaId) {
		return eboardRepository.getIdea(contenderId,ideaId);			
	}
}
