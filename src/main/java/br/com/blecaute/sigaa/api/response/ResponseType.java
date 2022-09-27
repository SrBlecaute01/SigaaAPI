package br.com.blecaute.sigaa.api.response;

import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum ResponseType {

    COOKIE,
    LOGIN,
    BULLETIN,
    DISCIPLINES,
    HISTORIC,
    ENROLLMENT_STATEMENT,
    CLASSROOM,
    STUDENT,

    ;

    @NotNull
    @Contract(pure = true)
    public <T extends ClientResponse> T getResponse() {
        return ResponseMap.getResponse(this);
    }

    @Contract("_ -> !null")
    public <T extends ClientResponse> T getResponse(@NonNull Class<T> clazz) {
        return clazz.cast(ResponseMap.getResponse(this));
    }

}