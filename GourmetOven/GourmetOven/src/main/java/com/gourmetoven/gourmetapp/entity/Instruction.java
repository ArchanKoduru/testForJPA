package com.gourmetoven.gourmetapp.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "instructions")
public class Instruction {
    @Id
    @Column(name = "instruction_id")
    @NotNull
    private Integer instruction_id;

    @Column(name = "instruction")
    @NotNull
    private String instruction;

    public Integer getInstruction_id() {
        return instruction_id;
    }

    public void setInstruction_id(Integer instruction_id) {
        this.instruction_id = instruction_id;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
