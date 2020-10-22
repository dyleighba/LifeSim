package com.leibeir.lifesimulator.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.leibeir.lifesimulator.util.RandomColour;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.util.Random;

public class TerrainMeshRenderer extends WorldRenderer {
    public final static int chunkSize = 128;
    public final int chunkGridSize;
    public final float wallDepth = -10f;

    public TerrainMeshRenderer(World world) {
        super(world);
        if ((float)world.getSize() % chunkSize > 0) {
            throw new ValueException("In the interest of visual correctness world.size much be a multiple of chunkSize.");
        }
        chunkGridSize = (int)Math.ceil((float)world.getSize() / chunkSize);
        generateAllChunkMesh();
    }

    private void generateAllChunkMesh() {
        modelInstances.clear();
        for (int chunk_x=0;chunk_x<chunkGridSize;chunk_x++) {
            for (int chunk_z=0;chunk_z<chunkGridSize;chunk_z++) {
                modelInstances.add(createChunkMesh(chunk_x, chunk_z));
            }
        }
    }

    private ModelInstance createChunkMesh(int chunk_x, int chunk_z) {
        int attr = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates;
        Texture myTexture = new Texture("badlogic.jpg");

        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        modelBuilder.manage(myTexture); //make modelbuilder responsible for disposing the texture resource

        Random r = new Random();
        int offset_x = chunkSize * chunk_x;
        int offset_z = chunkSize * chunk_z;
        for (int x=offset_x;x<offset_x+chunkSize;x++) {
            for (int z=offset_z;z<offset_z+chunkSize;z++) {
                float[] elevations = new float[]{
                        world.getElevation(x - 1, z + 1),
                        world.getElevation(x, z + 1),
                        world.getElevation(x + 1, z + 1),
                        world.getElevation(x - 1, z),
                        world.getElevation(x, z),
                        world.getElevation(x + 1, z),
                        world.getElevation(x - 1, z - 1),
                        world.getElevation(x, z - 1),
                        world.getElevation(x + 1, z - 1)
                };

                Vector3[] corners = new Vector3[] {
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

                float elevation = elevations[4];
                Color colour = RandomColour.noisyColour(grass);
                if (world.isSand(x, z) || world.isWater(x, z)){
                    colour = RandomColour.noisyColour(sand);
                }
                if (world.isDeepWater(x, z)) {
                    colour = RandomColour.noisyColour(deepSand);
                }
                Material blockMat = new Material(ColorAttribute.createDiffuse(colour));

                modelBuilder.part(String.format("faceX%dZ%dT%d",x,z,0), GL20.GL_TRIANGLES, attr,
                        //new Material(TextureAttribute.createDiffuse(myTexture)))
                        blockMat)
                        .triangle(corners[0], corners[1], corners[2]);
                modelBuilder.part(String.format("faceX%dZ%dT%d",x,z,1), GL20.GL_TRIANGLES, attr,
                        //new Material(TextureAttribute.createDiffuse(myTexture)))
                        blockMat)
                        .triangle(corners[1], corners[3], corners[2]);
            }
        }

        return new ModelInstance(modelBuilder.end(), 0, 0, 0);
    }
}
