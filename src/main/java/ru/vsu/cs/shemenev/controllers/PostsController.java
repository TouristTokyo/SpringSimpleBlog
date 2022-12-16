package ru.vsu.cs.shemenev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.cs.shemenev.dao.CommentDAO;
import ru.vsu.cs.shemenev.dao.PostDAO;
import ru.vsu.cs.shemenev.dao.TagDAO;
import ru.vsu.cs.shemenev.dao.UserDAO;
import ru.vsu.cs.shemenev.models.Comment;
import ru.vsu.cs.shemenev.models.Post;
import ru.vsu.cs.shemenev.models.Tag;

import javax.validation.Valid;

@Controller
@RequestMapping("/posts")
public class PostsController {
    private final UserDAO userDAO;
    private final PostDAO postDAO;

    private final TagDAO tagDAO;
    private final CommentDAO commentDAO;

    @Autowired
    public PostsController(UserDAO userDAO, PostDAO postDAO, TagDAO tagDAO, CommentDAO commentDAO) {
        this.userDAO = userDAO;
        this.postDAO = postDAO;
        this.tagDAO = tagDAO;
        this.commentDAO = commentDAO;
    }


    @GetMapping
    public String showPosts(Model model) {
        if (!userDAO.isLogin()) {
            return "redirect:/";
        }
        model.addAttribute("posts", postDAO.getPosts());
        return "posts/postsAll";
    }

    @GetMapping("/new")
    public String createPost(Model model) {
        if (!userDAO.isLogin()) {
            return "redirect:/";
        }
        model.addAttribute("post", new Post());
        model.addAttribute("tag", new Tag());
        return "posts/new";
    }

    @GetMapping("/{id}")
    public String showPost(@PathVariable("id") int id, Model model) {
        if (!userDAO.isLogin()) {
            return "redirect:/";
        }
        Post post = postDAO.getPostById(id);
        if (post == null) {
            return "notFound";
        }
        model.addAttribute("post", post);
        model.addAttribute("author", userDAO.getUserOnId(post.getUserId()));
        model.addAttribute("tag", tagDAO.getTagByPostId(post.getId()));
        model.addAttribute("currentUser", userDAO.getCurrentUser());
        model.addAttribute("comments", commentDAO.getComments(id));
        model.addAttribute("comment", new Comment());
        return "posts/showPost";
    }

    @PostMapping("/upload")
    public String createPost(@ModelAttribute("post") @Valid Post post, BindingResult bindingResultPost,
                             @ModelAttribute("tag") @Valid Tag tag, BindingResult bindingResultTag,
                             @RequestPart("image") MultipartFile file) {

        if (bindingResultPost.hasErrors()) {
            return "posts/new";
        }
        if (bindingResultTag.hasErrors()) {
            return "posts/new";
        }
        postDAO.addPost(post, file, userDAO.getCurrentUser().getId());
        System.out.println(postDAO.getLastPostId());
        tagDAO.addTag(postDAO.getLastPostId(), tag);
        return "redirect:/posts";
    }

    @DeleteMapping("{id}")
    public String deletePost(@PathVariable("id") int id) {
        postDAO.deletePost(id);
        return "redirect:/posts";
    }

    @PostMapping("/{id}/new_comment")
    public String addComment(@PathVariable("id") int id, @ModelAttribute("comment") @Valid Comment comment,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/posts/" + id;
        }
        commentDAO.addComment(comment, id, userDAO.getCurrentUser().getId());
        return "redirect:/posts/" + id;
    }

    @DeleteMapping("/{id}/delete_comment")
    public String deleteComment(@PathVariable("id") int id, @RequestParam("comment_id") int commentId) {
        commentDAO.deleteComment(commentId);
        return "redirect:/posts/" + id;
    }

}
