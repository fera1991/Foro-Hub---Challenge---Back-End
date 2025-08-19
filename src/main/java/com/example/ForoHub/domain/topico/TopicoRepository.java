package com.example.ForoHub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Timer;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Page<Topico> findAllByAutor_Id(Long autorId, Pageable pageable);

    boolean existsByTituloAndMensaje(String titulo, String mensaje);

    Optional<Topico> findById(Long topicoId);

}
