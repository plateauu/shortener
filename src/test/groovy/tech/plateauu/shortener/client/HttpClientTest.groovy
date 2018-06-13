package tech.plateauu.shortener.client

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.web.client.RestTemplate
import spock.lang.Shared
import tech.plateauu.shortener.SpringContextTest
import tech.plateauu.shortener.client.HttpClient
import tech.plateauu.shortener.client.HttpClientConnectionException

import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

class HttpClientTest extends SpringContextTest {

    static final LONG_URL = 'http://www.google.com/'
    static final SHORT_URL = "http://short.url"
    static final KIND = "KIND"

    @Autowired
    RestTemplate restTemplate

    @Value('${google.api.url.shortener.key}')
    String api

    @Shared
    MockRestServiceServer server

    def setup() {
        server = MockRestServiceServer.createServer(restTemplate)
    }

    def "should send post request to make url short"() {
        def httpAddress = HttpClient.SHORTEN_URL + api
        def client = new HttpClient(api, restTemplate)
        server.expect(requestTo(httpAddress))
                .andExpect(method(POST))
                .andExpect(content().string(getRequestBody()))
                .andRespond(withSuccess(getShortenResponseBody(), APPLICATION_JSON))

        when:
        def shortUrl = client.doShort(LONG_URL)

        then:
        server.verify()

        and:
        shortUrl == SHORT_URL
    }

    def "should send get request to expand url"() {
        def address = String.format(HttpClient.EXPAND_URL, SHORT_URL, api)
        def client = new HttpClient(api, restTemplate)
        server.expect(requestTo(address))
                .andExpect(method(GET))
                .andExpect(queryParam("shortUrl", SHORT_URL))
                .andRespond(withSuccess(getResponseExpandedBody(), APPLICATION_JSON))

        when:
        def longUrl = client.expand(SHORT_URL)

        then:
        server.verify()

        and:
        longUrl == LONG_URL
    }

    def "should throw exception when bad request is sent to expand"() {
        given:
        def client = new HttpClient(api, restTemplate)
        def address = String.format(HttpClient.EXPAND_URL, SHORT_URL, api)
        server.expect(requestTo(address))
                .andExpect(method(GET))
                .andExpect(queryParam("shortUrl", SHORT_URL))
                .andRespond(withBadRequest())

        when:
        client.expand(SHORT_URL)


        then:
        server.verify()

        and:
        thrown(HttpClientConnectionException)
    }

    def "should throw exception when bad request is sent to shorten url"() {
        given:
        def httpAddress = HttpClient.SHORTEN_URL + api
        def client = new HttpClient(api, restTemplate)
        server.expect(requestTo(httpAddress))
                .andExpect(method(POST))
                .andExpect(content().string(getRequestBody()))
                .andRespond(withBadRequest())

        when:
        client.doShort(LONG_URL)

        then:
        server.verify()

        and:
        thrown(HttpClientConnectionException)
    }

    def getRequestBody() {
        """{"longUrl":"${LONG_URL}"}"""
    }


    def getShortenResponseBody() {
        """{
            "longUrl":"${LONG_URL}",
            "kind":"${KIND}",
            "id":"${SHORT_URL}"
        }"""
    }

    def getResponseExpandedBody() {
        """{
            "longUrl":"${LONG_URL}",
            "kind":"${KIND}",
            "id":"${SHORT_URL}",
            "status":"OK"
        }"""
    }
}
