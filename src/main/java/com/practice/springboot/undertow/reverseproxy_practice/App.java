package com.practice.springboot.undertow.reverseproxy_practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import io.undertow.server.HandlerWrapper;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.RequestDumpingHandler;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class App {
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public UndertowEmbeddedServletContainerFactory embeddedServletContainerFactory() {
		UndertowEmbeddedServletContainerFactory factory = new UndertowEmbeddedServletContainerFactory();
		
		// Customizing the deployment info and adding handlers.
		factory.addDeploymentInfoCustomizers(deploymentInfo -> {
			deploymentInfo.addInnerHandlerChainWrapper(new HandlerWrapper() {
				@Override
				public HttpHandler wrap(HttpHandler handler) {
					return new RequestDumpingHandler(handler);
				}
			});

		});
		return factory;
	}
}
