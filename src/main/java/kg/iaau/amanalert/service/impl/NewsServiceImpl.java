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
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        News news;

        if (model.getId() != null) {
            news = repository.findByIdAndDeletedIsNull(model.getId())
                    .orElseThrow(
                            () -> new NotFoundException(String.format("News with id = %s not found", model.getId()))
                    );
            news.setImage(imageResource == null || imageResource.getByteArray().length == 0 ? news.getImage() : imageResource.getByteArray());
            news.setImageName(imageResource == null || imageResource.getFilename() == null ? news.getImageName() : imageResource.getFilename());
            news.setTitle(model.getTitle());
            news.setDescription(model.getDescription());
        }
        else {
            news = News.builder()
                    .description(model.getDescription())
                    .title(model.getTitle())
                    .image(imageResource == null ? null : imageResource.getByteArray())
                    .imageName(imageResource == null ? null : imageResource.getFilename())
                    .build();
        }

        return new NewsModel().toModel(repository.save(news));
    }

    @Override
    public void deleteNews(Long newsId) {
        News news = repository.findByIdAndDeletedIsNull(newsId).orElseThrow(
                () -> new NotFoundException(String.format("News with id = %s not found", newsId))
        );

        news.setDeleted(new Date());
        repository.save(news);
    }

    @Override
    public byte[] getImageById(Long newsId) {
        return repository.findById(newsId).orElse(new News()).getImage();
    }

    @Override
    public List<NewsModel> getAllNews() {
        return repository.findAllByDeletedIsNullOrderByIdDesc().stream()
                .map(n -> new NewsModel().toModel(n))
                .collect(Collectors.toList());
    }
}
