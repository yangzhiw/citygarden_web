package com.citygarden.domain.util;


import com.citygarden.security.SecurityUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DomainCodeGenerator {
    private DomainCodeGenerator() {
    }

    public static final String generatePartnerCode() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date()) + RandomStringUtils.randomNumeric(4);
    }

    public static final String generateStrategyInstanceCode() {
        return "GL" + RandomStringUtils.randomNumeric(4);
    }

    public static final String generateStrategyTemplateCode() {
        return "GLM" + RandomStringUtils.randomNumeric(4);
    }

    public static final String generateScenarioTemplateCode() {
        return "CJM" + RandomStringUtils.randomNumeric(4);
    }

    public static final String generateScenarioInstanceCode() {
        return "CJ" + RandomStringUtils.randomNumeric(4);
    }

    public static final String generateSolutionTemplateCode() {
        return "JNM" + RandomStringUtils.randomNumeric(4);
    }

    public static final String generateSolutionInstanceCode() {
        return "JN" + RandomStringUtils.randomNumeric(4);
    }

    public static final String generateRuleTemplateCode() {
        return "GZM" + RandomStringUtils.randomNumeric(6);
    }

    public static final String generateRuleInstanceCode() {
        return "GZ" + RandomStringUtils.randomNumeric(6);
    }

    public static final String generateRuleConditionInstanceCode(){ return "GZT" + RandomStringUtils.randomNumeric(8);}


    public static final String generateCartDetailId() {
        return SecurityUtils.getCurrentUserLogin().toUpperCase() + new SimpleDateFormat("yyyyMMddHHmmSS").format(new Date())
                + RandomStringUtils.randomNumeric(6);
    }

}
