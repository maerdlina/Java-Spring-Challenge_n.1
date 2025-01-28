package com.jts.rest_api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.jts.rest_api.model.Note;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "http://localhost:4000")
public class NotesController {
    private static final String FILE_PATH = "src/main/resources/static/notes.txt";

    @PostMapping
    public void saveNote(@RequestBody Note note) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(note.getName());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping
    public List<Note> getNotes() {
        List<Note> notes = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            for (String line : lines) {
                notes.add(new Note(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return notes;
    }

    @DeleteMapping
    public void deleteAllNotes() {
        try {
            new BufferedWriter(new FileWriter(FILE_PATH)).close(); // Очищаем файл
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
