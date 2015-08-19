package br.com.senai.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario extends EntidadeBase<Long> {

	@Column(name = "nome", length = 30, nullable = false)
	private String nome;

	@Column(name = "senha", length = 10, nullable = false)
	private String senha;

	@Column(name = "email", length = 30, nullable = false)
	private String email;

	public Usuario() {

	}

	public Usuario(String nome, String senha, String email) {
		this.nome = nome;
		this.senha = senha;
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
