package com.base.repository;

import com.base.entity.DuplicationCheck;
import com.base.entity.DuplicationCheckId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DuplicationCheckRepository
        extends JpaRepository<DuplicationCheck, DuplicationCheckId> {
}
