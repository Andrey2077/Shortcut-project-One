package ru.shortcut.content.entity.material;


import ru.shortcut.content.entity.Author;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "articles")
@Data
@Schema(description = "Статьи")
public class ArticleEntity extends MaterialEntity {


    @NotBlank
    @Column(nullable = false)
    private String content;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "article_coauthors",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> coauthors = new HashSet<>();

    @PrePersist @PreUpdate
    private void validateAuthors() {
        if (coauthors.contains(getAuthor())) {
            throw new IllegalStateException("Автор не должен быть в соавторах");
        }
    }
}
