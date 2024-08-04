package com.supamenu.www.services.implementations;

import com.supamenu.www.dtos.abusiveContent.CreateAbusiveCommentReport;
import com.supamenu.www.dtos.abusiveContent.CreateAbusivePostReport;
import com.supamenu.www.exceptions.CustomException;
import com.supamenu.www.exceptions.NotFoundException;
import com.supamenu.www.models.AbuseReport;
import com.supamenu.www.models.Comment;
import com.supamenu.www.models.Post;
import com.supamenu.www.models.User;
import com.supamenu.www.repositories.IAbuseReportRepository;
import com.supamenu.www.repositories.ICommentRepository;
import com.supamenu.www.repositories.IPostRepository;
import com.supamenu.www.repositories.IUserRepository;
import com.supamenu.www.services.interfaces.AbuseReportService;
import com.supamenu.www.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AbuseReportServiceImpl implements AbuseReportService {
    private final ICommentRepository commentRepository;
    private final UserServiceImpl userService;
    private final IPostRepository postRepository;
    private final IAbuseReportRepository abuseReportRepository;


    @Override
    public AbuseReport createPostAbusiveReport(CreateAbusivePostReport abusiveReport) {
        try {
            User user = userService.getLoggedInUser();
            if(user == null){
                throw new AuthenticationException("You are not logged in");
            }
            Post post = postRepository.findById(abusiveReport.getPostId()).orElseThrow(()-> new NotFoundException("The post was not found"));
            AbuseReport abuseReport = new AbuseReport();
            abuseReport.setPost(post);
            abuseReport.setAuthor(user);
            abuseReport.setTitle(abusiveReport.getTitle());
            abuseReport.setDescription(abusiveReport.getDescription());
            return abuseReportRepository.save(abuseReport);
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @Override
    public AbuseReport createCommentAbusiveReport(CreateAbusiveCommentReport abusiveReport) {
        try {
            User user = userService.getLoggedInUser();
            if(user == null){
                throw new AuthenticationException("You are not logged in");
            }
            Comment comment = commentRepository.findById(abusiveReport.getCommentId()).orElseThrow(()-> new NotFoundException("The comment was not found"));
            AbuseReport abuseReport = new AbuseReport();
            abuseReport.setComment(comment);
            abuseReport.setAuthor(user);
            abuseReport.setTitle(abusiveReport.getTitle());
            abuseReport.setDescription(abusiveReport.getDescription());
            return abuseReportRepository.save(abuseReport);
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @Override
    public AbuseReport deleteAbusiveReport(UUID abusiveId) {
        try {
            AbuseReport abuseReport = abuseReportRepository.findById(abusiveId).orElseThrow(() -> new NotFoundException("The abusive post was not found"));
            abuseReportRepository.deleteById(abusiveId);
            return abuseReport;
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @Override
    public List<AbuseReport> getAbusiveReports() {
        try {
            return abuseReportRepository.findAll();
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @Override
    public List<AbuseReport> getAbusiveReportsByPost(UUID postId) {
        try {
            Post post = postRepository.findById(postId).orElseThrow(()-> new NotFoundException("The post was not found"));
            return abuseReportRepository.findAllByPost(post);
        }catch (Exception e){
            throw new CustomException(e);
        }
    }

    @Override
    public List<AbuseReport> getAbusiveReportsByComment(UUID commentId) {
        try {
            Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new NotFoundException("The comment was not found"));
            return abuseReportRepository.findAllByComment(comment);
        }catch (Exception e){
            throw new CustomException(e);
        }
    }
}

