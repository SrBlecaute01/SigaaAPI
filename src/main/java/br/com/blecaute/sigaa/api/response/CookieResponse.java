package br.com.blecaute.sigaa.api.response;

import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface CookieResponse extends ClientResponse {

    @NotNull
    default String getCookie(@NotNull OkHttpClient client) {
        List<String> cookies = getResponse(client).headers().values("Set-Cookie");
        return cookies.get(0).split(";")[0].split("JSESSIONID=")[1];
    }

}
