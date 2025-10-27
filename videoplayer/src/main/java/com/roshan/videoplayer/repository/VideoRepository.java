package com.roshan.videoplayer.repository;

import com.roshan.videoplayer.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findByCategoryIgnoreCase(String category);

    @Query("SELECT v FROM Video v JOIN v.tags t WHERE LOWER(t.tag) = LOWER(:tag)")
    List<Video> findByTagIgnoreCase(String tag);
}
