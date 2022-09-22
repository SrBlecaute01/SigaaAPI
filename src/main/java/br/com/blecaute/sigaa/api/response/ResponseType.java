package br.com.blecaute.sigaa.api.response;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum ResponseType {

    COOKIE,
    LOGIN,
    BULLETIN,
    DISCIPLINES,
    HISTORIC,
    ENROLLMENT_STATEMENT,
    VIRTUAL_CLASS,

    ;

    @NotNull
    @Contract(pure = true)
    public <T extends ClientResponse> T getResponse() {
        return ResponseMap.getResponse(this);
    }

}