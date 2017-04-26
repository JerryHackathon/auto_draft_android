package com.example.pianoafrik.ormapp.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;


@Entity
public class PhoneNumber {
    @Id
    private Long id;

    @NotNull
    @Index(name = "NUMBER_IDX")
    private String number;

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 117733371)
    public PhoneNumber(Long id, @NotNull String number) {
        this.id = id;
        this.number = number;
    }

    public PhoneNumber(String number) {
        this.number = number;
    }

    @Generated(hash = 857563609)
    public PhoneNumber() {
    }
    

   


    
}
