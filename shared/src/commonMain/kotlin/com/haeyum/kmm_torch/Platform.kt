package com.haeyum.kmm_torch

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform