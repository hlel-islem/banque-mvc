package com.fst.banque.entities;

public class compte {
	private int id;
    private String titulaire;
    private double solde;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitulaire() {
		return titulaire;
	}
	public void setTitulaire(String titulaire) {
		this.titulaire = titulaire;
	}
	public double getSolde() {
		return solde;
	}
	public void setSolde(double solde) {
		this.solde = solde;
	}
	public compte(int id, String titulaire, double solde) {
		this.id = id;
		this.titulaire = titulaire;
		this.solde = solde;
	}
	public compte() {}
	public static void add(compte c1) {
		// TODO Auto-generated method stub
		
	}

}
