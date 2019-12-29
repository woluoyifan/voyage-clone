package com.luoyifan.voyage.config;

import freemarker.template.DefaultObjectWrapper;
import freemarker.template.SimpleDate;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.annotation.Resource;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author EvanLuo
 */
@Configuration
public class FreemarkerConfig {
    @Resource
    void configureFreemarkerConfigurer(FreeMarkerConfig configurer) {
        configurer.getConfiguration().setObjectWrapper(new CustomObjectWrapper());
    }

    private static class CustomObjectWrapper extends DefaultObjectWrapper {
        @Override
        public TemplateModel wrap(Object obj) throws TemplateModelException {
            if (obj instanceof LocalDateTime) {
                Timestamp timestamp = Timestamp.valueOf((LocalDateTime) obj);
                return new SimpleDate(timestamp);
            }
            if (obj instanceof LocalDate) {
                Date date = Date.valueOf((LocalDate) obj);
                return new SimpleDate(date);
            }
            if (obj instanceof LocalTime) {
                Time time = Time.valueOf((LocalTime) obj);
                return new SimpleDate(time);
            }
            return super.wrap(obj);
        }
    }
}
