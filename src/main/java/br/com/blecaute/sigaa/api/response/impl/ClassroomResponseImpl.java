package br.com.blecaute.sigaa.api.response.impl;

import br.com.blecaute.sigaa.api.SigaaClient;
import br.com.blecaute.sigaa.api.response.ClassroomResponse;
import br.com.blecaute.sigaa.api.response.ResponseType;
import lombok.NonNull;
import lombok.SneakyThrows;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Document;

public class ClassroomResponseImpl implements ClassroomResponse {

    @Override @SneakyThrows @NotNull
    public Response getResponse(@NotNull OkHttpClient client, @Nullable String cookie, @Nullable RequestBody body) {
        final var builder = new Request.Builder()
                .url("https://sigaa.ifal.edu.br/sigaa/portais/discente/turmas.jsf")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Cookie", "JSESSIONID=" + cookie)
                .header("Referer", "https://sigaa.ifal.edu.br/sigaa/portais/discente/turmas.jsf");

        if (body != null) {
            builder.post(body);
        }

        return client.newCall(builder.build()).execute();
    }

    @Override
    public Document getClassroom(@NonNull SigaaClient client, @NotNull String id) {
        walk(client, null, ResponseType.STUDENT, ResponseType.DISCIPLINES);

        final var formBody = new FormBody.Builder()
                .add("j_id_jsp_1186832992_2", "j_id_jsp_1186832992_2")
                .add("j_id_jsp_1186832992_2:" + id, "j_id_jsp_1186832992_2:" + id)
                .add("javax.faces.ViewState", client.getViewState())
                .build();

        try (final var response = getResponse(client, formBody)) {
            final var document = validate(response);

            client.setViewState(getViewState(document));
            client.setLastResponse(ResponseType.CLASSROOM);

            return document;
        }
    }
}