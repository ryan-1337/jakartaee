package org.eclipse.jakarta.model;

import jakarta.persistence.*;

@Entity
@Table
@NamedQuery(name = "Technique.findAll", query = "SELECT t FROM Technique t")
public class Technique {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(unique = true, length = 20, nullable = false)
    private String name;

    private String difficulty;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
