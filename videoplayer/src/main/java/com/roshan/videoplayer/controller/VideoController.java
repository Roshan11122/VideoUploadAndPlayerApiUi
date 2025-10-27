package com.roshan.videoplayer.controller;

import com.roshan.videoplayer.model.Video;
import com.roshan.videoplayer.model.VideoTag;
import com.roshan.videoplayer.repository.VideoRepository;
import com.roshan.videoplayer.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/videos")
public class VideoController {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoService videoService;

    // ✅ Save to project root folder "uploads/"
    private static final String VIDEO_DIR = "./uploads/";

    @PostMapping("/upload")
    public ResponseEntity<Video> uploadVideo(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("tags") List<String> tags,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            // ✅ Ensure uploads folder exists
            Path uploadPath = Paths.get(VIDEO_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // ✅ Save file
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, file.getBytes());

            // ✅ Public URL for frontend
            String videoUrl = "http://localhost:8080/uploads/" + fileName;

            // ✅ Convert tags
            List<VideoTag> tagEntities = tags.stream()
                    .map(VideoTag::new)
                    .collect(Collectors.toList());

            Video video = new Video(title, description, videoUrl, category, tagEntities);
            tagEntities.forEach(tag -> tag.setVideo(video));

            Video saved = videoRepository.save(video);
            return ResponseEntity.ok(saved);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Video>> getAllVideos() {
        return ResponseEntity.ok(videoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVideoById(@PathVariable Long id) {
        return videoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVideo(@PathVariable Long id) {
        if (videoRepository.existsById(id)) {
            videoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Video>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(videoService.getVideosByCategory(category));
    }

    @GetMapping("/tag/{tag}")
    public ResponseEntity<List<Video>> getByTag(@PathVariable String tag) {
        return ResponseEntity.ok(videoService.getVideosByTag(tag));
    }
}
