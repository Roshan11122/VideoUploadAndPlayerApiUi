package com.roshan.videoplayer.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "videos")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Column(name = "video_url")
    private String videoUrl;

    private String category;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;


    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<VideoTag> tags;


    public Video() {}

    public Video(String title, String description, String videoUrl, String category, List<VideoTag> tags) {
        this.title = title;
        this.description = description;
        this.videoUrl = videoUrl;
        this.category = category;
        this.uploadedAt = LocalDateTime.now();
        this.tags = tags;
        this.tags.forEach(tag -> tag.setVideo(this));
    }

    // Getters and setters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }
    public List<VideoTag> getTags() { return tags; }
    public void setTags(List<VideoTag> tags) { this.tags = tags; }
}
