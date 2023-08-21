package com.tar.tarson.configuration;

import com.tar.tarson.tarson.TarSonConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TarSonAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public TarSonConverter tarSonConverter(){
        return new TarSonConverter();
    }
}
