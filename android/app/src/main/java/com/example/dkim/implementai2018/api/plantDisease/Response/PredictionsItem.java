package com.example.dkim.implementai2018.api.plantDisease.Response;

import com.google.gson.annotations.SerializedName;

public class PredictionsItem{

	@SerializedName("tagId")
	private String tagId;

	@SerializedName("probability")
	private double probability;

	@SerializedName("tagName")
	private String tagName;

	public void setTagId(String tagId){
		this.tagId = tagId;
	}

	public String getTagId(){
		return tagId;
	}

	public void setProbability(double probability){
		this.probability = probability;
	}

	public double getProbability(){
		return probability;
	}

	public void setTagName(String tagName){
		this.tagName = tagName;
	}

	public String getTagName(){
		return tagName;
	}

	@Override
 	public String toString(){
		return 
			"PredictionsItem{" + 
			"tagId = '" + tagId + '\'' + 
			",probability = '" + probability + '\'' + 
			",tagName = '" + tagName + '\'' + 
			"}";
		}
}