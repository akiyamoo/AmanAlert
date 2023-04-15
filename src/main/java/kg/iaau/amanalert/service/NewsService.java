package kg.iaau.amanalert.service;

import kg.iaau.amanalert.model.news.NewsModel;
import org.springframework.util.MultiValueMap;

public interface NewsService {
    NewsModel saveNews(MultiValueMap<String, Object> formData);
    void deleteNews();

    byte[] getImageById(Long newsId);
}
