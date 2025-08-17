package ru.shortcut.content.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.shortcut.common.constant.TypeOfMaterial;
import ru.shortcut.common.dto.MaterialDto;
import ru.shortcut.content.entity.material.ArticleEntity;
import ru.shortcut.content.entity.material.ClipEntity;



@Mapper(componentModel = "spring")
public interface MaterialMapper {

    MaterialDto toDto(ArticleEntity articleEntity);

    MaterialDto toDto(ClipEntity clipEntity);

    @Mapping(source = "type", target = "type", qualifiedByName = "mapStringToType")
    ArticleEntity toArticleEntity(MaterialDto dto);

    @Mapping(source = "type", target = "type", qualifiedByName = "mapStringToType")
    ClipEntity toClipEntity(MaterialDto dto);


    @Named("mapStringToType")
    default TypeOfMaterial mapStringToType(String type) {
        return TypeOfMaterial.valueOf(type);
    }

}