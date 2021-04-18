package br.com.augustogiacomini.leilao.util.builder;

import br.com.augustogiacomini.leilao.model.Lance;
import br.com.augustogiacomini.leilao.model.Leilao;
import br.com.augustogiacomini.leilao.model.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LanceBuilder {

    private BigDecimal valor;

    private Usuario usuario;

    private Leilao leilao;

    public LanceBuilder comValor(String valor) {
        this.valor = new BigDecimal(valor);
        return this;
    }

    public LanceBuilder comUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public LanceBuilder comLeilao(Leilao leilao) {
        this.leilao = leilao;
        return this;
    }

    public Lance criar() {
        Lance lance = new Lance(usuario, valor);
        lance.setLeilao(leilao);

        return lance;
    }
}
