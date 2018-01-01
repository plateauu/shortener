package tech.plateauu.shortener

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.web.client.RestTemplate
import spock.lang.Shared
import tech.plateauu.shortener.Client.HttpClient

import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

class HttpClientCommunicationTest extends SpringContextTest{

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

    def "should send post request to make url short"(){
        def httpAddress = HttpClient.SHORTEN_URL + api
        def client = new HttpClient(api, restTemplate)
        server.expect(requestTo(httpAddress))
                .andExpect(method(POST))
                .andExpect(content().string(getRequestBody()))
                .andRespond(withSuccess(getResponseBody(), APPLICATION_JSON))

        when:
        def shortUrl = client.doShort(LONG_URL)

        then:
        server.verify()

        and:
        shortUrl == SHORT_URL

    }

    def getRequestBody() {
        """{"longUrl":"${LONG_URL}"}"""
    }


    def getResponseBody() {
        """{
            "longUrl":"${LONG_URL}",
            "kind":"${KIND}",
            "id":"${SHORT_URL}"
        }"""
    }
}
