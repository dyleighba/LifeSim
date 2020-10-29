package com.leibeir.lifesimulator.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.leibeir.lifesimulator.api.tile.TileType;
import com.leibeir.lifesimulator.logic.data.Terrain;
import com.leibeir.lifesimulator.logic.terrain.TerrainGeneration;
import com.leibeir.lifesimulator.util.RandomColour;
import com.leibeir.lifesimulator.world.World;

import java.util.Random;

public class TerrainMeshRenderer extends Renderer {
    public final float wallDepth = -10f;

    private final Terrain terrain;

    public TerrainMeshRenderer(Terrain terrain) {
        this.terrain = terrain;
        update();
    }

    private Vector3[] getAveragedCorners(int x, int z) {
        float[] elevations = new float[]{
                terrain.getElevation(x - 1, z + 1),
                terrain.getElevation(x, z + 1),
                terrain.getElevation(x + 1, z + 1),
                terrain.getElevation(x - 1, z),
                terrain.getElevation(x, z),
                terrain.getElevation(x + 1, z),
                terrain.getElevation(x - 1, z - 1),
                terrain.getElevation(x, z - 1),
                terrain.getElevation(x + 1, z - 1)
        };

        return new Vector3[] {
                new Vector3(
                        x-0.5f,
                        (elevations[0] + elevations[1] + elevations[3] + elevations[4]) / 4,
                        z+0.5f),
                new Vector3(
                        x+0.5f,
                        (elevations[1] + elevations[2] + elevations[4] + elevations[5]) / 4,
                        z+0.5f),
                new Vector3(
                        x-0.5f,
                        (elevations[3] + elevations[4] + elevations[6] + elevations[7]) / 4,
                        z-0.5f),
                new Vector3(
                        x+0.5f,
                        (elevations[4] + elevations[5] + elevations[7] + elevations[8]) / 4,
                        z-0.5f)
        };
    }

    private Vector3 calcFlatNormal(Vector3 p1, Vector3 p2, Vector3 p3) {
        // u = p3 - p1
        float ux = p3.x - p1.x;
        float uy = p3.y - p1.y;
        float uz = p3.z - p1.z;

        // v = p2 - p1
        float vx = p2.x - p1.x;
        float vy = p2.y - p1.y;
        float vz = p2.z - p1.z;

        // n = cross(v, u)
        float nx = ((vy * uz) - (vz * uy));
        float ny = ((vz * ux) - (vx * uz));
        float nz = ((vx * uy) - (vy * ux));

        // // normalize(n)
        float num2 = ((nx * nx) + (ny * ny)) + (nz * nz);
        float num = 1f / (float) Math.sqrt(num2);
        nx *= num;
        ny *= num;
        nz *= num;

        return new Vector3(nx, ny, nz);
    }

    private MeshPartBuilder.VertexInfo[] generateTriangleInfo(Vector3 p1, Vector3 p2, Vector3 p3) {
        MeshPartBuilder.VertexInfo v1 = new MeshPartBuilder.VertexInfo();
        MeshPartBuilder.VertexInfo v2 = new MeshPartBuilder.VertexInfo();
        MeshPartBuilder.VertexInfo v3 = new MeshPartBuilder.VertexInfo();
        v1.setPos(p1);
        v2.setPos(p2);
        v3.setPos(p3);
        Vector3 normal = calcFlatNormal(p1, p2, p3);
        v1.setNor(normal);
        v2.setNor(normal);
        v3.setNor(normal);
        return new MeshPartBuilder.VertexInfo[] {v1, v2, v3};
    }

    @Override
    public void update() {
        modelInstances.clear();

        int attr = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates;
        Texture myTexture = new Texture("badlogic.jpg");

        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        modelBuilder.manage(myTexture); // make modelbuilder responsible for disposing the texture resource

        Random r = new Random();
        Vector3[] worldCorners = new Vector3[] {
                getAveragedCorners(0, terrain.getSize()-1)[0],
                getAveragedCorners(terrain.getSize()-1, terrain.getSize()-1)[1],
                getAveragedCorners(0, 0)[2],
                getAveragedCorners(terrain.getSize()-1, 0)[3],
                new Vector3(-0.5f, wallDepth, terrain.getSize()-0.5f),
                new Vector3(terrain.getSize()-0.5f, wallDepth, terrain.getSize()-0.5f),
                new Vector3(-0.5f, wallDepth, -0.5f),
                new Vector3(terrain.getSize()-0.5f, wallDepth, -0.5f)
        };
        Material edgeMat = new Material(ColorAttribute.createDiffuse(TileColour.get(TileType.Dirt)));
        // Fill triangles
        // TODO perhaps merge all these MeshPartBuilders into one. May or may not make the mesh more performant?
        MeshPartBuilder.VertexInfo[] vertexInfo;
        // x: 0
        vertexInfo = generateTriangleInfo(
                worldCorners[6], worldCorners[4], worldCorners[0]);
        modelBuilder.part("edgeXMinFill", GL20.GL_TRIANGLES, attr, edgeMat)
                .triangle(vertexInfo[0], vertexInfo[1], vertexInfo[2]);
        // x: world.getSize() - 1
        vertexInfo = generateTriangleInfo(
                worldCorners[3], worldCorners[5], worldCorners[7]);
        modelBuilder.part("edgeXMaxFill", GL20.GL_TRIANGLES, attr, edgeMat)
                .triangle(vertexInfo[0], vertexInfo[1], vertexInfo[2]);
        // z: 0
        vertexInfo = generateTriangleInfo(
                worldCorners[7], worldCorners[6], worldCorners[2]);
        modelBuilder.part("edgeZMinFill", GL20.GL_TRIANGLES, attr, edgeMat)
                .triangle(vertexInfo[0], vertexInfo[1], vertexInfo[2]);
        // z: world.getSize() - 1
        vertexInfo = generateTriangleInfo(
                worldCorners[1], worldCorners[4], worldCorners[5]);
        modelBuilder.part("edgeZMaxFill", GL20.GL_TRIANGLES, attr, edgeMat)
                .triangle(vertexInfo[0], vertexInfo[1], vertexInfo[2]);

        for (int x=0; x < terrain.getSize(); x++) {
            for (int z=0; z < terrain.getSize(); z++) {

                Vector3[] corners = getAveragedCorners(x, z);
                TileType type = terrain.getType(x, z);
                if (type == TileType.Water) type = TerrainGeneration.SHORE_TYPE;
                if (type == TileType.DeepWater) type = TileType.Dirt;
                Color colour = RandomColour.noisyColour(TileColour.get(type));

                Material blockMat = new Material(ColorAttribute.createDiffuse(colour));
                modelBuilder.part(String.format("faceX%dZ%dT%d",x,z,0), GL20.GL_TRIANGLES, attr, blockMat)
                        .triangle(corners[0], corners[1], corners[2]);
                modelBuilder.part(String.format("faceX%dZ%dT%d",x,z,1), GL20.GL_TRIANGLES, attr, blockMat)
                        .triangle(corners[1], corners[3], corners[2]);

                // Create edge
                if (x == 0) {
                    vertexInfo = generateTriangleInfo(
                            worldCorners[6], corners[0], corners[2]);
                    modelBuilder.part(String.format("edgeXMinZ%d",z), GL20.GL_TRIANGLES, attr, edgeMat)
                            .triangle(vertexInfo[0], vertexInfo[1], vertexInfo[2]);
                }
                else if (x == terrain.getSize()-1) {
                    vertexInfo = generateTriangleInfo(
                            worldCorners[5], corners[3], corners[1]);
                    modelBuilder.part(String.format("edgeXMaxZ%d",z), GL20.GL_TRIANGLES, attr, edgeMat)
                            .triangle(vertexInfo[0], vertexInfo[1], vertexInfo[2]);
                }
                if (z == 0) {
                    vertexInfo = generateTriangleInfo(
                            worldCorners[7], corners[2], corners[3]);
                    modelBuilder.part(String.format("edgeZMinX%d",x), GL20.GL_TRIANGLES, attr, edgeMat)
                            .triangle(vertexInfo[0], vertexInfo[1], vertexInfo[2]);
                }
                else if (z == terrain.getSize()-1) {
                    vertexInfo = generateTriangleInfo(
                            worldCorners[4], corners[1], corners[0]);
                    modelBuilder.part(String.format("edgeZMaxX%d",x), GL20.GL_TRIANGLES, attr, edgeMat)
                            .triangle(vertexInfo[0], vertexInfo[1], vertexInfo[2]);
                }
            }
        }

        modelInstances.add(new ModelInstance(modelBuilder.end(), 0, 0, 0));
    }
}
