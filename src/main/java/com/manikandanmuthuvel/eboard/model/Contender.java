package com.manikandanmuthuvel.eboard.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contender {
	  private String contenderId; 
	  private Citizen contender;
}

