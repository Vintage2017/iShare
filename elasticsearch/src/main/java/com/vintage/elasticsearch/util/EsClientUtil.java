package com.vintage.elasticsearch.util;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.LocalTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Description: 初始化客户端
 * @Author: Vintage Wang
 * @Date: 2018-09-20
 */

public class EsClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(EsClientUtil.class);

    public static TransportClient init(){
        TransportClient client = null;
        try{
            Settings settings = Settings.builder()
                    .put("cluster.name","elasticsearch")
                    .put("client.transport.sniff",true)
                    .build();
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"),9300));
            logger.info("Success:Created elasticSearch client!");
        }
        catch (UnknownHostException e){
            logger.info("Error:Creating elasticSearch client!",e);
        }
        return client;
    }
}
