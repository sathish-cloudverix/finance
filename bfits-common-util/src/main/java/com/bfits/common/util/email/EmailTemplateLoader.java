package com.bfits.common.util.email;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class EmailTemplateLoader {

    public String loadTemplate(String templateName) {
        try {
            ClassPathResource resource = new ClassPathResource("email-templates/" + templateName);
            InputStream inputStream = resource.getInputStream();
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load email template: " + templateName, e);
        }
    }
}
