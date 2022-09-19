package br.com.blecaute.sigaa.api;

import br.com.blecaute.sigaa.api.model.User;
import br.com.blecaute.sigaa.api.parser.ParserMap;
import br.com.blecaute.sigaa.api.response.CookieResponse;
import br.com.blecaute.sigaa.api.response.LoginResponse;
import br.com.blecaute.sigaa.api.response.ResponseType;
import lombok.NonNull;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class SigaaAPI {

    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient.Builder()
            .readTimeout(25, TimeUnit.SECONDS)
            .connectTimeout(25, TimeUnit.SECONDS)
            .build();

    @NotNull
    @Contract("_, _ -> new")
    public static CompletableFuture<SigaaClient> login(@NonNull String username, @NonNull String password) {
        return CompletableFuture.supplyAsync(() -> {
            CookieResponse cookieResponse = ResponseType.COOKIE.getResponse();
            String cookie = cookieResponse.getCookie(HTTP_CLIENT);

            SigaaClient client = new SigaaClient(HTTP_CLIENT, cookie);

            LoginResponse loginResponse = ResponseType.LOGIN.getResponse();
            Document document = loginResponse.login(HTTP_CLIENT, cookie, username, password);

            client.setUser(ParserMap.parse(User.class, document));

            return client;
        });
    }

}
