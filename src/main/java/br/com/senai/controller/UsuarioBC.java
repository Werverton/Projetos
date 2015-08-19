package br.com.senai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.senai.model.Usuario;
import br.com.senai.model.repository.IUsuarioDAO;

@Controller
public class UsuarioBC {

	@Autowired
	private IUsuarioDAO dao;

	public void salvarUsuario(Usuario usuario) {
		dao.save(usuario);
	}

	public void excluirUsuario(Usuario usuario) {
		dao.delete(usuario);
	}

	public List<Usuario> listarUsuario() {
		return dao.findAll();
	}

}
