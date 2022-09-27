package br.com.blecaute.sigaa.api.response;

import br.com.blecaute.sigaa.api.SigaaClient;
import lombok.NonNull;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Document;

public interface DisciplinesResponse extends ClientResponse {

    Document getDisciplines(@NonNull SigaaClient client);

}
