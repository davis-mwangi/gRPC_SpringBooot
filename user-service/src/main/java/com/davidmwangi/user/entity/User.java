package com.davidmwangi.user.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@ToString
@Data
@Table(name = "\"user\"")
public class User {
    @Id
    private String login;
    private String name;
    private String genre;

}
