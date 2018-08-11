package com.varmarken.landevicetracker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * TODO add class documentation.
 *
 * @author Janus Varmarken
 */
@SpringBootApplication
public class Application {

    static Thread mainThread;

    public static void main(String[] args) {
        mainThread = Thread.currentThread();
        System.out.println(String.format("##### mainThread: name='%s' threadId=%d #####", mainThread.getName(), mainThread.getId()));
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            Thread currentThread = Thread.currentThread();
            // Apparently currentThread is called 'restartedMain' and has a DIFFERENT thread ID than the main thread
            // ('main'), yet 'restartedMain' is equal to 'main'
            String debug = String.format("##### CommandLineRunner thread: name='%s' threadId=%d equalToMainThread=%b #####",
                    currentThread.getName(), currentThread.getId(), currentThread.equals(mainThread));
            System.out.println(debug);
        };
    }


}
