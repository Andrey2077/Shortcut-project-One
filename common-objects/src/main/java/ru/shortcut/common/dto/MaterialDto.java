package ru.shortcut.common.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.shortcut.common.constant.Chapter;
import ru.shortcut.common.constant.TypeOfMaterial;

@Schema(description = "Данные статьи/видеоклипа")
@Data
@AllArgsConstructor
public class MaterialDto {

    private Long id;

    private TypeOfMaterial type;

    private String title;

    private Chapter chapter;

    private String authorFullName;


}
