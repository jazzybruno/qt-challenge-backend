package com.supamenu.www.repositories;

import com.supamenu.www.models.AbuseReport;
import com.supamenu.www.models.Comment;
import com.supamenu.www.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IAbuseReportRepository extends JpaRepository<AbuseReport , UUID> {
    List<AbuseReport> findAllByPost(Post post);
    List<AbuseReport> findAllByComment(Comment comment);
}
