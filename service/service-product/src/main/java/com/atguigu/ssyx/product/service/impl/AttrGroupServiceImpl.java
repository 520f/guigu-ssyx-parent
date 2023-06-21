package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.model.product.AttrGroup;
import com.atguigu.ssyx.product.mapper.AttrGroupMapper;
import com.atguigu.ssyx.product.service.AttrGroupService;
import com.atguigu.ssyx.vo.product.AttrGroupQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <p>
 * 属性分组 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-04-04
 */
@Service
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroup> implements AttrGroupService {

    //平台属性分组列表
    @Override
    public Mono<IPage<AttrGroup>> selectPageAttrGroup(Page<AttrGroup> pageParam, AttrGroupQueryVo attrGroupQueryVo) {
        String name = attrGroupQueryVo.getName();
        LambdaQueryWrapper<AttrGroup> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(name),AttrGroup::getName,name);
        return Mono.just(baseMapper.selectPage(pageParam,wrapper));
    }

    //查询所有平台属性分组列表
    @Override
    public Mono<List<AttrGroup>> findAllListAttrGroup() {
       //LambdaQueryWrapper<AttrGroup> wrapper = new LambdaQueryWrapper<>();
        QueryWrapper<AttrGroup> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        return Mono.just(baseMapper.selectList(wrapper));
    }
}
