package com.shiffler.simplerestapi.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="books")
@Data
public class Book extends AbstractEntity{

    private String title;
    private String author;

    @Column(name="num_pages")
    private Integer numPages;

}
