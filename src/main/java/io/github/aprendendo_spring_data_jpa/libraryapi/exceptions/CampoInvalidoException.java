package io.github.aprendendo_spring_data_jpa.libraryapi.exceptions;

import lombok.Getter;

public class CampoInvalidoException extends RuntimeException {

  @Getter
  private String campo;

    public CampoInvalidoException(String campo, String mensagem) {
        super(mensagem);
        this.campo = campo;
    }
}
