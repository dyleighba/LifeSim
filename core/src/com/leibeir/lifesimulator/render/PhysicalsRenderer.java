package com.leibeir.lifesimulator.render;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.leibeir.lifesimulator.api.tile.PhysicalType;
import com.leibeir.lifesimulator.logic.data.Terrain;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.util.*;

public class PhysicalsRenderer extends Renderer {
    private Model model;
    private final Map<PhysicalType, List<Node>> modelMapping = new HashMap<>();
    private final Terrain terrain;

    private static final String modelFile = "AppReady.g3db";

    public PhysicalsRenderer(Terrain terrain) {
        this.terrain = terrain;
        for (PhysicalType physicalType : PhysicalType.values()) {
            modelMapping.put(physicalType, new ArrayList<>());
        }
        loadAsset(modelFile, Model.class);
    }

    @Override
    protected void doneAssetLoading() {
        model = assetManager.get(modelFile, Model.class);
        for (Node node : model.nodes) {
            for (PhysicalType physicalType : PhysicalType.values()) {
                if (node.id.contains(physicalType.name())) {
                    modelMapping.get(physicalType).add(node);
                }
            }
        }
        update();
    }

    @Override
    public void update() {
        Random r = new Random(terrain.seed);

        for (int x=0; x<terrain.getSize(); x++) {
            for (int z=0; z<terrain.getSize(); z++) {
                PhysicalType pType = terrain.getPhysicalType(x, z);
                int physicalMappingSize = modelMapping.get(pType).size();
                if (physicalMappingSize <= 0) {
                    if (pType == PhysicalType.Nothing) {
                        continue;
                    }
                    else {
                        throw new ValueException(String.format("%s doesn't have any models.", pType.name()));
                    }
                }
                int chosenNode = r.nextInt(physicalMappingSize);
                String nodeID = modelMapping.get(pType).get(chosenNode).id;
                ModelInstance instance = new ModelInstance(model, nodeID);

                instance.transform.setToTranslation(
                            x, terrain.getElevation(x, z)-0.1f, z);
                modelInstances.add(instance);
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        model.dispose();
    }
}
