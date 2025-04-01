package com.springboot.service;






import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.common.Params;
import com.springboot.entity.Notice;
import com.springboot.entity.Type;
import com.springboot.mapper.NoticeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hqf
 * @since 2025-03-01
 */
@Service
public class NoticeService {



@Resource
    private NoticeMapper noticeMapper;


    public void add(Notice notice) {
        notice.setTime(DateUtil.now());
        noticeMapper.insertSelective(notice);
    }

    public PageInfo<Notice> findbySearch(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<Notice> list=noticeMapper.findbySearch(params);
        return PageInfo.of(list);
    }

    public void update(Notice notice) {
        noticeMapper.updateByPrimaryKeySelective(notice);
    }

    public void delete(Integer id) {
        noticeMapper.deleteByPrimaryKey(id);
    }

    public List<Notice> findAll() {
        List<Notice> list=noticeMapper.findAll();
        return list;
    }
}
