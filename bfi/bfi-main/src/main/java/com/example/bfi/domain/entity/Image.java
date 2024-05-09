package com.example.bfi.domain.entity;

import com.example.bfi.domain.dto.enumeration.ImageCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * Image
 */
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Document(collection = "image")
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 36)
    @Id
    private String id;

    @Size(max = 255)
    @Field("url")
    private String url;

    @Size(max = 255)
    @Field("thumbnail")
    private String thumbnail;

    @Size(max = 4)
    @Field("type")
    private String type;

    @Field("category")
    private ImageCategory imageCategory;

    @Size(max = 5)
    @Field("width")
    private Integer width;

    @Size(max = 5)
    @Field("height")
    private Integer height;
}
