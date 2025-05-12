package com.virtusize.sdk

import java.io.Serializable

enum class RecommendedSize : Serializable {

    Small {
        override fun toString(): String {
            return "S"
        }
    },
    Medium {
        override fun toString(): String {
            return "M"
        }
    },
    Large {
        override fun toString(): String {
            return "L"
        }
    },
    ExtraLarge {
        override fun toString(): String {
            return "XL"
        }
    },
    Unknown {
        override fun toString(): String {
            return "Unknown"
        }
    }

}