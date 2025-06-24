package br.com.controledevendas.estoque.repository;

import br.com.controledevendas.estoque.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VendaRepository  extends JpaRepository<Venda, Integer> {
    @Query(value = """
    SELECT v from Venda v 
    WHERE 
    EXTRACT(YEAR from v.dataVenda) = :anos 
    and 
    EXTRACT(MONTH from v.dataVenda) = :mes
""" )
    List<Venda> buscarPorData(@Param("anos") int ano,@Param("mes") int mes);
}
