package com.jornada.shared.classes;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

public class Usuario implements Serializable, Comparable<Usuario>  {

	private static final long serialVersionUID = 3304488184672078101L;
	
	
	public static final String DB_UNIQUE_LOGIN = "unique_login";
	public static final String DB_PRIMEIRO_NOME = "primeiro_nome";
	public static final String DB_SOBRE_NOME = "sobre_nome";
	public static final String DB_EMAIL = "email";
	public static final String DB_CPF = "cpf";
	
	
	private int idUsuario;
	private String login;
	private String senha;
	private String primeiroNome;
    private String sobreNome;
    private String sessionId;
    private boolean loggedIn;
    private boolean primeiroLogin;
    private int idTipoUsuario;
    private int idTipoStatusUsuario;

    private String observacao;

	
	
	

	public Usuario() {
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getPrimeiroNome() {
		return primeiroNome;
	}

	public void setPrimeiroNome(String primeiroNome) {
		this.primeiroNome = primeiroNome;
	}

	public String getSobreNome() {
		return sobreNome;
	}

	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
	}

	

	public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

  

    @Override
    public int compareTo(Usuario o) {
      return (o == null || (o.primeiroNome+o.sobreNome) == null) ? -1 : -(o.primeiroNome+o.sobreNome).compareTo((o.primeiroNome+o.sobreNome));
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof Reserva) {
        return idUsuario == ((Usuario) o).idUsuario;
      }
      return false;
    }
    
    public static final ProvidesKey<Usuario> KEY_PROVIDER = new ProvidesKey<Usuario>() {
        @Override
        public Object getKey(Usuario item) {
          return item == null ? null : Integer.toString(item.getIdUsuario());
        }
      };
      
      public int getIdTipoStatusUsuario() {
          return idTipoStatusUsuario;
      }

      public void setIdTipoStatusUsuario(int idTipoStatusUsuario) {
          this.idTipoStatusUsuario = idTipoStatusUsuario;
      }

      public String getSessionId() {
          return sessionId;
      }

      public void setSessionId(String sessionId) {
          this.sessionId = sessionId;
      }

      public boolean getLoggedIn() {
          return loggedIn;
      }

      public void setLoggedIn(boolean loggedIn) {
          this.loggedIn = loggedIn;
      }

    public boolean isPrimeiroLogin() {
        return primeiroLogin;
    }

    public void setPrimeiroLogin(boolean primeiroLogin) {
        this.primeiroLogin = primeiroLogin;
    }

    public int getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(int idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }
      
     



}
