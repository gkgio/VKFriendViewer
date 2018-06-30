package com.gkgio.vkfriendsviewer.utils

class Error {
    class WrongGetException : RuntimeException() {
        override val message: String?
            get() = "Wrong GET parameters"
    }

}