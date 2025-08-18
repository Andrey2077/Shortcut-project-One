package ru.shortcut.content.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shortcut.content.entity.material.ClipEntity;

public interface ClipRepository extends JpaRepository <ClipEntity, Long>{
}
