package com.devteria.identityservice.controller;

import com.devteria.identityservice.dto.request.ApiResponse;
import com.devteria.identityservice.service.IStorageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/file-upload")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FileUploadController {
    IStorageService storageService;
    @PostMapping
    public ResponseEntity<ApiResponse> uploadFile(@RequestParam("file") MultipartFile file){
        try{
            String generatedFileName = storageService.storeFile(file);
            return ResponseEntity.ok(ApiResponse.builder()
                    .result(generatedFileName)
                    .message("File has been uploaded")
                    .build());

        }catch (Exception e){
            return ResponseEntity.badRequest().body(ApiResponse.builder()
                    .message("Failed to upload file")
                    .build());
        }
    }

    @GetMapping("/file/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName){
        try{
            byte[] file = storageService.readFileContent(fileName);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(file);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    //load all uploaded file
    @GetMapping("")
    public ResponseEntity<ApiResponse> getUploadFiles(){
        try{
            List<String> files = storageService.loadAll()
                    .map(path -> {
                        String urlPath = MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                "readDetailFile", path.getFileName().toString()).build().toUri().toString();
                                return urlPath;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.builder()
                    .result(files)
                    .message("List of uploaded files")
                    .build());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }
}
