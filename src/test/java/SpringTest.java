import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.access.ContextSingletonBeanFactoryLocator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    /**
     * Loads the context specified in one of the beanRefContext.xml files found
     * on the classpath.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testBeanRefContext() throws Exception {
        ApplicationContext ctx = (ApplicationContext) ContextSingletonBeanFactoryLocator
                .getInstance().useBeanFactory("example1").getFactory();

        // Found by name; can be multiple
        Map<String, String> someMap = ctx.getBean("someMap", Map.class);
        // Can only be one
        List<Object> someList = ctx.getBean(List.class);

        assertEquals(0, someMap.size());
        assertEquals(1, someList.size());
        assertEquals("hi", someList.get(0));
    }

    @Test
    public void testManual() throws Exception {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                "classpath:integer1.xml");
        Integer i = ctx.getBean("myint", Integer.class);
        assertEquals(Integer.valueOf(1), i);
    }

}
