package ru.shortcut.content.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shortcut.content.entity.Author;

public interface AuthorRepository extends JpaRepository <Author, Long>{
}
