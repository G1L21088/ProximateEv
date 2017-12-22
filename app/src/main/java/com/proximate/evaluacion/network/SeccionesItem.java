package com.proximate.evaluacion.network;

import com.google.gson.annotations.SerializedName;

public class SeccionesItem{

	@SerializedName("seccion")
	private String seccion;

	@SerializedName("abrev")
	private String abrev;

	@SerializedName("id")
	private int id;

	public void setSeccion(String seccion){
		this.seccion = seccion;
	}

	public String getSeccion(){
		return seccion;
	}

	public void setAbrev(String abrev){
		this.abrev = abrev;
	}

	public String getAbrev(){
		return abrev;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"SeccionesItem{" + 
			"seccion = '" + seccion + '\'' + 
			",abrev = '" + abrev + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}