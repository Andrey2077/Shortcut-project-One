package ru.shortcut.content.services;

import ru.shortcut.common.dto.MaterialDto;


import java.util.List;

public interface ContentService {

    List<MaterialDto> search(String title, String lastName, String by, String chapter);


}
