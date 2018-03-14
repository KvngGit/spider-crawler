package org.coin.crawler.controller;

import org.coin.crawler.service.intf.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shawnkent on 2018/3/14.
 */
@RestController
@RequestMapping("test")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @RequestMapping("inter")
    public void test() {
        templateService.test();
    }
}
