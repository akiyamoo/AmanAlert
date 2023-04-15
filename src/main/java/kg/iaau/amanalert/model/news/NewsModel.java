package kg.iaau.amanalert.model.news;

import kg.iaau.amanalert.base.BaseModelTo;
import kg.iaau.amanalert.entity.News;
import kg.iaau.amanalert.util.UrlHostUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsModel implements BaseModelTo<News> {
    Long id;
    String title;
    String description;
    String urlImage;

    @Override
    public NewsModel toModel(News news) {
        this.id = news.getId();
        this.description = news.getDescription();
        this.title = news.getTitle();
        this.urlImage = UrlHostUtil.getHostUrl() + UrlHostUtil.getUrlNewsImage() + this.id;

        return this;
    }
}