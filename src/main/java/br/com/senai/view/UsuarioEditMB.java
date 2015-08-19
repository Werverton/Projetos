package br.com.senai.view;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import br.com.senai.model.Usuario;
import br.com.senai.support.Fachada;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
@Named(value = "usuarioEditMB")
public class UsuarioEditMB {

	@Autowired
	private Fachada fachada;

	private Usuario usuario;

	@PostConstruct
	private void init() {
		usuario = new Usuario();
	}

	public void preAlterar(Usuario usuario) {
		setUsuario(usuario);
	}

	public String salvar() {
		fachada.salvarUsuario(usuario);
		return "success";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
