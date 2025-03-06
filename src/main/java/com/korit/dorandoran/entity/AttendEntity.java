package com.korit.dorandoran.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attend")
public class AttendEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attendId;

    private String userId;
    
    private int attendStatus = 1;
    
    private String attendAt;
}
