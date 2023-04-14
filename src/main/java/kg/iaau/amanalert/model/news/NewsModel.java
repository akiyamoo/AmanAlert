package kg.iaau.amanalert.model.news;

import kg.iaau.amanalert.base.BaseModelFrom;
import kg.iaau.amanalert.base.BaseModelTo;
import kg.iaau.amanalert.entity.News;

public class NewsModel implements BaseModelTo<News>, BaseModelFrom<News> {
    Long id;
    String title;
    String description;
    @Override
    public News ToEntity() {
        return null;
    }

    @Override
    public NewsModel toModel(News news) {
        return null;
    }
}