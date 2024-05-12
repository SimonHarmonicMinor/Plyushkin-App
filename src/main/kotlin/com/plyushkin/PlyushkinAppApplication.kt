package com.plyushkin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PlyushkinAppApplication

fun main(args: Array<String>) {
	runApplication<PlyushkinAppApplication>(*args)
}
