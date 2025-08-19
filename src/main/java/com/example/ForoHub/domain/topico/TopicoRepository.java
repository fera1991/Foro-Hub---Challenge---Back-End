package com.example.ForoHub.domain.topico;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Page<Topico> findAllByAutor_Id(Long autorId, Pageable pageable);

    boolean existsByTituloAndMensaje(String titulo, String mensaje);

    Optional<Topico> findById(Long topicoId);
}
