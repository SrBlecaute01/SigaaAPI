package br.com.blecaute.sigaa.api.response.impl;

import br.com.blecaute.sigaa.api.SigaaClient;
import br.com.blecaute.sigaa.api.response.ResponseType;
import br.com.blecaute.sigaa.api.response.StudentResponse;
import lombok.NonNull;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Document;

public class StudentResponseImpl implements StudentResponse {

    @Override @NotNull @SneakyThrows
    public Response getResponse(@NotNull OkHttpClient client, @Nullable String cookie, @Nullable RequestBody body) {
        final var builder = new Request.Builder()
                .url("https://sigaa.ifal.edu.br/sigaa/verPortalDiscente.do?")
                .header("Cookie", "JSESSIONID=" + cookie);

        if (body != null) {
            builder.post(body);
        }

        return client.newCall(builder.build()).execute();
    }

    @Override
    public Document getStudent(@NonNull SigaaClient client) {
        try (final var response  = getResponse(client, null)) {
            final var document = validate(response);

            client.setViewState(getViewState(document));
            client.setLastResponse(ResponseType.STUDENT);

            return document;
        }
    }

}
