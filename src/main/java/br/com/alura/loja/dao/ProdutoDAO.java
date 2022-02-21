package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.alura.loja.modelo.Produto;

public class ProdutoDAO {

	private EntityManager em;

	public ProdutoDAO(EntityManager em) {
		super();
		this.em = em;
	}
	
	public void cadastrar(Produto produto) {
		this.em.persist(produto);
	}
	
	public void atualizar(Produto produto) {
		produto = this.em.merge(produto);
	}
	
	public void remover(Produto produto) {
		produto = this.em.merge(produto);
		this.em.remove(produto);
	}
	
	public Produto buscarPorId(Long id) {
		return em.find(Produto.class, id);
	}
	
	public List<Produto> buscarTodos(){
		return em.createQuery("select p from Produto p", Produto.class).getResultList();
	}
	
	public List<Produto> buscarPorNome(String nome){
		TypedQuery<Produto> query = em.createQuery("select p from Produto p where p.nome = :nome", Produto.class);
		query.setParameter("nome", nome);
		return query.getResultList();
	}
	
	public List<Produto> buscarPorCategoria(String cateogoria){
		TypedQuery<Produto> query = em.createQuery("select p from Produto p where p.categoria.nome = :cateogoria", Produto.class);
		query.setParameter("cateogoria", cateogoria);
		return query.getResultList();
	}
	
	public BigDecimal buscarPrecoPorNome(String nome){
		TypedQuery<BigDecimal> query = em.createQuery("select p.preco from Produto p where p.nome = :nome", BigDecimal.class);
		query.setParameter("nome", nome);
		return query.getSingleResult();
	}

}
