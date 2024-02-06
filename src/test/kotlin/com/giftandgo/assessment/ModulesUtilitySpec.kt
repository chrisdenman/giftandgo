package com.giftandgo.assessment

import org.junit.jupiter.api.Test
import org.springframework.modulith.core.ApplicationModules
import org.springframework.modulith.docs.Documenter

class ModulesUtilitySpec {

    companion object {
        val APPLICATION__CLASS = Application::class.java
    }

    @Test
    fun verify() {
        ApplicationModules
            .of(APPLICATION__CLASS)
            .verify()
    }

    @Test
    fun buildDocumentation() {
        Documenter(ApplicationModules.of(APPLICATION__CLASS))
            .writeModuleCanvases()
    }

    @Test
    fun writeModulesToConsole() {
        ApplicationModules
            .of(APPLICATION__CLASS)
            .forEach(System.out::println)
    }
}
