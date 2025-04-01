package com.springboot.mapper;


import com.springboot.common.Params;
import com.springboot.entity.Notice;
import com.springboot.entity.Type;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hqf
 * @since 2025-03-01
 */

@Repository
public interface NoticeMapper extends Mapper<Notice> {


    List<Notice> findbySearch(@Param("params") Params params);

    @Select("select * from notice order by time desc limit 5")
    List<Notice> findAll();
}
