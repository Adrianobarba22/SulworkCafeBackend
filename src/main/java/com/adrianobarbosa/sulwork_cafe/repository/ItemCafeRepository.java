package com.adrianobarbosa.sulwork_cafe.repository;

import com.adrianobarbosa.sulwork_cafe.model.ItemCafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ItemCafeRepository extends JpaRepository<ItemCafe, Long> {

    @Query(value = "SELECT * FROM itens_cafe WHERE nome_item = :nomeItem AND data_cafe = :dataCafe", nativeQuery = true)
    Optional<ItemCafe> findByNomeItemAndDataCafe(@Param("nomeItem") String nomeItem, @Param("dataCafe") LocalDate dataCafe);

    @Query(value = "SELECT * FROM itens_cafe WHERE data_cafe = :dataCafe", nativeQuery = true)
    List<ItemCafe> findAllByDataCafe(@Param("dataCafe") LocalDate dataCafe);

    List<ItemCafe> findAllByDataCafeBeforeAndStatus(LocalDate dataAtual, ItemCafe.StatusItem statusItem);

    boolean existsByNomeItemAndDataCafe(String nomeItem, LocalDate dataCafe);

}
