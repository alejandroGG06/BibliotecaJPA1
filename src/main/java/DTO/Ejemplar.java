package DTO;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "ejemplar")
public class Ejemplar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "isbn", nullable = false)
    private DTO.Libro isbn;

    @ColumnDefault("'Disponible'")
    @Lob
    @Column(name = "estado")
    private String estado;

    @OneToMany(mappedBy = "ejemplar")
    private Set<DTO.Prestamo> prestamos = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DTO.Libro getIsbn() {
        return isbn;
    }

    public void setIsbn(DTO.Libro isbn) {
        this.isbn = isbn;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Set<DTO.Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(Set<DTO.Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

}