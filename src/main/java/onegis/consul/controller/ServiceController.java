package onegis.consul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: tunan
 * @version: v.1.0.1
 * @date: created on 16:55 2019-02-15
 */
@RestController
public class ServiceController {

    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 获取所有服务名为serice-producer的服务信息并返回到页面
     * @return
     */
    @GetMapping("/services")
    public Object services(){
        return discoveryClient.getInstances("service-producer");
    }

    /**
     * 从所有服务中选择一个服务（轮询）
     * @return
     */
    @GetMapping("/discover")
    public Object discover(){
        return loadBalancer.choose("service-producer").getUri().toString();
    }
}
