package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDAO;
import br.com.alura.loja.dao.ClienteDAO;
import br.com.alura.loja.dao.PedidoDAO;
import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.modelo.ItemPedido;
import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;
import br.com.alura.loja.vo.RelatorioVendasVo;

public class TestarRelacionamentos {

	
	EntityManager em;
	
	public TestarRelacionamentos() {
		inicializar();
	}
	
	public void inicializar() {
		em = JPAUtil.getEntityManager();
	}
	
	public void finalizar() {
		em.close();
	}
	
	private void cadastrarProdutos() {
		Categoria celulares = new Categoria("CELULARES");
		Produto iphoneX = new Produto("Iphone X", "Iphone X", new BigDecimal("3000"), celulares);
		Produto iphone11 = new Produto("Iphone 11", "Iphone 11", new BigDecimal("4000"), celulares);

		CategoriaDAO categoriaDAO = new CategoriaDAO(em);
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		
		em.getTransaction().begin();
		categoriaDAO.cadastrar(celulares);
		produtoDAO.cadastrar(iphoneX);
		produtoDAO.cadastrar(iphone11);
		em.getTransaction().commit();
	}
	
	private void cadastrarClientes() {
		ClienteDAO clienteDAO = new ClienteDAO(em);
		Cliente cliente = new Cliente("Arnon", "123");
		em.getTransaction().begin();
		clienteDAO.cadastrar(cliente);
		em.getTransaction().commit();
	}
	
	private void cadastrarPedidos() {
		
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		PedidoDAO pedidoDAO = new PedidoDAO(em);
		Produto produto = produtoDAO.buscarPorId(1L);
		Produto produto2 = produtoDAO.buscarPorId(2L);
		ClienteDAO clienteDAO = new ClienteDAO(em);
		
		Pedido pedido = new Pedido(clienteDAO.buscarPorId(1L));
		pedido.adicionarItem(new ItemPedido(10, pedido, produto));
		pedido.adicionarItem(new ItemPedido(5, pedido, produto2));
		
		em.getTransaction().begin();
		pedidoDAO.cadastrar(pedido);
		em.getTransaction().commit();
	}
	
	public void testarRelacionamentos() {
		PedidoDAO pedidoDAO = new PedidoDAO(em);
		Pedido pedido = pedidoDAO.buscarPorId(1L);
		System.out.println(pedido.toString());
		BigDecimal valorTotalVendido = pedidoDAO.valorTotalVendido();
		System.out.println("O valor total vendido é: "+valorTotalVendido);
		List<RelatorioVendasVo> relatorioDeVendas = pedidoDAO.relatorioDeVendas();
		relatorioDeVendas.forEach(System.out::println);
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		List<Produto> produtos = produtoDAO.buscarPorCategoriaNamedQuery("CELULARES");
		produtos.forEach(p-> System.out.println(p.getNome()));
		em.close();
	}
	
	public void testarCriteria() {
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		produtoDAO.buscarPorParametrosCriteria(null, null, LocalDate.now());
		
	}
	
	
	final static String ESC = "\033[";
	public static void main(String[] args) {

		TestarRelacionamentos testes = new TestarRelacionamentos();
		
		testes.cadastrarProdutos();
		testes.cadastrarClientes();
		testes.cadastrarPedidos();
		testes.finalizar();
		testes.inicializar();
		testes.testarCriteria();
//		testes.testarRelacionamentos();
//		Pedido pedido = testes.em.find(Pedido.class, 1l);
		testes.finalizar();
	}

}
