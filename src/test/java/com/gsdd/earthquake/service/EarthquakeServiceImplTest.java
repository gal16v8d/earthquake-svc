package com.gsdd.earthquake.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsdd.earthquake.domain.EarthquakeRequest;
import com.gsdd.earthquake.domain.EarthquakeResponse;
import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@ExtendWith(MockitoExtension.class)
class EarthquakeServiceImplTest {

  private static final String QUERY_PATH_INIT = "/query?format=geojson&starttime=";
  private static final String QUERY_PARAM_EXTRACT = "&minmagnitude=0.0&limit=10&offset=1";
  private static final ObjectMapper MAPPER = new ObjectMapper();
  @Mock
  private WebClient usgsClient;
  private MockWebServer server;
  private EarthquakeServiceImpl service;

  @BeforeEach
  void setUp() throws IOException {
    server = new MockWebServer();
    server.start();
    usgsClient = WebClient.create(server.url("/").toString());
    service = new EarthquakeServiceImpl(usgsClient);
  }

  @AfterEach
  void tearDown() throws IOException {
    server.shutdown();
  }

  EarthquakeResponse arrangeResponse() {
    EarthquakeResponse response = new EarthquakeResponse();
    response.setType("Test");
    return response;
  }

  @Test
  void testEarthquakeQuery() throws Exception {
    MockResponse mockResponse =
        new MockResponse().addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .setBody(MAPPER.writeValueAsString(arrangeResponse()))
            .setStatus(HttpStatus.OK.name())
            .setResponseCode(HttpStatus.OK.value());
    server.enqueue(mockResponse);
    var response = service.earthquakeQuery(new EarthquakeRequest());
    var request = server.takeRequest();
    Assertions.assertEquals(HttpMethod.GET.name(), request.getMethod());
    Assertions.assertTrue(request.getPath().contains(QUERY_PATH_INIT));
    Assertions.assertTrue(request.getPath().contains(QUERY_PARAM_EXTRACT));
    Assertions.assertNotNull(response);
  }

  @Test
  void testEarthquakeQueryException() throws Exception {
    MockResponse mockResponse =
        new MockResponse().addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .setBody("ERROR")
            .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.name())
            .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    server.enqueue(mockResponse);
    Assertions.assertThrows(
        WebClientResponseException.class,
        () -> service.earthquakeQuery(new EarthquakeRequest()));
    var request = server.takeRequest();
    Assertions.assertEquals(HttpMethod.GET.name(), request.getMethod());
    Assertions.assertTrue(request.getPath().contains(QUERY_PATH_INIT));
    Assertions.assertTrue(request.getPath().contains(QUERY_PARAM_EXTRACT));
  }

}
