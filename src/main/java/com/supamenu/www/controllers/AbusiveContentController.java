package com.supamenu.www.controllers;

import com.supamenu.www.dtos.abusiveContent.CreateAbusiveCommentReport;
import com.supamenu.www.dtos.abusiveContent.CreateAbusivePostReport;
import com.supamenu.www.dtos.response.ApiResponse;
import com.supamenu.www.exceptions.CustomException;
import com.supamenu.www.models.AbuseReport;
import com.supamenu.www.services.implementations.AbuseReportServiceImpl;
import com.supamenu.www.services.interfaces.AbuseReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/abusive-content")
public class AbusiveContentController {
    private final AbuseReportServiceImpl abuseReportService;


    @PostMapping("/create/post")
    public ResponseEntity<ApiResponse<AbuseReport>> createPostAbusiveReport(@RequestBody CreateAbusivePostReport abusiveReport) {
        try {
            return ApiResponse.success(
                    "Successfully Reported Post",
                    HttpStatus.CREATED,
                    abuseReportService.createPostAbusiveReport(abusiveReport)
            );
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @PostMapping("/create/comment")
    public ResponseEntity<ApiResponse<AbuseReport>> createCommentAbusiveReport(@RequestBody CreateAbusiveCommentReport abusiveReport) {
        try {
            return ApiResponse.success(
                    "Successfully Reported Comment",
                    HttpStatus.CREATED,
                    abuseReportService.createCommentAbusiveReport(abusiveReport)
            );
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @DeleteMapping("/delete/{abusiveId}")
    public ResponseEntity<ApiResponse<AbuseReport>> deleteAbusiveReport(@PathVariable  UUID abusiveId) {
        try {
            return ApiResponse.success(
                    "Successfully deleted Report",
                    HttpStatus.OK,
                    abuseReportService.deleteAbusiveReport(abusiveId)
            );
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<AbuseReport>>> getAbusiveReports() {
        try {
            return ApiResponse.success(
                    "Successfully retrieved Reports",
                    HttpStatus.OK,
                    abuseReportService.getAbusiveReports()
            );
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @GetMapping("/all/user")
    public ResponseEntity<ApiResponse<List<AbuseReport>>> getAbusiveReportsByUser() {
        try {
            return ApiResponse.success(
                    "Successfully retrieved Reports by user",
                    HttpStatus.OK,
                    abuseReportService.getAbusiveReportsByUser()
            );
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<ApiResponse<List<AbuseReport>>> getAbusiveReportsByPost(@PathVariable UUID postId) {
        try {
            return ApiResponse.success(
                    "Successfully retrieved Reports by post",
                    HttpStatus.OK,
                    abuseReportService.getAbusiveReportsByPost(postId)
            );
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponse<List<AbuseReport>>> getAbusiveReportsByComment(@PathVariable  UUID commentId) {
        try {
            return ApiResponse.success(
                    "Successfully retrieved Reports by comment",
                    HttpStatus.OK,
                    abuseReportService.getAbusiveReportsByComment(commentId)
            );
        }catch (Exception e){
            throw new CustomException(e);
        }
    }
}
