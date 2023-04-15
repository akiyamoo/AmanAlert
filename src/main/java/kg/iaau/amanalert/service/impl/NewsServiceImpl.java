package kg.iaau.amanalert.service.impl;

import kg.iaau.amanalert.model.news.NewsModel;
import kg.iaau.amanalert.service.NewsService;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class NewsServiceImpl implements NewsService {
    @Override
    public NewsModel saveNews(MultiValueMap<String, Object> formData) {
        return new NewsModel();
    }

    @Override
    public void deleteNews() {

    }

    @Override
    public byte[] getImageById(Long newsId) {
        return new byte[0];
    }
}
