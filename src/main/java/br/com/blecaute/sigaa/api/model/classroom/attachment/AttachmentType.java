package br.com.blecaute.sigaa.api.model.classroom.attachment;

import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jsoup.nodes.Element;

import java.util.Arrays;
import java.util.List;

@Getter(onMethod = @__(@Unmodifiable))
public enum AttachmentType {

    FORUM(ForumAttachment.class, "forumava.png"),
    CONTENT(ContentAttachment.class,"conteudo.png"),
    REFERENCE(ReferenceAttachment.class, "site_add.png"),
    FILE(FileAttachment.class, "pdf.png", "doc.png", "docx.png", "xls.png", "xlsx.png", "ppt.png", "pptx.png", "zip.png", "rar.png", "txt.png", "desconhecido.png"),
    TASK(TaskAttachment.class, "tarefa.png"),
    QUESTIONNAIRE(QuestionnaireAttachment.class, "questionario.png"),
    UNKNOWN(null);

    private final Class<? extends Attachment> clazz;
    private final List<String> sources;

    public static final AttachmentType[] VALUES = values();

    AttachmentType(Class<? extends Attachment> clazz, String... sources) {
        this.clazz = clazz;
        this.sources = Arrays.asList(sources);
    }

    public static AttachmentType valueOf(@NonNull Element element) {
        final var value = element.selectFirst("img");
        if (value == null) return AttachmentType.UNKNOWN;

        final var src = value.attr("src");
        for (AttachmentType type : VALUES) {
            for (String object : type.getSources()) {
                if (src.endsWith(object)) return type;
            }
        }

        return UNKNOWN;
    }

}
