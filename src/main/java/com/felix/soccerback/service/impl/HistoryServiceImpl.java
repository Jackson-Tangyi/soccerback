package com.felix.soccerback.service.impl;

import com.felix.soccerback.entity.History;
import com.felix.soccerback.mapper.HistoryMapper;
import com.felix.soccerback.service.IHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Felix
 * @since 2022-03-25
 */
@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements IHistoryService {

}
