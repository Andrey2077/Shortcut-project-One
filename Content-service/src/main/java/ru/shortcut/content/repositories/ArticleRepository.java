package ru.shortcut.content.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shortcut.content.entity.material.ArticleEntity;

public interface ArticleRepository extends JpaRepository <ArticleEntity, Long> {
}
