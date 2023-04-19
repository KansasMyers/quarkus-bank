package io.github.kansasmyers;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        info = @Info(
                title="Quarkus Bank",
                version = "0.0.1-SNAPSHOT",
                contact = @Contact(
                        name = "José Victor",
                        url = "https://github.com/KansasMyers",
                        email = "kansasmyers360@gmail.com"),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"))
)
public class QuarkusApplication extends Application {
}
