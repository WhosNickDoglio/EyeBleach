import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
 * MIT License
 *
 * Copyright (c) 2020 Nicholas Doglio
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(Libs.com_android_tools_build_gradle)
        classpath(Libs.kotlin_gradle_plugin)
        classpath(Libs.play_publisher)
    }
}

plugins {
    detekt
    benManesVersions
    ktlint
    id("com.autonomousapps.dependency-analysis") version "0.64.0"
    id("com.osacky.doctor") version "0.6.3"
}

// TODO dependency matching
allprojects {
    repositories {
        jcenter()
        google()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.freeCompilerArgs = listOf(
//                "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
//                "-Xuse-experimental=kotlinx.coroutines.FlowPreview",
            "-Xjvm-default=all",
            "-Xallow-jvm-ir-dependencies",
            "-Xopt-in=kotlin.Experimental",
            "-Xskip-prerelease-check"
        )
    }
}

tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
    gradleVersion = Versions.gradleLatestVersion
}

tasks.register<Delete>("clean") {
    delete("build")
}
