package br.com.augustogiacomini.leilao.dao;

import br.com.augustogiacomini.leilao.model.Lance;
import br.com.augustogiacomini.leilao.model.Leilao;
import br.com.augustogiacomini.leilao.model.Usuario;
import br.com.augustogiacomini.leilao.util.JPAUtil;
import br.com.augustogiacomini.leilao.util.builder.LanceBuilder;
import br.com.augustogiacomini.leilao.util.builder.LeilaoBuilder;
import br.com.augustogiacomini.leilao.util.builder.UsuarioBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LanceDaoTest {

    private LanceDao dao;

    private LeilaoDao leilaoDao;

    private EntityManager em;

    @BeforeEach
    void beforeEach() {
        this.em = JPAUtil.getEntityManager();
        this.dao = new LanceDao(em);
        this.leilaoDao = new LeilaoDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    void afterEach() {
        em.getTransaction().rollback();
    }

    @Test
    void deveBuscarOMaiorLanceDoLeilao() {
        Leilao leilao = new LeilaoBuilder()
                .comNome("Mochila")
                .comValorInicial("70")
                .comData(LocalDate.now())
                .comUsuario(criarUsuario())
                .criar();

        leilao = leilaoDao.salvar(leilao);

        Lance lance1 = new LanceBuilder()
                .comValor("100")
                .comUsuario(criarUsuario())
                .comLeilao(leilao)
                .criar();

        dao.salvar(lance1);

        Lance lance2 = new LanceBuilder()
                .comValor("200")
                .comUsuario(criarUsuario())
                .comLeilao(leilao)
                .criar();

        dao.salvar(lance2);

        Lance lance = dao.buscarMaiorLanceDoLeilao(leilao);

        assertEquals(new BigDecimal("200"), lance.getValor());
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
