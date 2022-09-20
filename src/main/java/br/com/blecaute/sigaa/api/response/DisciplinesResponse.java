package br.com.blecaute.sigaa.api.response;

import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Document;

public interface DisciplinesResponse extends ClientResponse {

    default Document getDisciplines(@NotNull OkHttpClient client, @Nullable String cookie) {
        return validate(getResponse(client, cookie, null));
    }

}
