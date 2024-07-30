package com.example.blog.Controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.blog.Models.Account;
import com.example.blog.Models.Post;
import com.example.blog.Service.AccountService;
import com.example.blog.Service.PostService;





@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id , Model model, Principal principal) {
       Optional<Post> optionalPost=postService.getById(id);
       String authUser="email";
       if(optionalPost.isPresent()){
        Post post=optionalPost.get();
       model.addAttribute("post", post);
       if(principal!=null)
       {
        authUser=principal.getName();
       }
       if(authUser.equals(post.getAccount().getEmail()))
       {
        model.addAttribute("isOwner", true);
       }
       else
       {
        model.addAttribute("isOwner", false);
       }
       return "post_views/post";
       }
       else
       {
        return "404";
       }
    }

    @GetMapping("/post/add")
    @PreAuthorize("isAuthenticated()")
    public String addPost(Model model, Principal principal) {
        String authUser = "email";
        if(principal != null){
            authUser = principal.getName();
        }
        Optional<Account> optionalAccount = accountService.findOneByEmail(authUser);
        if(optionalAccount.isPresent()){
            Post post = new Post();
            post.setAccount(optionalAccount.get());
            model.addAttribute("post", post);
            return "post_views/post_add";
        }else{
            return "redirect:/";
        }
    }
    @PostMapping("/post/add")
    @PreAuthorize("isAuthenticated()")
    public String addPostHandler(@ModelAttribute Post post, Principal principal){
        String authUser = "email";
        if(principal != null){
            authUser = principal.getName();
        }
        if (post.getAccount().getEmail().compareToIgnoreCase(authUser) < 0){
            return "redirect:/?error";
        }
        postService.save(post);
        return "redirect:/posts/"+post.getId();
    }
   
    @GetMapping("/post/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String getPostForEdit(@PathVariable Long id, Model model){
        Optional<Post> optionaPost = postService.getById(id);
        if(optionaPost.isPresent()){
            Post post = optionaPost.get();
            model.addAttribute("post", post);
            return "post_views/post_edit";
        }else{
            return "404";
        }
    }
    @PostMapping("/post/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String updatePost(@PathVariable Long id, @ModelAttribute Post post){
        Optional<Post> optionalPost = postService.getById(id);
        if(optionalPost.isPresent()){
            Post existingPost = optionalPost.get();
            existingPost.setTitle(post.getTitle());
            existingPost.setBody(post.getBody());
            postService.save(existingPost);
        }
        return "redirect:/posts/"+post.getId();

    }

    
}

