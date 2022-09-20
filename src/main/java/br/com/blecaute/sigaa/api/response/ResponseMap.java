package br.com.blecaute.sigaa.api.response;

import br.com.blecaute.sigaa.api.response.impl.BulletinResponseImpl;
import br.com.blecaute.sigaa.api.response.impl.CookieResponseImpl;
import br.com.blecaute.sigaa.api.response.impl.DisciplinesResponseImpl;
import br.com.blecaute.sigaa.api.response.impl.LoginResponseImpl;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ResponseMap {

    private static final Map<ResponseType, ClientResponse> RESPONSES = new HashMap<>();

    static {
        RESPONSES.put(ResponseType.COOKIE, new CookieResponseImpl());
        RESPONSES.put(ResponseType.LOGIN, new LoginResponseImpl());
        RESPONSES.put(ResponseType.BULLETIN, new BulletinResponseImpl());
        RESPONSES.put(ResponseType.DISCIPLINES, new DisciplinesResponseImpl());
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public static <T extends ClientResponse> T getResponse(ResponseType type) {
        return (T) Objects.requireNonNull(RESPONSES.get(type));
    }

}
