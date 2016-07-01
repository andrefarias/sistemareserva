package com.jornada.shared.classes;

import java.io.Serializable;
import java.util.Date;

import com.google.gwt.view.client.ProvidesKey;

public class Reserva implements Serializable, Comparable<Reserva> {

    /**
	 * 
	 */
    private static final long serialVersionUID = 4710332602168218200L;

    public static final String DB_UNIQUE_KEY = "unike_idcurso_nomeperiodo";

    // private int idCurso;
    private int idReserva;
    private String nomeReserva;
    private int numeroAdultos;
    private int numeroCriancas;
    private String telefone;
    private String cidade;
    private String observacao;
    private Date dataReserva;
    private String horario;
    private String turno;
    private String chegou;

    public Reserva() {

    }
    
    

    public String getChegou() {
        return chegou;
    }



    public void setChegou(String chegou) {
        this.chegou = chegou;
    }



    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public String getNomeReserva() {
        return nomeReserva;
    }

    public void setNomeReserva(String nomeReserva) {
        this.nomeReserva = nomeReserva;
    }

    public int getNumeroAdultos() {
        return numeroAdultos;
    }

    public void setNumeroAdultos(int numeroAdultos) {
        this.numeroAdultos = numeroAdultos;
    }

    public int getNumeroCriancas() {
        return numeroCriancas;
    }

    public void setNumeroCriancas(int numeroCriancas) {
        this.numeroCriancas = numeroCriancas;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Date getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(Date dataReserva) {
        this.dataReserva = dataReserva;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String toString() {
        return "projeto:com.jornada.shared.classes.Reserva";
    }

    @Override
    public int compareTo(Reserva o) {
        return (o == null || o.nomeReserva == null) ? -1 : -o.nomeReserva.compareTo(nomeReserva);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Reserva) {
            return idReserva == ((Reserva) o).idReserva;
        }
        return false;
    }

    public static final ProvidesKey<Reserva> KEY_PROVIDER = new ProvidesKey<Reserva>() {
        @Override
        public Object getKey(Reserva item) {
            return item == null ? null : Integer.toString(item.getIdReserva());
        }
    };

}
