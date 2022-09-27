package br.com.blecaute.sigaa.api.response;

import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface CookieResponse extends ClientResponse {

    @NotNull
    String getCookie(@NotNull OkHttpClient client);

}
