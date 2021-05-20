package com.blakdragon.petscapeclan

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class PetscapeClanApplication : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        //startup logic:
    }
}

fun main(args: Array<String>) {
    runApplication<PetscapeClanApplication>(*args)
}
