package io.zhenye.gateway.filter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Component
public class HttpAuthFilter implements GatewayFilter, Ordered {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_TRAILING_TOKENS, true);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerRequest request = ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());

        HttpMethod method = request.method();
        if (method != HttpMethod.POST) {
            return Mono.error(new Exception());
        }

        Optional<MediaType> mediaTypeOptional = request.headers().contentType();
        if (!mediaTypeOptional.isPresent()) {
            return Mono.error(new Exception());
        }

        MediaType mediaType = mediaTypeOptional.get();
        if (!mediaType.getType().equals(MediaType.APPLICATION_JSON.getType()) ||
                !mediaType.getSubtype().equals(MediaType.APPLICATION_JSON.getSubtype())) {
            return Mono.error(new Exception());
        }

//        Object body = exchange.getAttribute("cachedRequestBodyObject");
//        if (Objects.isNull(body)) {
//            return Mono.error(new Exception());
//        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }

}
