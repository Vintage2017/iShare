package com.vintage.elasticsearch.service;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.search.SearchRequestBuilder;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Vintage Wang
 * @Date: 2018-09-20
 */

public interface ElasticSearchService {

    /**
     *
     * @param builder
     * @param queryParam 查询参数
     * @param groupFiled 分组字段
     * @param orderFiled 排序字段
     * @param sort ASC,DESC
     * @return
     */
    List<JSONObject> getGroupAndSort(SearchRequestBuilder builder, Map<String,String> queryParam,String groupFiled,String orderFiled,String sort,Integer size);

}
