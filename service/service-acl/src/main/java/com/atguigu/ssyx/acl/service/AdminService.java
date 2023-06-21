package com.atguigu.ssyx.acl.service;

import com.atguigu.ssyx.model.acl.Admin;
import com.atguigu.ssyx.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import reactor.core.publisher.Mono;

public interface AdminService extends IService<Admin> {

    //1 用户列表
    Mono<IPage<Admin>> selectPageUser(Page<Admin> pageParam, AdminQueryVo adminQueryVo);
}
