package br.com.alura.loja.modelo;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity (name =  "categorias")
public class Categoria {

	@EmbeddedId
	private CategoriaId id;
	
	public Categoria() {
		super();
	}

	public Categoria(String nome) {
		super();
		this.id = new CategoriaId(nome, "TYPE");
	}

	public String getNome() {
		return this.id.getNome();
	}

	

}
