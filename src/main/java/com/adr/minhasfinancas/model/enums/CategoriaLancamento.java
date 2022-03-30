package com.adr.minhasfinancas.model.enums;

public enum CategoriaLancamento {

	CARTAO_DE_CREDITO("Cartão de crédito"),
	MORADIA("Moradia"),
	CASA("Casa"),
	LAZER("Lazer"),
	EDUCACAO("Educação"),
	SAUDE("Saúde"),
	OUTROS("Outros"),
	COMPRAS("Compras"),
	ALIMENTACAO("Alimentação"),
	DOACAO("Doação")
	;

	private final String categoria;

	CategoriaLancamento(final String categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString(){
		return categoria;
	}
}
