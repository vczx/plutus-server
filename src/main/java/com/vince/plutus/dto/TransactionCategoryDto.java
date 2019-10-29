package com.vince.plutus.dto;

import com.vince.plutus.model.TransactionCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionCategoryDto {

    private Long id;
    private String category;
    private String description;
    private String type;

    public TransactionCategoryDto() {
    }

    public TransactionCategoryDto(Long id, String category, String description, String type) {
        this.id = id;
        this.category = category;
        this.description = description;
        this.type = type;
    }

    public static TransactionCategoryDto fromModelToDto(TransactionCategory model) {
        return new TransactionCategoryDto(model.getId(), model.getCategory(), model.getDescription(), model.getType());
    }
}
