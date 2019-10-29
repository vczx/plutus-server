package com.vince.plutus.model;

import com.vince.plutus.dto.TransactionCategoryDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "transactioncategory")
@Getter
@Setter
public class TransactionCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String category;
    private String description;
    private String type;

    public TransactionCategory() {
    }

    public TransactionCategory(String category, String description, String type) {
        this.category = category;
        this.description = description;
        this.type = type;
    }


    public static TransactionCategory fromDtoToModel(TransactionCategoryDto dto) {
        TransactionCategory transactionCategory = new TransactionCategory();
        transactionCategory.setCategory(dto.getCategory());
        transactionCategory.setDescription(dto.getDescription());
        transactionCategory.setId(dto.getId());
        transactionCategory.setType(dto.getType());
        return transactionCategory;
    }

    public TransactionCategoryDto fromModelToDto(TransactionCategory model) {
        return new TransactionCategoryDto(model.getId(), model.getCategory(), model.getDescription(), model.getType());
    }
}
