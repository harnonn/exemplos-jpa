package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.vo.RelatorioVendasVo;

public class PedidoDAO {

	private EntityManager em;

	public PedidoDAO(EntityManager em) {
		super();
		this.em = em;
	}
	
	public void cadastrar(Pedido pedido) {
		this.em.persist(pedido);
	}
	
	public Pedido buscarPorId(Long id) {
		return em.find(Pedido.class, id);
	}
	
	public BigDecimal valorTotalVendido() {
		String jpql = "select SUM(p.valorTotal) from Pedido p";
		return em.createQuery(jpql, BigDecimal.class).getSingleResult();
	}
	
	public List<RelatorioVendasVo> relatorioDeVendas() {
		String jpql = "select new br.com.alura.loja.vo.RelatorioVendasVo(produto.nome, SUM(item.quantidade), MAX(pedido.data))"
				+ "from Pedido pedido "
				+ "join pedido.itens item "
				+ "join item.produto produto "
				+ "group by produto.nome "
				+ "order by item.quantidade DESC";
		
		return em.createQuery(jpql, RelatorioVendasVo.class).getResultList();
	}
	
	public Pedido buscarPedidoComCliente(Long id) {
		
		return em.createQuery("Select p from Pedido p join fetch p.cliente where p.id = :id", Pedido.class)
				.setParameter("id", id)
				.getSingleResult();
	}

}
