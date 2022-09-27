package br.com.blecaute.sigaa.api.response;

import br.com.blecaute.sigaa.api.SigaaClient;
import lombok.NonNull;
import org.jsoup.nodes.Document;

public interface StudentResponse extends ClientResponse {

    Document getStudent(@NonNull SigaaClient client);

}