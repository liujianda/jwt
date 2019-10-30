package com.offcn.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User{
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
}
