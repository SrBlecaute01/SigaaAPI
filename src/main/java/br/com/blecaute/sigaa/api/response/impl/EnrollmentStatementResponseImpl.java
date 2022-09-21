package br.com.blecaute.sigaa.api.response.impl;

import br.com.blecaute.sigaa.api.response.EnrollmentStatementResponse;
import lombok.SneakyThrows;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnrollmentStatementResponseImpl implements EnrollmentStatementResponse {

    @Override @NotNull @SneakyThrows
    public Response getResponse(@NotNull OkHttpClient client, @Nullable String cookie, @Nullable RequestBody body) {
        final var builder = new Request.Builder()
                .url("https://sigaa.ifal.edu.br/sigaa/portais/discente/discente.jsf")
                .header("Cookie", "JSESSIONID=" + cookie)
                .header("Referer", "https://sigaa.ifal.edu.br/sigaa/portais/discente/discente.jsf");

        if (body != null) {
            builder.post(body);
        }

        return client.newCall(builder.build()).execute();
    }

    @Override @SneakyThrows
    public byte[] getEnrollmentStatement(@NotNull OkHttpClient client, @Nullable String cookie) {
        final var formBody = new FormBody.Builder()
                .add("menu:form_menu_discente", "menu:form_menu_discente")
                .add("jscook_action", "menu_form_menu_discente_j_id_jsp_1051041857_101_menu:A]#{ declaracaoVinculo.emitirDeclaracao }")
                .add("javax.faces.ViewState", "j_id1")
                .build();

        return validate(getResponse(client, cookie, formBody), "application/pdf").bytes();
    }
}
