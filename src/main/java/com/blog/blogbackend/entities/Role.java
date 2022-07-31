package com.blog.blogbackend.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Getter
@Setter
@Data
public class Role {
    @Id
    private int id;
    private String name;
}
