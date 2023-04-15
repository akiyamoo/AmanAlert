package kg.iaau.amanalert.service.impl;

import com.google.gson.Gson;
import kg.iaau.amanalert.entity.News;
import kg.iaau.amanalert.model.news.NewsModel;
import kg.iaau.amanalert.repo.NewsRepository;
import kg.iaau.amanalert.service.NewsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NewsServiceImpl implements NewsService {
    NewsRepository repository;
    Gson gson = new Gson();

    @Override
    public NewsModel saveNews(MultiValueMap<String, Object> formData) throws IOException {
        NewsModel model = gson.fromJson((String) formData.getFirst("data"), NewsModel.class);
        ByteArrayResource imageResource = (ByteArrayResource) formData.getFirst("image");
        News news = repository.save(News.builder()
                .description(model.getDescription())
                .title(model.getTitle())
                .image(imageResource.getByteArray())
                .imageName(imageResource.getFilename())
                .build());

        return new NewsModel().toModel(news);
    }

    @Override
    public void deleteNews() {

    }

    @Override
    public byte[] getImageById(Long newsId) {
        return repository.findById(newsId).orElse(new News()).getImage();
    }
}
