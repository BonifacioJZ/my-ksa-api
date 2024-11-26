package com.bonifacio.my_ksa_api.persistence.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    @NotEmpty
    @NotBlank
    @Size(min = 0,max = 150)
    private String name;
    @Column
    @Size(max = 5000)
    private String description;
    @Column
    @NotNull
    @NotEmpty
    private String slug;

    @CreationTimestamp
    private Instant createAt;
    @UpdateTimestamp
    private Instant updateAt;

    public void  generateSlug(){
        var id = UUID.randomUUID().toString();
        this.slug = this.name.toLowerCase()+"-"+id.substring(0,8);
    }
}
