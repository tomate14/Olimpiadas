package Entidades;

import java.util.Calendar;

public class Partido {
	
	private int id;
	private Facultad facultad1;
	private Facultad facultad2;
	private String deporte;
	private String lugar;
	private String resultado;
	private Calendar fecha;
	public Facultad getFacultad1() {
		return facultad1;
	}
	public void setFacultad1(Facultad facultad1) {
		this.facultad1 = facultad1;
	}
	public Facultad getFacultad2() {
		return facultad2;
	}
	public void setFacultad2(Facultad facultad2) {
		this.facultad2 = facultad2;
	}
	public String getDeporte() {
		return deporte;
	}
	public void setDeporte(String deporte) {
		this.deporte = deporte;
	}
	public String getLugar() {
		return lugar;
	}
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public Calendar getFecha() {
		return fecha;
	}
	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	

}
