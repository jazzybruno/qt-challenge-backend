package com.supamenu.www.services.interfaces;

import com.supamenu.www.dtos.abusiveContent.CreateAbusiveCommentReport;
import com.supamenu.www.dtos.abusiveContent.CreateAbusivePostReport;
import com.supamenu.www.models.AbuseReport;

import java.util.List;
import java.util.UUID;

public interface AbuseReportService {
    AbuseReport createPostAbusiveReport(CreateAbusivePostReport abusiveReport);
    AbuseReport createCommentAbusiveReport(CreateAbusiveCommentReport abusiveReport);
    AbuseReport deleteAbusiveReport(UUID abusiveId);
    List<AbuseReport> getAbusiveReports();
    List<AbuseReport> getAbusiveReportsByPost(UUID postId);
    List<AbuseReport> getAbusiveReportsByComment(UUID commentId);
}
