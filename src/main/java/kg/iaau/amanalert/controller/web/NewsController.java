package kg.iaau.amanalert.controller.web;

import kg.iaau.amanalert.service.NewsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NewsController {
    NewsService newsService;

    @GetMapping(value = "/image/{newsId}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(@PathVariable Long newsId) {
        return newsService.getImageById(newsId);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveNews(@RequestParam("data") String json, @RequestParam("image") MultipartFile image) {
        try {
            MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
            formData.add("data", json);
            formData.add("image", new ByteArrayResource(image.getBytes()) {
                @Override
                public String getFilename() {
                    return image.getOriginalFilename();
                }
            });

            return ResponseEntity.ok(newsService.saveNews(formData));
        } catch (IOException e) {
            log.error("saveNews(): {}", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
