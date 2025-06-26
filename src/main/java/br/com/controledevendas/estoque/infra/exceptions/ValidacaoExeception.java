package br.com.controledevendas.estoque.infra.exceptions;

public class ValidacaoExeception extends RuntimeException {
    public ValidacaoExeception(String message) {
        super(message);
    }
}
