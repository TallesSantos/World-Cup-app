package io.github.tallessantos.world_cup_api.core.domain;


import io.github.tallessantos.world_cup_api.core.domain.metadata.GoalMetadataEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "media")
public class MediaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;

    private String fullStoragePath;

    private String storagePath;

    private String resourcePath;

    private String fullResourcePath;

    @Enumerated(EnumType.STRING)
    private MediaContentType mediaContentType;

    @Enumerated(EnumType.STRING)
    private MediaPlatform mediaPlatform;

    private String youtubeVideoId;

    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "goal_metadata_id")
    private GoalMetadataEntity goalMetadata;

    public String getYoutubeUrl() {

        if (youtubeVideoId == null) {
            return "";
        }

        return "https://youtube.com/watch?v=" +
                youtubeVideoId;
    }

    public String getYoutubeEmbedUrl() {

        if (youtubeVideoId == null) {
            return "";
        }

        return "https://www.youtube.com/embed/" +
                youtubeVideoId;
    }

}
