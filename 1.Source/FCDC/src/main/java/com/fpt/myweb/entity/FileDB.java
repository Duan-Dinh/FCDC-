package com.fpt.myweb.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "files")
public class FileDB extends BaseEntity {



    @JsonProperty
    @Column(name = "name")
    private String name;

    @JsonProperty
    @Column(name = "type")
    private String type;

    @Column(name = "data")
    @Lob
    private byte[] data;
    @OneToMany(mappedBy = "files") // chỗ ni tên gióng với tên chỗ khởi tạo role
    private List<User> users = new ArrayList<>();
    public FileDB() {
    }

    public FileDB(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }
}
