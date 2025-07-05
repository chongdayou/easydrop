package com.tenavcode.easydrop.file;

import org.springframework.data.jpa.repository.JpaRepository;


// spring boot auto add @Repository to an extension of JpaRepository
public interface FileMetaRepository extends JpaRepository<FileMeta, Long> {

}
