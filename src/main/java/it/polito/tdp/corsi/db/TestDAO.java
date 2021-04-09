package it.polito.tdp.corsi.db;

import it.polito.tdp.corsi.model.Corso;

public class TestDAO {

	public static void main(String[] args) {
		CorsoDAO corsodao = new CorsoDAO();
		System.out.println(corsodao.getStudentiByCorso(new Corso("01KSUPG",null,null,null)));

	}

}
