package br.com.blecaute.sigaa.api.response;

import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface EnrollmentStatementResponse extends ClientResponse {

    byte[] getEnrollmentStatement(@NotNull OkHttpClient client, @Nullable String cookie);

}