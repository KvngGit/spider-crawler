package org.coin.crawler.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.coin.crawler.entity.module.Template;
import org.coin.crawler.mapper.TemplateMapper;
import org.coin.crawler.service.intf.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shawnkent on 2018/3/14.
 */
@Service
public class TemplateServiceImpl implements TemplateService {
    @Autowired
    private TemplateMapper templateMapper;

    @Override
    public void test() {
        PageHelper.startPage(1, 5);
        Page<Template> p = templateMapper.findByList();
        List<Template> resultList = p.getResult();
        System.out.println();
    }
}
