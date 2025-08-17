package ru.shortcut.content.entity.material;



import ru.shortcut.common.constant.Chapter;
import ru.shortcut.content.entity.Author;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
public abstract class MaterialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "sequence", allocationSize = 1)
    @Schema(description = "Идентификатор ролика", example = "1")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Chapter chapter;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;


}
