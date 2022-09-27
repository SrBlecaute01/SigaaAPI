package br.com.blecaute.sigaa.api.response;

import br.com.blecaute.sigaa.api.SigaaClient;
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
import org.jsoup.nodes.Element;

import java.util.Objects;

public interface ClientResponse {

    @NotNull @SneakyThrows
    Response getResponse(@NotNull OkHttpClient client, @Nullable String cookie, @Nullable RequestBody body);

    @NotNull @SneakyThrows
    default Response getResponse(@NotNull OkHttpClient client) {
        return getResponse(client, null, null);
    }

    @NotNull @SneakyThrows
    default Response getResponse(@NonNull SigaaClient client, @Nullable RequestBody body) {
        return getResponse(client.getHttpClient(), client.getCookie(), body);
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

    @SneakyThrows
    default void walk(@NonNull SigaaClient client, @Nullable RequestBody body, @NonNull ResponseType... type) {
        final var responseType = type[type.length - 1];
        if (client.getLastResponse() == responseType) return;

        for (int index = 0; index < type.length - 1; index++) {
            walk(client, body, type[index]);
        }

        final var clientResponse = responseType.getResponse();
        try (final var response = clientResponse.getResponse(client, body)) {
            if (!response.isSuccessful() || response.code() != 200) {
                throw new ConnectionFailureException();
            }

            try (final var responseBody = response.body()) {
                if (responseBody == null) throw new ConnectionFailureException();

                final var text = responseBody.string();
                if (text.contains("Sua sessão foi expirada")) {
                    throw new ConnectionFailureException();
                }

                client.setLastResponse(responseType);
                client.setViewState(getViewState(text));
            }
        }
    }

    default String getViewState(@NonNull Element element) {
        final var state = element.getElementById("javax.faces.ViewState");
        return state != null ? state.attr("value") : "";
    }

    @SneakyThrows
    default String getViewState(@NonNull ResponseBody body) {
        return getViewState(body.string());
    }

    @SneakyThrows
    default String getViewState(@NonNull String body) {
        final var states = body.split("id=\"javax.faces.ViewState\" value=\"");
        return states[1].split("\" ")[0];
    }

}