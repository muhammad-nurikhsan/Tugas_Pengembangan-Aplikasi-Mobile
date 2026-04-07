package com.tugaskeduapam.myapplication

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform