package br.com.blecaute.sigaa.api.response;

import br.com.blecaute.sigaa.api.SigaaClient;
import lombok.NonNull;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface EnrollmentStatementResponse extends ClientResponse {

    byte[] getEnrollmentStatement(@NonNull SigaaClient client);

}