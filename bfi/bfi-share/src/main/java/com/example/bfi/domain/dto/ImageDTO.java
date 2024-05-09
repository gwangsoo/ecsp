package com.example.bfi.domain.dto;

import com.example.bfi.domain.dto.enumeration.ImageCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

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
public class ImageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 36)
    private String id;

    @Size(max = 255)
    private String url;

    @Size(max = 255)
    private String thumbnail;

    @Size(max = 4)
    private String type;

    private ImageCategory imageCategory;

    @Size(max = 5)
    private Integer width;

    @Size(max = 5)
    private Integer height;
}
