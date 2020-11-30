package com.lm.saletaxes.repository;

import com.lm.saletaxes.dao.TaxDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxRepository extends JpaRepository<TaxDao, Long> {
}
