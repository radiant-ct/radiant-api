package dev.pepecoral.radiant.modules.datasets.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "datasets")
@EqualsAndHashCode(of = { "id" })
public class Dataset {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column
    @Size(max = 255)
    @NotBlank
    String name;

    @Column(columnDefinition = "TEXT")
    @Size(max = 5000)
    @NotBlank
    String description;

    @Column(columnDefinition = "TEXT")
    @Size(max = 5000)
    @NotBlank
    String credits;

}
