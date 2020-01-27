package com.shiffler.simplerestapi.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="books")
@Data
@NoArgsConstructor
public class Book extends AbstractEntity{

    @NotNull
    @Size(min = 2, message = "Title should have at least 2 characters")
    @Size(max = 64, message = "Title can have no more than 64 characters")
    private String title;

    @NotNull
    @Size(min = 3, message = "Author should have at least 3 characters")
    @Size(max = 64, message = "Title can have no more than 64 characters")
    private String author;

    @NotNull
    @Min(value = 1, message = "Number of pages must be greater than 0")
    @Max(value= 9999, message = "Number of pages must be less than 10,000")
    @Column(name="num_pages")
    private Integer numPages;

    public Book(String title, String author, Integer numPages){
        this.title = title;
        this.author = author;
        this.numPages = numPages;
    }

}
