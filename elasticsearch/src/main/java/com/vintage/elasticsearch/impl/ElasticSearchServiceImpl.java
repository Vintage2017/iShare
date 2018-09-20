package com.vintage.elasticsearch.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vintage.elasticsearch.service.ElasticSearchService;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.InnerHitBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.collapse.CollapseBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.activation.FileDataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Vintage Wang
 * @Date: 2018-09-20
 */
@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {
    private final String SORT_ASC = "ASC";
    private final String SORT_DESC = "DESC";

    @Override
    public List<JSONObject> getGroupAndSort(SearchRequestBuilder builder, Map<String, String> queryParam, String groupFiled, String orderFiled, String sort, Integer size) {
        // 结果集
        List<JSONObject> result = new ArrayList<>();

        SortBuilder sortBuilder = new FieldSortBuilder(orderFiled);
        sortBuilder.order(SORT_ASC.equals(sort.toUpperCase()) ? SortOrder.ASC : SortOrder.DESC);
        InnerHitBuilder innerHitBuilder = new InnerHitBuilder().setName("innerHit").setSize(size).addSort(sortBuilder);
        CollapseBuilder collapseBuilder = new CollapseBuilder(groupFiled).setInnerHits(innerHitBuilder);
        builder.setCollapse(collapseBuilder);
        if (!queryParam.isEmpty()) {
            Map.Entry entry = queryParam.entrySet().iterator().next();
            builder.setQuery(QueryBuilders.termQuery(entry.getKey().toString(), entry.getValue()));
        }

        SearchResponse response = builder.execute().actionGet();
        SearchHits hits = response.getHits();
        for (SearchHit h : hits) {
            SearchHits innerHits = h.getInnerHits().get("innerHit");
            SearchHit[] innerSearchHits = innerHits.getHits();
            for (SearchHit innerHit : innerSearchHits) {
                JSONObject object = JSON.parseObject(innerHit.getSourceAsString());
                result.add(object);
            }
        }
        return result;
    }
}
