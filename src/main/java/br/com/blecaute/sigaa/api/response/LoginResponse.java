package br.com.blecaute.sigaa.api.response;

import br.com.blecaute.sigaa.api.exception.ConnectionFailureException;
import br.com.blecaute.sigaa.api.exception.InvalidCredentialsException;
import br.com.blecaute.sigaa.api.exception.InvalidUserException;
import lombok.SneakyThrows;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Objects;

public interface LoginResponse extends ClientResponse {

    @SneakyThrows
    default Document login(@NotNull OkHttpClient client, @NotNull String cookie, @NotNull String user, @NotNull String password) {
        final var response = getResponse(client, cookie, user, password);
        if (!response.isSuccessful() || response.code() != 200) {
            throw new ConnectionFailureException();
        }

        final var document = Jsoup.parse(Objects.requireNonNull(response.body()).string());
        if (document.toString().contains("Usuário e/ou senha inválidos")) {
            throw new InvalidCredentialsException();
        }

        if (document.location().contains("vinculos.jsf")) {
            throw new InvalidUserException();
        }

        return document;
    }

    @NotNull
    default Response getResponse(@NotNull OkHttpClient client, @NotNull String cookie,
                                 @NotNull String user, @NotNull String password) {

        return getResponse(client, cookie, new FormBody.Builder()
                .add("width", "0")
                .add("height", "0")
                .add("user.login", user)
                .add("user.senha", password)
                .add("entrar", "Entrar")
                .add("urlRedirect", "")
                .add("acao","")
                .build());

    }

}
