package br.com.senai.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.senai.model.Usuario;

public interface IUsuarioDAO extends JpaRepository<Usuario, Long> {

}
