package br.com.blecaute.sigaa.api.response;

import br.com.blecaute.sigaa.api.SigaaClient;
import org.jetbrains.annotations.NotNull;

public interface HistoricResponse extends ClientResponse {

    byte[] getHistoric(@NotNull SigaaClient client);

}