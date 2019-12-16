package com.manikandanmuthuvel.eboard.persistance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.manikandanmuthuvel.eboard.contract.repository.EboardRepositoryContract;
import com.manikandanmuthuvel.eboard.model.Citizen;
import com.manikandanmuthuvel.eboard.model.Contender;
import com.manikandanmuthuvel.eboard.model.Follower;
import com.manikandanmuthuvel.eboard.model.Idea;
import com.manikandanmuthuvel.eboard.model.Manifesto;
import com.manikandanmuthuvel.eboard.model.Rate;

public class EboardRepositoryImpl implements EboardRepositoryContract{
	Map<String,Contender> eboardRepository = new HashMap<String, Contender>();
	ArrayList<Map<String,Contender>> listOfContenders = new ArrayList<Map<String,Contender>>();
	ArrayList<Citizen> checkNumberOfFollower = new ArrayList<Citizen>();

	@Override
	public void createContender(Contender contender) {
		eboardRepository.put(contender.getContenderId(),contender);		
	}
	
	@Override
	public Contender findContenderBy(String contenderId) {
		Set<String> contenderKeys = eboardRepository.keySet();
		if(contenderKeys.isEmpty()) {
			return null;
		}else {
			for(String contenderKey : contenderKeys) {
				if(contenderKey.equals(contenderId)) {
					return eboardRepository.get(contenderKey);
				}
			}
		}
		return null;		
	}
	
	@Override
	public void postManifesto(String contenderId,Manifesto manifesto) {
		Contender contender= null;
		String matchingContenderKey="";
		Set<String> contenderKeys = eboardRepository.keySet();
		if(contenderKeys.isEmpty()) {
			return;
		}
		for(String contenderKey : contenderKeys) {
			if(contenderKey.equals(contenderId)) {
				contender = eboardRepository.get(contenderKey);
				contender.setManifesto(manifesto);
				eboardRepository.put(contender.getContenderId(),contender);
				return;
			}
		}
	}
	@Override
	public Contender getContender(String contenderId) {
		Contender contender;
		Set<String> keys = eboardRepository.keySet();
		if(keys.isEmpty()) {
			return null;
		}else {
			for(String key : keys) {
				if(key.equals(contenderId)) {
					return eboardRepository.get(key);
				}
			}
		}
		return null;		
	}
	@Override
	public Idea getIdea(String contenderId,String ideaId) {
		Contender contender= null;
		Manifesto availableManifesto = null;
		Idea availableIdea= null;

		Set<String> keys = eboardRepository.keySet();
		if(keys.isEmpty()) {
			contender = eboardRepository.get(contenderId);
			return null;
		}
		for(String key : keys) {
			if(key.equals(contenderId)) {
				contender = eboardRepository.get(key);
				availableManifesto = contender.getManifesto();
				availableIdea = availableManifesto.getIdeas().get(ideaId);
			}
		}
		return availableIdea;
	}
	@Override
	public Manifesto findManifestoBy(String contenderId) {
		Contender contender= null;
		Manifesto availableManifesto = null;
		Set<String> keys = eboardRepository.keySet();
		if(keys.isEmpty()) {
			contender = eboardRepository.get(contenderId);
			return null;
		}
		for(String key : keys) {
			if(key.equals(contenderId)) {
				contender = eboardRepository.get(key);
				availableManifesto = contender.getManifesto();
			}
		}
		return availableManifesto;
	}
	@Override
	public ArrayList<Idea> findIdeasBy(String contenderId) {
		Contender contender= null;
		Manifesto availableManifesto = null;
		Idea availableIdea = null;
		ArrayList<Idea> ideas = new ArrayList<>();
		
		Set<String> keys = eboardRepository.keySet();
		if(keys.isEmpty()) {
			contender = eboardRepository.get(contenderId);
			return null;
		}
		for(String key : keys) {
			if(key.equals(contenderId)) {
				contender = eboardRepository.get(key);
				availableManifesto = contender.getManifesto();
				if(availableManifesto == null) return ideas;
				Set<String> ideaskeys = availableManifesto.getIdeas().keySet();
				if(ideaskeys.isEmpty()) {
					return null;
				}
				for(String ideakey : ideaskeys) {			
					availableIdea = availableManifesto.getIdeas().get(ideakey);
					ideas.add(availableIdea);			
				}
			}
		}	
		return ideas;
	}
	
	@Override
	public ArrayList<Contender> findAllContenders() {
		ArrayList<Contender> contenders = new ArrayList<>();

		Set<String> keys = eboardRepository.keySet();
		if(keys.isEmpty()) {
			return contenders;
		}
		for(String key : keys) {
			contenders.add(eboardRepository.get(key));			
		}
		return contenders;
	}
	@Override
	public void setRating(Citizen citizen,String contenderId,String ideaId, Rate rate) {
		Map<String,Rate> rating = new HashMap<String, Rate>();
		Map<String,Rate> actualRating = new HashMap<String, Rate>();
		Contender contender = getContender(contenderId);
		Manifesto manifesto = findManifestoBy(contenderId);
		
		Idea actualIdea = getIdea(contenderId, ideaId);
		actualRating = actualIdea.getRating();
		if(actualRating == null) {
			rating.put(rate.getId(),rate);
			actualIdea.setRating(rating);
		}
		else {
			Set<String> rateKeys = actualIdea.getRating().keySet();
			updateRate(rate, rating, actualIdea, rateKeys);
		}
		setAverageRatingOfAnIdeaOfManifesto(contenderId,actualIdea);
		setFinalRatingOfContender(contenderId,actualIdea);
		setFollwers(citizen, contenderId, actualIdea, rate);
	}

	private void updateRate(Rate rate, Map<String, Rate> rating, Idea actualIdea, Set<String> rateKeys) {
		for (String rateKey : rateKeys) {
			if(rateKey.equals(rate.getId())) {
				actualIdea.getRating().put(rate.getId(), rate);
			}else {
				rating.put(rate.getId(),rate);
				actualIdea.getRating().put(rate.getId(), rate);
				rateKeys.remove(rateKey);
			}
		}
	}
	@Override
	public void addIdea(String contenderId,Idea newIdea) {
		Contender contender= null;
		Manifesto manifesto = null;
		Idea availableIdea = null;
		Map<String, Idea> newIdeaMap = new HashMap<>();
		ArrayList<Idea> availableIdeas = new ArrayList<>();

		contender = eboardRepository.get(contenderId);
		manifesto = contender.getManifesto();
		manifesto.getIdeas().put(newIdea.getId().toString(), newIdea);
		eboardRepository.put(contenderId,contender);
	}
	@Override
	public ArrayList<String> getFollowersEmail(String contenderId) {
		ArrayList<String> email = new ArrayList<String>();
		Contender contender = eboardRepository.get(contenderId);
		if(contender.getFollower() != null) {
			Map<String,Citizen> followers = contender.getFollower().getFollowers();
			Set<String> followersKeys = followers.keySet();
			for(String followerKey : followersKeys) {
				email.add(followers.get(followerKey).getEmail());
			}
		}		
		return email;
	}
	@Override
	public void setFollwers(Citizen citizen, String contenderId, Idea actualIdea, Rate rate)
	{
		Map<String, Citizen> firstFollower = new HashMap<String, Citizen>();
		Follower follower = new Follower();
		Contender contender = getContender(contenderId);
		
		if(actualIdea.getRating().get(rate.getId()).getRating() > 5) {			
			if(contender.getFollower() == null) {
				firstFollower.put(citizen.getId().toString(),citizen);
				follower.setFollowers(firstFollower);
				contender.setFollower(follower);
			}
			else {				
				contender.getFollower().getFollowers().put(citizen.getId().toString(),citizen);
			}
		}
		if(actualIdea.getRating().get(rate.getId()).getRating() < 5) {	
			checkNumberOfFollower.add(citizen);
			if(checkNumberOfFollower.size() > 3) {
				contender = null;
				eboardRepository.put(contenderId, contender);
				return;
			}
			if(contender.getFollower() != null) {
				contender.getFollower().getFollowers().remove(citizen.getId(), citizen);
			}
		
		}
		contender.getManifesto().getIdeas().put(actualIdea.getId(), actualIdea);
		eboardRepository.put(contenderId, contender);
	}
	@Override
	public void setAverageRatingOfAnIdeaOfManifesto(String contenderId,Idea actualIdea) {
		double averageRating = 0;
		double numberOfVoters = 0;
		Contender contender = getContender(contenderId);

		Set<String> rateKeys = actualIdea.getRating().keySet();		
		for (String rateKey : rateKeys) {
			averageRating += actualIdea.getRating().get(rateKey).getRating();
			numberOfVoters += 1;
		}
		averageRating = averageRating / numberOfVoters;
		actualIdea.setAverageRating(averageRating);
		contender.getManifesto().getIdeas().put(actualIdea.getId().toString(), actualIdea);
		eboardRepository.put(contenderId, contender);
	}
	@Override
	public void setFinalRatingOfContender(String contenderId,Idea actualIdea) {
		double finalRating = 0;
		Contender contender = getContender(contenderId);

		Set<String> ideasKeys = contender.getManifesto().getIdeas().keySet();
		for (String ideaKey : ideasKeys) {
			finalRating += contender.getManifesto().getIdeas().get(ideaKey).getAverageRating();
		}
		contender.setFinalRating(finalRating);
		eboardRepository.put(contenderId, contender);
	}
}
