package com.dtone.encryptor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseSave extends CrudRepository<EncryptionModel, Long> {
    @Query(nativeQuery = true,
            value = "select server_ip from encryption_model  where server_ip=:serverIp")
    String existsServerIp(@Param("serverIp")String serverIp);
}
