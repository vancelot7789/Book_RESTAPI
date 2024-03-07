package com.devtiro.database.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        /* solving nested object */
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        /*=======================*/
        return modelMapper;
    }

}
