package io.github.tallessantos.world_cup_api.core.domain;


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

    private String fullStoragePath;

    private String storagePath;

    private String resourcePath;

    private String fullResourcePath;

    @Enumerated(EnumType.STRING)
    private MediaContentType mediaContentType;

    private MediaPlataform mediaPlataform;

    private String youtubeKey;

}
