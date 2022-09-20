package br.com.blecaute.sigaa.api.response;

import br.com.blecaute.sigaa.api.exception.ConnectionFailureException;
import br.com.blecaute.sigaa.api.exception.ExpiredSessionException;
import br.com.blecaute.sigaa.api.exception.InvalidMediaException;
import br.com.blecaute.sigaa.api.exception.MediaNotFoundException;
import lombok.NonNull;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Objects;

public interface ClientResponse {

    @NotNull @SneakyThrows
    Response getResponse(@NotNull OkHttpClient client, @Nullable String cookie, @Nullable RequestBody body);

    @NotNull @SneakyThrows
    default Response getResponse(@NotNull OkHttpClient client) {
        return getResponse(client, null, null);
    }

    @SneakyThrows
    default Document validate(@NonNull Response response) {
        if (!response.isSuccessful() || response.code() != 200) {
            throw new ConnectionFailureException();
        }

        final var document = Jsoup.parse(Objects.requireNonNull(response.body()).string());
        if (document.toString().contains("Sua sessão foi expirada")) {
            throw new ExpiredSessionException();
        }

        return document;
    }

    @SneakyThrows @NotNull
    default ResponseBody validate(@NonNull Response response, @NonNull String mediaType) {
        if (!response.isSuccessful() || response.code() != 200) {
            throw new ConnectionFailureException();
        }

        final var body = response.body();
        if (body == null) {
            throw new MediaNotFoundException();
        }

        final var media = body.contentType();
        if (media == null || !media.toString().contains(mediaType)) {
            throw new InvalidMediaException();
        }

        return body;
    }

}