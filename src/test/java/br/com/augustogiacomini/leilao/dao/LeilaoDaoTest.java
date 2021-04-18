package br.com.augustogiacomini.leilao.dao;

import br.com.augustogiacomini.leilao.model.Leilao;
import br.com.augustogiacomini.leilao.model.Usuario;
import br.com.augustogiacomini.leilao.util.JPAUtil;
import br.com.augustogiacomini.leilao.util.builder.LeilaoBuilder;
import br.com.augustogiacomini.leilao.util.builder.UsuarioBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LeilaoDaoTest {

    private LeilaoDao dao;

    private EntityManager em;

    @BeforeEach
    void beforeEach() {
        this.em = JPAUtil.getEntityManager();
        this.dao = new LeilaoDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    void afterEach() {
        em.getTransaction().rollback();
    }

    @Test
    void deveCadastrarUmLeilao() {
        Leilao leilao = new LeilaoBuilder()
                .comNome("Mochila")
                .comValorInicial("70")
                .comData(LocalDate.now())
                .comUsuario(criarUsuario())
                .criar();

        leilao = dao.salvar(leilao);

        Leilao leilaoSalvo = dao.buscarPorId(leilao.getId());

        assertNotNull(leilaoSalvo);
    }

    @Test
    void deveAtualizarUmLeilao() {
        Leilao leilao = new LeilaoBuilder()
                .comNome("Mochila")
                .comValorInicial("70")
                .comData(LocalDate.now())
                .comUsuario(criarUsuario())
                .criar();

        leilao.setNome("Celular");
        leilao.setValorInicial(new BigDecimal("400"));
        leilao = dao.salvar(leilao);

        Leilao leilaoSalvo = dao.buscarPorId(leilao.getId());

        assertEquals("Celular", leilaoSalvo.getNome());
        assertEquals(new BigDecimal("400"), leilaoSalvo.getValorInicial());
    }

    private Usuario criarUsuario() {
        Usuario usuario = new UsuarioBuilder()
                .comNome("Fulano")
                .comEmail("fulano@email.com")
                .comSenha("12345678")
                .criar();
        em.persist(usuario);

        return usuario;
    }
}
