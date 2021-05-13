package com.blakdragon.petscapeoffline

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.core.io.ResourceLoader


@SpringBootApplication
class PetscapeOfflineApplication : ApplicationRunner {

    @Autowired
    private lateinit var resourceLoader: ResourceLoader

    override fun run(args: ApplicationArguments?) {
//        val resource = resourceLoader.getResource("classpath:firebase_config.json")
//        val serviceAccount = FileInputStream(resource.file)
//
//        val options: FirebaseOptions = FirebaseOptions.builder()
//            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//            .build()
//
//        FirebaseApp.initializeApp(options)
    }
}

fun main(args: Array<String>) {
    runApplication<PetscapeOfflineApplication>(*args)
}
