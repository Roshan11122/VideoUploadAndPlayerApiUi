package com.roshan.videoplayer.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "video_tags")
public class VideoTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tags")
    private String tag;


@ManyToOne
@JoinColumn(name = "video_id")
@JsonIgnore
private Video video;

    public VideoTag() {}

    public VideoTag(String tag) {
        this.tag = tag;
    }

    // Getters and setters
    public Long getId() { return id; }
    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }
    public Video getVideo() { return video; }
    public void setVideo(Video video) { this.video = video; }
}
