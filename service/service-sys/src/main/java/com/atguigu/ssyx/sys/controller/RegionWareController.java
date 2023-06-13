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
    public Result<IPage<RegionWare>> list(@PathVariable Long page,
                       @PathVariable Long limit,
                       RegionWareQueryVo regionWareQueryVo) {
        Page<RegionWare> pageParam = new Page<>(page,limit);
        return Result.ok(regionWareService.selectPageRegionWare(pageParam,regionWareQueryVo));
    }

    //添加开通区域
//    url: `${api_name}/save`,
//    method: 'post',
//    data: role
    @Operation(description = "添加开通区域")
    @PostMapping("save")
    public Result<Boolean> addRegionWare(@RequestBody RegionWare regionWare) {
        regionWareService.saveRegionWare(regionWare);
        return Result.ok(null);
    }

    //删除开通区域
//    url: `${api_name}/remove/${id}`,
//    method: 'delete'
    @Operation(description = "删除开通区域")
    @DeleteMapping("remove/{id}")
    public Result<Boolean> remove(@PathVariable Long id) {
        regionWareService.removeById(id);
        return Result.ok(null);
    }

    //取消开通区域
//    url: `${api_name}/updateStatus/${id}/${status}`,
//    method: 'post'
    @Operation(description = "取消开通区域")
    @PostMapping("updateStatus/{id}/{status}")
    public Result<Boolean> updateStatus(@PathVariable Long id,
                               @PathVariable Integer status) {
        regionWareService.updateStatus(id,status);
        return Result.ok(null);
    }
}

