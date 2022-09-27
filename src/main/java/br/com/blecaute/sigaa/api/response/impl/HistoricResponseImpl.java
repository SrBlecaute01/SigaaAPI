package br.com.blecaute.sigaa.api.response.impl;

import br.com.blecaute.sigaa.api.SigaaClient;
import br.com.blecaute.sigaa.api.response.HistoricResponse;
import br.com.blecaute.sigaa.api.response.ResponseType;
import lombok.SneakyThrows;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HistoricResponseImpl implements HistoricResponse {

    @Override @NotNull  @SneakyThrows
    public Response getResponse(@NotNull OkHttpClient client, @Nullable String cookie, @Nullable RequestBody body) {
        final var builder = new Request.Builder()
                .url("https://sigaa.ifal.edu.br/sigaa/portais/discente/discente.jsf")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Cookie", "JSESSIONID=" + cookie)
                .header("Referer", "https://sigaa.ifal.edu.br/sigaa/portais/discente/discente.jsf");

        if (body != null) {
            builder.post(body);
        }

        return client.newCall(builder.build()).execute();
    }

    @Override @SneakyThrows
    public byte[] getHistoric(@NotNull SigaaClient client) {
        walk(client, null, ResponseType.STUDENT);

        final var formBody = new FormBody.Builder()
                .add("menu:form_menu_discente", "menu:form_menu_discente")
                .add("jscook_action", "menu_form_menu_discente_j_id_jsp_1051041857_101_menu:A]#{ portalDiscente.historico }")
                .add("javax.faces.ViewState", client.getViewState())
                .build();

        final var body = validate(getResponse(client.getHttpClient(), client.getCookie(), formBody), "application/pdf");
        final var bytes = body.bytes();

        client.setViewState(getViewState(body));
        client.setLastResponse(ResponseType.STUDENT);

        return bytes;
    }
}
