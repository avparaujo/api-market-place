package br.com.alura.marketplace.infra.repository;

import br.com.alura.marketplace.domain.entity.factory.ProdutoFactory;
import br.com.alura.marketplace.infra.config.JpaConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static br.com.alura.marketplace.domain.entity.factory.ProdutoFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ActiveProfiles("test")
@DataJpaTest
@ContextConfiguration(classes = JpaConfig.class)
class ProdutoRepositoryExtTest {

    @Autowired
    TestEntityManager em;

    @Autowired
    ProdutoRepositoryExt repository;

    @DisplayName("Quando consultar por nome")
    @Nested
    class FindByNome {

        @DisplayName("Então deve consultar com sucesso")
        @Nested
        class Sucesso {

            @DisplayName("Dado um nome válido, em um cenário onde existe um registro")
            @Test
            void teste1() {
                //Cenário
                var produto = criarProduto()
                        .comTodosOsCamposExcetoDB();
                setField(produto, "nome", "Carrinho");
                repository.save(produto);

                //Dado
                var nome = "Carrinho";

                //Quando
                var atual = repository.findByNome(nome);

                //Então
                assertThat(atual)
                        .isNotEmpty();
                assertThat(atual.get().getNome())
                        .isEqualTo("Carrinho");
            }
        }
    }
}