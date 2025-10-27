package com.roshan.videoplayer.service;

import com.roshan.videoplayer.model.Video;
import com.roshan.videoplayer.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    public Optional<Video> getVideoById(Long id) {
        return videoRepository.findById(id);
    }

    public Video saveVideo(Video video) {
        video.setUploadedAt(java.time.LocalDateTime.now());
        return videoRepository.save(video);
    }

    public void deleteVideo(Long id) {
        videoRepository.deleteById(id);
    }

    public List<Video> getVideosByCategory(String category) {
        return videoRepository.findByCategoryIgnoreCase(category);
    }

    public List<Video> getVideosByTag(String tag) {
        return videoRepository.findByTagIgnoreCase(tag);
    }
}
