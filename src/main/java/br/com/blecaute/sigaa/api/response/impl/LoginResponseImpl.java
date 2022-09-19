package br.com.blecaute.sigaa.api.response.impl;

import br.com.blecaute.sigaa.api.response.LoginResponse;
import lombok.SneakyThrows;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

}