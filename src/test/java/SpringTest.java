import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.access.ContextSingletonBeanFactoryLocator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ex2.Finder;
import ex2.JoshPluginFinder;

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

    /**
     * In addition to XML configuration, Spring can also use Java classes with
     * annotations to configure services, etc.
     */
    @Test
    public void testJavaConfiguration() throws Exception {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(
                AppConfig1.class);
        String myValue = ctx.getBean("myValue", String.class);
        assertEquals("whoo", myValue);
    }

    @Test
    public void testFindByAnnotation() throws Exception {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(
                true);

        // scanner.addIncludeFilter(new
        // AnnotationTypeFilter(<TYPE_YOUR_ANNOTATION_HERE>.class));

        for (BeanDefinition bd : scanner.findCandidateComponents("ex"))
            assertEquals("ex.MyThing", bd.getBeanClassName());
    }

    @Test
    public void testServiceScanningWithRegex() throws Exception {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                "services.xml");
        assertServicesXmlFile(ctx);
    }

    /**
     * With Spring 3.0, the current best practice for scanning is to import XML.
     *
     * See: http://stackoverflow.com/questions/6683771/spring-configuration-and-
     * contextcomponent-scan
     */
    @Test
    public void testJavaConfigWithImport() throws Exception {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(
                BarConfig.class);
        assertServicesXmlFile(ctx);

    }

    //
    // Helpers
    //

    protected void assertServicesXmlFile(ApplicationContext ctx) {
        Map<String, Finder> finders = ctx.getBeansOfType(Finder.class);
        assertEquals(1, finders.size());
        String key = finders.keySet().iterator().next();
        Finder value = finders.get(key);
        assertEquals(JoshPluginFinder.class, value.getClass());
    }
}
