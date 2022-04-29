package com.nttn.pkot.view

import android.content.Context
import android.content.Intent
import com.nttn.baselib.base.BaseVBActivity
import com.nttn.baselib.base.BaseViewModel
import com.nttn.pkot.PrimaryActivityBinding
import com.nttn.pkot.R
import com.nttn.pkot.view.feature.MaterialDesignActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.random.Random

@ExperimentalCoroutinesApi
class PrimaryActivity : BaseVBActivity<PrimaryActivityBinding, BaseViewModel>() {

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, PrimaryActivity::class.java))
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_primary

    override fun initView() {
        setSupportActionBar(mBinding.titleLayout.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mBinding.btnPrimary.setOnClickListener {
            startActivity(Intent(this, MaterialDesignActivity::class.java))
        }

        printDivider("函数与返回值") {
            val random = Random
            val a = random.nextInt(100)
            val b = random.nextInt(100)
            val c = random.nextInt(100)

            println("sum1: $a + $b = ${sum1(a, b)}")
            println("sum2: $a + $b = ${sum2(a, b)}")
            println("sum3: $a + $b + $c = ${sum3(a)(b)(c)}")
        }

        val getBook = { name: String -> Book(name) }
        val bookNames = listOf(
            Book("Android"),
            Book("Kotlin"),
            Book("Java").also { it.page = 10 }
        ).map { it.name }

        printDivider("匿名函数") {
            println(getBook("Thinking in JAVA").name)
            println(bookNames)
            println("------------- ↑↑↑↑ -------------------")

            println("------------- ↓↓↓↓ -------------------")
            filterCustom(bookNames, fun(name: String): Boolean {
                return name.contains("Android")
            })
            //还可以简写
            filterCustom(bookNames) { name -> name.contains("Android") }
        }


        printDivider("Function类型") {
            listOf(1, 2, 3).forEach { justPrint(it)() }
            listOf(1, 2, 3).forEach { justPrint(it + 3).invoke() }
        }


        printDivider("中缀表达式") {
            val xm = Person("小明")
            bookNames.forEach { xm learn it }
        }

        printDivider("面向对象") {
            val bird = Bird(1.0, 2, "blue")
            println("bird's weight = ${bird.weight}, age = ${bird.age}, color = ${bird.color}, sex = ${bird.sex}")
            bird.printSex2()
        }
    }

    class Book(var name: String) {
        var page = 0
    }

    class Person(private var name: String) {
        infix fun learn(book: String) {
            println("$name learn $book.")
        }
    }

    class Bird(val weight: Double, age: Int, color: String) {
        val age: Int = age
        val color: String
        init {
            this.color = color
        }

        init {
            println("weight = $weight")
        }

        val sex: String by lazy {
            if (color == "yellow") "male" else "female"
        }

        lateinit var sex2: String

        fun printSex2() {
            sex2 = if (color == "yellow") "male" else "female"
            println("sex2 = $sex2")
        }
    }


    private fun printDivider(title: String?, test:() -> Unit) {
        println("------------- ↓↓$title↓↓ -------------------")
        test()
        println("------------- ↑↑$title↑↑ -------------------")
    }

    /**
     * 函数声明
     *
     * @param x 参数
     * @param y 参数
     * @return sum Int
     */
    private fun sum1(x: Int, y: Int): Int {
        return x + y
    }

    /**
     * 函数声明：
     * - 声明了参数类型，函数返回类型可以省略
     * - 返回类型支持推导，{}可以省略
     *
     * @param x 参数
     * @param y 参数
     * @return sum Int
     */
    private fun sum2(x: Int, y: Int) = x + y

    /**
     * 柯里化风格，简化多元函数参数
     */
    private fun sum3(x: Int) = { y: Int ->
        { z: Int ->
            x + y + z
        }
    }

    /**
     * 匿名函数
     */
    private fun filterCustom(bookNames: List<String>, contains: (name: String) -> Boolean) {
        bookNames.forEach { println("bookNames contains $it: ${contains(it)}") }
    }

    private fun justPrint(i : Int) : () -> Unit = { println(i) }
}