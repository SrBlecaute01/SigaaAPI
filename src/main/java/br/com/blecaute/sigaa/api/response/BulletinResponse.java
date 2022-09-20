package br.com.blecaute.sigaa.api.response;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;

public interface BulletinResponse extends ClientResponse {

    default Document getBulletin(@NotNull OkHttpClient client, @NotNull String cookie) {
        final var formBody = new FormBody.Builder()
                .add("menu:form_menu_discente", "menu:form_menu_discente")
                .add("jscook_action", "menu_form_menu_discente_j_id_jsp_1051041857_101_menu:A]#{ relatorioNotasAluno.gerarRelatorio }")
                .add("javax.faces.ViewState", "j_id1")
                .build();

        return validate(getResponse(client, cookie, formBody));
    }

}