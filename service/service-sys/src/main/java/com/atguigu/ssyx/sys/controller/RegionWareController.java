package com.atguigu.ssyx.sys.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.sys.RegionWare;
import com.atguigu.ssyx.sys.service.RegionWareService;
import com.atguigu.ssyx.vo.sys.RegionWareQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * <p>
 * 城市仓库关联表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-04-03
 */
@Tag(name = "开通区域接口")
@RestController
@RequestMapping("/admin/sys/regionWare")
//@CrossOrigin
public class RegionWareController {

    @Autowired
    private RegionWareService regionWareService;

    //开通区域列表
//    url: `${api_name}/${page}/${limit}`,
//    method: 'get',
//    params: searchObj
    @Operation(description = "开通区域列表")
    @GetMapping("{page}/{limit}")
    public Mono<Result<IPage<RegionWare>>> list(@PathVariable Long page,
                     @PathVariable Long limit,
                     RegionWareQueryVo regionWareQueryVo) {
        Page<RegionWare> pageParam = new Page<>(page,limit);
        return regionWareService.selectPageRegionWare(pageParam,regionWareQueryVo)
                .map(Result::ok)
                .switchIfEmpty(Mono.just(Result.ok(null)))
                .subscribeOn(Schedulers.parallel());
    }

    //添加开通区域
    @Operation(description = "添加开通区域")
    @PostMapping("save")
    public Mono<Result<Boolean>> addRegionWare(@RequestBody RegionWare regionWare) {
        regionWareService.saveRegionWare(regionWare);
        return Mono.just(Result.ok(null));
    }

    //删除开通区域
    @Operation(description = "删除开通区域")
    @DeleteMapping("remove/{id}")
    public Mono<Result<Boolean>> remove(@PathVariable Long id) {
        regionWareService.removeById(id);
        return Mono.just(Result.ok(null));
    }

    //取消开通区域
//    url: `${api_name}/updateStatus/${id}/${status}`,
//    method: 'post'
    @Operation(description = "取消开通区域")
    @PostMapping("updateStatus/{id}/{status}")
    public Mono<Result<Boolean>> updateStatus(@PathVariable Long id,@PathVariable Integer status) {
        regionWareService.updateStatus(id,status);
        return Mono.just(Result.ok(null));
    }
}

