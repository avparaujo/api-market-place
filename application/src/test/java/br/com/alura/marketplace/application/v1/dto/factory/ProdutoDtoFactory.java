package br.com.alura.marketplace.application.v1.dto.factory;

import br.com.alura.marketplace.application.v1.dto.ProdutoDto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import static br.com.alura.marketplace.application.v1.dto.factory.FotoDtoFactory.criarFotoDtoRequest;
import static br.com.alura.marketplace.domain.entity.Produto.Status.AVAILABLE;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class ProdutoDtoFactory {

    public static Request criarProdutoDtoRequest() {
        return new Request(ProdutoDto.Request.builder());
    }

    @RequiredArgsConstructor(access = PRIVATE)
    public static class Request{
        private final ProdutoDto.Request.RequestBuilder builder;

        public ProdutoDto.Request comTodosOsCampos() {
            return builder
                    .nome("Produto Teste")
                    .categoria("Categoria 1")
                    .status(AVAILABLE)
                    .descricao("Descricao do Produto Teste")
                    .valor(new BigDecimal("1.99"))
                    .foto(criarFotoDtoRequest()
                            .comTodosOsCampos())
                    .tag("tag-1")
                    .build();
        }
    }

}