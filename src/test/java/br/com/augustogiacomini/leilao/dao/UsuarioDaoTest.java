package br.com.augustogiacomini.leilao.dao;

import br.com.augustogiacomini.leilao.model.Usuario;
import br.com.augustogiacomini.leilao.util.JPAUtil;
import br.com.augustogiacomini.leilao.util.builder.UsuarioBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioDaoTest {

    private UsuarioDao dao;

    private EntityManager em;

    @BeforeEach
    void beforeEach() {
        this.em = JPAUtil.getEntityManager();
        this.dao = new UsuarioDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    void afterEach() {
        em.getTransaction().rollback();
    }

    @Test
    void deveEncontrarUsuarioCadastrado() {
        Usuario usuario = criarUsuario();
        Usuario usuarioEncontrado = this.dao.buscarPorUsername(usuario.getNome());

        assertNotNull(usuarioEncontrado);
    }

    @Test
    void naoDeveEncontrarUsuarioNaoCadastrado() {
        criarUsuario();

        assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername("beltrano"));
    }

    @Test
    void deveRemoverUmUsuario() {
        Usuario usuario = criarUsuario();
        dao.deletar(usuario);

        assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername(usuario.getNome()));
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
