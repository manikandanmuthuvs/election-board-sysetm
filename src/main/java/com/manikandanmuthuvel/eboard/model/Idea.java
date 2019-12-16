package com.manikandanmuthuvel.eboard.model;

import java.util.Map;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Idea {
	  String id;
	  String idea;
	  double averageRating;
	  Map<String,Rate> rating;
}
