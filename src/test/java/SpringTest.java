import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.access.ContextSingletonBeanFactoryLocator;


public class SpringTest {

    /**
     * Loads the context specified in one of the beanRefContext.xml files found
     * on the classpath.
     */
    @Test
    public void testBeanRefContext() throws Exception {
        ApplicationContext ctx = (ApplicationContext) ContextSingletonBeanFactoryLocator
                .getInstance().useBeanFactory("example1").getFactory();
        
        // Found by name; can be multiple
        Map<String, String> someMap = ctx.getBean("someMap", Map.class);
        // Can only be one
        List<Object> someList = ctx.getBean(List.class);
    }

    
}
