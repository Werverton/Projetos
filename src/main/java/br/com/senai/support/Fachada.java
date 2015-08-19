package br.com.senai.support;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.senai.controller.UsuarioBC;
import br.com.senai.model.Usuario;

@Component
public class Fachada {

	@Autowired
	private UsuarioBC usuarioBC;

	public void salvarUsuario(Usuario usuario) {
		usuarioBC.salvarUsuario(usuario);
	}

	public void excluirUsuario(Usuario usuario) {
		usuarioBC.excluirUsuario(usuario);
	}

	public List<Usuario> listarUsuario() {
		return usuarioBC.listarUsuario();
	}
}
