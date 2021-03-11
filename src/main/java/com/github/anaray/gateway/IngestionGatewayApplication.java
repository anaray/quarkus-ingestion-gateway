package com.github.anaray.gateway;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        tags = {
        },
        info = @Info(
                title = "Ingestion Gateway - Metrics",
                version = "0.0.1",
                contact = @Contact(
                        name = "dpoe",
                        url = "http://workday.com/contact",
                        email = "<dpoesupport>@workday.com"))
)
public class IngestionGatewayApplication extends Application {
}
