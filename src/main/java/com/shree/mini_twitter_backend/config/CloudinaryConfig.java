package com.shree.mini_twitter_backend.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dsbfandth",
                "api_key", "574567727633836",
                "api_secret", "lYa11JklLMVzDrNtDZ3laPEoFso"
        ));
    }
}