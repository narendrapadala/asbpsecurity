package corp.asbp.platform.security.config;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import corp.asbp.platform.security.util.CommonUtilProperties;
import corp.asbp.platform.security.util.RestTemplateErrorHandler;



@Configuration
@Import(CommonUtilProperties.class)
public class RestTemplateConfiguration {
	private static Logger LOGGER = LoggerFactory.getLogger(RestTemplateConfiguration.class);
	
	@Autowired
	private CommonUtilProperties properties;
	
    @Bean("restTemplate")
    public RestTemplate restTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        RestTemplate restTemplate = new RestTemplate(getRequestFactory(true));
        restTemplate.setErrorHandler(new RestTemplateErrorHandler());
        return restTemplate;
    }
    
    @Bean("restTemplateWoSsl")
    public RestTemplate restTemplateWoSsl() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        RestTemplate restTemplate = new RestTemplate(getRequestFactory(false));
        restTemplate.setErrorHandler(new RestTemplateErrorHandler());
        return restTemplate;
    }
    
    private ClientHttpRequestFactory getRequestFactory(boolean isSslVerificationRequired) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        Integer readTimeout = properties.getReadTimeout();
        Integer connectionTimeout = properties.getConnectionTimeout();
        Integer connectionRequestTimeout = properties.getConnectionRequestTimeout();
        
		factory.setReadTimeout(timeInMillis(readTimeout));
		factory.setConnectTimeout(timeInMillis(connectionTimeout));
		factory.setConnectionRequestTimeout(timeInMillis(connectionRequestTimeout));
        LOGGER.info("Rest template config: readTimeout => " + readTimeout + ", connectionTimeout => " + connectionTimeout + ", connectionRequestTimeout => " + connectionRequestTimeout);
        
        if (!isSslVerificationRequired) {
        	TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            };
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
            factory.setHttpClient(httpClient);
        }
        return factory;
    }
    
    private int timeInMillis(Integer timeInSeconds) {
		return (int) TimeUnit.SECONDS.toMillis(timeInSeconds);
	}
}
