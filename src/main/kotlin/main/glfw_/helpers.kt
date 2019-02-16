package main.glfw_

import glm_.L
import glm_.vec2.Vec2
import java.foreign.memory.Callback
import glm_.vec2.Vec2i
import libGlfw.glfw3
import libGlfw.glfw3.*
import libGlfw.glfw3_h.*
import main.*
import org.lwjgl.glfw.GLFW.GLFW_JOYSTICK_HAT_BUTTONS
import java.foreign.memory.Pointer


inline class GlfwMonitor(val ptr: Pointer<GLFWmonitor>) {

    val pos: Vec2i
        get() = scope {
            val x = allocInt()
            val y = allocInt()
            glfwGetMonitorPos(ptr, x, y)
            Vec2i(x(), y())
        }

    val physicalSize: Vec2i
        get() = scope {
            val x = allocInt()
            val y = allocInt()
            glfwGetMonitorPhysicalSize(ptr, x, y)
            Vec2i(x(), y())
        }

    val contentScale: Vec2
        get() = scope {
            val x = allocFloat()
            val y = allocFloat()
            glfwGetMonitorContentScale(ptr, x, y)
            Vec2(x(), y())
        }

    val name: String
        get() = glfwGetMonitorName(ptr).asString

    var userPointer: Pointer<Void>
        get() = glfwGetMonitorUserPointer(ptr)
        set(value) = glfwSetMonitorUserPointer(ptr, value)

    fun setCallback(cb: GlfwMonitorFun): GlfwMonitorFun? = glfwSetMonitorCallback(cb)

    val videoModes: Array<Pointer<GLFWvidmode>>
        get() = scopeInt { glfwGetVideoModes(ptr, it).toArray(it) }

    val videoMode: Pointer<GLFWvidmode>
        get() = glfwGetVideoMode(ptr)

    fun setGamma(gamma: Float) = glfwSetGamma(ptr, gamma)

    var gammaRamp: Pointer<GLFWgammaramp>?
        get() = glfwGetGammaRamp(ptr)
        set(value) = glfwSetGammaRamp(ptr, value)
}

fun Pointer<GLFWcursor>.destroy() = ::glfwDestroyCursor

object initHint {

    var joystickHatButtons = true
        set(value) {
            glfwInitHint(GLFW_JOYSTICK_HAT_BUTTONS, if(value) GLFW_TRUE else GLFW_FALSE)
            field = value
        }

    var cocoaChdirResources = true
        set(value) {
            glfwInitHint(GLFW_COCOA_CHDIR_RESOURCES, if(value) GLFW_TRUE else GLFW_FALSE)
            field = value
        }

    var cocoaMenubar = true
        set(value) {
            glfwInitHint(GLFW_COCOA_MENUBAR, if(value) GLFW_TRUE else GLFW_FALSE)
            field = value
        }
}