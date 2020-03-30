package com.dtone.encryptor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface LicenseSave extends JpaRepository<EncryptionModel, Long> {
    @Query(nativeQuery = true,
            value = "select exists (select server_ip from encryption_model  where server_ip=:serverIp)")
    boolean existsServerIp(@Param("serverIp")String serverIp);

    @Override
    Optional<EncryptionModel> findById(Long aLong);

}
