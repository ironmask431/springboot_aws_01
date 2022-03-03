package com.jojodu.book.springboot.web;

import com.jojodu.book.springboot.config.auth.LoginUser;
import com.jojodu.book.springboot.config.auth.dto.SessionUser;
import com.jojodu.book.springboot.service.posts.PostsService;
import com.jojodu.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts", postsService.findAllDesc());
        //SessionUser user = (SessionUser) httpSession.getAttribute("user");
        //@LoginUser 를 사용하기 때문에 위 코드는 주석처리함.
        //어느 컨트롤러 든지 @LoginUser 를 사용하면 세션유저 정보를 가져올 수 있습니다.
        if(user != null){
            model.addAttribute("name",user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(Model model, @LoginUser SessionUser user){
        if(user != null){
            model.addAttribute("name",user.getName());
        }
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model, @LoginUser SessionUser user){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("posts",dto);
        return "posts-update";
    }
}
