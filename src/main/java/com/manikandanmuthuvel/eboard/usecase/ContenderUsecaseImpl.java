package com.manikandanmuthuvel.eboard.usecase;

import java.util.ArrayList;

import com.manikandanmuthuvel.eboard.contract.repository.EboardRepositoryContract;
import com.manikandanmuthuvel.eboard.contract.usecase.ContenderUsecaseContract;
import com.manikandanmuthuvel.eboard.model.Contender;
import com.manikandanmuthuvel.eboard.model.Idea;
import com.manikandanmuthuvel.eboard.model.Manifesto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContenderUsecaseImpl implements ContenderUsecaseContract{
	private EboardRepositoryContract eboardRepository;
	private int MIN_NUMBEROF_IDEAS = 1;
	
	@Override
	public void contenderPostManifesto(String contenderId, Manifesto manifesto) {
		eboardRepository.postManifesto(contenderId, manifesto);	
	}	
	@Override
	public ArrayList<Idea> contenderGetsIdeasOfManifesto(String contenderId) {
		return eboardRepository.findIdeasBy(contenderId);
	}
	@Override
	public Manifesto contenderGetsManifesto(String contenderId) {
		return eboardRepository.findManifestoBy(contenderId);
	}
}
