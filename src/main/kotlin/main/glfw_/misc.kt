package main.glfw_

import java.foreign.memory.Callback
import libGlfw.glfw3
import java.foreign.memory.Pointer

typealias GlfwErrorFun = Callback<glfw3.FI2>
typealias GlfwWindowPosFun = Callback<glfw3.FI3>
typealias GlfwWindowSizeFun = Callback<glfw3.FI3>
typealias GlfwWindowCloseFun = Callback<glfw3.FI4>
typealias GlfwWindowRefreshFun = Callback<glfw3.FI4>
typealias GlfwWindowFocusFun = Callback<glfw3.FI5>
typealias GlfwWindowIconifyFun = Callback<glfw3.FI5>
typealias GlfwWindowMaximizeFun = Callback<glfw3.FI5>
typealias GlfwFramebufferSizeFun = Callback<glfw3.FI3>
typealias GlfwWindowContentScaleFun = Callback<glfw3.FI6>

typealias GlfwMonitorFun = Callback<glfw3.FI13>

typealias GlfwKeyFun = Callback<glfw3.FI9>
typealias GlfwCharFun = Callback<glfw3.FI10>
typealias GlfwCharModsFun = Callback<glfw3.FI11>
typealias GlfwMouseButtonFun = Callback<glfw3.FI7>
typealias GlfwCursorPosFun = Callback<glfw3.FI8>
typealias GlfwCursorEnterFun = Callback<glfw3.FI5>
typealias GlfwScrollFun = Callback<glfw3.FI8>
typealias GlfwDropFun = Callback<glfw3.FI12>

typealias GlfwJoystickFun = Callback<glfw3.FI14>

val Pointer<Byte>.asStringSafe: String?
    get() = when {
        isNull -> null
        else -> Pointer.toString(this)
    }

val Pointer<Byte>.asString: String
    get() = Pointer.toString(this)