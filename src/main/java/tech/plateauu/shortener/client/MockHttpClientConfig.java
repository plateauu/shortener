package tech.plateauu.shortener.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import tech.plateauu.shortener.AppProfiles;

@Configuration
@Profile(AppProfiles.DEV)
public class MockHttpClientConfig {

    @Bean
    public ShortenerHttpClient httpClient() {
        return new MockHttpClient();
    }

}
