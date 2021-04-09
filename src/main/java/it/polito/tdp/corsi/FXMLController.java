/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.corsi;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Model;
import it.polito.tdp.corsi.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtPeriodo"
    private TextField txtPeriodo; // Value injected by FXMLLoader

    @FXML // fx:id="txtCorso"
    private TextField txtCorso; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorsiPerPeriodo"
    private Button btnCorsiPerPeriodo; // Value injected by FXMLLoader

    @FXML // fx:id="btnNumeroStudenti"
    private Button btnNumeroStudenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnStudenti"
    private Button btnStudenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnDivisioneStudenti"
    private Button btnDivisioneStudenti; // Value injected by FXMLLoader

    @FXML // fx:id="txtRisultato"
    private TextArea txtRisultato; // Value injected by FXMLLoader

    @FXML
    void corsiPerPeriodo(ActionEvent event) {
    	txtRisultato.clear();
    	String periodoStringa = txtPeriodo.getText();
    	Integer periodo = null;
    	
    	try {
			periodo = Integer.parseInt(periodoStringa);
		} catch (NumberFormatException e) {
			txtRisultato.setText("Devi inserire un numero (1 o 2) per il periodo didattico");
		} catch (NullPointerException e) {
			txtRisultato.setText("Devi inserire un numero (1 o 2) per il periodo didattico");
		}
    	
    	if (periodo != 1 && periodo !=2) {
    		txtRisultato.setText("Devi inserire un numero (1 o 2) per il periodo didattico");
    		return ;
		}
    	
    	List<Corso> corsi = this.model.getCorsiByPeriodo(periodo);
    	
    	StringBuilder sb = new StringBuilder();
    	for (Corso c : corsi) {
			//sb.append(c +"\n");
    		sb.append(String.format("%-8s", c.getCodins()));
//    		%	=> qui vado a mettere un placeholder
//    		-	=> allineamento: tutto viene allineato a sinistra
//    		8	=> numero che identifica gli spazi che questa colonna dovrà avere (codins non può superare 7 char)
//    		s	=> tipo di dato da aggiungere (String)  		
    		sb.append(String.format("%-4d ", c.getCrediti()));
    		sb.append(String.format("%-50s ", c.getNome()));
    		sb.append(String.format("%-4d\n", c.getPd()));		
    	}
    	
    	txtRisultato.appendText(sb.toString());
    }
    

    @FXML
    void numeroStudenti(ActionEvent event) {
    	txtRisultato.clear();
    	String periodoStringa = txtPeriodo.getText();
    	Integer periodo = null;
    	
    	try {
			periodo = Integer.parseInt(periodoStringa);
		} catch (NumberFormatException e) {
			txtRisultato.setText("Devi inserire un numero (1 o 2) per il periodo didattico");
		} catch (NullPointerException e) {
			txtRisultato.setText("Devi inserire un numero (1 o 2) per il periodo didattico");
		}
    	
    	if (periodo != 1 && periodo !=2) {
    		txtRisultato.setText("Devi inserire un numero (1 o 2) per il periodo didattico");
    		return ;
		}
    	
    	Map<Corso, Integer> corsi = this.model.getCorsiByIscrittiForPeriod(periodo);
    	
    	StringBuilder sb = new StringBuilder();
    	for (Corso c : corsi.keySet()) {
			sb.append(c.getNome() + " " + corsi.get(c) +"\n");
		}
    	
    	txtRisultato.appendText(sb.toString());
    }

    @FXML
    void stampaDivisione(ActionEvent event) {
    	txtRisultato.clear();
    	
    	String codice = txtCorso.getText();
    	
    	//	Caso a)
    	if (!model.esisteCorso(codice)) {
			txtRisultato.appendText("Il corso non esiste");
			return ;
		}
    	
    	Map<String, Integer> divisione = model.getDivisioneCDS(codice);
    	    	
    	for (String cds : divisione.keySet()) {
			txtRisultato.appendText(cds + " " + divisione.get(cds) + "\n");
		}
    }

    @FXML
    void stampaStudenti(ActionEvent event) {
    	txtRisultato.clear();
    	
    	String codice = txtCorso.getText();
    	
    	//	Caso a)
    	if (!model.esisteCorso(codice)) {
			txtRisultato.appendText("Il corso non esiste");
			return ;
		}
    	
    	List<Studente> studenti = model.getStudentiByCorso(codice);
    	
    	//	Le stringhe non scatenano eccezioni
    	
    	//	Se la mappa non contiene elementi, può essere che:
    	//	a) il corso non esista
    	//	b) il corso esiste ma non vi sono studenti iscritti
    	//	Per sapere se a o b, devo creare il metodo esisteCorso => lo creo in Model
    	
    	//	Caso b)
    	if (studenti.size() == 0) {
			txtRisultato.appendText("Il corso non ha iscritti");
		}
    	
    	for (Studente studente : studenti) {
			txtRisultato.appendText(studente + "\n");
		}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPeriodo != null : "fx:id=\"txtPeriodo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCorso != null : "fx:id=\"txtCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCorsiPerPeriodo != null : "fx:id=\"btnCorsiPerPeriodo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnNumeroStudenti != null : "fx:id=\"btnNumeroStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnStudenti != null : "fx:id=\"btnStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDivisioneStudenti != null : "fx:id=\"btnDivisioneStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	//	NECESSARIO per incolonnare nella stampa
    	txtRisultato.setStyle("-fx-font-family: monospace");	
    }
    
    
}