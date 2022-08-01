package com.zfeng.minedemo.plugin.publish

import org.gradle.api.provider.Property


abstract class AARExtension {

    abstract val name: Property<String>
    abstract val version: Property<String>

}