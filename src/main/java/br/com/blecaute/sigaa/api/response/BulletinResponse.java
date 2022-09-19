package br.com.blecaute.sigaa.api.response;

import br.com.blecaute.sigaa.api.exception.ConnectionFailureException;
import br.com.blecaute.sigaa.api.exception.ExpiredSessionException;
import lombok.SneakyThrows;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Objects;

public interface BulletinResponse extends ClientResponse {

    @SneakyThrows
    default Document getBulletin(@NotNull OkHttpClient client, @NotNull String cookie) {
        final var formBody = new FormBody.Builder()
                .add("menu:form_menu_discente", "menu:form_menu_discente")
                .add("jscook_action", "menu_form_menu_discente_j_id_jsp_1051041857_101_menu:A]#{ relatorioNotasAluno.gerarRelatorio }")
                .add("javax.faces.ViewState",  "j_id1")
                .build();

        final var response = getResponse(client, cookie, formBody);
        if (!response.isSuccessful() || response.code() != 200) {
            throw new ConnectionFailureException();
        }

        final var document = Jsoup.parse(Objects.requireNonNull(response.body()).string());
        if (document.toString().contains("Sua sessão foi expirada")) {
            throw new ExpiredSessionException();
        }

        return document;
    }


}
