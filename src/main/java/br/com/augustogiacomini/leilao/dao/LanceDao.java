package br.com.augustogiacomini.leilao.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.augustogiacomini.leilao.model.Leilao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.augustogiacomini.leilao.model.Lance;

@Repository
public class LanceDao {

	private EntityManager em;

	@Autowired
	public LanceDao(EntityManager em) {
		this.em = em;
	}

	public void salvar(Lance lance) {
		em.persist(lance);
	}

	public Lance buscarMaiorLanceDoLeilao(Leilao leilao) {
		return em.createQuery("SELECT l FROM Lance l WHERE l.valor = (SELECT MAX(lance.valor) FROM Lance lance) AND l.leilao = :leilao", Lance.class)
				.setParameter("leilao", leilao)
				.getSingleResult();
	}
	
}
