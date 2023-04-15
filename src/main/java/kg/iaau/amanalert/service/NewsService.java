package kg.iaau.amanalert.service;

import kg.iaau.amanalert.model.news.NewsModel;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.List;

public interface NewsService {
    NewsModel saveNews(MultiValueMap<String, Object> formData) throws IOException;
    void deleteNews(Long newsId);

    byte[] getImageById(Long newsId);

    List<NewsModel> getAllNews();
}
