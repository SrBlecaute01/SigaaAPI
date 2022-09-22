package br.com.blecaute.sigaa.api.response.impl;

import br.com.blecaute.sigaa.api.response.VirtualClassResponse;
import lombok.SneakyThrows;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Document;

public class VirtualClassResponseImpl implements VirtualClassResponse {

    @Override @SneakyThrows @NotNull
    public Response getResponse(@NotNull OkHttpClient client, @Nullable String cookie, @Nullable RequestBody body) {
        final var builder = new Request.Builder()
                .url("https://sigaa.ifal.edu.br/sigaa/portais/discente/turmas.jsf")
                .header("Cookie", "JSESSIONID=" + cookie)
                .header("Referer", "https://sigaa.ifal.edu.br/sigaa/portais/discente/turmas.jsf");

        if (body != null) {
            builder.post(body);
        }

        return client.newCall(builder.build()).execute();
    }

    @Override
    public Document getVirtualClass(@NotNull OkHttpClient client, @NotNull String cookie, @NotNull String id) {
        final var formBody = new FormBody.Builder()
                .add("j_id_jsp_1186832992_2", "j_id_jsp_1186832992_2")
                .add("j_id_jsp_1186832992_2:" + id, "j_id_jsp_1186832992_2:" + id)
                .add("javax.faces.ViewState", "j_id94")
                .build();

        return validate(getResponse(client, cookie, formBody));
    }
}