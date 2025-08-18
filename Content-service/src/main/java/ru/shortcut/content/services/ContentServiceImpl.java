package ru.shortcut.content.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.jpa.domain.Specification;
import ru.shortcut.common.constant.Chapter;
import ru.shortcut.common.dto.MaterialDto;
import ru.shortcut.content.entity.Author;
import ru.shortcut.content.entity.material.ArticleEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.shortcut.content.entity.material.ClipEntity;
import ru.shortcut.content.mapper.MaterialMapper;
import ru.shortcut.content.repositories.ArticleRepository;
import ru.shortcut.content.repositories.ClipRepository;

import javax.persistence.criteria.Join;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService{

    private final ArticleRepository articleRepository;
    private final ClipRepository clipRepository;
    private final MaterialMapper materialMapper;

    @Override
    public List<MaterialDto> search(String title, String lastName, String by, String chapterString) {

        Chapter chapter = null;
        if (chapterString != null) {
            try {
                chapter = Chapter.valueOf(chapterString.toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Unknown chapter: " + chapterString);
            }
        }

        boolean filterByAuthor = "author".equalsIgnoreCase(by);
        boolean filterByCoauthor = "coauthor".equalsIgnoreCase(by);
        boolean filterAny = "any".equalsIgnoreCase(by) || by == null;
        List<MaterialDto> result = new ArrayList<>();
        Specification<ArticleEntity> articleSpec= createSpecificationArticle(title, chapter, lastName, filterByAuthor, filterByCoauthor, filterAny);

        for (ArticleEntity articleEntity : articleRepository.findAll(articleSpec)) {
            result.add(materialMapper.toDto(articleEntity));
        }

        Specification<ClipEntity> clipSpec= createSpecificationClip(title, chapter, lastName, filterByAuthor, filterByCoauthor, filterAny);
        for (ArticleEntity articleEntity : articleRepository.findAll(clipSpec)) {
            result.add(materialMapper.toDto(ClipEntity));
        }

    }


    private boolean matchesAuthor(String lastName, Author author) {
        return author != null &&
                author.getLastName() != null &&
                author.getLastName().equalsIgnoreCase(lastName);
    }

    private boolean containsCoauthor(String lastName, Iterable<Author> coauthors) {
        if (coauthors == null) return false;
        for (Author a : coauthors) {
            if (matchesAuthor(lastName, a)) return true;
        }
        return false;
    }

    private  Specification<ArticleEntity> createSpecificationArticle(String title,
                                                                     Chapter chapter,
                                                                     String lastName,
                                                                     boolean filterByAuthor,
                                                                     boolean filterByCoauthor,
                                                                     boolean filterAny) {
        Specification<ArticleEntity> articleSpec = Specification.where(null);
        if (title != null) {
            articleSpec = articleSpec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }

        if (chapter != null) {
            articleSpec = articleSpec.and((root, query, cb) ->
                    cb.equal(root.get("chapter"), chapter));
        }

        if (lastName != null) {
            Specification<ArticleEntity> authorSpec = (root, query, cb) ->
                    cb.equal(cb.lower(root.join("Author").get("lastName")), lastName.toLowerCase());

            Specification<ArticleEntity> coauthorSpec = (root, query, cb) -> {
                Join<ArticleEntity, Author> coauthors = root.joinSet("coAuthors");
                return cb.equal(cb.lower(coauthors.get("lastName")), lastName.toLowerCase());
            };

            if (filterByAuthor) articleSpec = articleSpec.and(authorSpec);
            else if (filterByCoauthor) articleSpec = articleSpec.and(coauthorSpec);
            else if (filterAny) articleSpec = articleSpec.and(authorSpec.or(coauthorSpec));
        }

        return articleSpec;
    }

    private  Specification<ClipEntity> createSpecificationClip(String title,
                                                                  Chapter chapter,
                                                                  String lastName,
                                                                  boolean filterByAuthor,
                                                                  boolean filterByCoauthor,
                                                                  boolean filterAny) {
        Specification<ClipEntity> clipSpec = Specification.where(null);
        if (title != null) {
            clipSpec = clipSpec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }

        if (chapter != null) {
            clipSpec = clipSpec.and((root, query, cb) ->
                    cb.equal(root.get("chapter"), chapter));
        }

        if (lastName != null) {
            Specification<ClipEntity> authorSpec = (root, query, cb) ->
                    cb.equal(cb.lower(root.join("Author").get("lastName")), lastName.toLowerCase());

            Specification<ClipEntity> coauthorSpec = (root, query, cb) -> {
                Join<ClipEntity, Author> coauthors = root.joinSet("coAuthors");
                return cb.equal(cb.lower(coauthors.get("lastName")), lastName.toLowerCase());
            };

            if (filterByAuthor) clipSpec = clipSpec.and(authorSpec);
            else if (filterByCoauthor) clipSpec = clipSpec.and(coauthorSpec);
            else if (filterAny) clipSpec = clipSpec.and(authorSpec.or(coauthorSpec));
        }

        return clipSpec;
    }



}
