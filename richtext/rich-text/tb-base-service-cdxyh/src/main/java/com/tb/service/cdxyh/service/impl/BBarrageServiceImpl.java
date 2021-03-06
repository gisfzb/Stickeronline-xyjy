package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.oConvertUtils;
import com.tb.base.common.vo.PageVo;
import com.tb.service.cdxyh.entity.BBarrageEntity;
import com.tb.service.cdxyh.repository.BBarrageRepository;
import com.tb.service.cdxyh.service.BBarrageService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@AsyncServiceHandler
public class BBarrageServiceImpl implements BBarrageService, BaseAsyncService {
    @Autowired
    private BBarrageRepository bBarrageRepository;
    @Override
    public void add(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BBarrageEntity bBarrageEntity = new BBarrageEntity(params);
        bBarrageEntity.setCreateTime(new Date());
        bBarrageEntity.setCreateBy(bBarrageEntity.getUserName());
        BBarrageEntity save = bBarrageRepository.save(bBarrageEntity);
        if (oConvertUtils.isNotEmpty(save)){
            future.complete(new JsonObject(Json.encode(save)));
        } else {
            future.complete(new JsonObject());
        }
        handler.handle(future);
    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        BBarrageEntity bBarrageEntity = new BBarrageEntity();
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();

//            exampleMatcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
            //创建实例
            Example<BBarrageEntity> ex = Example.of(bBarrageEntity, exampleMatcher);

            Page<BBarrageEntity> plist = bBarrageRepository.findAll(ex,pageable);
            future.complete(new JsonObject(Json.encode(plist)));
//        } else {
//            future.complete(new JsonObject().put("msg","请传入有效参数！"));
//        }
        handler.handle(future);
    }

    @Override
    public void edit(JsonObject params, Handler<AsyncResult<String>> handler) {

    }

    @Override
    public void delete(JsonObject params, Handler<AsyncResult<String>> handler) {

    }

    @Override
    public void queryall(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
        BBarrageEntity bBarrageEntity = new BBarrageEntity();
        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<BBarrageEntity> ex = Example.of(bBarrageEntity, matcher);
        List<BBarrageEntity> newsList = bBarrageRepository.findAll(ex);
        if (newsList == null || newsList.size() <= 0) {
            future.complete(new JsonArray());
        } else {
            future.complete(new JsonArray(Json.encode(newsList)));
        }
        handler.handle(future);
    }

    @Override
    public void getList(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {

    }

    @Override
    public void queryById(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BBarrageEntity bBarrageEntity = new BBarrageEntity(params);
        Optional<BBarrageEntity> res = bBarrageRepository.findById(bBarrageEntity.getId());
        if (res.isPresent()) {
            future.complete(new JsonObject(Json.encode(res.get())));
        }else{
            future.complete(new JsonObject());
        }
        handler.handle(future);
    }



}
