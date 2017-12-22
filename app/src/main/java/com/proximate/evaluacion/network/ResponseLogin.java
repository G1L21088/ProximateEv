package com.proximate.evaluacion.network;

import com.google.gson.annotations.SerializedName;

public class ResponseLogin{

	@SerializedName("success")
	private boolean success;

	@SerializedName("id")
	private int id;

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	@SerializedName("token")
	private String token;

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	@Override
 	public String toString(){
		return 
			"ResponseLogin{" + 
			"success = '" + success + '\'' + 
			",id = '" + id + '\'' + 
			",error = '" + error + '\'' + 
			",message = '" + message + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}
}