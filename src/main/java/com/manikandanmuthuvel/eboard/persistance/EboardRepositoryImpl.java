package com.manikandanmuthuvel.eboard.persistance;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.manikandanmuthuvel.eboard.contract.repository.EboardRepositoryContract;
import com.manikandanmuthuvel.eboard.model.Contender;

public class EboardRepositoryImpl implements EboardRepositoryContract{
	Map<String,Contender> eboardRepository = new HashMap<String, Contender>();

	@Override
	public void create(Contender contender) {
		eboardRepository.put(contender.getContenderId(),contender);		
	}
	
	@Override
	public Contender findBy(String contenderId) {
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
}
