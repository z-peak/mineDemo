package com.zfeng.kotlinknowledge

import android.util.Log

object KotlinUtils {

    const val TAG = "KotlinUtils"

    /**
     * 1、infix 不能定义成顶层函数，必须是某个类的成员函数，可使用扩展方法的方式将它定义到某个类中
     * 2、infix 函数必须且只有能接收一个参数，类型的话没有限制。
     */
    fun runInfix() {
        val result = "Hello"
        val msg = result append "world!"
        log("infix:$msg")
    }

    infix fun String.append(prefix: String): String = "$this $prefix"

    /**
     * plus、minus 操作符
     * 在 Java 中算术运算符只能用于基本数据类型，+ 运算符可以与 String 值一起使用，但是不能在集合中使用，在 Kotlin 中可以应用在任何类型
     */
    fun runPlus() {
        val numbersMap = mapOf("one" to 1, "two" to 2, "three" to 3)

        // plus (+)
        log(numbersMap + Pair("four", 4) + ("five" to 10)) // {one=1, two=2, three=3, four=4}
        log(numbersMap + Pair("one", 10)) // {one=10, two=2, three=3}
        log(numbersMap + Pair("five", 5) + Pair("one", 11)) // {one=11, two=2, three=3, five=5}

        // minus (-)
        log(numbersMap - "one") // {two=2, three=3}
        log(numbersMap - listOf("two", "four")) // {one=1, three=3}

//         numbersMap.plus() 点进去可以看到其实是kotlin官方帮我们做好了扩展，因此我们可以直接使用，我们自己也可以扩展。

        // 运算符重载，其实就是添加一个扩展函数
        val s1 = Salary(10)
        val s2 = Salary(20)
        log(s1 + s2) // 30
        log(s1 - s2) // -10
    }

    fun runMap() {
        // 在 Map 集合中，可以使用 withDefault 设置一个默认值，当键不在 Map 集合中，通过 getValue 返回默认值。
        val map = mapOf<String, String>(
            "a" to "hello",
            "b" to "world",
            "c" to "!"
        ).withDefault { "default" }
        log("${map["a"]} ${map["b"]} ${map["c"]} ${map["d"]}")
        log("${map.getValue("a")} ${map.getValue("b")} ${map.getValue("c")} ${map.getValue("d")}")
        // 源码实现也非常简单，当返回值为 null 时，返回设置的默认值。

        // !!! 但是这种写法和 plus 操作符在一起用，有一个 bug ，看一下下面这个例子。
        val newMap = map + mapOf("java" to 88)
        log(newMap.getValue("other")) // 调用 getValue 时抛出异常，异常信息：Key other is missing in the map.

        // 这段代码的意思就是，通过 plus(+) 操作符合并两个 map，返回一个新的 map, 但是忽略了默认值，所以看到上面的错误信息，我们在开发的时候需要注意这点。
        // 原因是：default 其实是依赖于 MapWithDefaultImpl ，使用 plus 之后 map 对象就变成了 LinkedHashMap ,可以打 log 或者 源码

    }

    fun runCheck() {
        val age = -1;

        // 使用 require 去检查
        require(age > 0) { "age must be negative" }

        // 使用 checkNotNull 检查
        val name: String? = null
        checkNotNull(name) {
            "name must not be null"
        }
    }

    /**
     *
     * 正确使用  作用域函数：run, with, let, also, apply
     *
     * 是否是扩展函数。
     * with()不是扩展函数
     *
     * 作用域函数的参数（this、it）。
     * 作用域函数的返回值（调用本身、其他类型即最后一行）。
     */
    fun runStandard() {
        val a: String? = "aaa"
        // with 普通函数，两个参数：一个对象 T ，一个对象 T 的扩展函数
        val b = with(a) {
            return@with this + "bbb"
        }
        // run 扩展函数，返回值是 传入 run 中的闭包的返回值，闭包函数是其自生的一个扩展函数，闭包函数有返回值
        val c = a.run {
            return@run a + "ccc"
        }

        // apply 扩展函数， 返回值是 其自身，闭包函数是其自生的一个扩展函数，闭包函数无返回值
        val d = a?.apply {

        }

        // let 扩展函数， 返回值是 传入 let 中的闭包的返回值，闭包函数是一个有参的函数，参数是其自身
        val e = a?.let {
            return@let 10086
        }

        // 注意run 与 let 的区别

        // also 扩展函数，返回值是其自身，闭包是一个有参函数，函数参数是其自身
        val f = a?.also {

        }

    }

    /**
     * in when
     * in 关键字其实是 contains 操作符的简写，它不是一个接口，也不是一个类型，仅仅是一个操作符，也就是说任意一个类只要重写了 contains 操作符，
     * 都可以使用 in 关键字，如果我们想要在自定义类型中检查一个值是否在列表中，只需要重写 contains() 方法即可，Collections 集合也重写了 contains 操作符。
     */
    fun runInAndWhen() {
        val const = ""
        when (const) {
            in "single" -> {
                log("single")
            }
            in listOf<String>("china", "USA") -> {
                log("list")
            }
            in mapOf<String, String>("a" to "first", "b" to "second") -> {
                log("map")
            }
            in setOf<String>("java", "kotlin") -> {
                log("set")
            }
            else -> {
                log("其它")
            }
        }
    }


    private fun log(msg: Any) {
//        Log.e(TAG, msg.toString())
        println("$TAG：$msg")
    }
}

data class Salary(var base: Int = 100) {
    override fun toString(): String = base.toString()

    // 等价于 定义一个方法，然后调用 s1.plus(s2)
//    fun plus(other: Salary): Salary {
//        return Salary(base + other.base)
//    }
}

operator fun Salary.plus(other: Salary): Salary = Salary(base + other.base)
operator fun Salary.minus(other: Salary): Salary = Salary(base - other.base)