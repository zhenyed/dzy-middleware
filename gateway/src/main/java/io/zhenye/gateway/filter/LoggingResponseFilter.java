package io.zhenye.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class LoggingResponseFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange.mutate().response(decorateResponse(exchange)).build());
    }

    private ServerHttpResponseDecorator decorateResponse(ServerWebExchange exchange) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();

        return new ServerHttpResponseDecorator(originalResponse) {
            @NonNull
            @Override
            public Mono<Void> writeWith(@NonNull Publisher<? extends DataBuffer> body) {
                try {
                    Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                    return super.writeWith(
                            fluxBody.buffer().map(
                                    dataBuffer -> {
                                        DataBuffer join = bufferFactory.join(dataBuffer);
                                        byte[] content = new byte[join.readableByteCount()];
                                        join.read(content);
                                        DataBufferUtils.release(join);
                                        String responseBody = new String(content, StandardCharsets.UTF_8);
                                        log.info("Http response: {} - {}", originalResponse.getStatusCode(), responseBody);
                                        return bufferFactory.wrap(content);
                                    }
                            )
                    );
                } catch (Exception e) {
                    log.error("Logging response exception", e);
                    return super.writeWith(body);
                }
            }
        };
    }

    @Override
    public int getOrder() {
        return -1000;
    }

}
