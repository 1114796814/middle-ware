package cn.edu.xmu.level46db.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/05/11 11:45
 */
@Mapper
public interface ExtendCETPoMapper {

    @Update("UPDATE cet_table SET stock = stock - 1 WHERE id = #{id} AND stock>0")
    int rush(@Param("id") Long id);

}
