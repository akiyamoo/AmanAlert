package kg.iaau.amanalert.service;

import kg.iaau.amanalert.model.news.NewsModel;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

public interface NewsService {
    NewsModel saveNews(MultiValueMap<String, Object> formData) throws IOException;
    void deleteNews();

    byte[] getImageById(Long newsId);
}
