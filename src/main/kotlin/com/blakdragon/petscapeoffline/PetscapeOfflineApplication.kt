package com.blakdragon.petscapeoffline

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class PetscapeOfflineApplication : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        //startup logic:
    }
}

fun main(args: Array<String>) {
    runApplication<PetscapeOfflineApplication>(*args)
}
