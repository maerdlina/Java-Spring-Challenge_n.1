package com.jts.rest_api.controller;

import com.jts.rest_api.model.Image;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
@Controller
@RequestMapping("/api/vote")
public class VoteController {

    private final List<Image> images = new ArrayList<>();
    private final Map<Image, Integer> ratings = new HashMap<>();
    private Image currentImage1;
    private Image currentImage2;

    public VoteController() {
        initializeImages();
        chooseNextImages();
    }

    private void initializeImages() {
        // Загрузка изображений
        images.clear();
        ratings.clear();
        images.add(new Image("/images/anime_books.gif"));
        images.add(new Image("/images/anime_computer.jpg"));
        images.add(new Image("/images/anime_computer1.jpg"));
        images.add(new Image("/images/anime_computer2.jpg"));
        images.add(new Image("/images/anime_computer3.jpg"));

        // Инициализация рейтингов
        for (Image image : images) {
            ratings.put(image, 0);
        }
    }

    @GetMapping
    public String showVotePage(Model model) {
        model.addAttribute("image1", currentImage1);
        model.addAttribute("image2", currentImage2);
        return "vote"; // Шаблон для голосования
    }

    @PostMapping
    public String chooseVote(@RequestParam("voteCard") int voteCard, Model model) {
        // Проверяем, что изображения не равны null
        if (currentImage1 == null && currentImage2 == null) {
            return "redirect:/api/vote/result"; // Перенаправление на страницу результата, если изображений нет
        }

        // Обработка голосования
        if (voteCard == 1 && currentImage1 != null) {
            ratings.put(currentImage1, ratings.get(currentImage1) + 1);
        } else if (voteCard == 2 && currentImage2 != null) {
            ratings.put(currentImage2, ratings.get(currentImage2) + 1);
        } else {
            // Если голосование за одно и то же изображение
            model.addAttribute("errorMessage", "Вы уже проголосовали за это изображение.");
            model.addAttribute("image1", currentImage1);
            model.addAttribute("image2", currentImage2);
            return "vote"; // Возвращаемся к шаблону голосования с сообщением об ошибке
        }

        chooseNextImages();

        // Проверка, остались ли изображения для голосования
        if (currentImage1 == null && currentImage2 == null) {
            return "redirect:/api/vote/result"; // Перенаправление на страницу результата
        }

        model.addAttribute("image1", currentImage1);
        model.addAttribute("image2", currentImage2);
        return "vote"; // Возвращаемся к шаблону голосования
    }


    private void chooseNextImages() {
        if (images.size() > 1) {
            Collections.shuffle(images);
            currentImage1 = images.remove(0);
            currentImage2 = images.remove(0);
        } else if (images.size() == 1) {
            currentImage1 = images.remove(0);
            currentImage2 = null; // Второе изображение отсутствует
        } else {
            currentImage1 = null;
            currentImage2 = null; // Все изображения проголосованы
        }
    }


    @GetMapping("/result")
    public String showResult(Model model) {
        Image bestImage = ratings.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        model.addAttribute("bestImage", bestImage);
        model.addAttribute("ratings", ratings); // Для отображения всех рейтингов, если нужно
        return "result"; // Шаблон для отображения результатов
    }

    @PostMapping("/reset")
    public String resetVoting() {
        initializeImages(); // Сброс данных и инициализация
        chooseNextImages(); // Выбор первых двух изображений
        return "redirect:/api/vote"; // Перенаправление на страницу голосования
    }
}
