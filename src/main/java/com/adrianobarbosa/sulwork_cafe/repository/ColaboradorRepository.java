package com.adrianobarbosa.sulwork_cafe.repository;

import com.adrianobarbosa.sulwork_cafe.model.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {


    @Query(value = "SELECT * FROM colaboradores WHERE cpf = :cpf", nativeQuery = true)
    Optional<Colaborador> findByCpf(@Param("cpf") String cpf);

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM colaboradores WHERE cpf = :cpf", nativeQuery = true)
    boolean existsByCpf(@Param("cpf") String cpf);
}
