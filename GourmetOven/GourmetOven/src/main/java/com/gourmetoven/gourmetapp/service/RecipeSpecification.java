package com.gourmetoven.gourmetapp.service;

import com.gourmetoven.gourmetapp.Dto.Request.SearchRequestDto;
import com.gourmetoven.gourmetapp.entity.Recipe;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class RecipeSpecification {
    public Specification<Recipe> getRecipes(SearchRequestDto searchRequestDto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (searchRequestDto.getType() != null && !searchRequestDto.getType().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("type"), searchRequestDto.getType()));
            }
            if (searchRequestDto.getServings() != null && searchRequestDto.getServings().intValue() != 999999) {
                predicates.add(criteriaBuilder.equal(root.get("servings"), searchRequestDto.getServings()));
            }
            if (searchRequestDto.getExcludeIngredients() != null && !searchRequestDto.getExcludeIngredients().isEmpty()) {
                predicates.add(criteriaBuilder.notEqual(root.in("recipeIngredients"), searchRequestDto.getExcludeIngredients()));
            }
            if (searchRequestDto.getIncludeIngredients() != null && !searchRequestDto.getIncludeIngredients().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.in("recipeIngredients"), searchRequestDto.getIncludeIngredients()));
            }
            if (searchRequestDto.getInstructions() != null && !searchRequestDto.getInstructions().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.in("instructions"), searchRequestDto.getInstructions()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
