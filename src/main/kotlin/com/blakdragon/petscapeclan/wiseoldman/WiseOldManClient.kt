package com.blakdragon.petscapeclan.wiseoldman

import org.springframework.web.reactive.function.client.WebClient


fun wiseOldManClient(): WebClient = WebClient.create("https://api.wiseoldman.net/")
