package com.felix.soccerback.service.impl;

import com.felix.soccerback.entity.News;
import com.felix.soccerback.mapper.NewsMapper;
import com.felix.soccerback.service.INewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Felix
 * @since 2022-04-03
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements INewsService {

}
