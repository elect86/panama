package main.glfw_

import glm_.L
import libGlfw.glfw3
import libGlfw.glfw3_h.*
import main.*
import java.foreign.memory.Pointer

enum class GlfwError(val code: Int, val description: String) {

    NONE(GLFW_NO_ERROR, ""),
    API_UNAVAILABLE(GLFW_API_UNAVAILABLE, "GLFW could not find support for the requested API on the system."),
    FORMAT_UNAVAILABLE(
        GLFW_FORMAT_UNAVAILABLE, """
        If emitted during window creation, the requested pixel format is not supported.

        If emitted when querying the clipboard, the contents of the clipboard could not be converted to the requested format."""
    ),
    INVALID_ENUM(
        GLFW_INVALID_ENUM,
        "One of the arguments to the function was an invalid enum value, for example requesting GLFW_RED_BITS with glfwGetWindowAttrib."
    ),
    INVALID_VALUE(
        GLFW_INVALID_VALUE, """
        One of the arguments to the function was an invalid value, for example requesting a non-existent OpenGL or OpenGL ES version like 2.7.

        Requesting a valid but unavailable OpenGL or OpenGL ES version will instead result in a GLFW_VERSION_UNAVAILABLE error."""
    ),
    NO_CURRENT_CONTEXT(
        GLFW_NO_CURRENT_CONTEXT,
        "This occurs if a GLFW function was called that needs and operates on the current OpenGL or OpenGL ES context but no context is current on the calling thread. One such function is glfwSwapInterval."
    ),
    NO_WINDOW_CONTEXT(
        GLFW_NO_WINDOW_CONTEXT,
        "A window that does not have an OpenGL or OpenGL ES context was passed to a function that requires it to have one."
    ),
    NOT_INITIALIZED(
        GLFW_NOT_INITIALIZED,
        "This occurs if a GLFW function was called that must not be called unless the library is initialized."
    ),
    OUT_OF_MEMORY(GLFW_OUT_OF_MEMORY, "A memory allocation failed."),
    PLATFORM_ERROR(
        GLFW_PLATFORM_ERROR,
        "A platform-specific error occurred that does not match any of the more specific categories."
    ),
    VERSION_UNAVAILABLE(
        GLFW_VERSION_UNAVAILABLE,
        "The requested OpenGL or OpenGL ES version (including any requested context or framebuffer hints) is not available on this machine."
    )
}

inline class ClientApi(val i: Int) {
    companion object {
        val GL = ClientApi(GLFW_OPENGL_API)
        val GL_ES = ClientApi(GLFW_OPENGL_ES_API)
        val NONE = ClientApi(GLFW_NO_API)
    }
}

inline class ContextCreationApi(val i: Int) {
    companion object {
        val NATIVE = ContextCreationApi(GLFW_NATIVE_CONTEXT_API)
        val EGL = ContextCreationApi(GLFW_EGL_CONTEXT_API)
    }
}

inline class OpenGlProfile(val i: Int) {
    companion object {
        val CORE = OpenGlProfile(GLFW_OPENGL_CORE_PROFILE)
        val COMPAT = OpenGlProfile(GLFW_OPENGL_CORE_PROFILE)
        val ANY = OpenGlProfile(GLFW_OPENGL_ANY_PROFILE)
    }
}

inline class ContextRobustness(val i: Int) {
    companion object {
        val LOSE_CONTEXT_ON_RESET = ContextRobustness(GLFW_LOSE_CONTEXT_ON_RESET)
        val NO_RESET_NOTIFICATION = ContextRobustness(GLFW_NO_RESET_NOTIFICATION)
        val NONE = ContextRobustness(GLFW_NO_ROBUSTNESS)
    }
}

inline class CursorStatus(val i: Int) {
    companion object {
        val NORMAL = CursorStatus(GLFW_CURSOR_NORMAL)
        val HIDDEN = CursorStatus(GLFW_CURSOR_HIDDEN)
        val DISABLED = CursorStatus(GLFW_CURSOR_HIDDEN)
    }
}

inline class Key(val i: Int) {
    companion object {
        // Printable keys. Also KP_*
        val SPACE = Key(GLFW_KEY_SPACE)
        val APOSTROPHE = Key(GLFW_KEY_APOSTROPHE)
        val COMMA = Key(GLFW_KEY_COMMA)
        val MINUS = Key(GLFW_KEY_MINUS)
        val PERIOD = Key(GLFW_KEY_PERIOD)
        val SLASH = Key(GLFW_KEY_SLASH)
        val _0 = Key(GLFW_KEY_0)
        val _1 = Key(GLFW_KEY_1)
        val _2 = Key(GLFW_KEY_2)
        val _3 = Key(GLFW_KEY_3)
        val _4 = Key(GLFW_KEY_4)
        val _5 = Key(GLFW_KEY_5)
        val _6 = Key(GLFW_KEY_6)
        val _7 = Key(GLFW_KEY_7)
        val _8 = Key(GLFW_KEY_8)
        val _9 = Key(GLFW_KEY_9)
        val SEMICOLON = Key(GLFW_KEY_SEMICOLON)
        val EQUAL = Key(GLFW_KEY_EQUAL)
        val A = Key(GLFW_KEY_A)
        val B = Key(GLFW_KEY_B)
        val C = Key(GLFW_KEY_C)
        val D = Key(GLFW_KEY_D)
        val E = Key(GLFW_KEY_E)
        val F = Key(GLFW_KEY_F)
        val G = Key(GLFW_KEY_G)
        val H = Key(GLFW_KEY_H)
        val I = Key(GLFW_KEY_I)
        val J = Key(GLFW_KEY_J)
        val K = Key(GLFW_KEY_K)
        val L = Key(GLFW_KEY_L)
        val M = Key(GLFW_KEY_M)
        val N = Key(GLFW_KEY_N)
        val O = Key(GLFW_KEY_O)
        val P = Key(GLFW_KEY_P)
        val Q = Key(GLFW_KEY_Q)
        val R = Key(GLFW_KEY_R)
        val S = Key(GLFW_KEY_S)
        val T = Key(GLFW_KEY_T)
        val U = Key(GLFW_KEY_U)
        val V = Key(GLFW_KEY_V)
        val W = Key(GLFW_KEY_W)
        val X = Key(GLFW_KEY_X)
        val Y = Key(GLFW_KEY_Y)
        val Z = Key(GLFW_KEY_Z)
        val LEFT_BRACKET = Key(GLFW_KEY_LEFT_BRACKET)
        val BACKSLASH = Key(GLFW_KEY_BACKSLASH)
        val RIGHT_BRACKET = Key(GLFW_KEY_RIGHT_BRACKET)
        val GRAVE_ACCENT = Key(GLFW_KEY_GRAVE_ACCENT)
        val WORLD_1 = Key(GLFW_KEY_WORLD_1)
        val WORLD_2 = Key(GLFW_KEY_WORLD_2)
        // Function keys.
        val ESCAPE = Key(GLFW_KEY_ESCAPE)
        val ENTER = Key(GLFW_KEY_ENTER)
        val TAB = Key(GLFW_KEY_TAB)
        val BACKSPACE = Key(GLFW_KEY_BACKSPACE)
        val INSERT = Key(GLFW_KEY_INSERT)
        val DELETE = Key(GLFW_KEY_DELETE)
        val RIGHT = Key(GLFW_KEY_RIGHT)
        val LEFT = Key(GLFW_KEY_LEFT)
        val DOWN = Key(GLFW_KEY_DOWN)
        val UP = Key(GLFW_KEY_UP)
        val PAGE_UP = Key(GLFW_KEY_PAGE_UP)
        val PAGE_DOWN = Key(GLFW_KEY_PAGE_DOWN)
        val HOME = Key(GLFW_KEY_HOME)
        val END = Key(GLFW_KEY_END)
        val CAPS_LOCK = Key(GLFW_KEY_CAPS_LOCK)
        val SCROLL_LOCK = Key(GLFW_KEY_SCROLL_LOCK)
        val NUM_LOCK = Key(GLFW_KEY_NUM_LOCK)
        val PRINT_SCREEN = Key(GLFW_KEY_PRINT_SCREEN)
        val PAUSE = Key(GLFW_KEY_PAUSE)
        val F1 = Key(GLFW_KEY_F1)
        val F2 = Key(GLFW_KEY_F2)
        val F3 = Key(GLFW_KEY_F3)
        val F4 = Key(GLFW_KEY_F4)
        val F5 = Key(GLFW_KEY_F5)
        val F6 = Key(GLFW_KEY_F6)
        val F7 = Key(GLFW_KEY_F7)
        val F8 = Key(GLFW_KEY_F8)
        val F9 = Key(GLFW_KEY_F9)
        val F10 = Key(GLFW_KEY_F10)
        val F11 = Key(GLFW_KEY_F11)
        val F12 = Key(GLFW_KEY_F12)
        val F13 = Key(GLFW_KEY_F13)
        val F14 = Key(GLFW_KEY_F14)
        val F15 = Key(GLFW_KEY_F15)
        val F16 = Key(GLFW_KEY_F16)
        val F17 = Key(GLFW_KEY_F17)
        val F18 = Key(GLFW_KEY_F18)
        val F19 = Key(GLFW_KEY_F19)
        val F20 = Key(GLFW_KEY_F20)
        val F21 = Key(GLFW_KEY_F21)
        val F22 = Key(GLFW_KEY_F22)
        val F23 = Key(GLFW_KEY_F23)
        val F24 = Key(GLFW_KEY_F24)
        val F25 = Key(GLFW_KEY_F25)
        val KP_0 = Key(GLFW_KEY_KP_0)
        val KP_1 = Key(GLFW_KEY_KP_1)
        val KP_2 = Key(GLFW_KEY_KP_2)
        val KP_3 = Key(GLFW_KEY_KP_3)
        val KP_4 = Key(GLFW_KEY_KP_4)
        val KP_5 = Key(GLFW_KEY_KP_5)
        val KP_6 = Key(GLFW_KEY_KP_6)
        val KP_7 = Key(GLFW_KEY_KP_7)
        val KP_8 = Key(GLFW_KEY_KP_8)
        val KP_9 = Key(GLFW_KEY_KP_9)
        val KP_DECIMAL = Key(GLFW_KEY_KP_DECIMAL)
        val KP_DIVIDE = Key(GLFW_KEY_KP_DIVIDE)
        val KP_MULTIPLY = Key(GLFW_KEY_KP_MULTIPLY)
        val KP_SUBTRACT = Key(GLFW_KEY_KP_SUBTRACT)
        val KP_ADD = Key(GLFW_KEY_KP_ADD)
        val KP_ENTER = Key(GLFW_KEY_KP_ENTER)
        val KP_EQUAL = Key(GLFW_KEY_KP_EQUAL)
        val LEFT_SHIFT = Key(GLFW_KEY_LEFT_SHIFT)
        val LEFT_CONTROL = Key(GLFW_KEY_LEFT_CONTROL)
        val LEFT_ALT = Key(GLFW_KEY_LEFT_ALT)
        val LEFT_SUPER = Key(GLFW_KEY_LEFT_SUPER)
        val RIGHT_SHIFT = Key(GLFW_KEY_RIGHT_SHIFT)
        val RIGHT_CONTROL = Key(GLFW_KEY_RIGHT_CONTROL)
        val RIGHT_ALT = Key(GLFW_KEY_RIGHT_ALT)
        val RIGHT_SUPER = Key(GLFW_KEY_RIGHT_SUPER)
        val MENU = Key(GLFW_KEY_MENU)
        val LAST = Key(GLFW_KEY_MENU)
    }

    fun getName(scancode: Int): String = glfwGetKeyName(i, scancode).asString

    val scancode: Int
        get() = glfwGetKeyScancode(i)
}

inline class MouseButton(val i: Int) {
    companion object {
        val _1 = MouseButton(GLFW_MOUSE_BUTTON_1)
        val _2 = MouseButton(GLFW_MOUSE_BUTTON_1)
        val _3 = MouseButton(GLFW_MOUSE_BUTTON_1)
        val _4 = MouseButton(GLFW_MOUSE_BUTTON_1)
        val _5 = MouseButton(GLFW_MOUSE_BUTTON_1)
        val _6 = MouseButton(GLFW_MOUSE_BUTTON_1)
        val _7 = MouseButton(GLFW_MOUSE_BUTTON_1)
        val _8 = MouseButton(GLFW_MOUSE_BUTTON_1)
        val LAST = MouseButton(GLFW_MOUSE_BUTTON_LAST)
        val LEFT = MouseButton(GLFW_MOUSE_BUTTON_LEFT)
        val RIGHT = MouseButton(GLFW_MOUSE_BUTTON_RIGHT)
        val MIDDLE = MouseButton(GLFW_MOUSE_BUTTON_MIDDLE)
    }
}

inline class CursorShape(val i: Int) {
    companion object {
        val ARROW_CURSOR = CursorShape(GLFW_ARROW_CURSOR)
        val CROSSHAIR_CURSOR = CursorShape(GLFW_CROSSHAIR_CURSOR)
        val HAND_CURSOR = CursorShape(GLFW_HAND_CURSOR)
        val HRESIZE_CURSOR = CursorShape(GLFW_HRESIZE_CURSOR)
        val IBEAM_CURSOR = CursorShape(GLFW_IBEAM_CURSOR)
        val VRESIZE_CURSOR = CursorShape(GLFW_VRESIZE_CURSOR)
    }
}

inline class Joystick(val i: Int) {
    companion object {
        val _1 = Joystick(GLFW_JOYSTICK_1)
        val _2 = Joystick(GLFW_JOYSTICK_2)
        val _3 = Joystick(GLFW_JOYSTICK_3)
        val _4 = Joystick(GLFW_JOYSTICK_4)
        val _5 = Joystick(GLFW_JOYSTICK_5)
        val _6 = Joystick(GLFW_JOYSTICK_6)
        val _7 = Joystick(GLFW_JOYSTICK_7)
        val _8 = Joystick(GLFW_JOYSTICK_8)
        val _9 = Joystick(GLFW_JOYSTICK_9)
        val _1GLFW_JOYSTICK_1 = Joystick(GLFW_JOYSTICK_10)
        val _11 = Joystick(GLFW_JOYSTICK_11)
        val _12 = Joystick(GLFW_JOYSTICK_12)
        val _13 = Joystick(GLFW_JOYSTICK_13)
        val _14 = Joystick(GLFW_JOYSTICK_14)
        val _15 = Joystick(GLFW_JOYSTICK_15)
        val _16 = Joystick(GLFW_JOYSTICK_16)
        val LAST = Joystick(GLFW_JOYSTICK_LAST)
    }

    val isPresent: Boolean
        get() = glfwJoystickPresent(i) == GLFW_TRUE

    val axes: FloatArray?
        get() = scopeInt { glfwGetJoystickAxes(i, it).toTypedArraySafe(it) }

    val buttons: ByteArray?
        get() = scopeInt { glfwGetJoystickButtons(i, it).toTypedArraySafe(it) }

    val hats: ByteArray?
        get() = scopeInt { glfwGetJoystickHats(i, it).toTypedArraySafe(it) }

    val name: String?
        get() = glfwGetJoystickName(i).asStringSafe

    val guid: String?
        get() = glfwGetJoystickGUID(i).asStringSafe

    var userPointer: Pointer<Void>
        get() = glfwGetJoystickUserPointer(i)
        set(value) = glfwSetJoystickUserPointer(i, value)

    val isGamepad: Boolean
        get() = glfwJoystickIsGamepad(i) == GLFW_TRUE

    val gamepadName: String?
        get() = glfwGetGamepadName(i).asStringSafe

    fun getGamepadState(state: Pointer<glfw3.GLFWgamepadstate>): GlfwError = GlfwError.values().first { it.code == glfwGetGamepadState(i, state) }
}

inline class VSync(val i: Int) {
    companion object {
        val ADAPTIVE_HALF_RATE = VSync(-2)
        val ADAPTIVE = VSync(-1)
        val OFF = VSync(0)
        val ON = VSync(1)
    }
}

inline class ReleaseBehaviour(val i: Int) {
    companion object {
        val ANY = ReleaseBehaviour(GLFW_ANY_RELEASE_BEHAVIOR)
        val FLUSH = ReleaseBehaviour(GLFW_RELEASE_BEHAVIOR_FLUSH)
        val NONE = ReleaseBehaviour(GLFW_RELEASE_BEHAVIOR_NONE)
    }

}