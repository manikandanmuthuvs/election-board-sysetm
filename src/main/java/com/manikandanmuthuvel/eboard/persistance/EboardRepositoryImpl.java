package com.manikandanmuthuvel.eboard.persistance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.manikandanmuthuvel.eboard.contract.repository.EboardRepositoryContract;
import com.manikandanmuthuvel.eboard.model.Contender;
import com.manikandanmuthuvel.eboard.model.Idea;
import com.manikandanmuthuvel.eboard.model.Manifesto;

public class EboardRepositoryImpl implements EboardRepositoryContract{
	Map<String,Contender> eboardRepository = new HashMap<String, Contender>();
	ArrayList<Map<String,Contender>> listOfContenders = new ArrayList<Map<String,Contender>>();

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
}
