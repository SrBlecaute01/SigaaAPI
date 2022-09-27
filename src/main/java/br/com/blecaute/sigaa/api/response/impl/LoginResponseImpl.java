package br.com.blecaute.sigaa.api.response.impl;

import br.com.blecaute.sigaa.api.exception.ConnectionFailureException;
import br.com.blecaute.sigaa.api.exception.InvalidCredentialsException;
import br.com.blecaute.sigaa.api.exception.InvalidUserException;
import br.com.blecaute.sigaa.api.response.LoginResponse;
import lombok.SneakyThrows;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class LoginResponseImpl implements LoginResponse {

    @Override @SneakyThrows @NotNull
    public Response getResponse(@NotNull OkHttpClient client, @Nullable String cookie, @Nullable RequestBody body) {
        final var builder = new Request.Builder()
                .url("https://sigaa.ifal.edu.br/sigaa/logar.do?dispatch=logOn")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Cookie", "JSESSIONID=" + cookie)
                .header("Referer", "https://sigaa.ifal.edu.br/sigaa/verTelaLogin.do");

        if (body != null) {
            builder.post(body);
        }

        return client.newCall(builder.build()).execute();
    }

    @Override
    public Document login(@NotNull OkHttpClient client, @NotNull String cookie, @NotNull String user, @NotNull String password) {
        try (final var response = getResponse(client, cookie, user, password)) {
            if (!response.isSuccessful() || response.code() != 200) {
                throw new ConnectionFailureException();
            }

            try (final var body = response.body()) {
                if (body == null) throw new ConnectionFailureException();

                final var document = Jsoup.parse(body.string());
                if (document.toString().contains("Usuário e/ou senha inválidos")) {
                    throw new InvalidCredentialsException();
                }

                if (document.location().contains("vinculos.jsf")) {
                    throw new InvalidUserException();
                }

                return document;
            }

        } catch (Exception exception) {
            throw new ConnectionFailureException();
        }
    }
}