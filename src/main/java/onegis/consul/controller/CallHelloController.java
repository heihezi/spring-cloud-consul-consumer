package onegis.consul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author: tunan
 * @version: v.1.0.1
 * @date: created on 17:03 2019-02-15
 */
@RefreshScope
@RestController
public class CallHelloController {

    @Value("${name}")
    private String name;

    @Value("${from}")
    private String from;

    @Autowired
    private LoadBalancerClient loadBalancer;

    @GetMapping("/call")
    public String call(){
        ServiceInstance serviceInstance = loadBalancer.choose("service-producer");
        System.out.println("服务地址: " + serviceInstance.getUri());
        System.out.println("服务名称: " + serviceInstance.getServiceId());

        String callServiceResult = new RestTemplate().getForObject(serviceInstance.getUri().toString() + "/hello", String.class);
        System.out.println(callServiceResult);
        return callServiceResult;
    }

    @GetMapping("/name")
    public String getName(){
        return name;
    }

    @GetMapping("/from")
    public String getFrom(){
        return from;
    }
}
