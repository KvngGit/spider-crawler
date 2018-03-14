package org.coin.crawler.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.coin.crawler.entity.module.Template;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by shawnkent on 2018/3/13.
 */
@Transactional
@Mapper
public interface TemplateMapper {

    Page<Template> findByList();
}
