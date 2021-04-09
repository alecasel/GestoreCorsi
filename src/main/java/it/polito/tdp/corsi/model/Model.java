package it.polito.tdp.corsi.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.db.CorsoDAO;

public class Model {
	private CorsoDAO corsodao;
	
	public Model() {
		corsodao = new CorsoDAO();
	}
	
	public List<Corso> getCorsiByPeriodo(Integer periodo) {
		return corsodao.getCorsiByPeriodo(periodo);
	}
	
	public Map<Corso, Integer> getCorsiByIscrittiForPeriod(Integer periodo) {
		return corsodao.getCorsiByIscrittiForPeriod(periodo);
	}
	
	//	N.B. Qui ci passiamo una String, non un Corso!
	public List<Studente> getStudentiByCorso(String codice) {
		return corsodao.getStudentiByCorso(new Corso(codice, null, null, null));
	}
	
	//	Dato il corso con codice ABC, mi aspetto 
	//	GEST -> 50, INF -> 40, MEC -> 30
	
	//	SOLUZIONE 1 - NON OTTIMIZZATA
	public Map<String, Integer> getDivisioneCDS(String codice) {
//		SOLUZIONE 1 - NON OTTIMIZZATA
//		Map<String, Integer> divisione = new HashMap<String, Integer>();
//		List<Studente> studenti = this.getStudentiByCorso(codice);
//
//		for (Studente studente : studenti) {
//			if (studente.getCDS() != null && !studente.getCDS().equals("")) {	//	Ci sono studenti non associati a corsi che lasciamo perdere
//				if (divisione.get(studente.getCDS()) == null) { // Se il CDS non c'è ancora nella mappa
//					divisione.put(studente.getCDS(), 1);
//				} else {
//					divisione.put(studente.getCDS(), divisione.get(studente.getCDS()) + 1);
//				}
//			}
//		}
//		return divisione;
	
		return corsodao.getDivisioneStudenti(new Corso(codice, null, null, null));
	}
	
	
	
	public boolean esisteCorso(String codice) {
		//	Uso metodo top-down (al contrario): devo supporre l'esistenza di un metodo nel DAO
		return corsodao.esisteCorso(new Corso(codice, null, null, null));
	}
	
}
