package com.example.helloworld.resources;
import com.example.helloworld.api.Saying;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Optional;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    public HelloWorldResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.orElse(defaultName));
        return new Saying(counter.incrementAndGet(), value);
    }

    @Path("/calc/{operation}")
    @GET
    public String calc(@QueryParam("first") String first , @QueryParam("second") String second,
                     @PathParam("operation") String operation) {
        System.out.println("operation:"+operation);
        try {
            int num1 = Integer.parseInt(first);
            int num2 = Integer.parseInt(second);
            if (operation.equals("+")) {
                return num1 + operation + num2 + " = " + (num1 + num2);
            }
            if (operation.equals("-")) {
                return num1 + operation + num2 + " = " + (num1 - num2);
            }
            if (operation.equals("*")) {
                return num1 + operation + num2 + " = " + (num1 * num2);
            }
            if (operation.equals("/")) {
                return num1 + operation + num2 + " = " + (num1 / num2);
            }
            return "Please enter a valid operation";
        } catch (Exception e) {
            e.printStackTrace();
            return "please enter valid numbers";
        }
    }

}