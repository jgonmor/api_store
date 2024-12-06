package com.jgonmor.store.repository;

import com.jgonmor.store.model.Sell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISellRepository extends JpaRepository<Sell, Long> {
}
