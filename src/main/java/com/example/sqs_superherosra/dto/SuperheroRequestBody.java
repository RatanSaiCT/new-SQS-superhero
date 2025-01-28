package com.example.sqs_superherosra.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Builder
public class SuperheroRequestBody {
	private String name;
	private String power;
	private String universe;

}