package com.proximate.evaluacion.network;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("apellidos")
	private String apellidos;

	@SerializedName("eliminado")
	private int eliminado;

	@SerializedName("documentos_abrev")
	private String documentosAbrev;

	@SerializedName("correo")
	private String correo;

	@SerializedName("numero_documento")
	private String numeroDocumento;

	@SerializedName("documentos_id")
	private int documentosId;

	@SerializedName("documentos_label")
	private String documentosLabel;

	@SerializedName("estados_usuarios_label")
	private String estadosUsuariosLabel;

	@SerializedName("id")
	private int id;

	@SerializedName("ultima_sesion")
	private String ultimaSesion;

	@SerializedName("secciones")
	private List<SeccionesItem> secciones;

	@SerializedName("nombres")
	private String nombres;

	public void setApellidos(String apellidos){
		this.apellidos = apellidos;
	}

	public String getApellidos(){
		return apellidos;
	}

	public void setEliminado(int eliminado){
		this.eliminado = eliminado;
	}

	public int getEliminado(){
		return eliminado;
	}

	public void setDocumentosAbrev(String documentosAbrev){
		this.documentosAbrev = documentosAbrev;
	}

	public String getDocumentosAbrev(){
		return documentosAbrev;
	}

	public void setCorreo(String correo){
		this.correo = correo;
	}

	public String getCorreo(){
		return correo;
	}

	public void setNumeroDocumento(String numeroDocumento){
		this.numeroDocumento = numeroDocumento;
	}

	public String getNumeroDocumento(){
		return numeroDocumento;
	}

	public void setDocumentosId(int documentosId){
		this.documentosId = documentosId;
	}

	public int getDocumentosId(){
		return documentosId;
	}

	public void setDocumentosLabel(String documentosLabel){
		this.documentosLabel = documentosLabel;
	}

	public String getDocumentosLabel(){
		return documentosLabel;
	}

	public void setEstadosUsuariosLabel(String estadosUsuariosLabel){
		this.estadosUsuariosLabel = estadosUsuariosLabel;
	}

	public String getEstadosUsuariosLabel(){
		return estadosUsuariosLabel;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setUltimaSesion(String ultimaSesion){
		this.ultimaSesion = ultimaSesion;
	}

	public String getUltimaSesion(){
		return ultimaSesion;
	}

	public void setSecciones(List<SeccionesItem> secciones){
		this.secciones = secciones;
	}

	public List<SeccionesItem> getSecciones(){
		return secciones;
	}

	public void setNombres(String nombres){
		this.nombres = nombres;
	}

	public String getNombres(){
		return nombres;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"apellidos = '" + apellidos + '\'' + 
			",eliminado = '" + eliminado + '\'' + 
			",documentos_abrev = '" + documentosAbrev + '\'' + 
			",correo = '" + correo + '\'' + 
			",numero_documento = '" + numeroDocumento + '\'' + 
			",documentos_id = '" + documentosId + '\'' + 
			",documentos_label = '" + documentosLabel + '\'' + 
			",estados_usuarios_label = '" + estadosUsuariosLabel + '\'' + 
			",id = '" + id + '\'' + 
			",ultima_sesion = '" + ultimaSesion + '\'' + 
			",secciones = '" + secciones + '\'' + 
			",nombres = '" + nombres + '\'' + 
			"}";
		}
}