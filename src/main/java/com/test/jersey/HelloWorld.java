package com.test.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/hello")
public class HelloWorld {
    //GET注解设置接受请求类型为GET
    @GET
    //Produces表明发送出去的数据类型为text/plain
    // 与Produces对应的是@Consumes，表示接受的数据类型为text/plain
    @Produces("text/plain")
    public String getMessage() {
        return "Hello world!";
    }
}