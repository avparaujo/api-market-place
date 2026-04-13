package br.com.alura.marketplace.iandt.setup;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

public interface WireMockSetup {

    WireMockServer WIRE_MOCK = new WireMockServer(9090);

    @BeforeAll
    static void wiremockBeforeAll() {
        WIRE_MOCK.start();
        WireMock.configureFor("localhost", WIRE_MOCK.port());
    }

    @AfterEach
    default void wiremockAfterEach(){
        WIRE_MOCK.resetAll();
    }
}
