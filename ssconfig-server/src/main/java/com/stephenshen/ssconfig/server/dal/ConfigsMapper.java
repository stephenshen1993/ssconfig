package com.stephenshen.ssconfig.server.dal;

import com.stephenshen.ssconfig.server.model.Configs;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * mapper for configs table
 * @author stephenshen
 * @date 2024/5/3 15:00:20
 */
@Repository
@Mapper
public interface ConfigsMapper {

    @Select("select * from configs where app=#{app} and env=#{env} and ns=#{ns}")
    List<Configs> list(String app, String env, String ns);

    @Select("select * from configs where app=#{app} and env=#{env} and ns=#{ns} and pkey=#{pkey}")
    Configs select(String app, String env, String ns, String pkey);

    @Insert("insert into configs (app, env, ns, pkey, pval) values (#{app}, #{env}, #{ns}, #{pkey}, #{pval})")
    int insert(Configs configs);

    @Update("update configs set pval=#{pval} where app=#{app} and env=#{env} and ns=#{ns} and pkey=#{pkey}")
    int update(Configs configs);
}