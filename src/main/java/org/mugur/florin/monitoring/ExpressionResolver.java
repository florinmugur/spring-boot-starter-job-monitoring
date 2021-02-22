package org.mugur.florin.monitoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class ExpressionResolver implements ApplicationContextAware {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final SpelExpressionParser parser = new SpelExpressionParser();

    private StandardEvaluationContext context;

    public String resolve(String expression) {
        try {
            Expression exp = parser.parseExpression(expression);
            return exp.getValue(context, String.class);
        } catch (Exception e) {
            log.warn("Could not resolve expression: {}. Exception: {}", expression, e.getMessage());
            return null;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = new StandardEvaluationContext();
        context.setBeanResolver(new BeanFactoryResolver(applicationContext));
    }

}
