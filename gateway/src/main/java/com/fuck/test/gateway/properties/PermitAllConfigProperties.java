package com.fuck.test.gateway.properties;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * DESCRIPTION:
 * security放行url pattern
 * @author zouyan
 * @create 2017-12-21 11:41
 * Created by fuck~ on 2017/12/21.
 */
@Component("permitAllConfigProperties")
@ConfigurationProperties(prefix="fuck.permitall")
public class PermitAllConfigProperties {

    private List<String> patterns;

    private String[] antPatterns = new String[]{};

    public List<String> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<String> patterns) {
        this.patterns = patterns;
    }

    public String[] getAntPatterns() {
        return antPatterns;
    }

    @PostConstruct
    public void init() {
        if (CollectionUtils.isNotEmpty(patterns)) {
            antPatterns = new String[patterns.size()];
            antPatterns = patterns.toArray(antPatterns);
        }
    }

}
