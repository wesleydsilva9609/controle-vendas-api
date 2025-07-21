package br.com.controledevendas.estoque.domain.repository;

import br.com.controledevendas.estoque.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {
    UserDetails findByEmail(String username);
}
