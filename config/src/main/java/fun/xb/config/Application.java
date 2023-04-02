package fun.xb.config;

import fun.xb.basefunction.constant.sys_constant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;




@SpringBootApplication
@ComponentScan("fun.xb")
public class Application {

    public static void main(String[] args) {

        System.out.println(args.length);
        for(String st  : args){
            if(st.indexOf("--file_path")!=-1){
                String [] file_split = st.split("=");
                sys_constant.file_home_path =file_split[file_split.length-1]+"\\";
            }
            System.out.println(sys_constant.file_home_path);
            System.out.println(System.getProperty("java.class.path"));
            System.out.println(System.getProperty("user.dir"));
            System.out.println(System.getProperty("user.home"));
        }
        SpringApplication.run(Application.class, args);

    }

}
