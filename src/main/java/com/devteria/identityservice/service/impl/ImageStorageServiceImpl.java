package com.devteria.identityservice.service.impl;

import com.devteria.identityservice.service.IStorageService;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;
@Service
public class ImageStorageServiceImpl implements IStorageService {
    private final Path storageFoler = Paths.get("uploads");
    public ImageStorageServiceImpl() {
        try {
//            if (!storageFoler.toFile().exists()) {
//                storageFoler.toFile().mkdir();
//            }
            Files.createDirectories(storageFoler);
        } catch (Exception e) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", e);
        }
    }
    private boolean isImage(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[] {"jpg", "jpeg", "png", "gif","bmp"})
                .contains(fileExtension.toLowerCase());
    }
    @Override
    public String storeFile(MultipartFile file) {
        try{
            if(file.isEmpty()){
                throw new RuntimeException("Failed to store empty file " + file.getOriginalFilename());
            }
            if(!isImage(file)){
                throw new RuntimeException("Failed to store file " + file.getOriginalFilename() + ". Only images are allowed.");
            }

            float fileSize = file.getSize() / 1_000_000f;
            if(fileSize > 5.0f){
                throw new RuntimeException("Failed to store file " + file.getOriginalFilename() + ". File size exceeds 5MB.");
            }

            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFileName = UUID.randomUUID().toString().replace("-","");
            generatedFileName = generatedFileName + "." + fileExtension;
            Path destinationFile = this.storageFoler.resolve(Paths.get(generatedFileName)).normalize().toAbsolutePath();
            if(!destinationFile.getParent().equals(this.storageFoler.toAbsolutePath())){
                throw new RuntimeException("Cannot store file outside current directory.");
            }
            try(InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);

            }
            return generatedFileName;
        }catch (IOException e){
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try{
            return Files.walk(this.storageFoler, 1)
                    .filter(path -> !path.equals(this.storageFoler))
                    .map(this.storageFoler::relativize);
        }
        catch (IOException e){
            throw new RuntimeException("Could not load the files.");
        }
    }

    @Override
    public byte[] readFileContent(String filename) {
        try{
            Path file = storageFoler.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            }
            else{
                throw new RuntimeException("Could not read the file.");
            }
        }
        catch (IOException e){
            throw new RuntimeException("Could not read the file. Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAllFiles() {

    }
}
