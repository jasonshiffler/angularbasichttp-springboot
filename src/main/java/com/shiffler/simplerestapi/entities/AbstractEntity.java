package com.shiffler.simplerestapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Data
@MappedSuperclass
public class AbstractEntity {

    @Id
    @Column(nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    //Keeps this field from showing up in JSON data
    @JsonIgnore
    @CreationTimestamp
    @Column(updatable = false)
    protected Instant created;

}
