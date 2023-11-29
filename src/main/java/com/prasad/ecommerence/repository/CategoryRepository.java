package com.prasad.ecommerence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prasad.ecommerence.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>{
    public Category findByName(String name);
    @Query("select c from Category c WHERE c.name=:name and c.parentCategory.name = :parentCategoryName ")
    public Category findByNameAndParent(@Param("name")String name, @Param("parentCategoryName")String parentCategoryName);

}
