package com.jgonmor.store.repository;

import com.jgonmor.store.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Client Repository Class
 * Manages clients in the database.
 *
 * @author Juanma G. Morcillo
 * @version 1.0
 */
@Repository
public interface IClientRepository extends JpaRepository<Client, Long> {
}
