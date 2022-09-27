package br.com.blecaute.sigaa.api.response;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;

public interface LoginResponse extends ClientResponse {

    Document login(@NotNull OkHttpClient client, @NotNull String cookie, @NotNull String user, @NotNull String password);

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
