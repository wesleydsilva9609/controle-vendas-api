package br.com.controledevendas.estoque.repository;

import br.com.controledevendas.estoque.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository  extends JpaRepository<Venda, Integer> {
}
