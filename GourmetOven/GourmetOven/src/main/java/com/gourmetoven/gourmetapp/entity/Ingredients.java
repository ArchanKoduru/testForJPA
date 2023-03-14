package com.gourmetoven.gourmetapp.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ingredients")
public class Ingredients {
    @Id
    @Column(name = "ingredient_id")
    @NotNull
    private Integer ingredientId;

    @Column(name = "ingredient_name")
    @NotNull
    private String name;

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
