package br.com.controledevendas.estoque.repository;

import br.com.controledevendas.estoque.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Object findByNomeContainingIgnoreCase(Produto produto);
}
