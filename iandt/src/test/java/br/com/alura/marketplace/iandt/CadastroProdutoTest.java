package br.com.alura.marketplace.iandt;

import br.com.alura.marketplace.application.Application;
import br.com.alura.marketplace.iandt.setup.LocalStackSetup;
import br.com.alura.marketplace.iandt.setup.PostgresSetup;
import br.com.alura.marketplace.iandt.setup.RabbitMQSetup;
import br.com.alura.marketplace.iandt.setup.WireMockSetup;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.petstore.model.factory.PetDtoFactory;
import io.awspring.cloud.s3.S3Template;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import static br.com.alura.marketplace.application.v1.dto.factory.ProdutoDtoFactory.criarProdutoDtoRequest;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.petstore.model.factory.PetDtoFactory.*;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ContextConfiguration(classes = Application.class)
@Testcontainers
public class CadastroProdutoTest implements LocalStackSetup, WireMockSetup, PostgresSetup, RabbitMQSetup {

    @LocalServerPort
    Integer port;

    @Autowired
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    S3Template s3Template;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @BeforeEach
    void beforeEach() {
        RestAssured.baseURI = "http://localhost:" + port + "/api";
        if (!s3Template.bucketExists(bucketName)) {
            s3Template.createBucket(bucketName);
        }
    }

    @DisplayName("Quando cadastrar um produto")
    @Nested
    class CadastrarProduto {

        @DisplayName("Então deve cadastrar com sucesso")
        @Nested
        class Sucesso {

            @BeforeEach
            void beforeEach() throws JsonProcessingException {
                var petDto = criarPetDto()
                        .comTodosOsCampos();
                WIRE_MOCK.stubFor(post("/petstore/pet")
                        .willReturn(WireMock.aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody(mapper.writeValueAsString(petDto))
                        )
                );
            }

            @DisplayName("Dado um produto com todos os campos")
            @Test
            void teste1() throws JsonProcessingException {
                //DADO
                var produto = criarProdutoDtoRequest()
                        .comTodosOsCampos();
                //QUANDO
                var resposta = RestAssured.given()
                        .log().all()
                        .header("Correlation-Id", "1e15fde6-ecbf-4212-b427-9a1d6cc3d3ae")
                        .contentType(JSON)
                        .body(mapper.writeValueAsString(produto))
                        .post("/v1/produtos")
                        .then()
                        .log().all()
                        .extract()
                        .response();

                //ENTAO
                assertThat(resposta.statusCode())
                        .isEqualTo(201);


            }

        }
    }


}
