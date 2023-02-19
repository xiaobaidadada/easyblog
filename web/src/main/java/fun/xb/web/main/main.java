package fun.xb.web.main;


import fun.xb.basefunction.service.markdown.re.MdMain;

public class main {
    public void k(){

    }

    public static void main(String[] args) throws InterruptedException {
        String context="# 关系web的 MVC框架建立在servet上\n" +
                "\n" +
                "需要运行在servelt容器中\n" +
                "\n" +
                "# 注册dispatcherservlet\n" +
                "\n" +
                "1. web.xml注册\n" +
                "2. java配置\n" +
                "\n" +
                "\n" +
                "\n" +
                "一个dispathcherservlet需要一个WebApplicationContext类型的spring ioc核心容器作为参数；\n" +
                "\n" +
                "- servlet容器上下文\n" +
                "- dispatcherservelt分发器\n" +
                "- web版的spring ioc 核心容器\n" +
                "\n" +
                "这三者要联系在一起；才可以完成配置；\n" +
                "\n" +
                "## 通过servelt容器接口 （java配置）\n" +
                "\n" +
                "这个类是servlet3.1版本容器提供的功能，他会扫描有没有实现了这些接口的类；\n" +
                "\n" +
                "```java\n" +
                "public class MyWebApplicationInitializer implements WebApplicationInitializer {\n" +
                "\n" +
                "    @Override\n" +
                "    public void onStartup(ServletContext servletContext) {\n" +
                "\t\t//这里的serveltcontext是tomcat等servelt容器提供的；\n" +
                "        \n" +
                "        // Load Spring web application configuration\n" +
                "        //加载 sping 核心容器\n" +
                "        AnnotationConfigWebApplicationContext context = new \t\t\t\t\t    AnnotationConfigWebApplicationContext();\n" +
                "        context.register(AppConfig.class);\n" +
                "\n" +
                "        // Create and register the DispatcherServlet\n" +
                "        //利用 spring ico核心容器创建一个 dispatcherservelt\n" +
                "        DispatcherServlet servlet = new DispatcherServlet(context);\n" +
                "        ServletRegistration.Dynamic registration = servletContext.addServlet(\"app\", servlet);\n" +
                "        registration.setLoadOnStartup(1);\n" +
                "        registration.addMapping(\"/app/*\");\n" +
                "    }\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "\n" +
                "\n" +
                "## 通过web.xml配置\n" +
                "\n" +
                "```xml\n" +
                "<web-app>\n" +
                "\n" +
                "    <listener>\n" +
                "        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>\n" +
                "    </listener>\n" +
                "\n" +
                "    <context-param>\n" +
                "        <param-name>contextConfigLocation</param-name>\n" +
                "        <param-value>/WEB-INF/app-context.xml</param-value>\n" +
                "        <!--加载spring ioc容器-->\n" +
                "    </context-param>\n" +
                "\n" +
                "    <servlet>\n" +
                "        <servlet-name>app</servlet-name>\n" +
                "        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>\n" +
                "        <init-param>\n" +
                "            <param-name>contextConfigLocation</param-name>\n" +
                "            <!--这个dispatcherservelt的实现也需要 spring ioc核心容器-->\n" +
                "            <param-value></param-value>\n" +
                "        </init-param>\n" +
                "        <load-on-startup>1</load-on-startup>\n" +
                "    </servlet>\n" +
                "\n" +
                "    <servlet-mapping>\n" +
                "        <servlet-name>app</servlet-name>\n" +
                "        <url-pattern>/app/*</url-pattern>\n" +
                "    </servlet-mapping>\n" +
                "\n" +
                "</web-app>\n" +
                "```\n" +
                "\n" +
                "## 封装dispatcherservelt和应用容器上下文来注册dispatcherservelt\n" +
                "\n" +
                "下面的接口他继承了WebApplicationInitializer 所以这个接口已经具有了 dispatcherservelt和 和servlet容器上下文；\n" +
                "\n" +
                "加载java配置\n" +
                "\n" +
                "```java\n" +
                "public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {\n" +
                "    //基于javaconfig\n" +
                "\n" +
                "    @Override\n" +
                "    protected Class<?>[] getRootConfigClasses() {\n" +
                "        return new Class<?>[] { RootConfig.class };\n" +
                "    }\n" +
                "    //这里返回的是spring的ioc注解的 javaconfig配置\n" +
                "\n" +
                "    @Override\n" +
                "    protected Class<?>[] getServletConfigClasses() {\n" +
                "        return new Class<?>[] { App1Config.class };\n" +
                "    }\n" +
                "    //这个返回的是这个dispatcherservelt相关的详细配置，这个和上面的其实没有什么区别\n" +
                "\n" +
                "    @Override\n" +
                "    protected String[] getServletMappings() {\n" +
                "        return new String[] { \"/app1/*\" };\n" +
                "    }\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "加载xml配置\n" +
                "\n" +
                "```java\n" +
                "public class MyWebAppInitializer extends AbstractDispatcherServletInitializer {\n" +
                "//基于xml\n" +
                "    @Override\n" +
                "    protected WebApplicationContext createRootApplicationContext() {\n" +
                "        return null;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    protected WebApplicationContext createServletApplicationContext() {\n" +
                "        XmlWebApplicationContext cxt = new XmlWebApplicationContext();\n" +
                "        cxt.setConfigLocation(\"/WEB-INF/spring/dispatcher-config.xml\");\n" +
                "        return cxt;\n" +
                "    }\n" +
                "    \n" +
                "    \n" +
                "\n" +
                "    @Override\n" +
                "    protected String[] getServletMappings() {\n" +
                "        return new String[] { \"/\" };\n" +
                "    }\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "```xml\n" +
                "<web-app>\n" +
                "\n" +
                "    <listener>\n" +
                "        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>\n" +
                "    </listener>\n" +
                "\n" +
                "    <context-param>\n" +
                "        <param-name>contextConfigLocation</param-name>\n" +
                "        <param-value>/WEB-INF/root-context.xml</param-value>\n" +
                "    </context-param>\n" +
                "\n" +
                "    <servlet>\n" +
                "        <servlet-name>app1</servlet-name>\n" +
                "        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>\n" +
                "        <init-param>\n" +
                "            <param-name>contextConfigLocation</param-name>\n" +
                "            <param-value>/WEB-INF/app1-context.xml</param-value>\n" +
                "        </init-param>\n" +
                "        <load-on-startup>1</load-on-startup>\n" +
                "    </servlet>\n" +
                "\n" +
                "    <servlet-mapping>\n" +
                "        <servlet-name>app1</servlet-name>\n" +
                "        <url-pattern>/app1/*</url-pattern>\n" +
                "    </servlet-mapping>\n" +
                "\n" +
                "</web-app>\n" +
                "```\n" +
                "\n" +
                "如果同时注册了多个dispatchservelt，都会起作用，有些组件bean的执行顺序可能会不一样；\n" +
                "\n" +
                "\n" +
                "\n" +
                "# 写beans\n" +
                "\n" +
                "- 前面已经配置了dispatcherservelt，它也是一个bean，spring mvc的web ioc容器不仅需要这个bean，还需要其它各种bean；这些bean之间可能有关系，都是通过dispatcherservelt来加载检测的；\n" +
                "- 每一个bean都有自己的作用，作用取决于它是什么bean；\n" +
                "\n" +
                "## 用于配置spring mvc的特殊bean\n" +
                "\n" +
                "1. HandlerMapping\n" +
                "2. HandlerAdapter\n" +
                "3. [`HandlerExceptionResolver`]\n" +
                "4. ViewResolver\n" +
                "5. LocaleResolver, LocaleContextResolver\n" +
                "6. [`ThemeResolver`](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-themeresolver)\n" +
                "7. [`MultipartResolver`](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-multipart)\n" +
                "8. [`FlashMapManager`](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-flash-attributes)\n" +
                "\n" +
                "## 一些bean：\n" +
                "\n" +
                "### 配置错误界面：\n" +
                "\n" +
                "  ```java\n" +
                "  @RestController\n" +
                "  public class ErrorController {\n" +
                "  \n" +
                "      @RequestMapping(path = \"/error\")\n" +
                "      public Map<String, Object> handle(HttpServletRequest request) {\n" +
                "          Map<String, Object> map = new HashMap<String, Object>();\n" +
                "          map.put(\"status\", request.getAttribute(\"javax.servlet.error.status_code\"));\n" +
                "          map.put(\"reason\", request.getAttribute(\"javax.servlet.error.message\"));\n" +
                "          return map;\n" +
                "      }\n" +
                "  }\n" +
                "  ```\n" +
                "\n" +
                "  ```xml\n" +
                "  <error-page>\n" +
                "      <location>/error</location>\n" +
                "  </error-page>\n" +
                "  ```\n" +
                "\n" +
                "  ### 控制器、匹配映射：\n" +
                "\n" +
                "  ```java\n" +
                "  @Controller\n" +
                "  \n" +
                "  public class HelloController {\n" +
                "  \n" +
                "      @GetMapping(\"/hello\")\n" +
                "      //这个url还可以捕捉变量，匹配多个文件等，模式\n" +
                "      /*/projects/{param}/versions用括号匹配变量再用下面的注解获取这个变量\n" +
                "      \n" +
                "      /projects/{project:[a-z]+}/versions  使用正则表达式匹配并捕获变量\n" +
                "      \n" +
                "      */\n" +
                "      @ResponseBody //加上了这个注解返回是字符串；用Ajax的传输\n" +
                "      public String handle(Model model,@PathVariable Long param) {\n" +
                "          model.addAttribute(\"message\", \"Hello World!\");\n" +
                "          return \"index\";//一般情况下返回的是一个文件的全名，是一个请求\n" +
                "      }\n" +
                "  }\n" +
                "  ```\n" +
                "\n" +
                "控制器函数的**参数**可以是很多种类，应该是通过反射实现的；\n" +
                "\n" +
                "这个方式非常的多，非常的花里胡哨\n" +
                "\n" +
                "- 原始的HttpServletRequst 跟servelt一样\n" +
                "- 字符串变量，名字和网络传送的参数一样\n" +
                "\n" +
                "---\n" +
                "\n" +
                "request mapping用注解的类型匹配有以下几个：\n" +
                "\n" +
                "- `@GetMapping`\n" +
                "- `@PostMapping`\n" +
                "- `@PutMapping`\n" +
                "- `@DeleteMapping`\n" +
                "- `@PatchMapping`\n" +
                "- \n" +
                "\n" +
                "\n" +
                "\n" +
                "  ### 视图解析器：\n" +
                "\n" +
                "```xml\n" +
                "<!--配置内部资源视图解析器-->\n" +
                "<bean class=\"org.springframework.web.servlet.view.InternalResourceViewResolver\">\n" +
                "<!--prefix = \"\" --视图名称前缀    suffix = \"\"--视图名称后缀 -->\n" +
                "    <property name=\"prefix\" value=\"/WEB-INF/views/\"></property>\n" +
                "    <property name=\"suffix\" value=\".jsp\"></property>\n" +
                "</bean>\n" +
                "```\n" +
                "\n" +
                "```java\n" +
                " @Bean\n" +
                "    public InternalResourceViewResolver resolver() {\n" +
                "        InternalResourceViewResolver resolver = new InternalResourceViewResolver();\n" +
                "        resolver.setViewClass(JstlView.class);\n" +
                "        resolver.setPrefix(\"/\");\n" +
                "        resolver.setSuffix(\".html\");\n" +
                "        return resolver;\n" +
                "    }\n" +
                "```\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "# 问题\n" +
                "\n" +
                "- spring的版本和tomcat版本不匹配的时候无法执行成功\n" +
                "- tomcat的包也要匹配才行\n" +
                "\n" +
                "\n" +
                "\n" +
                "# 重点内容\n" +
                "\n" +
                "控制器的编写是很重要的，他在spring 官网的文档中的位置是Annotated Controllers章节部分；\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "# 流程\n" +
                "\n" +
                "![bitmap_sharing_1](C:\\Users\\百宝箱\\文档资料位置\\文件笔记\\study_note\\java\\图片\\bitmap_sharing_1.png)\n" +
                "\n" +
                "\n" +
                "\n" +
                "箭头是处理流；\n" +
                "\n" +
                "## dispatcherServlet\n" +
                "\n" +
                "这是一个servelt\n" +
                "\n" +
                "```xml\n" +
                "<servlet>\n" +
                "           <servlet-name>dispatch</servlet-name>\n" +
                "           <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>\n" +
                "           <init-param><!--此参数可以在servelt中用函数来访问-->\n" +
                "               <param-name>contextConfigLocation</param-name>\n" +
                "               <param-value>classpath:springmvc.xml,classpath:spring.xml</param-value><!--classpath是web-inf下的classes。这是spring提供的.默认到web-inf下查找-->\n" +
                "           </init-param>\n" +
                "       </servlet>\n" +
                "    \n" +
                "    <servlet-mapping>\n" +
                "        <servlet-name>dispatch</servlet-name>\n" +
                "        <url-pattern>/</url-pattern>\n" +
                "        \n" +
                "    </servlet-mapping>\n" +
                "```\n" +
                "\n" +
                "- dispatcherServelt类在IDEA中需要在Artifacts选项下对web-inf创建lib，并把spring-mvc的包引入到此处才可以正常运行；否则运行的时候加载不了；\n" +
                "\n" +
                "- 拦截匹配表达式：\n" +
                "  - *.do 这样的只会匹配 \\* 。。。这样的名字的文件，不会拦截静态资源；可以不用加点\n" +
                "  - REST风格 用\"/\" 或者\"/*\"拦截，/和/\\*的区别式/不会拦截jsp而，/\\*会拦截jsp，静态资源可以在spring的配置文件或者web.xml中释放。spring内加`<mvc:default-servlet-handler>`标签，这相当于式dispatcherServelt做站内转发？\n" +
                "\n" +
                "## handlerMapping\n" +
                "\n" +
                "**它的作用相当于spring mvc的内部web.xml**； 它在web.xml拦截了路径后又做了一次映射，这个行为式 dispatcherservelt做的；**一个映射在这里就是一个bean，bean的生成多种多样，都是由dispatcherservelt来加载执行的，它会创建一个AnnotationConfigWebApplicationContext ioc环境**；\n" +
                "\n" +
                "有三种类型的（前面俩式类级别的，这个*控制器*类一定要继承`org.springframework.web.servlet.mvc.Controller`接口，实现接口的方法才行，后面的注解不管是什么类，什么方法只要加上了这个注解就可以出成为控制类方法）：\n" +
                "\n" +
                "1. SimpleUrlHandlerMapping的bean标签\n" +
                "\n" +
                "2. beanNameUrlHanderMapping的bean标签，可以不用加载这个类的bean，只需要写控制类bean标签的name属性（**url匹配加 /开头**），dispatcherservelt就会根据这个控制类bean创建一个匹配的 beanNameUrlHanderMapping;\n" +
                "\n" +
                "3. 使用注解来\n" +
                "\n" +
                "   使用注解需要先加载两个springmvc提供的bean类，加入`<mvc:annotation-driven/>`可以间接加载这俩类，然后其它的类被扫描到就行；\n" +
                "\n" +
                "\n" +
                "\n" +
                "## HandlerAdpter\n" +
                "\n" +
                "对于每一种加载资源的Mapping匹配，比如静态资源的url，servelt的url，注解类的控制器url，类级别的控制器url，他们都有一个adapter类，springmvc也就是dispatcherservelt 不会根据一个映射直接访问一个资源，而是先访问他们对应的adpter，adapter不用不用显示的写出这些bean，springmvc的dispatcherservelt会自行地给他们注册；\n" +
                "\n" +
                "他们的作用是dispatcherservelt调用他们，他们会先去加载一些其它的配置bean函数，比如jsp的前后缀（视图解析器），然后才会调用真正的控制类，达到了适配的作用；\n" +
                "\n" +
                "不用程序员来写他们；\n" +
                "\n" +
                "## Handler\n" +
                "\n" +
                "控制器，可以写类级别的，也可以写方法级别的（用注解），方式前面已经写了；\n" +
                "\n" +
                "控制器要么返回一个ModelAndView对象，要么返回一个字符串（注解的形式才行），继承控制器类只能返回数据对象，因为接口得返回值就是这样；\n" +
                "\n" +
                "## view和viewresoler\n" +
                "\n" +
                "视图可以是jsp，也可以是字符串；视图解析器会根据 一个`ModelAndView`对象，指定了视图名字并提供数据，在视图内可以用绑定的方式来使用数据；字符串也会生成ModelAndView不过没有名字；视图解析器执行完返回给dispatcherservlet一个html数据；\n" +
                "\n" +
                "一种资源只能配置一种解析器，每种视图的解析器不同，他们也都是一个类，一个bean；\n" +
                "\n" +
                "**一个视图解析器会解析一种文件，它是一个类**，这就需要给文件设置后缀访问；\n" +
                "\n" +
                "- 视图解析器只用于控制器函数返回对应一个文件；\n" +
                "\n" +
                "\n" +
                "\n" +
                "#  常用注解\n" +
                "\n" +
                " 有很多关于控制类函数参数的\n" +
                "\n" +
                "| 注解                                          | 用法                                     | 作用                                                         |\n" +
                "| --------------------------------------------- | ---------------------------------------- | ------------------------------------------------------------ |\n" +
                "| @Controller                                   | 注解一个类上                             | 表明他是控制类，成为一个bean，和其他的bean没有，实际上只有把类注解成这样bean才行 |\n" +
                "| **@RequestMapping**                           | 可以注解到类，也可以注解到函数，可以同时 | 路径匹配，要以\"/ \" web的根目录开始；                         |\n" +
                "| **@RequestParam**                             | 注解到控制函数的参数上                   | 获取url中的参数，跟普通的获取一样，不必像@PathVariable一样提供{}模板；如果想设置可以为null,需要加required = false |\n" +
                "| **@ResponseBody**                             | 注解到方法                               | 控制类函数将会直接返回字符串                                 |\n" +
                "| **@RequestBody**                              | 注解控制类函数的参数上                   | 一定是post请求，会把数据转化成一个实体类对象，**用于请求不是表单**这种简单的数据，可以注解在一个JSONobject对象上，也可以是一个字符串等，只能注解参数一次？常用来处理content-type不是默认的**application/x-www-form-urlcoded**编码的内容 |\n" +
                "| **@PathVariable**                             | 注解控制类函数的参数上                   | 这个需要搭配，前面的路径匹配注解提供`{a}`这样的模板，然后可以获取；如果想允许实际url中没有这个位置参数，需要在路径匹配中提供另多个路径，然后在该注解内加上required = false;@GetMapping(value = {\"l/{a}\",\"/l\"}) |\n" +
                "| **@RequestHeader**                            | 也是放在参数上                           |                                                              |\n" +
                "| **@CoookieValue**                             | 也是放在参数上                           |                                                              |\n" +
                "| **@GetMapping，@PostMapping，@DeleteMapping** | 指定请求方式                             |                                                              |\n" +
                "| @RestController                               | 它是一个组合注解，作用在类上             | **@ResponseBody**和@controller                               |\n" +
                "| @@RequestHeader                               | 注解到请求的参数上                       | 获取请求头的值                                               |\n" +
                "| @ModelAttribute                               | 1. 注解到方法<br />2. 注解到参数上       | 注解到参数上最常用，前面的是只能获取单个的请求k,v，这个可以直接获取全部的值转化成一个model对象；[全面解析Spring中@ModelAttribute注解的用法 - Cobcmw - 博客园 (cnblogs.com)](https://www.cnblogs.com/cobcmw/p/12092591.html) |\n" +
                "\n" +
                "```java\n" +
                "@Controller\n" +
                "@RequestMapping(\"/test\")\n" +
                "public class Test{\n" +
                "        @RequestMapping(value=\"/{a}\", method = RequestMethod.GET)\n" +
                "        @ResponseBody\n" +
                "        public String get(@PathVariable(\"a\")String a){\n" +
                "                return \"\";\n" +
                "       }\n" +
                "        @PostMapping(\"/test\")\n" +
                "        public void save(@RequestBody User user){}\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "# Handler的函数的参数\n" +
                "\n" +
                "- 可以是servelt中的，**HttpServletRequest**和**HttpServletResponse**\n" +
                "- 可以是**HttpSession**\n" +
                "- 可以是**Model**和ModelAndView作用一样\n" +
                "- 可以是一个没有被注解的实体类，它默认匹配的content-type是application/x-www-form-urlencoded，就是普通的表单\n" +
                "- 可以是一个被注解了RequestBody的实体类，它的默认类型是json\n" +
                "- 可以是一个普通的变量，需要被注解RequestParam或者PathVariable\n" +
                "- 可以是一个被注解了验证注解的实体类，这样就不用加RequestBody了，它会自动判断类型，但是类型仅限于json和普通表单？\n" +
                "\n" +
                "\n" +
                "\n" +
                "# 拦截器\n" +
                "\n" +
                "```java\n" +
                "public class intercept_login implements HandlerInterceptor {\n" +
                "\n" +
                "   public   boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)\n" +
                "            throws Exception {\n" +
                "                response.getWriter().print(\"sucess\");\n" +
                "       System.out.println(\"成功\");\n" +
                "        return false;\n" +
                "    }\n" +
                "\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "```java\n" +
                "@Configuration\n" +
                "@EnableWebMvc\n" +
                "public class WebConfig implements WebMvcConfigurer {\n" +
                "\n" +
                "    @Override\n" +
                "    public void addInterceptors(InterceptorRegistry registry) {\n" +
                "//        registry.addInterceptor(new LocaleChangeInterceptor());\n" +
                "//        registry.addInterceptor(new ThemeChangeInterceptor()).addPathPatterns(\"/**\").excludePathPatterns(\"/admin/**\");\n" +
                "        registry.addInterceptor(new intercept_login()).addPathPatterns(\"/**\");\n" +
                "    }\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "因为spring boot下的mvc bean工程不能再用xml，所以要用javaconfig来注册拦截器；\n" +
                "\n" +
                "拦截器中最关键的然后有三个，可以查看接口类型；\n" +
                "\n" +
                "# 一些问题\n" +
                "\n" +
                "1. springmvc控制器转发的界面，如果文件引用资源使用的是相对路径，那么这些相对路径是无法起效的；\n" +
                "2. 如果使用springboot的配置文件配置mvc而不是bean，有很多功能会失效，比如无法使用等多个视图解析器？\n";

        StringBuffer st=new StringBuffer();
            String re= MdMain.toHtml(context,st);
        System.out.println(st);




    }


}
