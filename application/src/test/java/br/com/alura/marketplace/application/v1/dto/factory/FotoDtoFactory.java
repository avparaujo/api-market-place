package br.com.alura.marketplace.application.v1.dto.factory;

import br.com.alura.marketplace.application.v1.dto.FotoDto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class FotoDtoFactory {

    public static Request criarFotoDtoRequest() {
        return new Request(FotoDto.Request.builder());
    }

    @RequiredArgsConstructor(access = PRIVATE)
    public static class Request {
        private final FotoDto.Request.RequestBuilder builder;

        public FotoDto.Request comTodosOsCampos() {
            return builder
                    .fileName("file-name-1.jpg")
                    .base64("Y2Fyb2xpbmEgSGVycmVyYQ==")
                    .build();
        }
    }

}