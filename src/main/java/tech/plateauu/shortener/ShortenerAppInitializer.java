package tech.plateauu.shortener;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import tech.plateauu.shortener.client.HttpClientConfig;

class ShortenerAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootApplication.class, HttpClientConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
