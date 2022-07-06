package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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

	public List<Produto> buscarPorCategoriaNamedQuery(String cateogoria){
		TypedQuery<Produto> query = em.createNamedQuery("Produto.produtosPorCategoria", Produto.class);
		query.setParameter("cateogoria", cateogoria);
		return query.getResultList();
	}

	
	public BigDecimal buscarPrecoPorNome(String nome){
		TypedQuery<BigDecimal> query = em.createQuery("select p.preco from Produto p where p.nome = :nome", BigDecimal.class);
		query.setParameter("nome", nome);
		return query.getSingleResult();
	}

	public List<Produto> buscarPorParametros(String nome, BigDecimal preco, LocalDate dataCadastro) {
		
		String jpql = "Select p from Produto p where 1=1 ";
		
		if (nome != null && !nome.trim().isEmpty()) {
			jpql += " and p.nome = :nome";
		}
		
		if (preco != null ) {
			jpql += " and p.preco = :preco";
		}
		
		if (dataCadastro != null ) {
			jpql += " and p.dataCadastro = :dataCadastro";
		}
		
		TypedQuery<Produto> query = em.createQuery(jpql, Produto.class);
		
		if (nome != null && !nome.trim().isEmpty()) {
			query.setParameter("nome", nome);
		}
		
		if (preco != null ) {
			query.setParameter("preco", preco);
		}
		
		if (dataCadastro != null ) {
			query.setParameter("dataCadastro", dataCadastro);
		}
		
		return query.getResultList();
	}
	
	public List<Produto> buscarPorParametrosCriteria(String nome, BigDecimal preco, LocalDate dataCadastro) {
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
		Root<Produto> from = query.from(Produto.class);
		Predicate filtros = builder.and();
		
		if (nome != null && !nome.trim().isEmpty()) {
			filtros = builder.and(filtros, builder.equal(from.get("nome"), nome));
		}
		
		if (preco != null ) {
			filtros = builder.and(filtros, builder.equal(from.get("preco"), preco));
		}
		
		if (dataCadastro != null ) {
			filtros = builder.and(filtros, builder.equal(from.get("dataCadastro"), dataCadastro));
		}
		
		query.where(filtros);
		
		return em.createQuery(query).getResultList();
	}
	
}
