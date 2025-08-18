package ru.shortcut.content.controllers;



import ru.shortcut.common.dto.MaterialDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.shortcut.content.services.ContentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("Materials/")
@Tag(name = "Статьи или видеоролики", description = "Обработка входящих запросов")
public class ContentController {

    private final ContentService contentService;

    @Operation(summary = "Принять запрос", description = "Принимает запрос на поиск статьи/видеоролика")
    @GetMapping("/search")
    public List<MaterialDto> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String by, // "author" или "coauthor"
            @RequestParam(required = false) String chapter
    ) {
        return contentService.search(title, lastName, by, chapter);
    }

}
