package com.lm.saletaxes.repository;

import com.lm.saletaxes.dao.TaxDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRepository extends JpaRepository<TaxDao, Long> {
}
