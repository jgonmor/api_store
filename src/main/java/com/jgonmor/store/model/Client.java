package com.jgonmor.store.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

/**
 * Client Model Class.
 * Represents a Client.
 *
 * @author Juanma G. Morcillo
 * @version 1.0
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@DynamicUpdate
public class Client {

    /**
     * The id of the Client.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the Client.
     */
    private String name;

    /**
     * The last name of the Client.
     */
    private String lastName;

    /**
     * The citizen id of the Client.
     */
    private String citizenId;

    /**
     * The sells of the Client.
     */
    @OneToMany(mappedBy = "client",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Sell> sells = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param id The id of the Client.
     * @param name The name of the Client.
     * @param lastName The last name of the Client.
     * @param citizenId The citizen id of the Client.
     */
    public Client(Long id, String name, String lastName, String citizenId) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.citizenId = citizenId;
    }


}
