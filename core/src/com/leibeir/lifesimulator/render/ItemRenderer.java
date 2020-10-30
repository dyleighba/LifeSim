package com.leibeir.lifesimulator.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.leibeir.lifesimulator.api.tile.ItemType;
import com.leibeir.lifesimulator.api.tile.PhysicalType;
import com.leibeir.lifesimulator.logic.SimulationRunner;
import com.leibeir.lifesimulator.logic.data.Frame;
import com.leibeir.lifesimulator.logic.data.Simulation;
import com.leibeir.lifesimulator.logic.data.Terrain;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.util.*;

public class ItemRenderer extends Renderer {
    private Model model;
    private final Map<ItemType, List<Node>> modelMapping = new HashMap<>();
    private final Simulation simulation;
    private final Terrain terrain;

    private static final String modelFile = "Items.g3db";

    public ItemRenderer(Simulation simulation) {
        this.simulation = simulation;
        terrain = simulation.terrain;
        for (ItemType itemType : ItemType.values()) {
            modelMapping.put(itemType, new ArrayList<>());
        }
        loadAsset(modelFile, Model.class);
    }

    @Override
    protected void doneAssetLoading() {
        model = assetManager.get(modelFile, Model.class);
        for (Node node : model.nodes) {
            for (ItemType itemType : ItemType.values()) {
                if (node.id.contains(itemType.name())) {
                    Gdx.app.debug("ItemRenderer", itemType.name());
                    modelMapping.get(itemType).add(node);
                }
            }
        }
        update();
    }

    @Override
    public void update() {
        modelInstances.clear();
        Random r = new Random(terrain.seed);
        Frame frame = simulation.getCurrentFrame();
        for (int x=0; x<terrain.getSize(); x++) {
            for (int z=0; z<terrain.getSize(); z++) {
                ItemType iType = frame.getItem(x, z);
                int itemMappingSize = modelMapping.get(iType).size();
                if (itemMappingSize <= 0) {
                    if (iType == ItemType.Nothing) {
                        continue;
                    }
                    else {
                        throw new ValueException(String.format("%s doesn't have any models.", iType.name()));
                    }
                }
                int chosenNode = r.nextInt(itemMappingSize);
                String nodeID = modelMapping.get(iType).get(chosenNode).id;
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
