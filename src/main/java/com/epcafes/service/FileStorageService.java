package com.epcafes.service;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;

import com.epcafes.enums.TipoCertificado;
import com.epcafes.model.FileDB;
import com.epcafes.repository.FileDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;



@Service
public class FileStorageService {

    @Autowired
    private FileDBRepository fileDBRepository;

    private final Path root = Paths.get("./uploads");

    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível inicializar pasta de upload!");
        }
    }

    public FileDB store(MultipartFile file, Long id, TipoCertificado tipoCertificado) throws IOException {
        UUID uuid = UUID.randomUUID();

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String extensao = fileName.substring(fileName.lastIndexOf(".") + 1);

        Files.copy(file.getInputStream(), this.root.resolve(uuid.toString() + "." + extensao));

        Path caminho = this.root.resolve(uuid.toString());

        String nomeExibicao = fileName.substring(0, fileName.lastIndexOf("."));

        FileDB FileDB = new FileDB(nomeExibicao,(uuid.toString() + "." + extensao), extensao, caminho.toString(), id, tipoCertificado);

        return fileDBRepository.save(FileDB);
    }

    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    public Resource load(String filename) {
        try {
            FileDB arquivo= fileDBRepository.findByUuidRegistrado(filename);

            Path file = root.resolve(arquivo.getUuidRegistrado());
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Não foi possível ler o arquivo!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public boolean delete(String filename) {
        try {
            FileDB arquivo= fileDBRepository.findByUuidRegistrado(filename);
            Path file = root.resolve(arquivo.getUuidRegistrado());
            fileDBRepository.delete(arquivo);
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


    public FileDB getFile(String id) {
        return fileDBRepository.findById(id).get();
    }


    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }
}