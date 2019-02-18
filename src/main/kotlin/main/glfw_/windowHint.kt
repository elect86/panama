package main.glfw_

import glm_.i
import libGlfw.glfw3_h.*
import main.scope

object windowHint {

    fun default() = glfwDefaultWindowHints()

    var resizable = true
        set(value) {
            glfwWindowHint(GLFW_RESIZABLE, if (value) GLFW_TRUE else GLFW_FALSE)
            field = value
        }

    var visible = true
        set(value) {
            glfwWindowHint(GLFW_VISIBLE, if (value) GLFW_TRUE else GLFW_FALSE)
            field = value
        }

    var decorated = true
        set(value) {
            glfwWindowHint(GLFW_DECORATED, if (value) GLFW_TRUE else GLFW_FALSE)
            field = value
        }

    var focused = true
        set(value) {
            glfwWindowHint(GLFW_FOCUSED, if (value) GLFW_TRUE else GLFW_FALSE)
            field = value
        }

    var autoIconify = true
        set(value) {
            glfwWindowHint(GLFW_AUTO_ICONIFY, if (value) GLFW_TRUE else GLFW_FALSE)
            field = value
        }

    var floating = false
        set(value) {
            glfwWindowHint(GLFW_FLOATING, if (value) GLFW_TRUE else GLFW_FALSE)
            field = value
        }

    var maximized = false
        set(value) {
            glfwWindowHint(GLFW_FLOATING, if (value) GLFW_TRUE else GLFW_FALSE)
            field = value
        }

    var centerCursor = true
        set(value) {
            glfwWindowHint(GLFW_CENTER_CURSOR, if (value) GLFW_TRUE else GLFW_FALSE)
            field = value
        }

    var transparentFramebuffer = false
        set(value) {
            glfwWindowHint(GLFW_TRANSPARENT_FRAMEBUFFER, if (value) GLFW_TRUE else GLFW_FALSE)
            field = value
        }

    var focusOnShow = true
        set(value) {
            glfwWindowHint(GLFW_FOCUS_ON_SHOW, if (value) GLFW_TRUE else GLFW_FALSE)
            field = value
        }

    var scaleToMonitor = false
        set(value) {
            glfwWindowHint(GLFW_SCALE_TO_MONITOR, if (value) GLFW_TRUE else GLFW_FALSE)
            field = value
        }

    var redBits = 8
        set(value) {
            glfwWindowHint(GLFW_RED_BITS, value)
            field = value
        }

    var greenBits = 8
        set(value) {
            glfwWindowHint(GLFW_GREEN_BITS, value)
            field = value
        }

    var blueBits = 8
        set(value) {
            glfwWindowHint(GLFW_BLUE_BITS, value)
            field = value
        }

    var alphaBits = 8
        set(value) {
            glfwWindowHint(GLFW_ALPHA_BITS, value)
            field = value
        }

    var depthBits = 24
        set(value) {
            glfwWindowHint(GLFW_DEPTH_BITS, value)
            field = value
        }

    var stencilBits = 8
        set(value) {
            glfwWindowHint(GLFW_STENCIL_BITS, value)
            field = value
        }

    var accumRedBits = 0
        set(value) {
            glfwWindowHint(GLFW_ACCUM_RED_BITS, value)
            field = value
        }

    var accumGreenBits = 0
        set(value) {
            glfwWindowHint(GLFW_ACCUM_GREEN_BITS, value)
            field = value
        }

    var accumBlueBits = 0
        set(value) {
            glfwWindowHint(GLFW_ACCUM_BLUE_BITS, value)
            field = value
        }

    var accumAlphaBits = 0
        set(value) {
            glfwWindowHint(GLFW_ACCUM_ALPHA_BITS, value)
            field = value
        }

    var auxBuffers = 0
        set(value) {
            glfwWindowHint(GLFW_AUX_BUFFERS, value)
            field = value
        }

    var samples = 0
        set(value) {
            glfwWindowHint(GLFW_SAMPLES, value)
            field = value
        }

    var refreshRate = GLFW_DONT_CARE
        set(value) {
            glfwWindowHint(GLFW_REFRESH_RATE, value)
            field = value
        }

    var stereo = false
        set(value) {
            glfwWindowHint(GLFW_STEREO, if (value) GLFW_TRUE else GLFW_FALSE)
            field = value
        }

    var srgb = false
        set(value) {
            glfwWindowHint(GLFW_SRGB_CAPABLE, if (value) GLFW_TRUE else GLFW_FALSE)
            field = value
        }

    var doubleBuffer = true
        set(value) {
            glfwWindowHint(GLFW_DOUBLEBUFFER, if (value) GLFW_TRUE else GLFW_FALSE)
            field = value
        }

    var api = ClientApi.GL
        set(value) {
            glfwWindowHint(GLFW_CLIENT_API, value.i)
            field = value
        }

    var contextCreationApi = ContextCreationApi.NATIVE
        set(value) {
            glfwWindowHint(GLFW_CONTEXT_CREATION_API, value.i)
            field = value
        }

    var contextVersion = 10
        set(value) {
            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, value / 10)
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, value % 10)
            field = value
        }

    var contextVersionMajor = 1
        set(value) {
            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, value)
            field = value
        }

    var contextVersionMinor = 0
        set(value) {
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, value)
            field = value
        }


    var robustness = ContextRobustness.NONE
        set(value) {
            glfwWindowHint(GLFW_CONTEXT_ROBUSTNESS, value.i)
            field = value
        }



    var releaseBehaviour = ReleaseBehaviour.ANY
        set(value) {
            glfwWindowHint(GLFW_CONTEXT_RELEASE_BEHAVIOR, value.i)
            field = value
        }

    var openglForwardComp = false
        set(value) {
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, if (value) GLFW_TRUE else GLFW_FALSE)
            field = value
        }

    var openglDebugContext = false
        set(value) {
            glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, if (value) GLFW_TRUE else GLFW_FALSE)
            field = value
        }

    var openglProfile = OpenGlProfile.ANY
        set(value) {
            glfwWindowHint(GLFW_OPENGL_PROFILE, value.i)
            field = value
        }

    var cocoaRetinaFramebuffer = true
        set(value) {
            glfwWindowHint(GLFW_COCOA_RETINA_FRAMEBUFFER, if (value) GLFW_TRUE else GLFW_FALSE)
            field = value
        }

    var cocoaFrameName = ""
        set(value) = scope(value) {
            glfwWindowHintString(GLFW_COCOA_FRAME_NAME, it)
        }

    var cocoaGraphicsSwitching = false
        set(value) {
            glfwWindowHint(GLFW_COCOA_GRAPHICS_SWITCHING, if (value) GLFW_TRUE else GLFW_FALSE)
            field = value
        }

    var x11_className = ""
        set(value) = scope(value) {
            glfwWindowHintString(GLFW_X11_CLASS_NAME, it)
        }

    var x11_instanceName = ""
        set(value) = scope(value) {
            glfwWindowHintString(GLFW_X11_INSTANCE_NAME, it)
        }

    inline operator fun invoke(block: windowHint.() -> Unit) = block()
}