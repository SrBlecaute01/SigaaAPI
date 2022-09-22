package br.com.blecaute.sigaa.api.mapper;

import lombok.NonNull;
import org.jsoup.nodes.Element;

public interface Mapper<T> {

    T map(@NonNull Element element);

}