package associacao;

import java.util.ArrayList;
import java.util.Date;

import exceptions.ValidacaoException;

public class Reuniao {
	private Associacao associacao;
	private Date data;
	private String pauta;
	
	private ArrayList<Associado> participantes = new ArrayList<>();
	
	public Reuniao(Date data, String pauta) {
		this.data = data;
		this.pauta = pauta;
	}
	public Reuniao(String pauta) {
		this.pauta = pauta;
	}
	public Reuniao() {
		
	}
	public Associacao getAssociacao() {
		return associacao;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getPauta() {
		return pauta;
	}
	public void setPauta(String pauta) {
		this.pauta = pauta;
	}
	
	public void validar() throws ValidacaoException {
		if(pauta == null || pauta == "") {
			throw new ValidacaoException("pauta");
		}
	}
	
	public ArrayList<Associado> getParticipantes() {
		return participantes;
	}
	public void setParticipantes(ArrayList<Associado> participantes) {
		this.participantes = participantes;
	}
	public void setAssociacao(Associacao a1) {
		this.associacao = a1;
	}
	public void setData(long time) {
		Date d = new Date(time);
		this.data = d;
	}
	
}
