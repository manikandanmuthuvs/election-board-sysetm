package com.manikandanmuthuvel.eboard.model;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mail {
	private String from;
	private ArrayList<String> to;
	private String title;
	private String body;		
}
