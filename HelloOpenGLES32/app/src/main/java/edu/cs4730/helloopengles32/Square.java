/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.cs4730.helloopengles32;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES32;

/**
 * A two-dimensional square for use as a drawn object in OpenGL ES 2.0.
 */
public class Square {

    private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
            "attribute vec4 vPosition;" +
            "void main() {" +
            // The matrix must be included as a modifier of gl_Position.
            // Note that the uMVPMatrix factor *must be first* in order
            // for the matrix multiplication product to be correct.
            "  gl_Position = uMVPMatrix * vPosition;" +
            //" gl_Position =vec4(vPosition.x,vPosition.y,0.,1.); " +
            "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
//            "uniform vec4 vColor;" +
            "void main() {" +
//            "  gl_FragColor = vColor;" +
            "  gl_FragColor = vec4(0.0f, 1.0f, 0.0f, 1.0f);" +
            "}";

    private final String geometryShaderCode =
//                    "#version 320 es" +
//                    "layout (triangles) in;" +
//                    "layout (triangle_strip, max_vertices=3) out;" +
                    "void main(){" +
//                    "    gl_Position = gl_in[0].gl_Position; " +
//                    "    EmitVertex();" +
//                    "    EndPrimitive();" +
//            "   gl_Position=vec4(position,1.0);" +
                        "int i;" +
                        "for (i = 0; i < gl_in.length(); i++){" +
                                "gl_Position = vec4(0.0); " +
                        "}" +
                    "}";

//    private final String vertexShaderCode =
//            // This matrix member variable provides a hook to manipulate
//            // the coordinates of the objects that use this vertex shader
//            "uniform mat4 uMVPMatrix;" +
//            "attribute vec4 vPosition;" +
//            "varying vec2 vUv;" +
//            "void main() {" +
//            // the matrix must be included as a modifier of gl_Position
//            // Note that the uMVPMatrix factor *must be first* in order
//            // for the matrix multiplication product to be correct.
//            "  vUv = (uMVPMatrix * vPosition).xy/4.;" +
//            "  gl_Position = uMVPMatrix * vPosition;" +
//            "}";
//
//    private final String fragmentShaderCode =
//                    "// Title:\n" +
//                    "\n" +
//                    "#ifdef GL_ES\n" +
//                    "precision highp float;\n" +
//                    "#endif\n" +
//                    "\n" +
//                    "uniform float u_time;\n" +
//                    "#define speed 0.4\n" +
//                    "#define baseRadius 0.4\n" +
//                    "#define colorVariation 0.6\n" +
//                    "#define brightnessVariation 0.3\n" +
//                    "#define backgroundColor vec3(0.,0.,0.)\n" +
//                    "#define variation 8.\n" +
//                    "#define resolution vec2(3.,3.)\n" +
//                    "#define time u_time\n" +
//                    "\n" +
//                    "varying vec2 vUv;\n" +
//                    "\n" +
//                    "vec3 n(vec2 x, float t) {\n" +
//                    "    vec3 v = floor(vec3(x, t));\n" +
//                    "    vec3 u = vec3(mod(v.xy, variation), v.z);\n" +
//                    "    vec3 c = fract( u.xyz * (\n" +
//                    "        vec3(0.16462, 0.84787, 0.98273) +\n" +
//                    "        u.xyz * vec3(0.24808, 0.75905, 0.13898) +\n" +
//                    "        u.yzx * vec3(0.31517, 0.62703, 0.26063) +\n" +
//                    "        u.zxy * vec3(0.47127, 0.58568, 0.37244)\n" +
//                    "    ) + u.yzx * (\n" +
//                    "        vec3(0.35425, 0.65187, 0.12423) +\n" +
//                    "        u.yzx * vec3(0.95238, 0.93187, 0.95213) +\n" +
//                    "        u.zxy * vec3(0.31526, 0.62512, 0.71837)\n" +
//                    "    ) + u.zxy * (\n" +
//                    "        vec3(0.95213, 0.13841, 0.16479) +\n" +
//                    "        u.zxy * vec3(0.47626, 0.69257, 0.19738)\n" +
//                    "    ) );\n" +
//                    "    return v + c;\n" +
//                    "}\n" +
//                    "\n" +
//                    "// Generate a predictably random color based on an input coord\n" +
//                    "vec3 col(vec2 x, float t) {\n" +
//                    "    return vec3(\n" +
//                    "        0.5 + max( brightnessVariation * cos( x.y * x.x ), 0.0 )\n" +
//                    "    ) + clamp(\n" +
//                    "        colorVariation * cos(fract(vec3(x, t)) * 371.0241),\n" +
//                    "        vec3( -0.4 ),\n" +
//                    "        vec3( 1.0 )\n" +
//                    "    );\n" +
//                    "}\n" +
//                    "\n" +
//                    "vec2 idx(vec2 x) {\n" +
//                    "    return floor(fract(x * 29.0) * 3.0) - vec2(1.0);\n" +
//                    "}\n" +
//                    "\n" +
//                    "float circle(vec2 x, vec2 c, float r) {\n" +
//                    "    return max(0.0, 1.0 - dot(x - c, x - c) / (r * r));\n" +
//                    "}\n" +
//                    "\n" +
//                    "void main() {\n" +
//                    "    \n" +
//                    "    vec2 x = vec2(vUv.x,vUv.y*1.77) * resolution;\n" +
//                    "    float t = time * speed;\n" +
//                    "    vec4 c = vec4(vec3(0.0), 0.1);\n" +
//                    "    \n" +
//                    "    for (int N = 0; N < 3; N++) {\n" +
//                    "        for (int k = -1; k <= 0; k++) {\n" +
//                    "            for (int i = -1; i <= 1; i++) {\n" +
//                    "                for (int j = -1; j <= 1; j++) {\n" +
//                    "                    vec2 X = x + vec2(j, i);\n" +
//                    "                    float t = t + float(N) * 38.0;\n" +
//                    "                    float T = t + float(k);\n" +
//                    "                    vec3 a = n(X, T);\n" +
//                    "                    vec2 o = idx(a.xy);\n" +
//                    "                    vec3 b = n(X + o, T + 1.0);\n" +
//                    "                    vec2 m = mix(a.xy, b.xy, (t - a.z) / (b.z - a.z));\n" +
//                    "                    float r = baseRadius * sin(3.1415927 * clamp((t - a.z) / (b.z - a.z), 0.0, 1.0));\n" +
//                    "                    if (length(a.xy - b.xy) / (b.z - a.z) > 2.0) {\n" +
//                    "                        r = 0.0;\n" +
//                    "                    }\n" +
//                    "                    c += vec4(col(a.xy, a.z), 1.0) * circle(x, m, r);\n" +
//                    "                }\n" +
//                    "            }\n" +
//                    "        }\n" +
//                    "    }\n" +
//                    "    \n" +
//                    "    gl_FragColor = vec4(c.rgb / max(1e-5, c.w) + backgroundColor, 1.0);\n" +
//                    "    \n" +
//                    "}";




    private final FloatBuffer vertexBuffer;
    private final ShortBuffer drawListBuffer;
    private final int mProgram;
    private int mPositionHandle;
//    private int mColorHandle;
    private int mMVPMatrixHandle;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
//    static float squareCoords[] = {
//            -0.5f,  0.5f, 0.0f,   // top left
//            -0.5f, -0.5f, 0.0f,   // bottom left
//             0.5f, -0.5f, 0.0f,   // bottom right
//             0.5f,  0.5f, 0.0f }; // top right

    static float squareCoords[] = {
            -0.25f,  0.25f, 0.0f,   // top left
            -0.25f, -0.25f, 0.0f,   // bottom left
            0.25f, -0.25f, 0.0f,   // bottom right
            0.25f,  0.25f, 0.0f }; // top right

    private final short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

//    float color[] = { 0.2f, 0.709803922f, 0.898039216f, 1.0f };

    /**
     * Sets up the drawing object data for use in an OpenGL ES context.
     */
    public Square() {
        // initialize vertex byte buffer for shape coordinates
        //Vertx Bufer Object
        ByteBuffer bb = ByteBuffer.allocateDirect(
        // (# of coordinate values * 4 bytes per float)
                squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

        // prepare shaders and OpenGL program
        int vertexShader = MyGLRenderer.loadShader(
                GLES32.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(
                GLES32.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        int geometryShader = MyGLRenderer.loadShader(
                GLES32.GL_GEOMETRY_SHADER,
                geometryShaderCode);

//        GLES32.glGetShaderiv(geometryShader, GLES32.GL_COMPILE_STATUS, success, 0);
//        if(success[0] == 0)
//            Log.v(Debug.shaderHelper, "Geometry Shader Compile Failed: " + glGetShaderInfoLog(geometryShader));

        mProgram = GLES32.glCreateProgram();             // create empty OpenGL Program
        GLES32.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        //GLES32.glAttachShader(mProgram, geometryShader);   // add the geometry shader to program
        GLES32.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES32.glLinkProgram(mProgram);                  // create OpenGL program executables
    }

    /**
     * Encapsulates the OpenGL ES instructions for drawing this shape.
     *
     * @param mvpMatrix - The Model View Project matrix in which to draw
     * this shape.
     */
    final long START_TIME = System.currentTimeMillis();
    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL environment
        GLES32.glUseProgram(mProgram);

        //Vertex Array Object - get handle to vertex shader's vPosition member
        mPositionHandle = GLES32.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES32.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES32.glVertexAttribPointer(
                mPositionHandle, COORDS_PER_VERTEX,
                GLES32.GL_FLOAT, false,
                vertexStride, vertexBuffer);

//        // get handle to fragment shader's vColor member
//        mColorHandle = GLES32.glGetUniformLocation(mProgram, "vColor");
//
//        // Set color for drawing the triangle
//        GLES32.glUniform4fv(mColorHandle, 1, color, 0);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES32.glGetUniformLocation(mProgram, "uMVPMatrix");
        MyGLRenderer.checkGlError("glGetUniformLocation");

        // Apply the projection and view transformation
        GLES32.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        MyGLRenderer.checkGlError("glUniformMatrix4fv");


        float time = ((float) (System.currentTimeMillis() - START_TIME)) / 1000.0f;
        int iGlobalTimeLocation = GLES32.glGetUniformLocation(mProgram, "u_time");
        GLES32.glUniform1f(iGlobalTimeLocation, time);

        // Draw the square
        //GLES32.glDrawArrays(GLES32.GL_TRIANGLES,0,4);
        GLES32.glDrawElements(
                GLES32.GL_TRIANGLES, drawOrder.length,
                GLES32.GL_UNSIGNED_SHORT, drawListBuffer);

        // Disable vertex array
        //GLES32.glDisableVertexAttribArray(mPositionHandle);
    }

}