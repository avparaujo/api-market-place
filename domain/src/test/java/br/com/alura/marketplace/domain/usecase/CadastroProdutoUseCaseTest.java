package br.com.alura.marketplace.domain.usecase;

import br.com.alura.marketplace.domain.entity.Produto;
import br.com.alura.marketplace.domain.exception.BusinessException;
import br.com.alura.marketplace.domain.repository.BucketRepository;
import br.com.alura.marketplace.domain.repository.PetStoreRepository;
import br.com.alura.marketplace.domain.repository.ProdutoRepository;
import br.com.alura.marketplace.domain.repository.QueueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static br.com.alura.marketplace.domain.entity.factory.ProdutoFactory.criarProduto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class CadastroProdutoUseCaseTest {

    @InjectMocks
    CadastroProdutoUseCase cadastroProdutoUseCase;

    @Mock
    ProdutoRepository produtoRepository;
    @Mock
    PetStoreRepository petStoreRepository;
    @Mock
    BucketRepository bucketRepository;
    @Mock
    QueueRepository queueRepository;

    @DisplayName("Quando cadastrar produto")
    @Nested
    class Cadastrar {

        @BeforeEach
        void beforeEach() {
            lenient().
                when(petStoreRepository.cadastrarPet(any()))
                    .thenAnswer(invocationOnMock -> {
                        Produto produto = invocationOnMock.getArgument(0);
                        setField(produto, "petStorePetId", 99L);
                        return produto;
                    });
        }

        @DisplayName("Então deve executar com sucesso")
        @Nested
        class Sucesso {

            @BeforeEach
            void beforeEach() {
                when(produtoRepository.save(any()))
                        .thenAnswer(invocationOnMock -> {
                            Produto produto = invocationOnMock.getArgument(0);
                            setField(produto, "produtoId", UUID.fromString("e42cd22b-ce9b-40ba-bf94-f09e56c07b3d"));
                            return produto;
                        });
            }

            @DisplayName("Dado um produto com todo os campos")
            @Test
            void teste1(){
                //DADO
                var produto = criarProduto()
                        .comTodosOsCampos();

                //QUANDO
                var atual = cadastroProdutoUseCase.cadastrar(produto);

                //ENTAO
                assertThat(atual.getProdutoId())
                        .isEqualTo(UUID.fromString("e42cd22b-ce9b-40ba-bf94-f09e56c07b3d"));

            }

            @DisplayName("Dado um produto com campo status igual à ${status}")
            @ParameterizedTest
            @EnumSource(Produto.Status.class)
            void teste2(Produto.Status status){
                //DADO
                var produto = criarProduto()
                        .comTodosOsCampos();
                setField(produto, "status", status);

                //QUANDO
                var atual = cadastroProdutoUseCase.cadastrar(produto);

                //ENTAO
                assertThat(atual.getStatus())
                        .isEqualTo(status);

            }

            @DisplayName("Dado um produto com campo status igual à ${status}")
            @ParameterizedTest
            @CsvSource(value = {
                    "AVAILABLE | (Disponível)",
                    "PENDING | (Pendente)",
                    "SOLD | (Vendido)",
            }, delimiterString = "|")
            void teste3(Produto.Status status, String descricao){
                //DADO
                var produto = criarProduto()
                        .comTodosOsCampos();
                setField(produto, "status", status);

                //QUANDO
                var atual = cadastroProdutoUseCase.cadastrar(produto);

                //ENTAO
                assertThat(atual.getDescricao())
                        .endsWith(descricao);

            }
        }

//        @DisplayName("Então deve retornar erro")
//        @Nested
//        class Falha {
//            @DisplayName("Dado um produto com o nome que começa com -")
//            @Test
//            void teste1(){
//                //DADO
//                var produto = criarProduto()
//                        .comTodosOsCampos();
//                setField(produto, "nome", "- Nome Teste");
//
//
//                //QUANDO
//                var atual = assertThrows(BusinessException.class,
//                        () -> cadastroProdutoUseCase.cadastrar(produto));
//
//                //ENTAO
//                assertThat(atual)
//                        .hasMessage("O nome do produto não pode começar com -");
//
//
//            }
//        }


    }
}
