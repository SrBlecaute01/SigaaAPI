package br.com.blecaute.sigaa.api.response.impl;

import br.com.blecaute.sigaa.api.response.CookieResponse;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CookieResponseImpl implements CookieResponse {

    private final Request request = new Request.Builder().url("https://sigaa.ifal.edu.br/sigaa/verTelaLogin.do").get().build();

    @Override @SneakyThrows @NotNull
    public Response getResponse(@NotNull OkHttpClient client, @Nullable String cookie, @Nullable RequestBody body) {
        return client.newCall(request).execute();
    }

    @Override @NotNull
    public String getCookie(@NotNull OkHttpClient client) {
        try (final var response = getResponse(client)) {
            final var cookies = response.headers().values("Set-Cookie");
            return cookies.get(0).split(";")[0].split("JSESSIONID=")[1];
        }
    }
}