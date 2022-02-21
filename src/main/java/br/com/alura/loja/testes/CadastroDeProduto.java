package br.com.alura.loja.testes;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDAO;
import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

public class CadastroDeProduto {

	public static void main(String[] args) {

		cadastrarProdutos();
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		
		produtoDAO.buscarTodos().forEach(iProduto -> System.out.println(iProduto.toString()));
		System.out.println("------------------------");
		produtoDAO.buscarPorNome("Iphone X").forEach(iProduto -> System.out.println(iProduto.toString()));
		System.out.println("------------------------");
		produtoDAO.buscarPorCategoria("CELULARES").forEach(iProduto -> System.out.println(iProduto.toString()));
		System.out.println("------------------------");
		BigDecimal preco = produtoDAO.buscarPrecoPorNome("Iphone 11");
		System.out.println("Preço: " + preco);
		
		em.close();
		
	}

	private static void cadastrarProdutos() {
		EntityManager em = JPAUtil.getEntityManager();
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
		
		em.close();
	}

}
