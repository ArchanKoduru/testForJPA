package com.gourmetoven.gourmetapp.repository;

import com.gourmetoven.gourmetapp.entity.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructionRepository extends JpaRepository<Instruction, Integer> {
}
