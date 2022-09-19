package br.com.blecaute.sigaa.api.response;

import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ClientResponse {

    @NotNull @SneakyThrows
    Response getResponse(@NotNull OkHttpClient client, @Nullable String cookie, @Nullable RequestBody body);

    @NotNull @SneakyThrows
    default Response getResponse(@NotNull OkHttpClient client) {
        return getResponse(client, null, null);
    }

}