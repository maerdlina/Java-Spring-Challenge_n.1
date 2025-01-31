package com.jts.rest_api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/card")
public class CardController {

    @PostMapping
    public String chooseVote(@RequestParam("voteCard") int voteCard, Model model) {
        // Здесь мы добавляем логику для выбора изображения
        String imgSrc = (voteCard == 1) ? "/images/anime_books.gif" : "/images/anime_computer.jpg";

        model.addAttribute("vote", voteCard);
        model.addAttribute("imgSrc", imgSrc);
        return "result_old";
    }
}
