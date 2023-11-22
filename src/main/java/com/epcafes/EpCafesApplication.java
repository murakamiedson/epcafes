package com.epcafes;

import javax.annotation.Resource;

import com.epcafes.repository.FileDBRepository;
import com.epcafes.service.FileStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EpCafesApplication implements CommandLineRunner {

	@Resource
	FileStorageService storageService;

	@Resource
	FileDBRepository fileDBRepository;

	public static void main(String[] args) {
		SpringApplication.run(EpCafesApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
		//deleta o armazenamento de arquivos para debug
		storageService.deleteAll();
		fileDBRepository.deleteAll();
		//inicia o armazenamento de arquivos
		storageService.init();
	}
}