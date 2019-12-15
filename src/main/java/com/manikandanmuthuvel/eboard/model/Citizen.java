package com.manikandanmuthuvel.eboard.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Citizen {
	  private String id;
	  private String firstName;
	  private String lastName;
	  private String email;
	  private int age;
	  private String createdAt;
	  private String updatedAt;
}
