package com.example.FifthSpring.repository;

import com.example.FifthSpring.model.TagArticleMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleHashTagRepository extends JpaRepository<TagArticleMap,Long> {

//    Optional<TagArticleMap> findByArticle(Article article);

    List<TagArticleMap> findAllByArticle(Article article);

    void deleteByArticle(Article article);

    Page<TagArticleMap> findAllByHashTagTagName(Pageable pageable,String tagName);
}
