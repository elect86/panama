import java.foreign.NativeTypes;
import java.foreign.Scope;
import java.foreign.memory.Array;
import java.foreign.memory.Pointer;

import static opengl.gl_h.*;
import static opengl.freeglut_std_h.*;

public class TeapotJava {

    float rot = 0;

    TeapotJava(Scope sc) {
        // Misc Parameters
        Array<Float> pos = sc.allocateArray(NativeTypes.FLOAT, new float[] {0.0f, 15.0f, -15.0f, 0});
        Array<Float> spec = sc.allocateArray(NativeTypes.FLOAT, new float[] {1, 1, 1, 0});
        Array<Float> shini = sc.allocateArray(NativeTypes.FLOAT, new float[] {113});

        // Reset Background
        glClearColor(0, 0, 0, 0);

        // Setup Lighting
        glShadeModel(GL_SMOOTH);
        glLightfv(GL_LIGHT0, GL_POSITION, pos.elementPointer());
        glLightfv(GL_LIGHT0, GL_AMBIENT, spec.elementPointer());
        glLightfv(GL_LIGHT0, GL_DIFFUSE, spec.elementPointer());
        glLightfv(GL_LIGHT0, GL_SPECULAR, spec.elementPointer());
        glMaterialfv(GL_FRONT, GL_SHININESS, shini.elementPointer());
        glEnable(GL_LIGHTING);
        glEnable(GL_LIGHT0);
        glEnable(GL_DEPTH_TEST);
    }

    void display() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glPushMatrix();
        glRotatef(-20, 1, 1, 0);
        glRotatef(rot, 0, 1, 0);
        glutSolidTeapot(0.5);
        glPopMatrix();
        glutSwapBuffers();
    }

    void onIdle() {
        rot += 0.1;
        glutPostRedisplay();
    }

    public static void main(String[] args) {
        try (Scope sc = Scope.globalScope()) {
            Pointer<Integer> argc = sc.allocate(NativeTypes.INT32);
            argc.set(0);
            glutInit(argc, Pointer.ofNull());
            glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGBA | GLUT_DEPTH);
            glutInitWindowSize(900, 900);
            glutCreateWindow(sc.allocateCString("Hello Panama!"));
            TeapotJava teapot = new TeapotJava(sc);
            glutDisplayFunc(sc.allocateCallback(teapot::display));
            glutIdleFunc(sc.allocateCallback(teapot::onIdle));
            glutMainLoop();
        }
    }
}