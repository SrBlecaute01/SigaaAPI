package br.com.blecaute.sigaa.api.response;

import br.com.blecaute.sigaa.api.response.impl.*;
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
        RESPONSES.put(ResponseType.HISTORIC, new HistoricResponseImpl());
        RESPONSES.put(ResponseType.ENROLLMENT_STATEMENT, new EnrollmentStatementResponseImpl());
        RESPONSES.put(ResponseType.VIRTUAL_CLASS, new VirtualClassResponseImpl());
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public static <T extends ClientResponse> T getResponse(ResponseType type) {
        return (T) Objects.requireNonNull(RESPONSES.get(type));
    }

}
