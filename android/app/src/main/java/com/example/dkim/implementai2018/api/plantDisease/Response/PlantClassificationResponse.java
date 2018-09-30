package com.example.dkim.implementai2018.api.plantDisease.Response;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class PlantClassificationResponse{

	@SerializedName("created")
	private String created;

	@SerializedName("project")
	private String project;

	@SerializedName("iteration")
	private String iteration;

	@SerializedName("id")
	private String id;

	@SerializedName("predictions")
	private List<PredictionsItem> predictions;

	public void setCreated(String created){
		this.created = created;
	}

	public String getCreated(){
		return created;
	}

	public void setProject(String project){
		this.project = project;
	}

	public String getProject(){
		return project;
	}

	public void setIteration(String iteration){
		this.iteration = iteration;
	}

	public String getIteration(){
		return iteration;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setPredictions(List<PredictionsItem> predictions){
		this.predictions = predictions;
	}

	public List<PredictionsItem> getPredictions(){
		return predictions;
	}

	@Override
 	public String toString(){
		return 
			"PlantClassificationResponse{" + 
			"created = '" + created + '\'' + 
			",project = '" + project + '\'' + 
			",iteration = '" + iteration + '\'' + 
			",id = '" + id + '\'' + 
			",predictions = '" + predictions + '\'' + 
			"}";
		}
}