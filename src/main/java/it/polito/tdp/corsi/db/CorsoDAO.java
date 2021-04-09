package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Studente;

public class CorsoDAO {

	public List<Corso> getCorsiByPeriodo(Integer periodo) {
		String sql = "select * "
				+ "from corso "
				+ "where pd = ?";
		List<Corso> result = new ArrayList<Corso>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, periodo);
			
			ResultSet rs = ps.executeQuery();			
			while (rs.next()) {
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				result.add(c);				
			}	
			
			rs.close();
			ps.close();
			conn.close();
			
		} catch (SQLException se) {
			throw new RuntimeException(se);
		}
		return result;
	}
	
	public Map<Corso, Integer> getCorsiByIscrittiForPeriod(Integer periodo) {
		String sql = "SELECT c.codins, c.nome, c.crediti, c.pd, COUNT(*) AS tot "
				+ "FROM corso c, iscrizione i "
				+ "WHERE c.codins=i.codins AND c.pd=? "
				+ "GROUP BY c.codins, c.nome, c.crediti, c.pd";
		
		Map<Corso, Integer> result = new HashMap<Corso, Integer>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, periodo);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				Integer n = rs.getInt("tot");	//	Ho chiamato "tot" il count della query
				result.put(c, n);
			}	
			
			rs.close();
			ps.close();
			conn.close();
			
		} catch (SQLException se) {
			throw new RuntimeException(se);
		}
		return result;
	}
	
	public List<Studente> getStudentiByCorso(Corso corso) {
		String sql = "SELECT s.matricola, s.cognome, s.nome, s.CDS "
				+ "FROM studente s, iscrizione i "
				+ "WHERE s.matricola = i.matricola "
				+ "AND i.codins = ?";
		
		List<Studente> result = new LinkedList<Studente>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, corso.getCodins());
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Studente s = new Studente(rs.getInt("matricola"), rs.getString("nome"), rs.getString("cognome"), rs.getString("CDS"));
				result.add(s);
			}
			
			rs.close();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return result;
	}

	//	METODO BOOLEANO!
	public boolean esisteCorso(Corso corso) {
		String sql = "SELECT * FROM corso WHERE codins = ?";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, corso.getCodins());
			
			ResultSet rs = ps.executeQuery();
			//	Usiamo ora if e non while perché c'è solo un risultato
			if (rs.next()) {
				rs.close();
				ps.close();
				conn.close();
				return true;
			} else {
				rs.close();
				ps.close();
				conn.close();
				return false;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Map<String, Integer> getDivisioneStudenti(Corso corso) {
		String sql = "SELECT s.CDS, COUNT(*) AS tot "
				+ "FROM studente s, iscrizione i "
				+ "WHERE s.matricola = i.matricola AND i.codins = ? AND s.CDS <> '' "
				+ "GROUP BY s.CDS";
		Map<String, Integer> result = new HashMap<String, Integer>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, corso.getCodins());
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				result.put(rs.getString("CDS"), rs.getInt("tot"));
			}
			
			rs.close();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return result;
	}
	
}
