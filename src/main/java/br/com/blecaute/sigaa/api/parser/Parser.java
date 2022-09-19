package br.com.blecaute.sigaa.api.parser;

import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

public interface Parser<T> {

    T parse(@NotNull Element element);

}