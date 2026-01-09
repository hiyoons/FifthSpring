package com.example.FifthSpring.repository;

import com.example.FifthSpring.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    @Transactional
    void deleteAllByMember(Member member);

    @Query(value="SELECT a FROM Article a ORDER BY a.created")
    Page<Article> orderByCreated(Pageable pageable);


}
