package com.example.bff.web.graphql;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
class RequestErrorInterceptor implements WebGraphQlInterceptor {

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {

        return chain.next(request).map(response -> {
            if (response.isValid()) {
                return response;
            }

            List<GraphQLError> errors = response.getErrors().stream()
                    .map(error -> GraphqlErrorBuilder.newError()
                            .message("Request error. Ensure request is valid and try again.")
                            .errorType(ErrorTypes.BAD_REQUEST)
                            .build())
                    .collect(Collectors.toList());

            return response.transform(builder -> builder.errors(errors).build());
        });
    }
}