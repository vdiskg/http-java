package io.github.vdiskg.http;

import java.net.URI;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestClient;

/**
 * @author vdisk
 * @version 1.0
 * @since 2024-12-16 10:01
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HttpJavaTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void ping() {
        RestClient restClient = RestClient.create();
        URI uri = URI.create("http://localhost:" + this.port + "/actuator/health");
        RestClient.ResponseSpec responseSpec = restClient.get()
            .uri(uri)
            .retrieve();
        Map body = responseSpec.body(Map.class);
        Assertions.assertNotNull(body);
        Assertions.assertEquals("UP", body.get("status"));
    }
}